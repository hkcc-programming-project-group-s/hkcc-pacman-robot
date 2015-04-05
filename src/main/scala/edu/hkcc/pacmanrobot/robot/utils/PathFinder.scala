package edu.hkcc.pacmanrobot.robot.utils

import java.util.concurrent.{Semaphore, ConcurrentHashMap}

import edu.hkcc.pacmanrobot.utils.Utils.{getTabularSize, minus}
import edu.hkcc.pacmanrobot.utils.{Point2D, Utils}
import neuroevolution.geneticalgorithm.{GA, ProblemType}

import scala.collection.mutable.Set


/**
 * Created by beenotung on 4/1/15.
 */
object PathFinder {

  val directionMapBuffer = new ConcurrentHashMap[String, Array[Int]]()
  val bitInts = for (i <- 0 to 1024) yield Math.pow(2, i)

  def decode(rawCode: Array[Boolean]): Array[Int] = {
    var directionMap: Array[Int] = directionMapBuffer.get(rawCode.toString)
    if (directionMap == null) {
      directionMap = Array.tabulate[Int](rawCode.length / Direction.bitSize)(i => PathFinder.Direction.randomDirection)
      directionMapBuffer.put(rawCode.toString, directionMap)
    }
    decode(rawCode, directionMap)
    directionMap
  }

  def decode(rawCode: Array[Boolean], directionMap: Array[Int]) = {
    directionMap.indices.par.foreach(i => directionMap(i) = bitsToInt(rawCode, Direction.bitSize * i, Direction.bitSize))
  }
  def encode( directionMap: Array[Int],rawCode: Array[Boolean]) = {
    directionMap.indices.par.foreach(i=>)
  }


  def bitsToInt(bits: Array[Boolean], start: Int, length: Int): Int = {
    var value = 0
    Range(start, start + length).foreach(i => if (bits(i)) value += bitInts(i - start + 1))
    value
  }
  def intToBits(value:Int,bits: Array[Boolean], start: Int, length: Int) {
    Range(1,length+1).reverse.foreach(i=>
    if(value>=)
    )
    Range(start, start + length).foreach(i => if (bits(i)) result += bitInts(i - start + 1))
  }

  object Direction {
    val UP = 0
    val DOWN = 1
    val LEFT = 2
    val RIGHT = 3
    val UP_LEFT = 4
    val UP_RIGHT = 5
    val DOWN_LEFT = 6
    val DOWN_RIGHT = 7
    val bitSize: Int = 3
    val UP_SET = Set(UP, UP_LEFT, UP_RIGHT)
    val DOWN_SET = Set(DOWN, DOWN_LEFT, DOWN_RIGHT)
    val LEFT_SET = Set(LEFT, UP_LEFT, DOWN_LEFT)
    val RIGHT_SET = Set(RIGHT, UP_RIGHT, DOWN_RIGHT)
    val ALL_SET = Set(UP, DOWN, LEFT, RIGHT, UP_LEFT, UP_RIGHT, DOWN_LEFT, DOWN_RIGHT)
    val ALL_SET_ARRAY = ALL_SET.toArray

    def getDirection(xy: Point2D[Int]): Int = {
      var result = ALL_SET
      result.dropWhile({ s: Int => xy._2 < 0 && UP_SET.contains(s) })
      result.dropWhile({ s: Int => xy._2 > 0 && DOWN_SET.contains(s) })
      result.dropWhile({ s: Int => xy._1 > 0 && LEFT_SET.contains(s) })
      result.dropWhile({ s: Int => xy._1 < 0 && RIGHT_SET.contains(s) })
      if (result.isEmpty)
        randomDirection
      else
        result.head
    }

    def randomDirection: Int = ALL_SET_ARRAY(Utils.random.nextInt(ALL_SET_ARRAY.length))
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
    obstacleMap = newObstacleMap
  }

  def setLocation(source: Point2D[Int], destination: Point2D[Int]) = {
    this.source = source
    this.destination = destination
  }

  def getOverallDirectionType: Int = {
    PathFinder.Direction.getDirection(minus(destination, source))
  }

  override def run = {
    setup
    ai.start
    while (true) {
      loop
      Thread.sleep(LOOP_INTERVAL)
    }
  }

  def loop = {
    //TODO

  }

  def setup = {
    /*ai = new GA(POP_SIZE = 32, BIT_SIZE = getTabularSize(obstacleMap) * PathFinder.Direction.bitSize,
      P_SELECTION = 0.25, P_MUTATION_POW = 2, A_MUTATION_POW = 4, EVAL_FITNESS_FUNCTION = eval, PROBLEM_TYPE = ProblemType.Minimize, LOOP_INTERVAL = 100)*/
    ai=new GA(POP_SIZE = 32,BIT_SIZE = getTabularSize(obstacleMap)*PathFinder.Direction.bitSize,P_SELECTION = 0.25,
    P_MUTATION_POW = 2,A_MUTATION_POW = 4,PARENT_IMMUTABLE = false,
      EVAL_FITNESS_FUNCTION = eval,PROBLEM_TYPE = ProblemType.Minimize,
    LOOP_INTERVAL=100L)
    ai.genes.foreach(gene => gene.rawCode)
  }

  def eval(rawCode: Array[Boolean]): Double = {
    val directionMap = PathFinder.decode(rawCode)
    val MAX_STEP = getMaxStep
    var step = 0L
    do {

    } while (step < MAX_STEP)
  }

  def getMaxStep: Long = {
    val distance = getOverallDirection
    Math.round(Math.pow(distance._1 * distance._1 + distance._2 * distance._2, 2))
  }

  def getOverallDirection: Point2D[Int] = {
    minus(destination, source)
  }
}
