package edu.hkcc.pacmanrobot.utils.studentrobot.code

import java.net.{InetAddress, NetworkInterface}

import edu.hkcc.pacmanrobot.utils.Config
import edu.hkcc.pacmanrobot.utils.map.Message

/**
 * Created by 13058456a on 3/21/2015.
 */
object DeviceInfo extends Message {
  override val port: Int = Config.PORT_DEVICE_INFO

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
}

import edu.hkcc.pacmanrobot.utils.studentrobot.code.DeviceInfo.getLocalMacAddress

class DeviceInfo(val MAC_ADDRESS: Array[Byte] = getLocalMacAddress, var name: String, var ip: String, var deviceType: Byte, var lastConnectionTime: Long = 0) extends Serializable {
  def set(newInfo: DeviceInfo): Unit = {
    name = newInfo.name
    ip = newInfo.ip
    deviceType = newInfo.deviceType
    lastConnectionTime = newInfo.lastConnectionTime
  }
}