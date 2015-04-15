package edu.hkcc.pacmanrobot.utils.message

import java.net.{InetAddress, NetworkInterface}

import edu.hkcc.pacmanrobot.utils.Config
import edu.hkcc.pacmanrobot.utils.message.messenger.Messenger


/**
 * Created by 13058456a on 3/21/2015.
 */
object DeviceInfo extends Message {
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

  def getLocalMacAddress: Array[Byte] = {
    NetworkInterface.getByInetAddress(InetAddress.getLocalHost).getHardwareAddress
  }

  def create(name: String, deviceType: Byte): DeviceInfo = {
    new DeviceInfo(name, InetAddress.getLocalHost.getHostAddress, deviceType, shouldSave = true)
  }

  def request(deviceType: Byte, messenger: Messenger[DeviceInfo]) = {
    messenger.sendMessage(new DeviceInfo(deviceType = deviceType, shouldSave = false, name = "", ip = ""))
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
}

import edu.hkcc.pacmanrobot.utils.message.DeviceInfo.getLocalMacAddress

/**
 *
 * @param name
 * @param ip
 * @param deviceType
 * @param lastConnectionTime
 * @param shouldSave
 * true => server save
 * false => server response to client (send all that type)
 */
class DeviceInfo(var name: String, var ip: String, var deviceType: Byte, var lastConnectionTime: Long = 0, val shouldSave: Boolean) extends Serializable {
  val MAC_ADDRESS: Array[Byte] = getLocalMacAddress

  def deviceType_=(newType: Byte): Unit = {
    deviceType = newType
  }

  def set(newInfo: DeviceInfo): Unit = {
    name = newInfo.name
    ip = newInfo.ip
    deviceType = newInfo.deviceType
    lastConnectionTime = newInfo.lastConnectionTime
  }
}