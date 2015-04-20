package edu.hkcc.pacmanrobot.server

import java.net.Socket

import edu.hkcc.pacmanrobot.utils.Config
import edu.hkcc.pacmanrobot.utils.map.ObstacleMap
import edu.hkcc.pacmanrobot.utils.message.DeviceInfo
import edu.hkcc.pacmanrobot.utils.message.messenger.{Messenger, ObstacleMapMessenger}

import scala.collection.mutable.ArrayBuffer

/**
 * Created by beenotung on 3/21/15.
 */


/**
 * Created by beenotung on 3/27/15.
 */

object ObstacleMapManager {
  val obstacleMap = new ObstacleMap
  val messengers = new ArrayBuffer[ObstacleMapMessenger]()

  def autoGet_func(getRemoteMacAddress: Array[Byte], message: ObstacleMap): Unit = {
    if (messengers.length > 1)
      messengers.foreach(m => {
        if (!m.getRemoteMacAddress.equals(DeviceInfo.getLocalMacAddress))
          m.sendMessage(message)
      })
    obstacleMap.merge(message)
  }
}

class ObstacleMapManager extends MessengerManager[ObstacleMap](Config.PORT_MAP, initMessenger_func = messenger => messenger.sendMessage(ObstacleMapManager.obstacleMap), autoGet_func = (remoteMacAddress, map) => ObstacleMapManager.autoGet_func(remoteMacAddress, map)) {
  //override def messengers = ???
  /*override def genMessenger(socket: Socket): Messenger[ObstacleMap] = {
    new ObstacleMapMessenger(ObstacleMapManager.obstacleMap, socket, this)
  }*/
  override def genMessenger(socket: Socket) = {
    val newMessenger = new Messenger[ObstacleMap](socket, Config.PORT_MAP, this) {
      override def autoGet(message: ObstacleMap): Unit = {
        ObstacleMapManager.autoGet_func(getRemoteMacAddress, message)
      }
    }
    //val newMessenger = new ObstacleMapMessenger(ObstacleMapManager.obstacleMap, socket, this)
    println("client connected: " + newMessenger.socket.getInetAddress.getHostAddress + ":" + servicePort)
    newMessenger.start
    //   newMessenger.sendMessage(ObstacleMapManager.obstacleMap)
    add(newMessenger)
  }

}