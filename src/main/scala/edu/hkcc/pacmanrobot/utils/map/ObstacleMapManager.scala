package edu.hkcc.pacmanrobot.utils.map

import edu.hkcc.pacmanrobot.server.MessengerManager
import edu.hkcc.pacmanrobot.utils.Config
import edu.hkcc.pacmanrobot.utils.message.{DeviceInfo, ObstacleMapMessenger}

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
        obstacleMap.merge(message)
      })
  }
}

class ObstacleMapManager extends MessengerManager[ObstacleMap](Config.PORT_MAP, (remoteMacAddress, map) => ObstacleMapManager.autoGet_func(remoteMacAddress, map)) {
  override val messengers = ObstacleMapManager.messengers
  override def send
}
