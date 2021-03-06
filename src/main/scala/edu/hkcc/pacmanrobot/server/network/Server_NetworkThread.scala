package edu.hkcc.pacmanrobot.server.network

import java.net.BindException
import java.util.concurrent.ConcurrentHashMap
import java.util.function.BiConsumer

import edu.hkcc.pacmanrobot.debug.Debug
import edu.hkcc.pacmanrobot.server.config.gui.GameMonitorJFrame
import edu.hkcc.pacmanrobot.utils.Config._
import edu.hkcc.pacmanrobot.utils.Utils.random
import edu.hkcc.pacmanrobot.utils.map.{MapKey, MapUnit, ObstacleMap}
import edu.hkcc.pacmanrobot.utils.message._
import edu.hkcc.pacmanrobot.utils.network.PacmanNetwork
import edu.hkcc.pacmanrobot.utils.studentrobot.code.GameStatus
import edu.hkcc.pacmanrobot.utils.{Config, Timer}


/**
 * Created by beenotung on 4/2/2015.
 * this is java compatible lazy singleton
 */
object Server_NetworkThread {
  private var instance: Server_NetworkThread = null

  @throws(classOf[BindException])
  def getInstance(): Server_NetworkThread = {
    if (instance == null)
      Server_NetworkThread.synchronized({
        if (instance == null) {
          Debug.getInstance().printMessage("start init Server_NetworkThread")
          instance = new Server_NetworkThread()
          Debug.getInstance().printMessage("finished init Server_NetworkThread")
          instance.start()
          Debug.getInstance().printMessage("started Server_NetworkThread")
        }
      })
    instance
  }
}

@throws(classOf[BindException])
class Server_NetworkThread extends Thread {
  Debug.getInstance().printMessage("Server_NetworkThread init 0%")

  Debug.getInstance().printMessage("init DeviceInfo Manager")
  val deviceInfoManager = new DeviceInfoManager

  Debug.getInstance().printMessage("init FlashLight Manager")
  val flashLightManager = new FlashLightManager

  val robotPositions = new ConcurrentHashMap[Array[Byte], Position]()
  Debug.getInstance().printMessage("init Robot-Position Messenger-Manager")
  val robotPositionMessengerManager = new MessengerManager[RobotPosition](PORT_ROBOT_POSITION, initMessenger_func = messenger => {}, autoGet_func = {
    (macAddress, message) => {
      if (macAddress.equals(message.deviceInfo.MAC_ADDRESS))
      //update robot position
        robotPositions.put(message.deviceInfo.MAC_ADDRESS, message.position)
      else
      //response request
        response_robotPosition(macAddress, message)
    }
  })

  val controllerRobotPairManager = new ControllerRobotPairManager
  Debug.getInstance().printMessage("init Controller-Robot-Pair Messenger-Manager")
  val controllerRobotPairMessengerManager = new MessengerManager[ControllerRobotPair](PORT_CONTROLLER_ROBOT_PAIR, initMessenger_func = messenger => {}, autoGet_func = (macAddress, controllerRobotPair: ControllerRobotPair) => {
    if (controllerRobotPair.shouldSave)
      controllerRobotPairManager.setControllerRobotPair(controllerRobotPair.controller_macAddress, controllerRobotPair.robot_macAddress)
    else
      response_pair(macAddress)
  }
  )

  Debug.getInstance().printMessage("init Game-Status Messenger-Manager")
  val gameStatusMessengerManager = new MessengerManager[GameStatus](PORT_GAME_STATUS,
    initMessenger_func = { (messenger) => messenger.sendMessage(gameStatus) },
    autoGet_func = { (remoteMacAddress, gameStatus) => {
      if (GameStatus.STATE_REQUEST.equals(gameStatus.status))
        response_gameStatus(remoteMacAddress)
      else
        switchGameStatus(gameStatus)
    }
    })

  Debug.getInstance().printMessage("init MovementCommand Messenger-Manager")
  val movementCommandMessengerManager = new MessengerManager[MovementCommand](PORT_MOVEMENT_COMMAND, initMessenger_func = messenger => {}, autoGet_func = (remoteMacAddress, message) => {
    copyMovementCommand(remoteMacAddress, message)
  })

  Debug.getInstance().printMessage("init Obstacle-Map Manager")
  val obstacleMapManager = new ObstacleMapManager

  Debug.getInstance().printMessage("init Game-Status")
  val winCheckThread = new Thread() {
    var running = false

    override def start = {
      if (!running)
        try {
          super.start
        }
        catch {
          case e: IllegalThreadStateException => {
            //This is expected when restart the game (not first round)
          }
        }
    }

    override def run = {
      running = true
      while (running && Server_NetworkThread.getInstance().running) {
        // check if opposite robots are closed
        var win = false
        var lose = false
        deviceInfoManager.getDeviceInfosByDeviceType(DeviceInfo.DEVICE_TYPE_STUDENT_ROBOT).foreach(student => {
          val studentPosition = robotPositions.get(student)
          deviceInfoManager.getDeviceInfosByDeviceType(DeviceInfo.DEVICE_TYPE_ASSIGNMENT_ROBOT).foreach(assignment =>
            win &= studentPosition.distance(robotPositions.get(assignment)) <= Config.ROBOT_MIN_DISTANCE
          )
          deviceInfoManager.getDeviceInfosByDeviceType(DeviceInfo.DEVICE_TYPE_DEADLINE_ROBOT).foreach(deadlinerobot =>
            lose &= studentPosition.distance(robotPositions.get(deadlinerobot)) <= Config.ROBOT_MIN_DISTANCE
          )
        })
        // further work on more game level will use win and lose is different priority
        if (win)
          switchGameStatus(new GameStatus(GameStatus.STATE_WIN, "Student Win", DeviceInfo.DEVICE_TYPE_STUDENT_ROBOT))
        else if (lose)
          switchGameStatus(new GameStatus(GameStatus.STATE_LOSE, "Student Lose", DeviceInfo.DEVICE_TYPE_DEADLINE_ROBOT))
      }
      running = false
    }
  }

  Debug.getInstance().printMessage("Server_NetworkThread init 40%")
  var gameStatus: GameStatus = new GameStatus(GameStatus.STATE_SETUP)
  Debug.getInstance().printMessage("Server_NetworkThread init 50%")
  @volatile var running = false

  override def
  start = {
    if (!running)
      super.start
  }

  def response_gameStatus(macAddress: Array[Byte]): Unit = {
    gameStatusMessengerManager.sendByMacAddress(macAddress, gameStatus)
  }

  def response_pair(macAddress: Array[Byte]): Unit = {
    controllerRobotPairManager.controllerRobotPairs.forEach(new BiConsumer[Array[Byte], Array[Byte]] {
      override def accept(t: Array[Byte], u: Array[Byte]): Unit = {
        controllerRobotPairMessengerManager.sendByMacAddress(macAddress,
          new ControllerRobotPair(t, u, true))
      }
    })
    controllerRobotPairMessengerManager.sendByMacAddress(macAddress, null)
  }

  /*
  val obstacleMapMessengerManager = new MessengerManager[ObstacleMap](PORT_MAP, (macAddress, message) => {
    obstacleMapSubscribers.foreach(messenger =>
      if (!macAddress.equals(messenger.getRemoteMacAddress)) messenger.sendMessage(message))
    obstacleMap.merge(message)
  })*/

  def response_robotPosition(macAddress: Array[Byte], message: RobotPosition): Unit = {
    if (message.deviceInfo.MAC_ADDRESS != null) {
      //response desired target
      message.position = robotPositions.get(message.deviceInfo.MAC_ADDRESS)
      robotPositionMessengerManager.sendByMacAddress(macAddress, message)
    }
    else {
      //response desired types
      deviceInfoManager.getDeviceInfosByDeviceType(message.deviceInfo.deviceType).foreach(deviceInfo =>
        robotPositionMessengerManager.sendByMacAddress(macAddress, new RobotPosition(deviceInfo, robotPositions.get(deviceInfo.MAC_ADDRESS)))
      )
    }
  }

  def copyMovementCommand(controllerMacAddress: Array[Byte], message: MovementCommand): Unit = {
    Debug.getInstance().printMessage("copying movement command to tcp listener: " + message.point2D.toString)
    movementCommandMessengerManager.sendByMacAddress(controllerRobotPairManager.getRobot_macAddress(controllerMacAddress), message)
  }

  def switchGameStatus(newGameStatus: Byte): Unit = {
    gameStatus.status = newGameStatus
    switchGameStatus(gameStatus)
  }

  def switchGameStatus(newGameStatus: GameStatus): Unit = {
    if (GameStatus.STATE_REQUEST.equals(newGameStatus.status)) return
    this.gameStatus = newGameStatus
    gameStatusMessengerManager.foreach(messenger => messenger.sendMessage(newGameStatus))
    newGameStatus.status match {
      case GameStatus.STATE_SETUP => gameSetup
      case GameStatus.STATE_START => gameStart
      case GameStatus.STATE_PAUSE => gamePause
      case GameStatus.STATE_RESUME => gameResume
      case GameStatus.STATE_STOP => gameStop
    }
  }

  def gameResume: Unit = {
    //switchGameStatus(GameStatus.STATE_START)
    winCheckThread.start
  }

  def gamePause: Unit = {
    winCheckThread.running = false
  }

  def gameStart: Unit = {
    ObstacleMapManager.obstacleMap.clear()
    winCheckThread.start
  }

  def gameStop: Unit = {
    switchGameStatus(GameStatus.STATE_SETUP)
    winCheckThread.running = false
  }

  def gameSetup: Unit = {
    GameMonitorJFrame.getInstance().reset()
    winCheckThread.running = false

  }

  override def run = {
    println("start run server service thread")
    setup
    test
    running = true
    while (running) {
      Thread.sleep(SAVE_INTERVAL)
      save
    }
  }

  // generate noise on the map as obstacle (for debug usage)
  def test = {
    import ObstacleMapManager.obstacleMap
    val bufferedMap = new ObstacleMap
    Timer.setTimeInterval({
      if (gameStatus.status.equals(GameStatus.STATE_START) || gameStatus.status.equals(GameStatus.STATE_RESUME)) {
        Debug.getInstance().printMessage("random put")
        (1 to 5).foreach(i => {
          val key = new MapKey(random nextInt 4000, random nextInt 4000)
          //  Debug.getInstance().printMessage("generated obstacle: x=" + key.x + "\t y=" + key.y)
          bufferedMap.put(new MapUnit(key, System.currentTimeMillis()))
        }
        )
        val toSend = bufferedMap.clone
        //println("number of obstacleMapSubscribers=" + obstacleMapManager.messengers.size)
        obstacleMapManager.foreach(m =>
          m.sendMessage(toSend)
        )
        obstacleMap.merge(bufferedMap)
        bufferedMap.clear
      }
    }, true, 500)
  }

  //def obstacleMapSubscribers = obstacleMapMessengerManager.messengers


  def setup: Unit = {
    Config.getInstance(true)
    load
    PacmanNetwork.startServerPulisher()
    startMessengerManagers
    // handle udp messenger

    udpManager.start
  }

  def startMessengerManagers = {
    //TODO check missed?
    controllerRobotPairMessengerManager.start
    gameStatusMessengerManager.start
    movementCommandMessengerManager.start
    obstacleMapManager.start
    robotPositionMessengerManager.start
  }

  def load = {
    //TODO read last status from database
    //load game status
    gameStatus = new GameStatus(GameStatus.STATE_SETUP)
    //load obstacle map
  }

  def save = {
    //TODO save status to database
    println("start backup process")
  }

  Debug.getInstance().printMessage("Server_NetworkThread init 100%")
}
