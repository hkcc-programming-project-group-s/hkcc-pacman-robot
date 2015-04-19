package edu.hkcc.pacmanrobot.utils.message.messenger

import java.net.Socket
import java.util.concurrent.Semaphore

import edu.hkcc.pacmanrobot.server.{MessengerManager, ObstacleMapManager}
import edu.hkcc.pacmanrobot.utils.Config
import edu.hkcc.pacmanrobot.utils.map.ObstacleMap
import edu.hkcc.pacmanrobot.utils.message.DeviceInfo

/**
 * Created by beenotung on 4/14/15.
 */
class ObstacleMapMessenger(val obstacleMap: ObstacleMap, socket: Socket, manager: ObstacleMapManager)
  extends Messenger[ObstacleMap](socket, Config.PORT_MAP, manager: MessengerManager[ObstacleMap]) {
  val bufferedObstacleMap = Array.fill[ObstacleMap](2)(new ObstacleMap)
  val switchBufferIndexSemaphore = new Semaphore(1)
  private var bufferIndex: Int = 0

  def this(obstacleMap: ObstacleMap = new ObstacleMap) = {
    this(obstacleMap = obstacleMap, Messenger.connect(Config.PORT_MAP, isServer = false), null)
  }

  override def sendMessage(deltaMap: ObstacleMap): Unit = {
    switchBufferIndexSemaphore.acquire
    bufferedObstacleMap(bufferIndex).merge(deltaMap)
    switchBufferIndexSemaphore.release
  }

  override def autoGet(message: ObstacleMap): Unit = {
    if (manager != null)
      manager.foreach(messenger => {
        if (!messenger.getRemoteMacAddress.equals(DeviceInfo.getLocalMacAddress))
          messenger.sendMessage(message)
      })
    //println("autoget")
    //    message.forEach(new BiConsumer[MapKey,Long] {
    //      override def accept(t: MapKey, u: Long): Unit = {
    //        println(Vector(t,u))
    //      }
    //    })
    obstacleMap.merge(message)
  }

  def getMap: ObstacleMap = {
    obstacleMap.clone
  }

  override protected def sendMessage: Unit = {
    val operateBufferedObstacleMap = bufferedObstacleMap(1 - bufferIndex)
    if (!operateBufferedObstacleMap.isEmpty) {
      outputStream.writeObject(operateBufferedObstacleMap)
      obstacleMap.merge(operateBufferedObstacleMap)
      operateBufferedObstacleMap.clear
    }
    switchBufferIndex
  }

  def switchBufferIndex = {
    switchBufferIndexSemaphore.acquire()
    bufferIndex = 1 - bufferIndex
    switchBufferIndexSemaphore.release()
  }
}
