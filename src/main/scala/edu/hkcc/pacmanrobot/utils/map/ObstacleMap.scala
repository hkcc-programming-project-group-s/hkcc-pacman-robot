package edu.hkcc.pacmanrobot.utils.map

/**
 * Created by beenotung on 3/21/15.
 */

import java.util.concurrent.ConcurrentHashMap
import java.util.function.BiConsumer

import edu.hkcc.pacmanrobot.utils.Config


class ObstacleMap extends ConcurrentHashMap[MapKey, Long] with Cloneable with Message {
  override val port: Int = Config.PORT_MAP

  def isExist(target: MapUnit): Boolean = {
    keySet.contains(target.location)
  }

  /*alternative method to access the map*/
  def put(mapUnit: MapUnit) {
    put(mapUnit.location, mapUnit.time)
  }

  /*alternative method to access the map*/
  def get(mapUnit: MapUnit) {
    mapUnit.time = get(mapUnit.location)
  }

  def merge(obstacleMap: ObstacleMap): Unit =
  {
    obstacleMap.forEach(new BiConsumer[MapKey,Long] {
      override def accept(key: MapKey, value: Long): Unit = {
        put(key,value)
      }
    })
  }

  override def clone: AnyRef = {
    val newInstance: ObstacleMap = new ObstacleMap
    forEach(new BiConsumer[MapKey, Long] {
      override def accept(key: MapKey, value: Long) = {
        newInstance.put(key.clone.asInstanceOf[MapKey], value)
      }
    })
    newInstance
  }
}