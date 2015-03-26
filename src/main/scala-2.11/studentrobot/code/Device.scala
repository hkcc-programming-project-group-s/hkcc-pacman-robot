package studentrobot.code

/**
 * Created by beenotung on 3/23/15.
 */

abstract class Device extends Thread {
  val deviceInfo: DeviceInfo = new DeviceInfo()
  /*val gameStatusMessenger: Messenger[GameStatus] = Messenger.create[GameStatus](GameStatus, { gameStatus: GameStatus =>
    gameStatus.status match {
      case GameStatus.STATE_SETUP => gameSetup
      case GameStatus.STATE_START => gameStart
      case GameStatus.STATE_PAUSE => gamePause
      case GameStatus.STATE_RESUME => gameResume
      case GameStatus.STATE_STOP => gameStop
    }
  })
  val deviceInfoMessenger: Messenger[DeviceInfo] = Messenger.create[DeviceInfo](DeviceInfo, { newDeviceInfo: DeviceInfo => deviceInfo.set(newDeviceInfo) })*/
  var gameStatus: GameStatus = _

  def gameSetup

  def gameStart

  def gamePause

  def gameResume

  def gameStop

  def setup

  override def start = {
    println(deviceInfo.name + " start ")
    setup
    super.start
  }

  def loop
}
