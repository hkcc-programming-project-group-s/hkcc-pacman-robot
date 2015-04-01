package edu.hkcc.pacmanrobot.robot.utils

import edu.hkcc.pacmanrobot.robot.utils.PathFinder.Direction.DirectionType
import edu.hkcc.pacmanrobot.utils.Utils.getTabularSize
import edu.hkcc.pacmanrobot.utils.map.ObstacleMap
import edu.hkcc.pacmanrobot.utils.{Point2D, Utils}
import neuroevolution.geneticalgorithm.GA

import scala.collection.parallel.mutable.ParArray

/**
 * Created by beenotung on 4/1/15.
 */
object PathFinder {
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
    val bitSize = 3
  }
}

class PathFinder(private var obstacleMap: Array[Array[Boolean]], var LOOP_INTERVAL: Int = 100) extends Thread {
  var ai: GA = _
  var source = new Point2D[Int](0, 0)
  var destination = new Point2D[Int](0, 0)

  def setMap(newObstacleMap: Array[Array[Boolean]]) = {
    val newSize = getTabularSize(newObstacleMap)
    if (getTabularSize(obstacleMap) != newSize)
      ai.resize(newSize)
    obstacleMap=newObstacleMap
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
  def decode(rawCode:Array[Boolean]):Array[Array[DirectionType]]={
    ParArray.tabulate[DirectionType](obstacleMap.length,obstacleMap(0).length)((x,y)=>{

    })
    obstacleMap.indices.par.foreach(x=>{
      obstacleMap(x).indices.par.foreach(y=>{
        obstacleMap(x)(y)=
      })
    })
  }
  def eval(rawCode:Array[Boolean]):Double={

  }

  def setup = {
    ai = new GA(POP_SIZE = 32, BIT_SIZE = getTabularSize(obstacleMap) * PathFinder.Direction.bitSize,
      P_SELECTION=0.25,P_MUTATION = 0.1,A_MUTATION = 0.03,EVAL_FITNESS_FUNCTION = eval,PROBLEM_TYPE = GA.ProblemType.Minimize,LOOP_INTERVAL=100)
  }
}
