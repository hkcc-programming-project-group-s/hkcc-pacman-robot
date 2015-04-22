package edu.hkcc.pacmanrobot.controller.gamemonitor.core

import edu.hkcc.pacmanrobot.controller.gamemonitor.gui.content.PairControllerRobotJPanel
import edu.hkcc.pacmanrobot.server.Server
import edu.hkcc.pacmanrobot.utils.Config
import edu.hkcc.pacmanrobot.utils.message.messenger.Messenger
import edu.hkcc.pacmanrobot.utils.message.{ControllerRobotPair, DeviceInfo, FlashRequest}
import edu.hkcc.pacmanrobot.utils.studentrobot.code.GameStatus

import scala.collection.JavaConverters._

/**
 * Created by beenotung on 4/15/15.
 */
class GameMonitorSAO(val server: Server) {

  //var deviceInfos: Vector[DeviceInfo] = new Vector[DeviceInfo]
  var reason: String = null
  var canResume: Boolean = true

  var deviceInfoMessenger: Messenger[DeviceInfo] = Messenger.create(Config.PORT_DEVICE_INFO, message => {
    //don't use autoget
  }, null)


  var flashRequestMessenger: Messenger[FlashRequest] = Messenger.create(Config.PORT_FLASH_REQUEST, message => {}, null)

  var controllerRobotPairMessenger: Messenger[ControllerRobotPair] = Messenger.create(Config.PORT_CONTROLLER_ROBOT_PAIR, message => {
    if (pairControllerRobotJPanel != null)
      fetchControllerRobotPairs //TODO
  }, null)

  var _pairControllerRobotJPanel: PairControllerRobotJPanel = null

  var gameStatus: GameStatus = server.gameStatus

  def pairControllerRobotJPanel = _pairControllerRobotJPanel

  def pairControllerRobotJPanel_=(pairControllerRobotJPanel: PairControllerRobotJPanel) = _pairControllerRobotJPanel = pairControllerRobotJPanel

  def fetchControllerRobotPairs: java.util.Collection[ControllerRobotPair] = {
    val v = Vector.fill[ControllerRobotPair](0)(null)
    v.asJavaCollection
    null
    //TODO get pair and save it
  }

  def fetchDeviceInfos: java.util.Collection[DeviceInfo] = {
    null
    //TODO get deviceinfo and save it
  }

  def savePair(controllerRobotPair: ControllerRobotPair) {
    //TODO send pair of controller and robot to server
  }

  def gameStatus_(newStatus: Byte): Unit = {
    //TODO set game status
    val newGameStatus: GameStatus = gameStatus
    newGameStatus.status = newStatus
    gameStatus_(newGameStatus)
  }

  def gameStatus_(gameStatus: GameStatus) = {
    //TODO set game status
  }


}
