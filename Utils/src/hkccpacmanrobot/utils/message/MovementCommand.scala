package hkccpacmanrobot.utils.message

import hkccpacmanrobot.utils.Position

/**
 * Created by beenotung on 2/10/15.
 */
object MovementCommand {
  val MODE_DIRECTION: Byte = 1
  val MODE_POSITION: Byte = 2
}

class MovementCommand   (val studentId: Int, val mode: Byte, val content: Position) extends Serializable{
  def this(mode: Byte, content: Position) = this(0, mode, content)
}

