package edu.hkcc.pacmanrobot.utils.studentrobot.code

import java.sql.Timestamp

import edu.hkcc.pacmanrobot.utils.Config
import edu.hkcc.pacmanrobot.utils.map.Message

/**
 * Created by 13058536A on 3/25/2015.
 */

class Position(var x: Double = 0d, var y: Double = 0d, var z: Double = 0d, var time: Timestamp = new Timestamp(System.currentTimeMillis())) extends Message {
  override val port: Int = Config.PORT_POSITION
}
