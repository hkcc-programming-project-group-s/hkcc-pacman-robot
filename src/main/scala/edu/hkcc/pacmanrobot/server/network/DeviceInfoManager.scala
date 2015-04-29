package edu.hkcc.pacmanrobot.server.network

import java.net.{BindException, InetAddress}
import java.util.concurrent.ConcurrentHashMap
import java.util.function.BiConsumer

import edu.hkcc.pacmanrobot.debug.Debug
import edu.hkcc.pacmanrobot.utils.Config
import edu.hkcc.pacmanrobot.utils.message.DeviceInfo

import scala.collection.mutable.ArrayBuffer

/**
 * Created by beenotung on 4/6/15.
 */
@throws(classOf[BindException])
class DeviceInfoManager {
  Debug.getInstance().printMessage("DeviceInfoManager init 0%")
  val deviceInfos = new ConcurrentHashMap[Array[Byte], DeviceInfo]()
  Debug.getInstance().printMessage("DeviceInfoManager init 5%")


  Debug.getInstance().printMessage("resolving self HostName for DeviceInfo")
  val name = InetAddress.getLocalHost.getHostName
  Debug.getInstance().printMessage("resolved HostName = \t" + name)
  addDeviceInfo(DeviceInfo.create(name, DeviceInfo.DEVICE_TYPE_SERVER))
  val messengerManager = new MessengerManager[DeviceInfo](Config.PORT_DEVICE_INFO, initMessenger_func = { _ => {} }, autoGet_func = { (remoteMacAddress, message) => {
    if (message.shouldSave) {
      deviceInfos.put(message.MAC_ADDRESS, message)
      message.shouldSave = false
    }
    else
      response(remoteMacAddress, message)
  }
  })

  Debug.getInstance().printMessage("DeviceInfoManager init 20%")

  def addDeviceInfo(deviceInfo: DeviceInfo) = {
    //Debug.getInstance().printMessage("null check deviceInfo: "+deviceInfo.asInstanceOf[Object])
    //Debug.getInstance().printMessage("null check deviceInfo.mac: "+deviceInfo.MAC_ADDRESS.asInstanceOf[Object])
    //Debug.getInstance().printMessage("null check hashmap: "+deviceInfos.asInstanceOf[Object])
    deviceInfos.put(deviceInfo.MAC_ADDRESS, deviceInfo)
  }

  Debug.getInstance().printMessage("DeviceInfoManager init 90%")

  def response(remoteMacAddress: Array[Byte], deviceInfo: DeviceInfo): Unit = {
    getDeviceInfosByDeviceType(deviceInfo.deviceType).foreach(deviceInfo =>
      messengerManager.sendByMacAddress(remoteMacAddress, deviceInfo)
    )
    messengerManager.sendByMacAddress(remoteMacAddress, null)
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

  def getDeviceInfoByMacAddress(macAddress: Array[Byte]): DeviceInfo = {
    var device: DeviceInfo = null
    deviceInfos.forEach(new BiConsumer[Array[Byte], DeviceInfo] {

      override def accept(k: Array[Byte], v: DeviceInfo): Unit = {
        if (macAddress.equals(v.MAC_ADDRESS))
          device = v
      }
    })
    device
  }

  def update(macAddress: Array[Byte]) = {
    deviceInfos.get(macAddress).lastConnectionTime = System.currentTimeMillis()
  }

  def update(ip: String) = {
    deviceInfos.forEach(new BiConsumer[Array[Byte], DeviceInfo] {
      override def accept(k: Array[Byte], v: DeviceInfo): Unit = {
        if (v.ip.equals(ip))
          v.lastConnectionTime = System.currentTimeMillis()
      }
    })
  }

  Debug.getInstance().printMessage("DeviceInfoManager init 100%")
}
