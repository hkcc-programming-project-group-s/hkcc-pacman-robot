package hkccpacmanrobot.robot.studentrobot

import java.net.Socket

import hkccpacmanrobot.robot.core.Robot
import hkccpacmanrobot.robot.utils.L298NAO
import hkccpacmanrobot.utils.Config
import hkccpacmanrobot.utils.message.MovementCommand

/**
 * Created by beenotung on 3/26/15.
 */
class StudentRobot extends Robot {
  val movementCommandMessenger: MovementCommandMessenger = new MovementCommandMessenger

  override def gameSetup: Unit = ???

  override def gameResume: Unit = ???

  override def gamePause: Unit = ???

  override def gameStart: Unit = ???

  override def gameStop: Unit = ???

  override def run = {
    while (true) {
      if ((!movementCommandMessenger.socket.isConnected) || movementCommandMessenger.socket.isClosed)
        movementCommandMessenger.socket = new Socket(Config.serverAddress, MovementCommand.port)
      loop
    }
  }

  override def loop: Unit = {
    val movementCommand: MovementCommand = movementCommandMessenger.getMovementCommand
    if (movementCommand.mode.equals(MovementCommand.MODE_POLAR)) {
      L298NAO.move(movementCommand.point2D.d1, movementCommand.point2D.d2)
    }
  }
}
