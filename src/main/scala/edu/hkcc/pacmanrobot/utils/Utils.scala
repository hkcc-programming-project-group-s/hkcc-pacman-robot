package edu.hkcc.pacmanrobot.utils

import scala.util.Random

/**
 * Created by beenotung on 4/1/15.
 */
object Utils {
  val random = new Random(System.currentTimeMillis())

  @deprecated
  def getRangeInt[Type](array: Array[Type], getValue: Type => Int): Int = {
    if (array.length < 1) 0
    else {
      val range = array.foldLeft[(Int, Int)](getValue(array(0)), getValue(array(0)))((accum: (Int, Int), content: Type) => {
        ( {
          if (accum._1 < getValue(content)) accum._1 else getValue(content)
        }, {
          if (accum._2 > getValue(content)) accum._2 else getValue(content)
        })
      })
      range._2 - range._1 + 1
    }
  }

  def getTabularSize[T](array: Array[Array[T]]): Int = {
    array.length * array(0).length
  }

  def minus(p1: Point2D[Int], p2: Point2D[Int]): Point2D[Int] = {
    new Point2D[Int](p1._1 - p2._1, p1._2 - p2._2)
  }
}
