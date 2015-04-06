package edu.hkcc.pacmanrobot.server

import edu.hkcc.pacmanrobot.utils.Config
import edu.hkcc.pacmanrobot.utils.studentrobot.code.DeviceInfo

import scala.collection.mutable

/**
 * Created by beenotung on 4/6/15.
 */
class DeviceInfoManager {
  val deviceInfos = new mutable.HashMap[Array[Byte], DeviceInfo]
  val messengerManager = new MessengerManager[DeviceInfo](Config.PORT_DEVICE_INFO,
    message => deviceInfos.put(message.MAC_ADDRESS, message)
  )

  def getDeviceId(macAddress: Array[Byte]): Int = {
    val keys = deviceInfos.keySet.toArray
    var id = -1
    Range(0, keys.length).foreach(i => if (keys(i).equals(macAddress)) id = i)
    id
  }

  def getMacAddress(id: Int): Array[Byte] = {
    val keys = deviceInfos.keys.toArray
    keys(id)
  }
}
