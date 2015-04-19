package edu.hkcc.pacmanrobot.controller.gamemonitor.core

import edu.hkcc.pacmanrobot.controller.gamemonitor.gui.content.PairControllerRobotJPanel
import edu.hkcc.pacmanrobot.controller.gamemonitor.gui.utils.DeviceInfoJPanelHandler
import edu.hkcc.pacmanrobot.utils.Config
import edu.hkcc.pacmanrobot.utils.message.{ControllerRobotPair, DeviceInfo, FlashRequest}
import edu.hkcc.pacmanrobot.utils.message.messenger.Messenger

/**
 * Created by beenotung on 4/15/15.
 */
class GameMonitorSAO {
  var deviceInfoMessenger: Messenger[DeviceInfo] = Messenger.create(Config.PORT_DEVICE_INFO, message => {
    if (deviceInfoJPanelHandler != null)
      deviceInfoJPanelHandler.receiveDeviceInfo(message)
  }, null)
  var _deviceInfoJPanelHandler: DeviceInfoJPanelHandler = null
  var flashRequestMessenger: Messenger[FlashRequest] = Messenger.create(Config.PORT_FLASH_REQUEST, message => {}, null)
  var controllerRobotPairMessenger: Messenger[ControllerRobotPair] = Messenger.create(Config.PORT_CONTROLLER_ROBOT_PAIR, message => {
    if (pairControllerRobotJPanel != null)
      pairControllerRobotJPanel.receivePair(message)
  }, null)
  var _pairControllerRobotJPanel: PairControllerRobotJPanel = null

  def setHandler(newHandler: DeviceInfoJPanelHandler) {
    deviceInfoJPanelHandler = newHandler
  }

  def deviceInfoJPanelHandler = _deviceInfoJPanelHandler

  def deviceInfoJPanelHandler_=(deviceInfoJPanelHandler: DeviceInfoJPanelHandler) = _deviceInfoJPanelHandler = deviceInfoJPanelHandler

  def pairControllerRobotJPanel = _pairControllerRobotJPanel

  def pairControllerRobotJPanel_=(pairControllerRobotJPanel: PairControllerRobotJPanel) = _pairControllerRobotJPanel = pairControllerRobotJPanel
}
