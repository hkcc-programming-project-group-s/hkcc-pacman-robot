package studentrobot.code


/**
 * Created by beenotung on 3/26/15.
 */
class MovementCommandMessenger extends Messenger[MovementCommand](MovementCommand.port) {
  var command: MovementCommand = MovementCommand.stop

  override def getMessage: MovementCommand = {
    while (!inputQueue.isEmpty)
      command = inputQueue.poll
    command
  }

  override def autoGet(message: MovementCommand): Unit = {
    //println(message.toString)
    command=message
  }
}
