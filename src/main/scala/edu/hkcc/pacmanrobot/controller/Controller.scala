package edu.hkcc.pacmanrobot.controller

import edu.hkcc.pacmanrobot.utils.GameDevice
import edu.hkcc.pacmanrobot.utils.message.MovementCommand
import edu.hkcc.pacmanrobot.utils.message.messenger.Messenger


/**
 * Created by beenotung on 3/23/15.
 */
abstract class Controller extends GameDevice {
  val movementCommandMessenger: Messenger[MovementCommand] = Messenger.create[MovementCommand](MovementCommand.port, { message => }, null)
  var shouldSend = true

  def sendCommand(command: MovementCommand) {
    movementCommandMessenger.sendMessage(command)
  }

  override def gameSetup: Unit = {
    shouldSend = false
  }

  override def gameResume: Unit = {
    shouldSend = true
  }

  override def gamePause: Unit = {
    shouldSend = false
  }

  override def gameStop: Unit = {
    shouldSend = false
  }

  override def gameStart: Unit = {
    shouldSend = true
  }

  override def run = {
    super.run
    new Thread(new Runnable {
      override def run(): Unit = {

      }
    })
  }.start()
}
