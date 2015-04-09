package edu.hkcc.pacmanrobot.utils

import scala.util.Random

/**
 * Created by beenotung on 4/1/15.
 */
object Utils {
  val random = new Random(System.currentTimeMillis())

 
  def getObstacleMapRange[Boolean](array: Array[Array[Boolean]]): Point2D[Point2D[Int]] = {
    val range=new Point2D[Point2D[Int]](new Point2D[Int](0,0),new Point2D[Int](0,0))
    if (array.length < 1) range
    else {
      val range=new Point2D[Point2D[Int]](new Point2D[Int](0,0),new Point2D[Int](0,0))
      array.indices.foreach(x=>array(x).indices.foreach(y=> {
        
      }    ))
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
