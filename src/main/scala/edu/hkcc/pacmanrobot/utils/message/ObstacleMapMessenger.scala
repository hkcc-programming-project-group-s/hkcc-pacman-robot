package edu.hkcc.pacmanrobot.utils.message

import java.util.concurrent.Semaphore

import edu.hkcc.pacmanrobot.server.MessengerManager
import edu.hkcc.pacmanrobot.utils.Config
import edu.hkcc.pacmanrobot.utils.map.ObstacleMap

/**
 * Created by beenotung on 4/14/15.
 */
class ObstacleMapMessenger(messengerManager: MessengerManager[ObstacleMap]) extends Messenger[ObstacleMap](Config.PORT_MAP, messengerManager: MessengerManager[ObstacleMap]) {
  val bufferedObstacleMap = new ObstacleMap
  val obstacleMapSemaphore = new Semaphore(1)
  val bufferedObstacleMapSemaphore = new Semaphore(1)
  private val obstacleMap_ = new ObstacleMap

  def _obstacleMap(newMap: ObstacleMap) = {
    bufferedObstacleMapSemaphore.acquire()
    bufferedObstacleMap.merge(newMap)
    bufferedObstacleMapSemaphore.release()
  }

  override def sendMessage(deltaMap: ObstacleMap): Unit = {
    bufferedObstacleMapSemaphore.acquire()
    bufferedObstacleMap.merge(deltaMap)
    bufferedObstacleMapSemaphore.release()
  }

  override def autoGet(message: ObstacleMap): Unit = {
    if (messengerManager != null)
      messengerManager.foreach(m => {
        if (!m.getRemoteMacAddress.equals(DeviceInfo.getLocalMacAddress))
          m.sendMessage(message)
        obstacleMap.merge(message)
      })
  }

  def obstacleMap: ObstacleMap = {
    obstacleMapSemaphore.acquire
    val result = obstacleMap_.clone
    obstacleMapSemaphore.release
    result
  }

  override protected def sendMessage: Unit = {
    bufferedObstacleMapSemaphore.acquire()
    if (!bufferedObstacleMap.isEmpty) {
      outputStream.writeObject(bufferedObstacleMap)
      bufferedObstacleMap.clear
    }
    bufferedObstacleMapSemaphore.release()
  }
}
