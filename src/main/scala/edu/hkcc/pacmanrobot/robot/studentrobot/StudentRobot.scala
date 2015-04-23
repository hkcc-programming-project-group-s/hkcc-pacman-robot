package edu.hkcc.pacmanrobot.robot.studentrobot

import edu.hkcc.pacmanrobot.robot.core.AbstractRobot
import edu.hkcc.pacmanrobot.robot.utils.L298NAO
import edu.hkcc.pacmanrobot.utils.Config
import edu.hkcc.pacmanrobot.utils.message.MovementCommand
import edu.hkcc.pacmanrobot.utils.message.messenger.Messenger


/**
 * Created by beenotung on 3/26/15.
 */
class StudentRobot extends AbstractRobot {
  val movementCommandMessenger = new Messenger[MovementCommand](Config.PORT_MOVEMENT_COMMAND) {
    override def autoGet(message: MovementCommand): Unit = {
      movementCommand = message
    }
  }
  var movementCommand: MovementCommand = MovementCommand.stop

  override def run = {
    if (movementCommand.mode == MovementCommand.MODE_POLAR)
      L298NAO.move_pwm(movementCommand.point2D)
  }
}
