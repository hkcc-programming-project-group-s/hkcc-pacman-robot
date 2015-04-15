package edu.hkcc.pacmanrobot.server

import java.net.InetAddress
import java.util.concurrent.ConcurrentHashMap
import java.util.function.BiConsumer

import edu.hkcc.pacmanrobot.utils.Config
import edu.hkcc.pacmanrobot.utils.message.DeviceInfo

import scala.collection.mutable.ArrayBuffer

/**
 * Created by beenotung on 4/6/15.
 */
class DeviceInfoManager {
  val deviceInfos = new ConcurrentHashMap[Array[Byte], DeviceInfo]()

  {
    val server = DeviceInfo.create(InetAddress.getLocalHost.getHostName, DeviceInfo.DEVICE_TYPE_SERVER)
    deviceInfos.put(server.MAC_ADDRESS, server)
  }

  val messengerManager = new MessengerManager[DeviceInfo](Config.PORT_DEVICE_INFO, (remoteMacAddress, message) =>
    if (message.shouldSave)
      deviceInfos.put(message.MAC_ADDRESS, message)
    else
      response(remoteMacAddress, message)
  )

  def response(remoteMacAddress: Array[Byte], deviceInfo: DeviceInfo): Unit = {
    getDeviceInfosByDeviceType(deviceInfo.deviceType).foreach(deviceInfo =>
      messengerManager.sendByMacAddress(remoteMacAddress, deviceInfo)
    )
  }

  def getDeviceInfosByDeviceType(deviceType: Byte): Array[DeviceInfo] = {
    val result = new ArrayBuffer[DeviceInfo]()
    deviceInfos.forEach(new BiConsumer[Array[Byte], DeviceInfo] {
      override def accept(k: Array[Byte], v: DeviceInfo): Unit = {
        if (deviceType.equals(v.deviceType))
          result :+ v
      }
    })
    result.toArray
  }

  def update(macAddress: Array[Byte]) = {
    deviceInfos.get(macAddress).lastConnectionTime = System.currentTimeMillis()
  }
}
