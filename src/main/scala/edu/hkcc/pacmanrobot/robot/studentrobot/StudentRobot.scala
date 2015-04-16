package edu.hkcc.pacmanrobot.robot.studentrobot

import edu.hkcc.pacmanrobot.robot.core.{DefaultRobot, Robot}
import edu.hkcc.pacmanrobot.robot.utils.L298NAO
import edu.hkcc.pacmanrobot.utils.Config
import edu.hkcc.pacmanrobot.utils.Config.MOTOR_CYCLE_INTERVAL
import edu.hkcc.pacmanrobot.utils.message.MovementCommand
import edu.hkcc.pacmanrobot.utils.message.messenger.Messenger


/**
 * Created by beenotung on 3/26/15.
 */
class StudentRobot(defaultRobot: DefaultRobot) extends Robot(defaultRobot) {
  val movementCommandMessenger = new Messenger[MovementCommand](Config.PORT_MOVEMENT_COMMAND) {
    override def autoGet(message: MovementCommand): Unit = {
      movementCommand = message
    }
  }
  var movementCommand: MovementCommand = MovementCommand.stop

  override def run = {
    movementCommandMessenger.start
    while (true) {
      loop
      Thread.sleep(MOTOR_CYCLE_INTERVAL)
    }
  }


  override def loop: Unit = {
    //val direction=in.readObject().asInstanceOf[java.lang.Double]
    if (movementCommand.mode.equals(MovementCommand.MODE_POLAR)) {
      L298NAO.move_pwm(movementCommand.point2D)
    } else {
      //TODO calculate polar command from current location
    }
  }
}
