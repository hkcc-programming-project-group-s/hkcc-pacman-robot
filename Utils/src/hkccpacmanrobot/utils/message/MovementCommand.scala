package hkccpacmanrobot.utils.message

import hkccpacmanrobot.utils.Maths.Point2D
import hkccpacmanrobot.utils.{Config, Maths}


/**
 * Created by beenotung on 2/10/15.
 */
object MovementCommand extends Message {
  override val port: Int = Config.PORT_MOVEMENT_COMMAND

  val MODE_POLAR: Byte = 0x01
  val MODE_RECTANGULAR: Byte = 0x02
  def stop:MovementCommand={
    new MovementCommand(MODE_POLAR,new Point2D)
  }
}

/*
* POLAR: d1 = degree (in radian), d2 = distance
* RECTANGULAR:: d1 =x, d2 = y
* */
class MovementCommand(val mode: Byte, val point2D: Maths.Point2D) extends Message with Cloneable{
  override val port: Int = MovementCommand.port
  override def clone:MovementCommand={
    new MovementCommand(mode.clone.asInstanceOf[Byte], point2D.clone)
  }
}

