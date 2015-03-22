package hkccpacmanrobot.utils.message

import hkccpacmanrobot.utils.Config

/**
 * Created by 13058456a on 3/21/2015.
 */
object GameState  extends  AbstractMessage{
  override val port: Int = Config.PORT_GAME_STATUS

  val STATE_SETUP: Byte = 0x01
  val STATE_START: Byte = 0x02
  val STATE_RESUME: Byte = 0x03
  val STATE_STOP: Byte = 0x04
  val STATE_PAUSE: Byte = 0x05
  val STATE_WIN: Byte = 0x06
  val STATE_LOSE: Byte = 0x07
}

/*
* message is not empty only when status is paused
* */
class GameState(gameStatus: Byte, message: String = "") extends Serializable
