package edu.hkcc.pacmanrobot.utils.studentrobot.code

import java.sql.Timestamp

/**
 * Created by 13058536A on 3/25/2015.
 */

class Position(var x: Double = 0d, var y: Double = 0d, var z: Double = 0d, var time: Timestamp = new Timestamp(System.currentTimeMillis())) extends Message {
  override val port: Int = Config.PORT_POSITION
}
