package edu.hkcc.pacmanrobot.server

import java.net.InetAddress
import java.util.concurrent.ConcurrentHashMap
import java.util.function.BiConsumer

import edu.hkcc.pacmanrobot.server.ObstacleMapManager.obstacleMap
import edu.hkcc.pacmanrobot.utils.Config._
import edu.hkcc.pacmanrobot.utils.Utils.random
import edu.hkcc.pacmanrobot.utils.map.{MapKey, MapUnit, ObstacleMap}
import edu.hkcc.pacmanrobot.utils.message._
import edu.hkcc.pacmanrobot.utils.studentrobot.code.GameStatus
import edu.hkcc.pacmanrobot.utils.{Config, Timer}


/**
 * Created by beenotung on 4/2/2015.
 */
class Server extends Thread {
  val deviceInfoManager = new DeviceInfoManager

  val positions = new ConcurrentHashMap[DeviceInfo, Position]()
  val positionMessengerManager = new MessengerManager[Position](PORT_POSITION, {
    (macAddress, position) => {
      positions.put(deviceInfoManager.getDeviceInfoByMacAddress(macAddress), position)
    }
  })

  val robotPositionMessengerManager = new MessengerManager[RobotPosition](PORT_ROBOT_POSITION, {
    (macAddress, position) => {
      response_robotPosition(macAddress, position.deviceInfo.deviceType)
    }
  })

  val controllerRobotPairManager = new ControllerRobotPairManager
  val controllerRobotPairMessengerManager = new MessengerManager[ControllerRobotPair](PORT_CONTROLLER_ROBOT_PAIR, (macAddress, controllerRobotPair: ControllerRobotPair) => {
    if (controllerRobotPair.shouldSave)
      controllerRobotPairManager.setControllerRobotPair(controllerRobotPair.controller_macAddress, controllerRobotPair.robot_macAddress)
    else
      response_pair(macAddress)
  }
  )
  val gameStatusMessengerManager = new MessengerManager[GameStatus](PORT_GAME_STATUS, { (remoteMacAddress, gamestatus) => {
    if (GameStatus.STATE_NORMAL.equals(gamestatus.status))
      deviceInfoManager.update(remoteMacAddress)
    else
      switchGameStatus(gamestatus)
  }
  })
  val movementCommandMessengerManager = new MessengerManager[MovementCommand](PORT_MOVEMENT_COMMAND, (remoteMacAddress, message) => {
    copyMovementCommand(remoteMacAddress, message)
  })
  val obstacleMapManager = new ObstacleMapManager

  def response_pair(macAddress: Array[Byte]):Unit = {
    controllerRobotPairManager.controllerRobotPairs.forEach(new BiConsumer[Array[Byte], Array[Byte]] {
      override def accept(t: Array[Byte], u: Array[Byte]): Unit = {
        controllerRobotPairMessengerManager.sendByMacAddress(macAddress,
          new ControllerRobotPair(t, u, true))
      }
    })

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

  def switchGameStatus(gameStatus: GameStatus): Unit = {
    gameStatusMessengerManager.foreach(messenger => messenger.sendMessage(gameStatus))
    gameStatus.status match {
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
    startMessengerManagers
  }

  def startMessengerManagers = {
    //TODO check missed?
    controllerRobotPairMessengerManager.start
    gameStatusMessengerManager.start
    movementCommandMessengerManager.start
    //movementCommandThread.start
    obstacleMapManager.start
    //obstacleMapMessengerManager.start
    positionMessengerManager.start
  }

  def load = {
    //TODO read last status from database
    //if()
    //gameSetup
  }

  def save = {
    //TODO save status to database
  }

}
