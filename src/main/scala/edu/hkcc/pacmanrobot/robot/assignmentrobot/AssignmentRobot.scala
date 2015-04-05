package edu.hkcc.pacmanrobot.robot.assignmentrobot

import edu.hkcc.pacmanrobot.robot.core.Robot
import edu.hkcc.pacmanrobot.utils.studentrobot.code.DeviceInfo

/**
 * Created by beenotung on 4/5/15.
 */
class AssignmentRobot(name: String) extends Robot {
  override var deviceInfo: DeviceInfo = DeviceInfo.create(name, DeviceInfo.DEVICE_TYPE_ASSIGNMENT_ROBOT)

  override def loop: Unit = ???

  override def gameResume: Unit = ???

  override def gamePause: Unit = ???

  override def gameStart: Unit = ???

  override def gameStop: Unit = ???

  override def gameSetup: Unit = ???
}
