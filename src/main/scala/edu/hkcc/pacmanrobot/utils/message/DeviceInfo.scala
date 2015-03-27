package edu.hkcc.pacmanrobot.utils.studentrobot.code

/**
 * Created by 13058456a on 3/21/2015.
 */
object DeviceInfo extends Message {
  override val port: Int = Config.PORT_DEVICE_INFO

  val DEVICE_TYPE_CONTROLLER: Byte = 1
  val DEVICE_TYPE_ASSIGNMENT_ROBOT: Byte = 2
  val DEVICE_TYPE_STUDENT_ROBOT: Byte = 3
  val DEVICE_TYPE_DEADLINE_ROBOT: Byte = 4
  val DEVICE_TYPE_SERVER: Byte = 5
}

class DeviceInfo(var name: String, var IP: String, var id: Long, var lastConnectionTime: Long = 0) extends Serializable {
  def set(newInfo: DeviceInfo): Unit = {
    name = newInfo.name
    IP = newInfo.IP
    id = newInfo.id
    lastConnectionTime = newInfo.lastConnectionTime
  }

  def this() = {
    this("unnamed_device", "127.0.0.1", 0, 0)
  }
}