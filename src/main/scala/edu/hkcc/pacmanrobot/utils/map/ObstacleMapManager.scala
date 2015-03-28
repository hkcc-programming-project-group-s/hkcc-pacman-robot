package hkccpacmanrobot.utils.map

/**
 * Created by beenotung on 3/21/15.
 */


import java.util.concurrent.Semaphore
import java.util.function.BiConsumer

import edu.hkcc.pacmanrobot.utils.studentrobot.code.{MapContent, MapKey, MapUnit, ObstacleMap}

/**
 * Created by beenotung on 3/27/15.
 */
trait ObstacleMapManager {

  var map: ObstacleMap = _

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

  def shouldUpdate(key: MapKey, value: MapContent): Boolean

  protected def sendMap = {
    val bufferedMap = getDeltaUpdatedMap(shouldUpdate)
    lastSendMapTime = System.currentTimeMillis
    //TODO networking
  }

  protected def receiveMap = {
    //TODO networking
    lastReceiveMapTime = System.currentTimeMillis
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
