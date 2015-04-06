package edu.hkcc.pacmanrobot.server

import java.net.ServerSocket
import java.util.concurrent.ConcurrentHashMap

import edu.hkcc.pacmanrobot.utils.Config._
import edu.hkcc.pacmanrobot.utils.map.{ObstacleMap, ObstacleMapManager}
import edu.hkcc.pacmanrobot.utils.message.{ControllerRobotPair, MovementCommand, MovementCommandMessenger}
import edu.hkcc.pacmanrobot.utils.studentrobot.code.{DeviceInfo, GameStatus, Messenger, Position}

import scala.collection.mutable.ArrayBuffer

/**
 * Created by 13058456a on 4/2/2015.
 */
object Server extends App {
  val deviceInfoManager = new DeviceInfoManager

  val positions = new ConcurrentHashMap[Int, Position]()
  val positionMessengerManager = new MessengerManager[Position](PORT_POSITION, {
    (macAddress, position) => {
      positions.put(deviceInfoManager.getDeviceId(macAddress), position)
    }
  })

  val controllerRobotPairMessengerManager = new MessengerManager[ControllerRobotPair](PORT_CONTROLLER_ROBOT_PAIR, { (_,controllerRobotPair) => controllerRobotPairManager.setControllerRobotPair(controllerId = controllerRobotPair.controllerId, robotId = controllerRobotPair.robotId) })

  val gameStatusMessengerManager = new MessengerManager[GameStatus](PORT_GAME_STATUS, { (_,gamestatus) => switchGameStatus(gamestatus) })

  val movementCommandMessengers = new ArrayBuffer[MovementCommandMessenger]
  val MovementCommandThread = new Thread(new Runnable {
    override def run(): Unit = {
      val serverSocket = new ServerSocket(PORT_MOVEMENT_COMMAND)
      while (true) {
        movementCommandMessengers += new MovementCommandMessenger(serverSocket.accept()) {
          override def autoGet_func(message: MovementCommand): Unit = {
            val robotId = controllerRobotPairManager.getRobotId(messenger.
              getRemoteMacAddress)
            movementCommandMessengers.par.foreach(messenger =>
              if (messenger.getRemoteMacAddress.equals(robotId))
                messenger.sendMessage(message)
            )
          }
        }
      }
    }
  })
  val ObstacleMapThread = new Thread(new Runnable {
    override def run(): Unit = {
      val serverSocket = new ServerSocket(PORT_MAP)
      while (true) {
        obstacleMapMessengers :+= new Messenger[ObstacleMap](serverSocket.accept(), PORT_MAP) {
          override def autoGet(message: ObstacleMap): Unit = {
            obstacleMapMessengers.par.foreach(messenger => messenger.sendMessage(message))
            obstacleMapManager.addMap(message)
          }
        }
      }
    }
  })
  val PositionThread = new Thread(new Runnable {
    override def run(): Unit = {
      val serverSocket = new ServerSocket(PORT_POSITION)
      while (true) {
        positionMessengers :+= new Messenger[Position](serverSocket.accept(), PORT_POSITION) {
          override def autoGet(message: Position): Unit = {
            positionManager = message
          }
        }
      }
    }

  })
  var gameStatus: GameStatus = new GameStatus(GameStatus.STATE_SETUP)
  var deviceInfos = new ConcurrentHashMap[Array[Byte], DeviceInfo]()
  val controllerRobotPairManager = new ControllerRobotPairManager
  val obstacleMapMessengers = Vector.empty[Messenger[ObstacleMap]]
  val positionManager = new Position
  val obstacleMapManager = new ObstacleMapManager {}
  val positionMessengers = Array.empty[Messenger[Position]]

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

  gameSetup

  def gameResume: Unit = ???

  def gamePause: Unit = ???

  def gameStart: Unit = ???

  def gameStop: Unit = ???

  def gameSetup: Unit = ???

  override def main(args: Array[String]) {
    //TODO
    setup
  }

  def setup: Unit = ???
}
