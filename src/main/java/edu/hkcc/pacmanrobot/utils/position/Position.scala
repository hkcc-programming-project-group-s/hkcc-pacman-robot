package hkccpacmanrobot.utils.position

import hkccpacmanrobot.utils.Config
import hkccpacmanrobot.utils.Maths.Point2D
import hkccpacmanrobot.utils.message.Message

/**
 * Created by 13058536A on 3/25/2015.
 */
class Position(var x: Double = 0d, var y: Double = 0d) extends Message {
  override val port: Int = Config.PORT_POSITION
}
