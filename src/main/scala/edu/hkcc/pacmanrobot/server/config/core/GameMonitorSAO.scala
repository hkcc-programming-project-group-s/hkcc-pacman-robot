package edu.hkcc.pacmanrobot.server.config.core

import edu.hkcc.pacmanrobot.server.network.Server_NetworkThread
import edu.hkcc.pacmanrobot.utils.message.{ControllerRobotPair, DeviceInfo}
import edu.hkcc.pacmanrobot.utils.studentrobot.code.GameStatus

import scala.collection.JavaConverters._

/**
 * Created by beenotung on 4/15/15.
 */
object GameMonitorSAO {
  //var deviceInfos: Vector[DeviceInfo] = new Vector[DeviceInfo]
  //var _pairControllerRobotJPanel: PairControllerRobotContentPanel = null

  //  var deviceInfoMessenger: Messenger[DeviceInfo] = Messenger.create(Config.PORT_DEVICE_INFO, message => {
  //    //don't use autoget
  //  }, null)

  def requestFlash(macAddress: Array[Byte], shouldLight: Boolean) = {
    if (shouldLight)
      Server_NetworkThread.getInstance().flashLightManager.flash(macAddress)
    else
      Server_NetworkThread.getInstance().flashLightManager.clear
  }

  //def pairControllerRobotJPanel = _pairControllerRobotJPanel

  //def pairControllerRobotJPanel_=(pairControllerRobotJPanel: PairControllerRobotContentPanel) = _pairControllerRobotJPanel = pairControllerRobotJPanel

  def fetchControllerRobotPairs: java.util.Collection[ControllerRobotPair] = {
    val v = Vector.fill[ControllerRobotPair](0)(null)
    v.asJavaCollection
    null
    //TODO get pair and save it
  }

  def updateDeviceInfo(deviceInfo: DeviceInfo) = {
    Server_NetworkThread.getInstance().deviceInfoManager.deviceInfos.put(deviceInfo.MAC_ADDRESS, deviceInfo)
  }

  def fetchDeviceInfos: java.util.Collection[DeviceInfo] = {
    null
    //TODO get deviceinfo and save it
  }

  def savePair(controllerRobotPair: ControllerRobotPair) {
    Server_NetworkThread.getInstance().controllerRobotPairManager.setControllerRobotPair(
      controllerRobotPair.controller_macAddress, controllerRobotPair.robot_macAddress
    )
  }

  def gameStatus_(newStatus: Byte): Unit = {
    gameStatus_(newStatus, 0)
  }

  def gameStatus_(newStatus: Byte, furtherInfo: Byte = 0): Unit = {
    gameStatus_(new GameStatus(newStatus, furtherInfo = furtherInfo))
  }

  def gameStatus_(newGameStatus: GameStatus) = {
    Server_NetworkThread.getInstance().switchGameStatus(newGameStatus)
  }

  def clearFlash = {
    Server_NetworkThread.getInstance().flashLightManager.clear
  }

  def canResume: Boolean = {
    gameStatus.furtherInfo.equals(GameStatus.STATE_REQUEST)
  }

  def gameStatus = Server_NetworkThread.getInstance().gameStatus

  def reason: String = gameStatus.message
}
