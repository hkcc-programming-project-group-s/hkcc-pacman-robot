package edu.hkcc.pacmanrobot.controller.pccontroller.core

import edu.hkcc.pacmanrobot.controller.pccontroller.PcControllerJFrame
import edu.hkcc.pacmanrobot.server.network.Server_NetworkThread
import edu.hkcc.pacmanrobot.utils.GameDevice
import edu.hkcc.pacmanrobot.utils.message.messenger.Messenger
import edu.hkcc.pacmanrobot.utils.message.{DeviceInfo, MovementCommand}
import edu.hkcc.pacmanrobot.utils.studentrobot.code.GameStatus


/**
 * Created by Beeno Tung on 4/15/2015.
 */
object PcController {
  /*var instance: PcControllerSAO = null
  def getInstance: PcControllerSAO = {
    if (instance == null)
      synchronized {
        if (instance == null) instance = new PcControllerSAO()
      }
    instance
  }*/
}

object PcControllerSAO extends GameDevice {
  val movementCommandMessenger: Messenger[MovementCommand] = Messenger.create[MovementCommand](MovementCommand.port, { message => }, null)
  override var deviceInfo: DeviceInfo = DeviceInfo.create("PC Controller", DeviceInfo.DEVICE_TYPE_CONTROLLER)
  var reason: String = null
  var canResume: Boolean = true;
  var isControllerPause: Boolean = false;
  var shouldSend = true

  override def loop: Unit = ???

  override def gameResume: Unit = {
    shouldSend = true
    PcControllerJFrame.getInstance().palying
  }

  override def gamePause: Unit = {
    shouldSend = false
    reason = gameStatus.message
    if (!gameStatus.furtherInfo.equals(0)) {
      PcControllerJFrame.getInstance().unresume()
      canResume = false
    }
    else if (gameStatus.furtherInfo.equals(0)) {
      PcControllerJFrame.getInstance().reaume()
      canResume = true
      reason = null
    }
  }

  override def gameStop: Unit = {
    shouldSend = false
  }

  override def gameSetup: Unit = {
    shouldSend = false
  }

  override def gameStart: Unit = {
    shouldSend = true
  }

  /**
   * fetch device info from server
   * @return
   * deviceInfo collection (e.g.Vector)
   */
  def fetchDeviceInfos: java.util.Collection[DeviceInfo] = {
    null
    //TODO get deviceinfo and return it
  }

  def sendMovementCommand(x: Int, y: Int): Unit = {
    //TODO send movement command

  }

  def sendCommand(command: MovementCommand) {
    movementCommandMessenger.sendMessage(command)
  }


  def sendGameStatus(status: Byte): Unit = {
    if (status.equals(GameStatus.STATE_STOP)) {
      //TODO send stop to server
    }
    else if (status.equals(GameStatus.STATE_PAUSE)) {
      reason = "Pause by" + deviceInfo.name
      //TODO send reason and pause to server
    }
    else if (status.equals(GameStatus.STATE_RESUME)) {
      //TODO send resume to server
    }
  }

  def getReason: String = {
    reason
  }

  override def init = {
    //TODO
  }
}
