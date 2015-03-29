package edu.hkcc.pacmanrobot.utils.message

import edu.hkcc.pacmanrobot.utils.Config
import edu.hkcc.pacmanrobot.utils.studentrobot.code.{Messenger, Position}


/**
 * Created by 13058536A on 3/25/2015.
 */
class PositionManager {
  val messenger: Messenger[Position] = Messenger.create[Position](Config.PORT_POSITION)
}
