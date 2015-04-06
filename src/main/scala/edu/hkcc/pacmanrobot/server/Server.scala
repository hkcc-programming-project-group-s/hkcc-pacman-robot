package edu.hkcc.pacmanrobot.server

import java.net.ServerSocket
import java.util.concurrent.ConcurrentHashMap

import edu.hkcc.pacmanrobot.utils.Config._
import edu.hkcc.pacmanrobot.utils.map.ObstacleMap
import edu.hkcc.pacmanrobot.utils.message.{ControllerRobotPair, MovementCommand, MovementCommandMessenger}
import edu.hkcc.pacmanrobot.utils.studentrobot.code.{GameStatus, Messenger, Position}

import scala.collection.parallel.mutable.ParArray

/**
 * Created by 13058456a on 4/2/2015.
 */
object Server extends App {
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

  var movementCommandMessengers = ParArray.empty[MovementCommandMessenger]
  val MovementCommandThread = new Thread(new Runnable {
    override def run(): Unit = {
      val serverSocket = new ServerSocket(PORT_MOVEMENT_COMMAND)
      while (true) {
        movementCommandMessengers :+= new MovementCommandMessenger(serverSocket.accept()) {
          override def autoGet_func(message: MovementCommand): Unit = {
            val robotId = controllerRobotPairManager.getRobotId(deviceInfoManager.getDeviceIdByMacAddress(messenger.getRemoteMacAddress))
            movementCommandMessengers.par.foreach(messenger =>
              if (messenger.getRemoteMacAddress.equals(robotId))
                messenger.sendMessage(message)
            )
          }
        }
      }
    }
  })


  val obstacleMap = new ObstacleMap
  val obstacleMapMessengerManager = new MessengerManager[ObstacleMap](PORT_MAP, (macAddress, message) => {
    obstacleMapSubscribers.foreach(messenger => if (!macAddress.equals(messenger.getRemoteMacAddress)) messenger.sendMessage(message))
    obstacleMap.merge(message)
  })
  val obstacleMapSubscribers: ParArray[Messenger[ObstacleMap]] = obstacleMapMessengerManager.messengers

  def switchGameStatus(gameStatus: GameStatus): Unit = {
    gameStatusMessengerManager.messengers.foreach(messenger => messenger.sendMessage(gameStatus))
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

  def gameSetup: Unit = ???

  override def main(args: Array[String]) {
    setup
    while (true) {
      Thread.sleep(SAVE_INTERVAL)
      save
    }
  }

  def setup: Unit = {
    load
    gameSetup
  }

  def load = {
    //TODO read last status from database
  }

  def save = {
    //TODO save status to database
  }
}
