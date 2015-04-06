package edu.hkcc.pacmanrobot.utils.map

/**
 * Created by beenotung on 3/21/15.
 */


import java.util.concurrent.Semaphore
import java.util.function.BiConsumer

import edu.hkcc.pacmanrobot.utils.Config
import edu.hkcc.pacmanrobot.utils.studentrobot.code.Messenger


/**
 * Created by beenotung on 3/27/15.
 */
abstract class ObstacleMapManager extends Thread {

  val messenger: Messenger[ObstacleMap] = Messenger.create[ObstacleMap](Config.PORT_MAP)

  val inputThread: Thread = new Thread(new Runnable {
    override def run(): Unit = {
      while (true) {
        receiveMap
        Thread.sleep(Config.SYNC_MAP_CYCLE_INTERVAL)
      }
    }
  })
  val outThread: Thread = new Thread(new Runnable {
    override def run(): Unit = {
      while (true) {
        sendMap
        Thread.sleep(Config.SYNC_MAP_CYCLE_INTERVAL)
      }
    }
  })

  override def run = {
    inputThread.start
    outThread.start
  }

  var map: ObstacleMap = new ObstacleMap

  private val semaphore: Semaphore = new Semaphore(1, true)

  private var lastSendMapTime: Long = _

  private var lastReceiveMapTime: Long = _

  def getMap: ObstacleMap = {
    semaphore.tryAcquire
    val bufferedMap = map.clone.asInstanceOf[ObstacleMap]
    semaphore.release
    bufferedMap
  }

  protected def getDeltaUpdatedMap(shouldUpdate: (MapKey, MapContent) => Boolean): ObstacleMap = {
    semaphore.tryAcquire
    val bufferedMap = new ObstacleMap
    map.forEach(new BiConsumer[MapKey, MapContent] {
      override def accept(key: MapKey, value: MapContent) = {
        if (shouldUpdate(key, value))
          bufferedMap.put(key.clone.asInstanceOf[MapKey], value.asInstanceOf[MapContent])
      }
    })
    semaphore.release
    bufferedMap
  }

  def shouldUpdate(key: MapKey, value: MapContent): Boolean = {
    if(value.time<lastSendMapTime) true
    else false
  }


  protected def sendMap = {
    val bufferedMap = getDeltaUpdatedMap(shouldUpdate)
    lastSendMapTime = System.currentTimeMillis
    messenger.sendMessage(bufferedMap);
    //TODO call messenger
  }

  def receiveMap = {
    lastReceiveMapTime = System.currentTimeMillis
    myReceiveMap
  }

  //TODO get all from queue
  protected def myReceiveMap ={
    addMap(messenger.getMessage)
  }

  def addMap(deltaObstacleMap: ObstacleMap) {
    semaphore.tryAcquire
    map.putAll(deltaObstacleMap)
    semaphore.release
  }

  def addMap(mapUnits: List[MapUnit]) {
    semaphore.tryAcquire
    semaphore.release
  }

  def remove(key: MapKey) {
    semaphore.tryAcquire
    map.remove(key)
    semaphore.release
  }

  def remove(deltaObstacleMap: ObstacleMap) {
    semaphore.tryAcquire
    semaphore.release
  }
}
