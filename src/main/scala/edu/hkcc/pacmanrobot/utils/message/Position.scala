package edu.hkcc.pacmanrobot.utils.studentrobot.code

import edu.hkcc.pacmanrobot.utils.Config
import edu.hkcc.pacmanrobot.utils.message.Message

/**
 * Created by 13058536A on 3/25/2015.
 */

class Position(var x: Int , var y: Int , var z: Int , var time: Long = System.currentTimeMillis()) extends Message {
  override val port: Int = Config.PORT_POSITION
}
