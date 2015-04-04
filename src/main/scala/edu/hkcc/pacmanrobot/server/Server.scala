package edu.hkcc.pacmanrobot.server

import java.net.ServerSocket
import java.net.Socket
import edu.hkcc.pacmanrobot.robot.core.Robot
import edu.hkcc.pacmanrobot.robot.edu.hkcc.pacmanrobot.utils.studentrobot.MovementCommandMessenger
import edu.hkcc.pacmanrobot.utils.message.{MovementCommand, ControllerRobotPair}
import edu.hkcc.pacmanrobot.utils.studentrobot.code.{Messenger, DeviceInfo, GameStatus}
import edu.hkcc.pacmanrobot.utils.{Config, Device}
import java.util.Scanner

import scala.collection.mutable.ArrayBuffer

/**
 * Created by 13058456a on 4/2/2015.
 */
object Server extends App {
  override def main(args: Array[String]) {
    //TODO
    setup
  }

  var gameStatus: GameStatus = new GameStatus(GameStatus.STATE_SETUP)
  gameSetup
  val studentRobots = new ArrayBuffer[RobotInfo]
  var deviceInfoMessengers = Vector.empty[Messenger[DeviceInfo]]
  val deviceInfoThread = new Thread(new Runnable {
    override def run(): Unit = {
      val deviceInfoServerSocket = new ServerSocket(Config.PORT_DEVICE_INFO)
      while (true) {
        deviceInfoMessengers :+= new Messenger[DeviceInfo](deviceInfoServerSocket.accept(), Config.PORT_DEVICE_INFO) {
          override def autoGet(message: DeviceInfo): Unit = {}
        }
      }
    }
  })


  var controllerRobotPairMessengers = Vector.empty[Messenger[ControllerRobotPair]]
  val controllerRobotPairThread = new Thread(new Runnable {
    override def run(): Unit = {
      val serverSocket = new ServerSocket(Config.PORT_CONTROLLER_ROBOT_PAIR)
      while (true) {
        controllerRobotPairMessengers :+= new Messenger[ControllerRobotPair](serverSocket.accept(), Config.PORT_CONTROLLER_ROBOT_PAIR) {
          override def autoGet(messenger: ControllerRobotPair): Unit = {
            //TODO
          }
        }
      }
    }
  })

  var gameStatusMessengers = Vector.empty[Messenger[GameStatus]]
  val gameStatueThread = new Thread(new Runnable {
    var endGame = false

    override def run(): Unit = {
      val serverSocket = new ServerSocket(Config.PORT_GAME_STATUS)
      while (true) {
        gameStatusMessengers :+= new Messenger[GameStatus](serverSocket.accept(), Config.PORT_GAME_STATUS) {
          override def autoGet(message: GameStatus): Unit = {
            gameStatus = message
            gameStatus.status match {
              case GameStatus.STATE_SETUP => gameSetup
              case GameStatus.STATE_START => gameStart
              case GameStatus.STATE_PAUSE => gamePause
              case GameStatus.STATE_RESUME => gameResume
              case GameStatus.STATE_STOP => gameStop
            }
          }
        }
      }
    }

  })


  val movementCommandMessengers =new  ArrayBuffer[Messenger[MovementCommand]]
  val MovementCommandThread = new Thread(new Runnable {
    override def run(): Unit = {
      val serverSocket = new ServerSocket(Config.PORT_MOVEMENT_COMMAND)
      while (true) {
        movementCommandMessengers += new Messenger[MovementCommand](serverSocket.accept(),Config.PORT_MOVEMENT_COMMAND) {
          override def autoGet(messenger: MovementCommand): Unit = {
            //TODO
          }
        }
      }
    }
  })


  def gameResume: Unit = ???

  def gamePause: Unit = ???

  def setup: Unit = ???

  def gameStart: Unit = ???

  def gameStop: Unit = ???

  def gameSetup: Unit = ???
}
