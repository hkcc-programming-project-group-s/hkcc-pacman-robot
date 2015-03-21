package hkccpacmanrobot.utils.map

/**
 * Created by beenotung on 3/21/15.
 */


import java.util.List
import java.util.concurrent.Semaphore
import java.util.function.BiConsumer

//remove if not needed
import scala.collection.JavaConversions._

object ObstacleMapManager {

  trait ShouldUpdateOperator {

    def mapCompare(obstacleMapManager: ObstacleMapManager, key: MapKey, value: MapContent): Boolean
  }
}

class ObstacleMapManager {

  var map: ObstacleMap = _

  private var semaphore: Semaphore = new Semaphore(1, true)

  private var lastSendMapTime: Long = _

  private var lastReceiveMapTime: Long = _

  def getMap(): ObstacleMap = {
    semaphore.tryAcquire()
    val bufferedMap = map.clone().asInstanceOf[ObstacleMap]
    semaphore.release()
    bufferedMap
  }

  protected def getDeltaMap(operator: ShouldUpdateOperator): ObstacleMap = {
    semaphore.tryAcquire()
    val bufferedMap = new ObstacleMap()
    map.forEach(new BiConsumer[MapKey, MapContent]() {

      override def accept(key: MapKey, value: MapContent) {
        if (operator.mapCompare(ObstacleMapManager.this, key, value)) bufferedMap.put(key, value)
      }
    })
    semaphore.release()
    bufferedMap
  }

  protected def sendMap(operator: ShouldUpdateOperator) {
    val bufferedMap = getDeltaMap(operator)
    lastSendMapTime = System.currentTimeMillis()
  }

  def addMap(deltaObstacleMap: ObstacleMap) {
    semaphore.tryAcquire()
    map.putAll(deltaObstacleMap)
    semaphore.release()
  }

  def addMap(mapUnits: List[MapUnit]) {
    semaphore.tryAcquire()
    semaphore.release()
  }

  def remove(key: MapKey) {
    semaphore.tryAcquire()
    map.remove(key)
    semaphore.release()
  }

  def remove(deltaObstacleMap: ObstacleMap) {
    semaphore.tryAcquire()
    semaphore.release()
  }
}
