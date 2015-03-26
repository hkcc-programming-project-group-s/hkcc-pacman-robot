package studentrobot.code

/**
 * Created by beenotung on 3/21/15.
 */

import java.util.concurrent.ConcurrentHashMap
import java.util.function.BiConsumer

class ObstacleMap extends ConcurrentHashMap[MapKey, MapContent] with Cloneable {
  def isExist(target: MapUnit): Boolean = {
    keySet.contains(target.key)
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

}