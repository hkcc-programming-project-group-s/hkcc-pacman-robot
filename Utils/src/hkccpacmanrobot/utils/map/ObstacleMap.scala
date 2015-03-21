package hkccpacmanrobot.utils.map

/**
 * Created by beenotung on 3/21/15.
 */

import scala.collection.mutable

class ObstacleMap extends mutable.HashMap[MapKey, MapContent] with Cloneable {
  def isExist(target: MapUnit): Boolean = {
    keySet.contains(target.key)
  }

  def put(mapUnit: MapUnit) {
    put(mapUnit.key, mapUnit.value)
  }

  def get(mapUnit: MapUnit) {
    mapUnit.value.set(get(mapUnit.key).get)
  }

  override def clone: AnyRef = {
    val newInstance: ObstacleMap = new ObstacleMap
    for (key <- keys) newInstance.put(key.clone.asInstanceOf[MapKey], get(key).clone.asInstanceOf[MapContent])
    return newInstance
  }
}