package edu.hkcc.pacmanrobot.utils.message

import java.util.concurrent.Semaphore

import edu.hkcc.pacmanrobot.server.MessengerManager
import edu.hkcc.pacmanrobot.utils.Config
import edu.hkcc.pacmanrobot.utils.map.{ObstacleMap, ObstacleMapManager}

/**
 * Created by beenotung on 4/14/15.
 */
class ObstacleMapMessenger(val obstacleMap: ObstacleMap = new ObstacleMap, manager: ObstacleMapManager) extends Messenger[ObstacleMap](Config.PORT_MAP, manager: MessengerManager[ObstacleMap]) {
  val bufferedObstacleMap = Array.fill[ObstacleMap](2)(new ObstacleMap)
  val switchBufferIndexSemaphore = new Semaphore(1)
  private var bufferIndex: Int = 0

  def switchBufferIndex = {
    switchBufferIndexSemaphore.acquire()
    bufferIndex = 1 - bufferIndex
    switchBufferIndexSemaphore.release()
  }

  override def sendMessage(deltaMap: ObstacleMap): Unit = {
    switchBufferIndexSemaphore.acquire
    bufferedObstacleMap(bufferIndex).merge(deltaMap)
    switchBufferIndexSemaphore.release
  }

  override def autoGet(message: ObstacleMap): Unit = {
    if (manager != null)
      manager.foreach(m => {
        if (!m.getRemoteMacAddress.equals(DeviceInfo.getLocalMacAddress))
          m.sendMessage(message)
        obstacleMap.merge(message)
      })
  }

  override def getMessage: ObstacleMap = {
    obstacleMap.clone
  }

  override protected def sendMessage: Unit = {
    val operateBufferedObstacleMap = bufferedObstacleMap(1 - bufferIndex);
    if (!operateBufferedObstacleMap.isEmpty) {
      outputStream.writeObject(operateBufferedObstacleMap)
      obstacleMap.merge(operateBufferedObstacleMap)
      operateBufferedObstacleMap.clear
    }
    switchBufferIndexSemaphore
  }
}
