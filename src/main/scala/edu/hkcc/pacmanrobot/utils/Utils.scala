package edu.hkcc.pacmanrobot.utils

import scala.util.Random

/**
 * Created by beenotung on 4/1/15.
 */
object Utils {
  val random = new Random(System.currentTimeMillis())


  def getObstacleMapRange[AnyValCompanion](array: Array[Array[AnyValCompanion]]): Point2D[Point2D[Int]] = {
    val range = new Point2D[Point2D[Int]](new Point2D[Int](0, 0), new Point2D[Int](0, 0))
    if (array.length < 1) range
    else {
      array.indices.foreach(x => array(x).indices.foreach(y => {
        if (x < range._1._1) range._1.set_1(x)
        else if (x > range._1._2) range._1.set_2(x)
        if (y < range._2._1) range._2.set_1(y)
        else if (y > range._2._2) range._2.set_2(y)
      }))
      /*val range = array.foldLeft[(Int, Int)](getRange(array), getRange(array(0)))((accum: (Int, Int), content: Type) => {
        ( {
          if (accum._1 < getRange(content)) accum._1 else getRange(content)
        }, {
          if (accum._2 > getRange(content)) accum._2 else getRange(content)
        })
      })*/
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
