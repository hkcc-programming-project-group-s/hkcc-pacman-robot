package hkccpacmanrobot.utils.message

/**
 * Created by 13058456a on 3/21/2015.
 */
object GameState{
  val STATE_SET_UP : Byte = 1
  val STATE_START : Byte = 2
  val STATE_RESUME : Byte = 3
  val STATE_STOP : Byte = 4
  val STATE_PAUSE : Byte = 5

}
class GameState extends Serializable{

}
