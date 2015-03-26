package hkccpacmanrobot.robot.core

import hkccpacmanrobot.robot.utils.L298NAO
import hkccpacmanrobot.utils.Device
import hkccpacmanrobot.utils.message.{MovementCommand, Messenger}

/**
 * Created by 13058536A on 3/25/2015.
 */
abstract class Robot extends Device{
    //public ObstacleMap<RobotMapUnit> map = new ObstacleMap<RobotMapUnit>();
  val motor:L298NAO=new L298NAO
}
