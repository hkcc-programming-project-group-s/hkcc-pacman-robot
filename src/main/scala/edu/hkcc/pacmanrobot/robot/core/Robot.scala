package edu.hkcc.pacmanrobot.robot.core

import edu.hkcc.pacmanrobot.robot.utils.L298NAO
import edu.hkcc.pacmanrobot.utils.GameDevice

/**
 * Created by 13058536A on 3/25/2015.
 */
abstract class Robot extends GameDevice {
  //public ObstacleMap<RobotMapUnit> map = new ObstacleMap<RobotMapUnit>();

  override def setup = {
    L298NAO.both_stop()
  }
}
