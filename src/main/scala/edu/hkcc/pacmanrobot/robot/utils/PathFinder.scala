package edu.hkcc.pacmanrobot.robot.utils

import edu.hkcc.pacmanrobot.utils.Utils.getTabularSize
import edu.hkcc.pacmanrobot.utils.map.ObstacleMap
import edu.hkcc.pacmanrobot.utils.{Point2D, Utils}
import neuroevolution.geneticalgorithm.GA

/**
 * Created by beenotung on 4/1/15.
 */
object PathFinder {
  def create(map: ObstacleMap): PathFinder = {
    //TODO

    null
  }

  object Direction extends Enumeration {
    type DirectionType = super.Value
    val UP = Value(0)
    val DOWN = Value(1)
    val LEFT = Value(2)
    val RIGHT = Value(3)
    val UP_LEFT = Value(4)
    val UP_RIGHT = Value(5)
    val DOWN_LEFT = Value(6)
    val DOWN_RIGHT = Value(7)
    val bitSize = 8
  }

}

class PathFinder(private var map: Array[Array[Boolean]], var LOOP_INTERVAL: Int = 100) extends Thread {
  var ai: GA = _
  var source = new Point2D[Int](0, 0)
  var destination = new Point2D[Int](0, 0)

  def _map

  def setMap(newMap: Array[Array[Boolean]]) = {
    //TODO
    val newSize = getTabularSize(newMap)
    if (getTabularSize(map) != newSize)
      ai.resize(newSize)
  }

  def setLocation(source: Point2D[Int], destination: Point2D[Int]) = {
    this.source = source
    this.destination = destination
  }

  override def run = {
    setup
    while (true) {
      loop
      Thread.sleep(LOOP_INTERVAL)
    }
  }

  def loop = {
    //TODO
  }

  def setup = {
    ai = new GA(POP_SIZE = 32, BIT_SIZE = getTabularSize(map) * PathFinder.Direction.bitSize,
      P_SELECTION=0.25,P_MUTATION = 0.1,A_MUTATION = 0.03,EVAL_FITNESS_FUNCTION = eval,PROBLEM_TYPE = GA.ProblemType.Minimize,LOOP_INTERVAL=100)
  }
  def eval(rawCode:Array[Double])
}
