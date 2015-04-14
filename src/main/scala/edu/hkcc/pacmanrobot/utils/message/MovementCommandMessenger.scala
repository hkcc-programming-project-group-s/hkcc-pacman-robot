package edu.hkcc.pacmanrobot.utils.message

import java.net.Socket

import edu.hkcc.pacmanrobot.server.MessengerManager
import edu.hkcc.pacmanrobot.utils.Config


/**
 * Created by beenotung on 3/26/15.
 */
abstract class MovementCommandMessenger(socket: Socket,messengerManager: MessengerManager[MovementCommand]) {
  def autoGet_func(message: MovementCommand)

  def this(isServer: Boolean) = {
    this(Messenger.connect(Config.PORT_MOVEMENT_COMMAND,isServer), isServer)
  }

  var movementCommand: MovementCommand = MovementCommand.stop
  val messenger = new Messenger[MovementCommand](Config.PORT_MOVEMENT_COMMAND, messengerManager) {
    override def autoGet(message: MovementCommand): Unit = {
      autoGet_func(message)
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

  def start = messenger.start

  def getRemoteMacAddress = messenger.getRemoteMacAddress
}
