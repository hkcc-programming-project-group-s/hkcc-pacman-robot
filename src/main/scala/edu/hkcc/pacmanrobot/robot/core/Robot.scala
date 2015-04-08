package edu.hkcc.pacmanrobot.robot.core

import java.net.ServerSocket

import edu.hkcc.pacmanrobot.robot.utils.L298NAO
import edu.hkcc.pacmanrobot.utils.map._
import edu.hkcc.pacmanrobot.utils.{Config, GameDevice}

/**
 * Created by 13058536A on 3/25/2015.
 */
abstract class Robot extends GameDevice {
  //public ObstacleMap<RobotMapUnit> map = new ObstacleMap<RobotMapUnit>();

  //TODO val obstacleMapManager = new ObstacleMapManager{}
   def gameStart ={
    //obstacleMapManager.run
  }
  override def setup = {
    L298NAO.both_stop()
  }
}
