package edu.hkcc.pacmanrobot.server

import java.net.ServerSocket
import java.util.concurrent.ConcurrentHashMap

import edu.hkcc.pacmanrobot.utils.Config
import edu.hkcc.pacmanrobot.utils.map.ObstacleMap
import edu.hkcc.pacmanrobot.utils.message.{ControllerRobotPair, MovementCommand, MovementCommandMessenger}
import edu.hkcc.pacmanrobot.utils.studentrobot.code.{DeviceInfo, GameStatus, Messenger, Position}

import scala.collection.mutable.ArrayBuffer

/**
 * Created by 13058456a on 4/2/2015.
 */
object Server extends App {
  //val studentRobots = new ArrayBuffer[RobotInfo]
  val deviceInfoMessengerManager = new MessengerManager[DeviceInfo](Config.PORT_DEVICE_INFO, { deviceInfo => deviceInfos.put(deviceInfo.MAC_ADDRESS, deviceInfo) })
  val controllerRobotPairMessengerManager = new MessengerManager[ControllerRobotPair](Config.PORT_CONTROLLER_ROBOT_PAIR, { controllerRobotPair => controllerRobotPairManager.setControllerRobotPair(controllerId = controllerRobotPair.controllerId, robotId = controllerRobotPair.robotId) })
  val gameStatusMessengerManager = new MessengerManager[GameStatus](Config.PORT_GAME_STATUS, { gamestatus => switchGameStatus(gamestatus) })
  val movementCommandMessengers = new ArrayBuffer[MovementCommandMessenger]

  gameSetup
  val MovementCommandThread = new Thread(new Runnable {
    override def run(): Unit = {
      val serverSocket = new ServerSocket(Config.PORT_MOVEMENT_COMMAND)
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
      val serverSocket = new ServerSocket(Config.PORT_MAP)
      while (true) {
        obstacleMapMessengers :+= new Messenger[ObstacleMap](serverSocket.accept(), Config.PORT_MAP) {
          override def autoGet(message: ObstacleMap): Unit = {}
        }
      }
    }
  })
  val PositionThread = new Thread(new Runnable {
    override def run(): Unit = {
      val serverSocket = new ServerSocket(Config.PORT_POSITION)
      while (true) {
        positionMessengers :+= new Messenger[Position](serverSocket.accept(), Config.PORT_POSITION) {
          override def autoGet(message: Position): Unit = {}
        }
      }
    }

  })
  var gameStatus: GameStatus = new GameStatus(GameStatus.STATE_SETUP)
  var deviceInfos = new ConcurrentHashMap[Array[Byte], DeviceInfo]()
  var controllerRobotPairManager = new ControllerRobotPairManager
  var obstacleMapMessengers = Vector.empty[Messenger[ObstacleMap]]
  var positionMessengers = Array.empty[Messenger[Position]]

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
    //TODO
    setup
  }

  def setup: Unit = ???
}
