package edu.hkcc.pacmanrobot.robot.core

import edu.hkcc.pacmanrobot.robot.assignmentrobot.AssignmentRobot
import edu.hkcc.pacmanrobot.robot.deadlinerobot.DeadlineRobot
import edu.hkcc.pacmanrobot.robot.studentrobot.StudentRobot
import edu.hkcc.pacmanrobot.utils.message.DeviceInfo._
import edu.hkcc.pacmanrobot.utils.message.messenger.{Messenger, ObstacleMapMessenger}
import edu.hkcc.pacmanrobot.utils.message.{DeviceInfo, Position}
import edu.hkcc.pacmanrobot.utils.{Config, GameDevice}

/**
 * Created by beenotung on 4/16/15.
 */
object DefaultRobot extends GameDevice {
  val obstacleMapMessenger = new ObstacleMapMessenger
  val positionMessenger = Messenger.create[Position](Config.PORT_POSITION, message => {}, null)
  override var deviceInfo: DeviceInfo = DeviceInfo.create("noname_robot", DeviceInfo.DEVICE_TYPE_UNCLASSED_ROBOT)
  var newRobot: AbstractRobot = null

  override def loop: Unit = ???


  override def gameResume: Unit = ???

  override def gamePause: Unit = ???

  override def gameStop: Unit = ???

  override def gameSetup: Unit = ???

  override def gameStart: Unit = {

  }

  def switchToRobot(robotType: Byte): Unit = {
    robotType match {
      case DEVICE_TYPE_STUDENT_ROBOT => {
        newRobot = new StudentRobot()
        newRobot.start
      }
      case DEVICE_TYPE_ASSIGNMENT_ROBOT => {
        newRobot = new AssignmentRobot()
        newRobot.start
      }
      case DEVICE_TYPE_DEADLINE_ROBOT => {
        newRobot = new DeadlineRobot()
        newRobot.start
      }
    }
  }

  override def init: Unit = {
    positionMessenger.start()
    obstacleMapMessenger.start()
  }
}
