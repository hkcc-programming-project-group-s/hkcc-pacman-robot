package edu.hkcc.pacmanrobot.utils

import java.util.function.BiConsumer

import edu.hkcc.pacmanrobot.utils.map.{MapKey, ObstacleMap}

import scala.util.Random

/**
 * Created by beenotung on 4/1/15.
 */
object Utils {
  val random = new Random(System.currentTimeMillis())

  def getObstacleMapRange(obstacleMap: ObstacleMap): Point2D[Point2D[Int]] = {
    val range = new Point2D[Point2D[Int]](new Point2D[Int](0, 0), new Point2D[Int](0, 0))
    if (obstacleMap == null || obstacleMap.size < 1) range
    else {
      obstacleMap.forEach(new BiConsumer[MapKey, Long] {
        override def accept(k: MapKey, v: Long): Unit = {
          if (k.x < range._1._1) range._1.set_1(k.x)
          else if (k.x > range._1._2) range._1.set_2(k.x)
          if (k.y < range._2._1) range._2.set_1(k.y)
          else if (k.y > range._2._2) range._2.set_2(k.y)
        }
      })
      range
    }
  }

  @Deprecated
  def getObstacleMapRange[AnyValCompanion](array: Array[Array[AnyValCompanion]]): Point2D[Point2D[Int]] = {
    val range = new Point2D[Point2D[Int]](new Point2D[Int](0, 0), new Point2D[Int](0, 0))
    if (array == null || array.length < 1) range
    else {
      array.indices.foreach(x => array(x).indices.foreach(y => {
        if (x < range._1._1) range._1.set_1(x)
        else if (x > range._1._2) range._1.set_2(x)
        if (y < range._2._1) range._2.set_1(y)
        else if (y > range._2._2) range._2.set_2(y)
      }))
      range
    }
  }

  def getTabularSize[T](array: Array[Array[T]]): Int = {
    array.length * array(0).length
  }

  def minus(p1: Point2D[Int], p2: Point2D[Int]): Point2D[Int] = {
    new Point2D[Int](p1._1 - p2._1, p1._2 - p2._2)
  }
}
