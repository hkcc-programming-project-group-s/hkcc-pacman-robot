package edu.hkcc.pacmanrobot.server

import java.net.InetAddress
import java.util.Calendar
import java.util.concurrent.ConcurrentHashMap

import edu.hkcc.pacmanrobot.server.ObstacleMapManager.obstacleMap
import edu.hkcc.pacmanrobot.utils.Config._
import edu.hkcc.pacmanrobot.utils.Utils.random
import edu.hkcc.pacmanrobot.utils.map.{MapKey, MapUnit, ObstacleMap}
import edu.hkcc.pacmanrobot.utils.message.{ControllerRobotPair, MovementCommand}
import edu.hkcc.pacmanrobot.utils.studentrobot.code.{GameStatus, Position}
import edu.hkcc.pacmanrobot.utils.{Config, Timer}


/**
 * Created by 13058456a on 4/2/2015.
 */
class Server extends Thread {
  val deviceInfoManager = new DeviceInfoManager

  val positions = new ConcurrentHashMap[Int, Position]()
  val positionMessengerManager = new MessengerManager[Position](PORT_POSITION, {
    (macAddress, position) => {
      positions.put(deviceInfoManager.getDeviceIdByMacAddress(macAddress), position)
    }
  })

  val controllerRobotPairManager = new ControllerRobotPairManager
  val controllerRobotPairMessengerManager = new MessengerManager[ControllerRobotPair](PORT_CONTROLLER_ROBOT_PAIR, { (_, controllerRobotPair) =>
    controllerRobotPairManager.setControllerRobotPair(controllerId = deviceInfoManager.getDeviceIdByMacAddress(controllerRobotPair.controllerId), robotId = deviceInfoManager.getDeviceIdByMacAddress(controllerRobotPair.robotId))
  })

  val gameStatusMessengerManager = new MessengerManager[GameStatus](PORT_GAME_STATUS, { (_, gamestatus) => switchGameStatus(gamestatus) })

  val movementCommandMessengerManager = new MessengerManager[MovementCommand](PORT_MOVEMENT_COMMAND, (remoteMacAddress, message) => {
    copyMovementCommand(remoteMacAddress, message)
  })
  val obstacleMapManager = new ObstacleMapManager

  def copyMovementCommand(controllerMacAddress: Array[Byte], message: MovementCommand) :Unit= {
    val robotId = controllerRobotPairManager.getRobotId(deviceInfoManager.getDeviceIdByMacAddress(controllerMacAddress))
    if (robotId != 0)
      movementCommandMessengerManager.sendByMacAddress(deviceInfoManager.getMacAddressByDeviceId(robotId), message)
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
      println
      println(Calendar.getInstance().getTime)
      println("random put")
      bufferedMap.put(new MapUnit(new MapKey(random nextInt 4000, random nextInt 4000), System.currentTimeMillis()))
      val toSend = bufferedMap.clone
      println("number of obstacleMapSubscribers=" + obstacleMapManager.messengers.length)
      obstacleMapManager.messengers.foreach(m =>
        m.sendMessage(toSend)
      )
      obstacleMap.merge(bufferedMap)
      bufferedMap.clear
    }, true, 10)
  }

  //def obstacleMapSubscribers = obstacleMapMessengerManager.messengers

  def setup: Unit = {
    Config.serverAddress = InetAddress.getLocalHost.getHostAddress
    println("server ip: " + Config.serverAddress)
    load
    startMessengerManagers
  }

  def startMessengerManagers = {
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
