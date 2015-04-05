package edu.hkcc.pacmanrobot.robot.edu.hkcc.pacmanrobot.utils.studentrobot

import java.net.Socket

import edu.hkcc.pacmanrobot.utils.Config
import edu.hkcc.pacmanrobot.utils.message.MovementCommand
import edu.hkcc.pacmanrobot.utils.studentrobot.code.Messenger


/**
 * Created by beenotung on 3/26/15.
 */
class MovementCommandMessenger(socket: Socket) {
  def this() = {
    this(Messenger.connect(Config.PORT_MOVEMENT_COMMAND))
  }

  var movementCommand: MovementCommand = MovementCommand.stop
  val messenger = new Messenger[MovementCommand](Config.PORT_MOVEMENT_COMMAND) {
    override def autoGet(message: MovementCommand): Unit = {
      movementCommand = message
    }

    override def getMessage: MovementCommand = {
      while (!inputQueue.isEmpty)
        movementCommand = inputQueue.poll
      movementCommand
    }

    override def sendMessage(message: MovementCommand): Unit = {
      while (!outputQueue.isEmpty)
        outputQueue.poll()
      outputQueue.add(message)
    }
  }

  def sendMessage(message: MovementCommand): Unit = {
    messenger.sendMessage(message)
  }
  def start=messenger.start
}
