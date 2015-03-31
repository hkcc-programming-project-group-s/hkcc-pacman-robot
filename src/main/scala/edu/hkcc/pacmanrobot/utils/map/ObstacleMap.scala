package edu.hkcc.pacmanrobot.utils.map

/**
 * Created by beenotung on 3/21/15.
 */

import java.util.concurrent.ConcurrentHashMap
import java.util.function.BiConsumer

import edu.hkcc.pacmanrobot.utils.{Config, Utils}


class ObstacleMap extends ConcurrentHashMap[MapKey, MapContent] with Cloneable with Message {
  override val port: Int = Config.PORT_MAP
  var valid_time: Double = 1000L * 60 * 5 * 2

  def isExist(target: MapUnit): Boolean = {
    isExist(target.key)
  }

  def isExist(key: MapKey): Boolean = {
    keySet.contains(key)
  }

  /*alternative method to access the map*/
  def put(mapUnit: MapUnit) {
    put(mapUnit.key, mapUnit.value)
  }

  /*alternative method to access the map*/
  def get(mapUnit: MapUnit) {
    mapUnit.value.set(get(mapUnit.key))
  }

  override def clone: AnyRef = {
    val newInstance: ObstacleMap = new ObstacleMap
    forEach(new BiConsumer[MapKey, MapContent] {
      override def accept(key: MapKey, value: MapContent) = {
        newInstance.put(key.clone.asInstanceOf[MapKey], value.asInstanceOf[MapContent])
      }
    })
    newInstance
  }

  def toArray: Array[Array[Boolean]] = {
    var width: Int = 0
    var height: Int = 0
    (width, height) = getDimension
    val array = Array.tabulate[Boolean](width, height) { (x, y) => {
      val key: MapKey = searchKeys[MapKey](8, new java.util.function.Function[MapKey, MapKey] {
        override def apply(t: MapKey): MapKey = {
          if (t.x.toInt.equals(x) && t.y.toInt.equals(y)) t else null
        }
      })
      hasObstacle(key)
    }
    }
    array
  }

  def hasObstacle(key: MapKey, time: Long = System.currentTimeMillis()): Boolean = {
    getObstacleProb(key, time) >= 0.5
  }

  //prob less than 50% at the fifth seconds
  def getObstacleProb(key: MapKey, time: Long = System.currentTimeMillis()): Double = {
    if (key == null) return 0d
    if (isExist(key))
      1 - (time - get(key).time) / valid_time
    else 0d
  }

  def getDimension: (Long, Long) = {
    val buffer: Array[MapKey] = keySet().toArray[MapKey](buffer)
    val width = Utils.getRangeLong[MapKey](array = buffer, getValue = (mapKey: MapKey) => mapKey.x)
    val height = Utils.getRangeLong[MapKey](array = buffer, getValue = (mapKey: MapKey) => mapKey.y)
    (width, height)
  }
}

}