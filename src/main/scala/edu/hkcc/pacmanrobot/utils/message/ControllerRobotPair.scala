package edu.hkcc.pacmanrobot.utils.message

import edu.hkcc.pacmanrobot.utils.Config

/**
 * Created by 13058456a on 4/2/2015.
 */
class ControllerRobotPair (val controller_macAddress:Array[Byte],val robot_macAddress:Array[Byte],var shouldSave:Boolean)extends Message{
  override def port(): Int = Config.PORT_CONTROLLER_ROBOT_PAIR
}
