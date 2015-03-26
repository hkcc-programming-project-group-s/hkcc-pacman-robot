package hkccpacmanrobot.utils


/**
 * Created by beenotung on 3/22/15.
 */
object Maths {

  val F: Double = 0d
  val F_R: Double = Math.PI * 0.25
  val R: Double = Math.PI * 0.5
  val B_R: Double = Math.PI * 0.75
  val B: Double = Math.PI
  val B_L: Double = Math.PI * 1.25
  val L: Double = Math.PI * 1.5
  val F_L: Double = Math.PI * 1.75
  val PI2: Double = Math.PI * 2d

  def isForward(direction: Double): Boolean = {
    isBetween(F_L, direction, PI2) || isBetween(0d, direction, F_R)
  }

  def isRight(direction: Double): Boolean = {
    isBetween(F_R, direction, B_R)
  }

  def isBackward(direction: Double): Boolean = {
    isBetween(B_R, direction, B_L)
  }

  def isLeft(direction: Double): Boolean = {
    isBetween(B_L, direction, F_L)
  }

  def isForwardRight(direction: Double): Boolean = {
    isBetween(F_R, direction, R)
  }

  def isBackwardRight(direction: Double): Boolean = {
    isBetween(R, direction, B_R)
  }

  def isBetween(lower: Double, target: Double, upper: Double): Boolean = {
    lower < target && target < upper
  }

  def isBackwardLeft(direction: Double): Boolean = {
    isBetween(B_L, direction, L)
  }

  def isForwardLeft(direction: Double): Boolean = {
    isBetween(L, direction, F_L)
  }


  /*
  * reserved for rectangular coordinate or polar coordinate
  * POLAR: d1 = degree (in radian), d2 = distance
  * RECTANGULAR:: d1 =x, d2 = y
  */
  class Point2D(var d1: Double = 0d, var d2: Double = 0d) extends Cloneable {
    override def clone: Point2D = {
      new Point2D(d1, d2)
    }
  }

}
