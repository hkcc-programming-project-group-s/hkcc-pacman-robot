package edu.hkcc.pacmanrobot.utils.map

/**
 * Created by beenotung on 3/21/15.
 */

import java.util.concurrent.ConcurrentHashMap
import java.util.function.BiConsumer

import edu.hkcc.pacmanrobot.utils.message.Message
import edu.hkcc.pacmanrobot.utils.{Config, Point2D, Utils}

import scala.collection.parallel.mutable.ParArray

object ObstacleMap {
  private var _estimated_game_duration_in_minutes: Double = 5d
  private var deprecate_rate: Double = 1d / estimated_game_duration_in_minutes / -1000d / 60d

  def get_deprecate_rate = deprecate_rate

  def estimated_game_duration_in_minutes: Double = _estimated_game_duration_in_minutes

  def estimated_game_duration_in_minutes_=(newValue: Double) {
    _estimated_game_duration_in_minutes = newValue
    deprecate_rate = 1d / newValue / -1000d / 60d
  }

  def prob(discoverTime: Long, compareTime: Long): Double = {
    Math.exp((compareTime - discoverTime) * deprecate_rate)
  }

  def getRange(obstacleMap: ObstacleMap): Point2D[Point2D[Int]] = {
    val range = new Point2D[Point2D[Int]](new Point2D[Int](0, 0), new Point2D[Int](0, 0))
    if (obstacleMap != null)
      obstacleMap.forEach(new BiConsumer[MapKey, Long] {
        override def accept(key: MapKey, value: Long) = {
          if (range._1._1 > key.x)
            range._1._1 = key.x
          if (range._1._2 < key.x)
            range._1._2 = key.x

          if (range._2._1 > key.y)
            range._2._1 = key.y
          if (range._2._2 < key.y)
            range._2._2 = key.y
        }
      })
    range
  }
}

class ObstacleMap extends ConcurrentHashMap[MapKey, Long] with Cloneable with Message {
  override val port: Int = Config.PORT_MAP

  def isExist(target: MapUnit): Boolean = {
    containsKey(target.location)
  }

  /*alternative method to access the map*/
  def put(mapUnit: MapUnit) {
    put(mapUnit.location, mapUnit.time)
  }

  /*alternative method to access the map*/
  def get(mapUnit: MapUnit) {
    mapUnit.time = get(mapUnit.location)
  }

  def merge(obstacleMap: ObstacleMap): Unit = {
    obstacleMap.forEach(new BiConsumer[MapKey, Long] {
      override def accept(key: MapKey, value: Long): Unit = {
        put(key, value)
      }
    })
  }

  def to2DArrayBoolean: Array[Array[Boolean]] = {
    val map = to2DArrayLong
    if (map == null)
      null
    else {
      val now = System.currentTimeMillis()
      //Array.tabulate[Boolean](map.length, map(0).length)((x, y) => ObstacleMap.prob(map(x)(y), now) > 0.5)
      Array.tabulate[Boolean](map.length, map(0).length)((x, y) => ObstacleMap.prob(map(x)(y), now) > Utils.random.nextDouble())
    }
  }

  def to2DArrayLong: Array[Array[Long]] = {
    if (isEmpty) null
    else {
      val map = clone
      val range = ObstacleMap.getRange(map)
      val array = Array.fill[Long](range._1._2 - range._1._1 + 1, range._2._2 - range._2._1 + 1)(0L)
      map.forEach(new BiConsumer[MapKey, Long] {
        override def accept(k: MapKey, v: Long): Unit = {
          try
            array(k.x - range._1._1)(k.y - range._2._1) = v
          catch {
            case e: Exception => {
              println(e.toString)
              e.printStackTrace()
              println()
              println("range:")
              println(range._1)
              println(range._2)
              println
              println("array size= " + array.length + ", " + array(0).length)
              println(k.x + ", " + k.y)
            }
          }
        }
      })
      array
    }
  }

  override def clone: ObstacleMap = {
    val newInstance: ObstacleMap = new ObstacleMap
    forEach(new BiConsumer[MapKey, Long] {
      override def accept(key: MapKey, value: Long) = {
        newInstance.put(key.clone.asInstanceOf[MapKey], value.toLong)
      }
    })
    newInstance
  }

  def to2DParArrayBoolean: ParArray[ParArray[Boolean]] = {
    val map = to2DArrayLong
    if (map == null)
      null
    else {
      val now = System.currentTimeMillis()
      ParArray.tabulate[Boolean](map.length, map(0).length)((x, y) => ObstacleMap.prob(map(x)(y), now) > 0.5)
    }
  }
}