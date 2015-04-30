package edu.hkcc.pacmanrobot.utils.message

/**
 * Created by 13058536A on 3/25/2015.
 */
object Position {
  def distance(p1: Position, p2: Position): Double = {
    Math.sqrt(Math.pow(p1.x - p2.x, 2) + Math.pow(p1.y - p2.y, 2) + Math.pow(p1.z - p2.z, 2))
  }
}

class Position(var x: Int, var y: Int, var z: Int, var time: Long = System.currentTimeMillis()) {
  def distance(targetPosition: Position) = Position.distance(this, targetPosition)
}
