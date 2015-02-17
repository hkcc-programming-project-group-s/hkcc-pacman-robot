package hkccpacmanrobot.utils

/**
 * Created by beenotung on 2/10/15.
 */
object ControllCommand {
  val MODE_DIRECTION: Byte = 1
  val MODE_POSITION: Byte = 2
}

class ControllCommand(val studentId: Int, val mode: Byte, val content: Position) {
  def this(mode: Byte, content: Position) = this(0, mode, content)
}

