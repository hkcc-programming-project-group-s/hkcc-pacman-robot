package hkccpacmanrobot.robot.studentrobot

import java.util.concurrent.Semaphore

import hkccpacmanrobot.utils.message.{Messenger, MovementCommand}

/**
 * Created by beenotung on 3/26/15.
 */
class MovementCommandMessenger extends Messenger[MovementCommand](MovementCommand) {
  val semaphore: Semaphore = new Semaphore(1)
  private var movementCommand: MovementCommand = MovementCommand.stop

  override def autoGet(message: MovementCommand): Unit = {
    movementCommand = message
  }

  override def getMessage: MovementCommand = {
    if (inputQueue.isEmpty)
      MovementCommand.stop
    else
      inputQueue.poll
  }

  def getMovementCommand: MovementCommand = {
    semaphore.tryAcquire
    val result: MovementCommand = movementCommand.clone
    movementCommand=MovementCommand.stop
    semaphore.release
    result
  }

  def setMovementCommand(newMovementCommand: MovementCommand) = {
    semaphore.tryAcquire
    movementCommand = newMovementCommand
    semaphore.release
  }
}
