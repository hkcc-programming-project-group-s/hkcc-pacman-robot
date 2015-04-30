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

  var _mac_map = new ConcurrentHashMap[String, Array[Byte]]()

  def addDeviceInfo(deviceInfo: DeviceInfo) = {
    //Debug.getInstance().printMessage("null check deviceInfo: "+deviceInfo.asInstanceOf[Object])
    //Debug.getInstance().printMessage("null check deviceInfo.mac: "+deviceInfo.MAC_ADDRESS.asInstanceOf[Object])
    //Debug.getInstance().printMessage("null check hashmap: "+deviceInfos.asInstanceOf[Object])
    //  Debug.getInstance().printMessage("new device info registered: " + deviceInfo.toString)
    deviceInfos.putIfAbsent(duplicateFilter(deviceInfo.MAC_ADDRESS), deviceInfo)
  }

  def duplicateFilter(mac_address: Array[Byte]): Array[Byte] = {
    mac_map.putIfAbsent(mac_address.toVector.toString.intern(), mac_address)
    val result = mac_map.get(mac_address.toVector.toString.intern())
    //Debug.getInstance().printMessage("mac key: "+mac_address.toVector.toString.intern().hashCode)
    //Debug.getInstance().printMessage("mac object: "+result)
    result
  }

  def mac_map: ConcurrentHashMap[String, Array[Byte]] = {
    if (_mac_map == null) {
      getClass.synchronized {
        if (_mac_map == null)
          _mac_map = new ConcurrentHashMap[String, Array[Byte]]()
      }
    }
    _mac_map
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

  def getAll: Array[DeviceInfo] = {
    val result = new ArrayBuffer[DeviceInfo]()
    //Debug.getInstance().printMessage("get all start")
    deviceInfos.forEach(new BiConsumer[Array[Byte], DeviceInfo] {
      override def accept(k: Array[Byte], v: DeviceInfo): Unit = {
        result += v
        //Debug.getInstance().printMessage("get all: " + v.toString)
      }
    })
    //Debug.getInstance().printMessage("get all end")
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
    try
      deviceInfos.get(macAddress).lastConnectionTime = System.currentTimeMillis()
    catch {
      case e: NullPointerException => {
        // the device is not registered
      }
      case e: Exception => {
        //just in case
      }
    }
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
