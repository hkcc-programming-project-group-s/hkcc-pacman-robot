package edu.hkcc.pacmanrobot.utils.message

import edu.hkcc.pacmanrobot.debug.Debug
import edu.hkcc.pacmanrobot.utils.Config
import edu.hkcc.pacmanrobot.utils.message.messenger.Messenger
import edu.hkcc.pacmanrobot.utils.network.NetworkUtils

/**
 * Created by 13058456a on 3/21/2015.
 */
object DeviceInfo extends Message {
  Debug.getInstance().printMessage("Singleton DeviceInfo first loaded")
  override val port: Int = Config.PORT_DEVICE_INFO

  val DEVICE_TYPE_DELETE: Byte = 0
  val DEVICE_TYPE_CONTROLLER: Byte = 1
  val DEVICE_TYPE_UNCLASSED_ROBOT: Byte = 2
  val DEVICE_TYPE_ASSIGNMENT_ROBOT: Byte = 3
  val DEVICE_TYPE_STUDENT_ROBOT: Byte = 4
  val DEVICE_TYPE_DEADLINE_ROBOT: Byte = 5
  val DEVICE_TYPE_SERVER: Byte = 6
  val DEVICE_TYPE_GMAE_REPORT: Byte = 7

  val ROBOT_SET = Set(DEVICE_TYPE_ASSIGNMENT_ROBOT, DEVICE_TYPE_DEADLINE_ROBOT, DEVICE_TYPE_STUDENT_ROBOT, DEVICE_TYPE_UNCLASSED_ROBOT)

  val MAC_ADDRESS_BYTES = 6
  val NAME_MAX_LENGTH = 32

  def create(name: String, deviceType: Byte): DeviceInfo = {
    Debug.getInstance().printMessage("resolving self IP for DeviceInfo")
    val ip = NetworkUtils.getOnlineInetAddress.getHostAddress
    Debug.getInstance().printMessage("resolved self IP = \t" + ip.toString)
    new DeviceInfo(_name = name, ip = ip, _deviceType = deviceType, shouldSave = true)
  }

  def request(deviceType: Byte, messenger: Messenger[DeviceInfo]) = {
    messenger.sendMessage(new DeviceInfo(_deviceType = deviceType, shouldSave = false, _name = "", ip = ""))
  }

  def isRobot(deviceType: Byte): Boolean = {
    deviceType match {
      case DeviceInfo.DEVICE_TYPE_UNCLASSED_ROBOT => true
      case DeviceInfo.DEVICE_TYPE_ASSIGNMENT_ROBOT => true
      case DeviceInfo.DEVICE_TYPE_STUDENT_ROBOT => true
      case DeviceInfo.DEVICE_TYPE_DEADLINE_ROBOT => true
      case _ => false
    }
  }

  def MakeRequest(macAddress: Array[Byte]): DeviceInfo = {
    new DeviceInfo(MAC_ADDRESS = macAddress, _name = null, ip = null, shouldSave = false, _deviceType = 0)
  }
}

/**
 * @param MAC_ADDRESS
 * @param _name
 * @param ip
 * @param _deviceType
 * @param lastConnectionTime
 * @param shouldSave
 * true => server save
 * false => server response to client (send all that type)
 */
class DeviceInfo(val MAC_ADDRESS: Array[Byte] = NetworkUtils.getLocalMacAddress, private var _name: String, var ip: String, var _deviceType: Byte, var lastConnectionTime: Long = 0L, var shouldSave: Boolean) extends Message {
  //Debug.getInstance().printMessage("DeviceInfo init 0%")

  def set(newInfo: DeviceInfo): Unit = {
    name_(newInfo.name)
    ip = newInfo.ip
    _deviceType = newInfo._deviceType
    lastConnectionTime = newInfo.lastConnectionTime
    set_shouldSave
  }

  def name_(name: String) = {
    _name = name
    set_shouldSave
  }

  //Debug.getInstance().printMessage("DeviceInfo init 20%")

  def set_shouldSave = shouldSave = true

  override def port(): Int = Config.PORT_DEVICE_INFO

  //Debug.getInstance().printMessage("DeviceInfo init 40%")

  override def toString: String = {
    "name: " + name + "\tip: " + ip + "\t mac address: " + MAC_ADDRESS.toVector.toString() + "\t device type: " + deviceType
  }

  def name = _name

  //Debug.getInstance().printMessage("DeviceInfo init 80%")

  def deviceType = _deviceType

  def deviceType_=(newType: Byte) = {
    _deviceType = newType
    set_shouldSave
  }

  //Debug.getInstance().printMessage("DeviceInfo init 100%")
}