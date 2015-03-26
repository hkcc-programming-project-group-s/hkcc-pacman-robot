package hkccpacmanrobot.utils.message

import hkccpacmanrobot.utils.{Config, Maths}


/**
 * Created by beenotung on 2/10/15.
 */
object MovementCommand extends Message {
  override val port: Int = Config.PORT_MOVEMENT_COMMAND

  val MODE_POLAR: Byte = 0x01
  val MODE_RECTANGULAR: Byte = 0x02
}

/*
* POLAR: d1 = degree (in radian), d2 = distance
* RECTANGULAR:: d1 =x, d2 = y
* */
class MovementCommand(mode: Byte, point2D: Maths.Point2D) extends Message {
  override val port: Int = MovementCommand.port
}

