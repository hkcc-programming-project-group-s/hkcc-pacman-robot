package edu.hkcc.pacmanrobot.controller.pccontroller.core

import edu.hkcc.pacmanrobot.utils.{Config, GameDevice}
import edu.hkcc.pacmanrobot.utils.message.DeviceInfo
import edu.hkcc.pacmanrobot.utils.message.messenger.Messenger

/**
 * Created by 13058456a on 4/15/2015.
 */
class PcControllerSAO extends GameDevice {
  override var deviceInfo: DeviceInfo = DeviceInfo.create("PC Controller", DeviceInfo.DEVICE_TYPE_CONTROLLER)

  override def loop: Unit = ???

  override def gameResume: Unit = ???

  override def gamePause: Unit = ???

  override def setup: Unit = ???

  override def gameStart: Unit = ???

  override def gameStop: Unit = ???

  override def gameSetup: Unit = ???

  def fetchDeviceInfos:java.util.Collection[DeviceInfo]= {
    null
    //TODO get deviceinfo and save it
  }

  def sendMovementCommand(x:Int,y:Int): Unit ={

  }

}
