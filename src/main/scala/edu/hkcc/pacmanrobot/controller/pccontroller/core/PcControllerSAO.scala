package edu.hkcc.pacmanrobot.controller.pccontroller.core

import edu.hkcc.pacmanrobot.utils.GameDevice
import edu.hkcc.pacmanrobot.utils.message.DeviceInfo

/**
 * Created by 13058456a on 4/15/2015.
 */
class PcControllerSAO extends GameDevice{
  override var deviceInfo: DeviceInfo = DeviceInfo.create("PC Controller",DeviceInfo.DEVICE_TYPE_CONTROLLER)

  override def loop: Unit = ???

  override def gameResume: Unit = ???

  override def gamePause: Unit = ???

  override def setup: Unit = ???

  override def gameStart: Unit = ???

  override def gameStop: Unit = ???

  override def gameSetup: Unit = ???


}
