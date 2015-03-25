package hkccpacmanrobot.controller

import hkccpacmanrobot.utils.message.{Device, Messenger, MovementCommand}

/**
 * Created by beenotung on 3/23/15.
 */
class Controller extends Device {
  val movementCommandMessenger: Messenger[MovementCommand] = Messenger.create[MovementCommand](MovementCommand)
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
  override def run={
    super.run
    new Thread(new Runnable {
      override def run(): Unit = {

      }
    })
  }.start()
}
