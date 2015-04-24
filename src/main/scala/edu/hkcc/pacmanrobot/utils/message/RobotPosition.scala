package edu.hkcc.pacmanrobot.utils.message

import edu.hkcc.pacmanrobot.utils.Config

/**
 * Created by beenotung on 4/16/15.
 */
class RobotPosition(val deviceInfo: DeviceInfo, var position: Position) extends Message {
  override def port(): Int = Config.PORT_ROBOT_POSITION
}
