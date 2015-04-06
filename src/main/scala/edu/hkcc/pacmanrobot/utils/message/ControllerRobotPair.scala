package edu.hkcc.pacmanrobot.utils.message

import edu.hkcc.pacmanrobot.utils.Config
import edu.hkcc.pacmanrobot.utils.map.Message

/**
 * Created by 13058456a on 4/2/2015.
 */
class ControllerRobotPair (val controllerId:Array[Byte],val robotId:Array[Byte])extends Message{
  override def port(): Int = Config.PORT_CONTROLLER_ROBOT_PAIR
}