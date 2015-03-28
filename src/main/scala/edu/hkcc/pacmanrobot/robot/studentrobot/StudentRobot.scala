package edu.hkcc.pacmanrobot.robot.studentrobot

import edu.hkcc.pacmanrobot.robot.core.Robot
import edu.hkcc.pacmanrobot.robot.edu.hkcc.pacmanrobot.utils.studentrobot.MovementCommandMessenger
import edu.hkcc.pacmanrobot.robot.utils.L298NAO
import edu.hkcc.pacmanrobot.utils.studentrobot.code.Config.MOTOR_CYCLE_INTERVAL
import edu.hkcc.pacmanrobot.utils.studentrobot.code.MovementCommand


/**
 * Created by beenotung on 3/26/15.
 */
class StudentRobot extends Robot {
  val movementCommandMessenger: MovementCommandMessenger = new MovementCommandMessenger

  override def gameSetup: Unit = {
    movementCommandMessenger.start
  }

  override def gameResume: Unit = ???

  override def gamePause: Unit = ???

  override def gameStart: Unit = ???

  override def gameStop: Unit = ???

  override def run = {
    while (true) {
      movementCommandMessenger.checkConnection
      loop
      Thread.sleep(MOTOR_CYCLE_INTERVAL)
    }
  }

  override def loop: Unit = {
    val movementCommand: MovementCommand = movementCommandMessenger.getMessage
    if (movementCommand.mode.equals(MovementCommand.MODE_POLAR)) {
      L298NAO.move(movementCommand.point2D.d1, movementCommand.point2D.d2)
    }
  }
}
