package edu.hkcc.pacmanrobot.utils.studentrobot.code

import edu.hkcc.pacmanrobot.utils.Config

/**
 * Created by 13058536A on 3/25/2015.
 */
class PositionManager {
  val messenger: Messenger[Position] = Messenger.create[Position](Config.PORT_POSITION)
}