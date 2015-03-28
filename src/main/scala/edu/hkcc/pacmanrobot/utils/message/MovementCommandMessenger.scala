package edu.hkcc.pacmanrobot.robot.edu.hkcc.pacmanrobot.utils.studentrobot

import java.util.concurrent.Semaphore

import edu.hkcc.pacmanrobot.utils.studentrobot.code.{Config, Messenger, MovementCommand}


/**
 * Created by beenotung on 3/26/15.
 */
class MovementCommandMessenger extends Messenger[MovementCommand](Config.PORT_MOVEMENT_COMMAND) {
  val semaphore: Semaphore = new Semaphore(1)
  private var movementCommand: MovementCommand = MovementCommand.stop

  override def autoGet(message: MovementCommand): Unit = {
    movementCommand = message
  }

  override def getMessage: MovementCommand = {
    if (inputQueue.isEmpty)
      movementCommand = MovementCommand.stop
    else
      movementCommand = inputQueue.poll
    movementCommand
  }
}
