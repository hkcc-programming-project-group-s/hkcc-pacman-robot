package studentrobot.code


/**
 * Created by beenotung on 3/26/15.
 */
class MovementCommandMessenger extends Messenger[MovementCommand](MovementCommand.port) {

  override def getMessage: MovementCommand = {
    var command: MovementCommand = MovementCommand.stop
    while (!inputQueue.isEmpty)
      command = inputQueue.poll
    command
  }

  override def autoGet(message: MovementCommand): Unit = ???
}
