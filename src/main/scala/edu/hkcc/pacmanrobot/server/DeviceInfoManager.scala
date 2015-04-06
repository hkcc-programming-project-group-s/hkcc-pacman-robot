package edu.hkcc.pacmanrobot.server

import edu.hkcc.pacmanrobot.utils.Config
import edu.hkcc.pacmanrobot.utils.message.DeviceInfo

import scala.collection.parallel.mutable.ParArray

/**
 * Created by beenotung on 4/6/15.
 */
class DeviceInfoManager {
  var deviceInfos = ParArray.empty[DeviceInfo]
  val messengerManager = new MessengerManager[DeviceInfo](Config.PORT_DEVICE_INFO, (_, message) =>
    if (deviceInfos.foldLeft(false)((contain, deviceInfo) => contain || message.equals(deviceInfo)))
      deviceInfos :+= message)

  def getDeviceIdByMacAddress(macAddress: Array[Byte]): Int = {
    var id = -1
    Range(0, deviceInfos.length).foreach(i => if (macAddress.equals(deviceInfos(i))) id = i)
    id
  }

  def getMacAddressByDeviceId(deviceId: Int): Array[Byte] = {
    deviceInfos(deviceId).MAC_ADDRESS
  }
}
