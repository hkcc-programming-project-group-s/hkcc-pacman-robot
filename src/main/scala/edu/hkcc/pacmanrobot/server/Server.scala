package edu.hkcc.pacmanrobot.server

import java.net.{InetAddress, ServerSocket}
import java.util.Calendar
import java.util.concurrent.ConcurrentHashMap

import edu.hkcc.pacmanrobot.utils.Config._
import edu.hkcc.pacmanrobot.utils.Utils.random
import edu.hkcc.pacmanrobot.utils.map.{MapKey, MapUnit, ObstacleMap}
import edu.hkcc.pacmanrobot.utils.message.{ControllerRobotPair, Messenger, MovementCommand, MovementCommandMessenger}
import edu.hkcc.pacmanrobot.utils.studentrobot.code.{GameStatus, Position}
import edu.hkcc.pacmanrobot.utils.{Config, Timer}

import scala.collection.parallel.mutable.ParArray


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

  var movementCommandMessengers = ParArray.empty[MovementCommandMessenger]
  val movementCommandThread = new Thread(new Runnable {
    override def run(): Unit = {
      val serverSocket = new ServerSocket(PORT_MOVEMENT_COMMAND)
      while (true) {
        movementCommandMessengers :+= new MovementCommandMessenger(serverSocket.accept(), true) {
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
  def obstacleMapSubscribers: ParArray[Messenger[ObstacleMap]] = obstacleMapMessengerManager.messengers

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

  def gameSetup: Unit = {}

  def test = {
    val bufferedMap = new ObstacleMap
    Timer.setTimeInterval({
      println
      println(Calendar.getInstance().getTime)
      println("random put")
      bufferedMap.put(new MapUnit(new MapKey(random.nextInt, random.nextInt), System.currentTimeMillis()))
      println("number of obstacleMapSubscribers="+obstacleMapSubscribers.size)
      obstacleMapSubscribers.foreach(m => m.sendMessage(bufferedMap))
      obstacleMap.merge(bufferedMap)
      bufferedMap.clear
    }, true, 500)
  }

  override def run = {
    setup
    test
    while (true) {
      Thread.sleep(SAVE_INTERVAL)
      save
    }
  }

  def startMessengerManagers = {
    controllerRobotPairMessengerManager.start
    gameStatusMessengerManager.start
    movementCommandThread.start
    obstacleMapMessengerManager.start
    positionMessengerManager.start
  }

  def setup: Unit = {
    Config.serverAddress = InetAddress.getLocalHost.getHostAddress
    println("server ip: " + Config.serverAddress)
    load
    startMessengerManagers
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
