package edu.hkcc.pacmanrobot.server.network

import java.net.InetAddress
import java.util.concurrent.ConcurrentHashMap
import java.util.function.BiConsumer

import edu.hkcc.pacmanrobot.utils.Config._
import edu.hkcc.pacmanrobot.utils.Utils.random
import edu.hkcc.pacmanrobot.utils.map.{MapKey, MapUnit, ObstacleMap}
import edu.hkcc.pacmanrobot.utils.message._
import edu.hkcc.pacmanrobot.utils.network.PacmanNetwork
import edu.hkcc.pacmanrobot.utils.studentrobot.code.GameStatus
import edu.hkcc.pacmanrobot.utils.{Config, Timer}


/**
 * Created by beenotung on 4/2/2015.
 */
object Server_NetworkThread extends Thread {
  val deviceInfoManager = new DeviceInfoManager
  val flashLightManager = new FlashLightManager
  val positions = new ConcurrentHashMap[DeviceInfo, Position]()
  val positionMessengerManager = new MessengerManager[Position](PORT_POSITION, initMessenger_func = messenger => {}, autoGet_func = {
    (macAddress, position) => {
      positions.put(deviceInfoManager.getDeviceInfoByMacAddress(macAddress), position)
    }
  })
  val robotPositionMessengerManager = new MessengerManager[RobotPosition](PORT_ROBOT_POSITION, initMessenger_func = messenger => {}, autoGet_func = {
    (macAddress, position) => {
      response_robotPosition(macAddress, position.deviceInfo.deviceType)
    }
  })
  val controllerRobotPairManager = new ControllerRobotPairManager
  val controllerRobotPairMessengerManager = new MessengerManager[ControllerRobotPair](PORT_CONTROLLER_ROBOT_PAIR, initMessenger_func = messenger => {}, autoGet_func = (macAddress, controllerRobotPair: ControllerRobotPair) => {
    if (controllerRobotPair.shouldSave)
      controllerRobotPairManager.setControllerRobotPair(controllerRobotPair.controller_macAddress, controllerRobotPair.robot_macAddress)
    else
      response_pair(macAddress)
  }
  )
  val gameStatusMessengerManager = new MessengerManager[GameStatus](PORT_GAME_STATUS,
    initMessenger_func = { (messenger) => messenger.sendMessage(gameStatus) },
    autoGet_func = { (remoteMacAddress, gameStatus) => {
      if (GameStatus.STATE_REQUEST.equals(gameStatus.status))
        response_gameStatus(remoteMacAddress)
      else
        switchGameStatus(gameStatus)
    }
    })
  val movementCommandMessengerManager = new MessengerManager[MovementCommand](PORT_MOVEMENT_COMMAND, initMessenger_func = messenger => {}, autoGet_func = (remoteMacAddress, message) => {
    copyMovementCommand(remoteMacAddress, message)
  })
  val obstacleMapManager = new ObstacleMapManager
  var gameStatus: GameStatus = new GameStatus(GameStatus.STATE_SETUP)

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

  def response_robotPosition(macAddress: Array[Byte], robotType: Byte): Unit = {

  }

  def copyMovementCommand(controllerMacAddress: Array[Byte], message: MovementCommand): Unit = {
    movementCommandMessengerManager.sendByMacAddress(controllerRobotPairManager.getRobot_macAddress(controllerMacAddress), message)
  }

  /*
  val obstacleMapMessengerManager = new MessengerManager[ObstacleMap](PORT_MAP, (macAddress, message) => {
    obstacleMapSubscribers.foreach(messenger =>
      if (!macAddress.equals(messenger.getRemoteMacAddress)) messenger.sendMessage(message))
    obstacleMap.merge(message)
  })*/


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

  def gameResume: Unit = ???

  def gamePause: Unit = ???

  def gameStart: Unit = ???

  def gameStop: Unit = ???

  def gameSetup: Unit = {}

  override def run = {
    setup
    test
    while (true) {
      Thread.sleep(SAVE_INTERVAL)
      save
    }
  }

  def test = {
    import ObstacleMapManager.obstacleMap
    val bufferedMap = new ObstacleMap
    Timer.setTimeInterval({
      //println
      //println(Calendar.getInstance().getTime)
      //println("random put")
      (1 to 100).foreach(i =>
        bufferedMap.put(new MapUnit(new MapKey(random nextInt 4000, random nextInt 4000), System.currentTimeMillis()))
      )
      val toSend = bufferedMap.clone
      //println("number of obstacleMapSubscribers=" + obstacleMapManager.messengers.size)
      obstacleMapManager.foreach(m =>
        m.sendMessage(toSend)
      )
      obstacleMap.merge(bufferedMap)
      bufferedMap.clear
    }, true, 500)
  }

  //def obstacleMapSubscribers = obstacleMapMessengerManager.messengers

  def setup: Unit = {
    Config.serverAddress = InetAddress.getLocalHost.getHostAddress
    println("server ip: " + Config.serverAddress)
    load
    PacmanNetwork.startServerPulisher()
    startMessengerManagers
  }

  def startMessengerManagers = {
    //TODO check missed?
    controllerRobotPairMessengerManager.start
    gameStatusMessengerManager.start
    movementCommandMessengerManager.start
    obstacleMapManager.start
    positionMessengerManager.start
  }

  def load = {
    //TODO read last status from database
    //load game status
    gameStatus = new GameStatus(GameStatus.STATE_SETUP)
    //load obstacle map
  }

  def save = {
    //TODO save status to database
  }
}
