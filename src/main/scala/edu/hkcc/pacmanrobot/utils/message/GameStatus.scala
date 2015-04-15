package edu.hkcc.pacmanrobot.utils.studentrobot.code

import edu.hkcc.pacmanrobot.utils.Config
import edu.hkcc.pacmanrobot.utils.message.Message

/**
 * Created by 13058456a on 3/21/2015.
 */
object GameStatus extends Message {
  override val port: Int = Config.PORT_GAME_STATUS

  /*class GameStatusType extends Enumeration{
    type GameStatus = Value
    val SETUP,START,RESUME,STOP,PAUSE,WIN,LOSE=Value
  }*/
  val STATE_NORMAL:Byte = 0x08
  val STATE_SETUP: Byte = 0x01
  val STATE_START: Byte = 0x02
  val STATE_RESUME: Byte = 0x03
  val STATE_STOP: Byte = 0x04
  val STATE_PAUSE: Byte = 0x05
  val STATE_WIN: Byte = 0x06
  val STATE_LOSE: Byte = 0x07
  var status: Byte=0
}

/*
* message is not empty only when status is paused
* */
//class GameStatus(val status: GameStatus.GameStatusType,val message: String = "") extends Serializable
class GameStatus(var status: Byte, var message: String = "") extends Message {
  override def port(): Int = Config.PORT_GAME_STATUS
  this.status=status
}
