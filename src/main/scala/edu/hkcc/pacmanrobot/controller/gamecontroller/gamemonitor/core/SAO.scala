package edu.hkcc.pacmanrobot.controller.gamemonitor.core

import edu.hkcc.pacmanrobot.controller.gamemonitor.gui.{PairControllerRobotJPanel, PositionSetting}
import edu.hkcc.pacmanrobot.controller.gamemonitor.gui.utils.DeviceInfoJPanelHandler
import edu.hkcc.pacmanrobot.utils.Config
import edu.hkcc.pacmanrobot.utils.message.ControllerRobotPair
import edu.hkcc.pacmanrobot.utils.message.DeviceInfo
import edu.hkcc.pacmanrobot.utils.message.FlashRequest
import edu.hkcc.pacmanrobot.utils.message.messenger.Messenger
import scala.Function1
import scala.runtime.BoxedUnit

/**
 * Created by beenotung on 4/15/15.
 */
class SAO {
  var deviceInfoMessenger: Messenger[DeviceInfo] = Messenger.create(Config.PORT_DEVICE_INFO, message => {
    if (deviceInfoJPanelHandler != null)
      deviceInfoJPanelHandler.receiveDeviceInfo(message)
  }, null)
  var _deviceInfoJPanelHandler: DeviceInfoJPanelHandler = null

  def deviceInfoJPanelHandler = _deviceInfoJPanelHandler

  def deviceInfoJPanelHandler_=(deviceInfoJPanelHandler: DeviceInfoJPanelHandler) = _deviceInfoJPanelHandler = deviceInfoJPanelHandler

  def setHandler(newHandler: DeviceInfoJPanelHandler) {
    deviceInfoJPanelHandler = newHandler
  }

  var flashRequestMessenger: Messenger[FlashRequest] = Messenger.create(Config.PORT_FLASH_REQUEST, message => {}, null)

  var controllerRobotPairMessenger: Messenger[ControllerRobotPair] = Messenger.create(Config.PORT_CONTROLLER_ROBOT_PAIR, message => {
    if (pairControllerRobotJPanel != null)
      pairControllerRobotJPanel.receivePair(message)
  }, null)

  var _pairControllerRobotJPanel: PairControllerRobotJPanel = null

  def pairControllerRobotJPanel = _pairControllerRobotJPanel

  def pairControllerRobotJPanel_=(pairControllerRobotJPanel: PairControllerRobotJPanel) = _pairControllerRobotJPanel = pairControllerRobotJPanel
}
