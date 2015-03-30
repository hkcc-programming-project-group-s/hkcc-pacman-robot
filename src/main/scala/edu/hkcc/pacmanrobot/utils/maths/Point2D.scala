package edu.hkcc.pacmanrobot.utils.maths

import java.io.Serializable

/**
 * Created by beenotung on 3/30/15.
 */
class Point2D(var _1: Double = 0d, var _2: Double = 0d) extends Cloneable with Serializable {
  override def clone: Point2D = {
    new Point2D(_1, _2)
  }
  def this(direction:Double){
    this(direction,5d)
  }

  override def toString: String = {
    "[%.2f,%.2f]\n".format(_1,_2)
  }
}