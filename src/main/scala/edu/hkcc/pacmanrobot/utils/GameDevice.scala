package edu.hkcc.pacmanrobot.utils

import edu.hkcc.pacmanrobot.utils.message.DeviceInfo
import edu.hkcc.pacmanrobot.utils.message.messenger.Messenger
import edu.hkcc.pacmanrobot.utils.studentrobot.code.GameStatus


/**
 * Created by beenotung on 3/23/15.
 */

abstract class Device extends Thread {
  var gameStatus: GameStatus = new GameStatus(GameStatus.STATE_SETUP)
  var deviceInfo: DeviceInfo

  def gameSetup

  def gameStart

  def gamePause

  def gameResume

  def gameStop

  def setup
}

abstract class GameDevice extends Device {
  val deviceInfoMessenger: Messenger[DeviceInfo] = Messenger.create[DeviceInfo](Config.PORT_DEVICE_INFO, message => {
    deviceInfo.set(message)
  }, null)
  val gameStatusMessenger: Messenger[GameStatus] = Messenger.create[GameStatus](Config.PORT_GAME_STATUS, { gameStatus: GameStatus => {
    this.gameStatus = gameStatus
    gameStatus.status match {
      case GameStatus.STATE_SETUP => gameSetup
      case GameStatus.STATE_START => gameStart
      case GameStatus.STATE_PAUSE => gamePause
      case GameStatus.STATE_RESUME => gameResume
      case GameStatus.STATE_STOP => gameStop
    }
  }
  }, null)

  override def start = {
    println(deviceInfo.name + " start ")
    setup
    super.start
  }

  def loop
}
