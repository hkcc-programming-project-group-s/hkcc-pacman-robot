package edu.hkcc.pacmanrobot.robot.utils

import edu.hkcc.pacmanrobot.utils.Point2D
import edu.hkcc.pacmanrobot.utils.map.ObstacleMap

/**
 * Created by beenotung on 4/1/15.
 */
object PathFinder {
  def create(map: ObstacleMap): PathFinder = {
    //TODO

    null
  }
}

class PathFinder(getMap: => (Array[Array[Boolean]])) extends Thread {
  val a: NeuroEvolution = new NeuroEvolution()
  var source = new Point2D[Int](0, 0)
  var destination = new Point2D[Int](0, 0)

  def setLocation(source: Point2D[Int], destination: Point2D[Int]) = {
    this.source = source
    this.destination = destination
  }

  override def run = {

  }
}
