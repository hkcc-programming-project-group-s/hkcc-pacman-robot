package hkccpacmanrobot.utils.message

/**
 * Created by beenotung on 3/23/15.
 */

abstract class Device extends Thread {
  private val gameStatusMessenger: Messenger[GameStatus] = Messenger.create[GameStatus](GameStatus)

  def gameSetup

  def gameStart

  def gamePause

  def gameResume

  def gameStop


  var gameStatus: GameStatus = _

  override def run() = {
    new Thread(new Runnable {
      override def run(): Unit = {
        while(true){
          if (gameStatusMessenger.hasMessage) {
            gameStatus = gameStatusMessenger.getMessage
            gameStatus.status match {
              case GameStatus.STATE_SETUP => gameSetup
              case GameStatus.STATE_START => gameStart
              case GameStatus.STATE_PAUSE => gamePause
              case GameStatus.STATE_RESUME => gameResume
              case GameStatus.STATE_STOP => gameStop
            }
          }
          Thread.sleep(1000)
        }
      }
    }).start()
  }
}