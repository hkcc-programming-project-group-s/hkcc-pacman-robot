package edu.hkcc.pacmanrobot.server.network

import java.net.NetworkInterface
import java.util.concurrent.ConcurrentLinkedQueue

import edu.hkcc.pacmanrobot.utils.lang.ConcurrencyDrawer
import edu.hkcc.pacmanrobot.utils.message.DeviceInfo
import edu.hkcc.pacmanrobot.utils.message.udpmessage.UDPMessengerSingleton.ReceiveActor
import edu.hkcc.pacmanrobot.utils.message.udpmessage.{Decoder, Encoder, UDPMessengerSingleton}
import edu.hkcc.pacmanrobot.utils.studentrobot.code.GameStatus
import edu.hkcc.pacmanrobot.utils.{Config, Worker}

/**
 * Created by beenotung on 4/29/15.
 */
object udpManager extends Thread {
  val messenger = UDPMessengerSingleton.getInstance(new ReceiveActor {
    override def apply(ip: String): Unit = {
      Server_NetworkThread.getInstance().deviceInfoManager.update(ip)
    }
  })
  val deviceInfos = new ConcurrentLinkedQueue[DeviceInfo]()
  val deviceInfoThread = new Thread() {
    override def run = {
      //output
      Worker.forkAndStart({
        while (running) {
          if (!deviceInfos.isEmpty) {
            val info = deviceInfos.poll()
            val data = Encoder.getInstance().getDeviceInfo(info.MAC_ADDRESS, info.name, info.ip, info.deviceType, info.lastConnectionTime, info.shouldSave)
            UDPMessengerSingleton.getInstance().send(data, 0, data.length, Config.PORT_DEVICE_INFO)
          }
        }
      })
      //input
      while (running) {
        val wrapper = messenger.deviceInfoBytesDrawer.waitGetContent()
        val deviceInfo = Decoder.getInstance().getDeviceInfo(wrapper.data)
        Server_NetworkThread.getInstance().deviceInfoManager.addDeviceInfo(deviceInfo)
        Server_NetworkThread.getInstance().deviceInfoManager.update(NetworkInterface.getByInetAddress(wrapper.senderAddress).getHardwareAddress)
      }
    }
  }
  val movementCommandThread = new Thread() {
    override def run = {
      while (running) {
        val wrapper = messenger.movementCommandBytesDrawer.waitGetContent()
        val movementCommand = Decoder.getInstance().getMovementCommand(wrapper.data)
        Server_NetworkThread.getInstance().copyMovementCommand(NetworkInterface.getByInetAddress(wrapper.senderAddress).getHardwareAddress, movementCommand)
      }
    }
  }
  val gameStatusThread = new Thread() {
    override def run = {
      while (running) {
        val status = gameStatus.waitGetContent()
        val data = Encoder.getInstance().getGameStatus(status.status, status.message, status.furtherInfo)
        messenger.send(data, 0, data.length, Config.PORT_GAME_STATUS)
      }
    }
  }
  var running = false
  var gameStatus = new ConcurrencyDrawer[GameStatus]()

  override def run = {
    running = true
    deviceInfoThread.start
    movementCommandThread.start
    //    while (running) {
    //  }
  }

  override def start = {
    if (!running)
      super.start
  }
}
