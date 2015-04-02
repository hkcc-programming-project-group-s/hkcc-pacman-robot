package edu.hkcc.pacmanrobot.server

import java.net.ServerSocket

import edu.hkcc.pacmanrobot.utils.studentrobot.code.{Messenger, DeviceInfo}
import edu.hkcc.pacmanrobot.utils.{Config, Device}

/**
 * Created by 13058456a on 4/2/2015.
 */
object Server extends Device {
  var deviceInfoMessengers=Vector.empty[Messenger[DeviceInfo]]
  gameSetup
  val deviceRegisterThread = new Thread(new Runnable {
    override def run(): Unit = {
      val deviceInfoServerSocket = new ServerSocket(Config.PORT_DEVICE_INFO)
      while (true) {
        deviceInfoMessengers:+=new Messenger[DeviceInfo](deviceInfoServerSocket.accept(),Config.PORT_DEVICE_INFO) {
          override def autoGet(message: DeviceInfo): Unit = ???
        }
      }
    }
  })

  override def gameSetup: Unit = {
    deviceRegisterThread.start
      ..Thread.start
  }

  override def gameResume: Unit = ???

  override def gamePause: Unit = ???

  override def setup: Unit = ???

  override def gameStart: Unit = ???

  override def gameStop: Unit = ???
}
