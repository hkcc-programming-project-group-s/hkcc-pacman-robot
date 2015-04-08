package edu.hkcc.pacmanrobot.robot.utils

import java.util.concurrent.ConcurrentHashMap

import edu.hkcc.pacmanrobot.robot.utils.PathFinder.Direction.direction_array
import edu.hkcc.pacmanrobot.utils.Utils.{getTabularSize, minus}
import edu.hkcc.pacmanrobot.utils.{Point2D, Utils}
import neuroevolution.geneticalgorithm.{GA, ProblemType}

import scala.collection.mutable.Set

/**
 * Created by beenotung on 4/1/15.
 */
object PathFinder {

  val directionMapBuffer = new ConcurrentHashMap[String, Array[Int]]()
  val bitInts = for (i <- 0 to 30) yield Math.round(Math.pow(2, i)).toInt

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

  def bitsToInt(bits: Array[Boolean], start: Int, length: Int): Int = {
    Range(start, start + length).foldLeft(0)((value, i) => if (bits(i)) value + bitInts(i - start) else value)
  }

  object Direction {
    val UP: Byte = 0
    val DOWN: Byte = 1
    val LEFT: Byte = 2
    val RIGHT: Byte = 3
    val UP_LEFT: Byte = 4
    val UP_RIGHT: Byte = 5
    val DOWN_LEFT: Byte = 6
    val DOWN_RIGHT: Byte = 7
    val bitSize: Int = 3
    val UP_SET = Set(UP, UP_LEFT, UP_RIGHT)
    val DOWN_SET = Set(DOWN, DOWN_LEFT, DOWN_RIGHT)
    val LEFT_SET = Set(LEFT, UP_LEFT, DOWN_LEFT)
    val RIGHT_SET = Set(RIGHT, UP_RIGHT, DOWN_RIGHT)
    val ALL_SET = Set(UP, DOWN, LEFT, RIGHT, UP_LEFT, UP_RIGHT, DOWN_LEFT, DOWN_RIGHT)
    val ALL_SET_ARRAY = ALL_SET.toArray

    val direction_array = Array((0, 1), (0, -1), (-1, 0), (1, 0), (-1, 1), (1, 1), (-1, -1), (1, -1))

    def getDirection(xy: Point2D[Int]): Byte = {
      var result = ALL_SET
      result.dropWhile({ s: Byte => xy._2 < 0 && UP_SET.contains(s) })
      result.dropWhile({ s: Byte => xy._2 > 0 && DOWN_SET.contains(s) })
      result.dropWhile({ s: Byte => xy._1 > 0 && LEFT_SET.contains(s) })
      result.dropWhile({ s: Byte => xy._1 < 0 && RIGHT_SET.contains(s) })
      if (result.isEmpty)
        randomDirection
      else
        result.head
    }

    def randomDirection: Byte = ALL_SET_ARRAY(Utils.random.nextInt(ALL_SET_ARRAY.length))
  }
}

class PathFinder(private var obstacleMap: Array[Array[Boolean]], var LOOP_INTERVAL: Int = 100) extends Thread {
  var ai: GA = _
  var source = new Point2D[Int](0, 0)
  var destination = new Point2D[Int](0, 0)
  val PUNISH_PER_OBSTACLE = 2
  val PUNISH_FIRST_OBSTACLE = 1000

  def setMap(newObstacleMap: Array[Array[Boolean]]) = {
    val newSize = getTabularSize(newObstacleMap)
    if (getTabularSize(obstacleMap) != newSize)
      ai.resize(newSize* PathFinder.Direction.bitSize)
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
    ai = new GA(POP_SIZE = 32, BIT_SIZE = getTabularSize(obstacleMap) * PathFinder.Direction.bitSize, P_SELECTION = 0.25,
      P_MUTATION_POW = 2, A_MUTATION_POW = 4, PARENT_IMMUTABLE = false,
      EVAL_FITNESS_FUNCTION = eval, PROBLEM_TYPE = ProblemType.Minimize,
      LOOP_INTERVAL = 100L)
    ai.genes.foreach(gene => gene.rawCode)
  }

  def eval(rawCode: Array[Boolean]): Double = {
    val directionMap = PathFinder.decode(rawCode)
    val MAX_STEP = getMaxStep
    var step = 0
    val currentPosition = source
    var obstacleCount = 0
    while (step < MAX_STEP && !destination.equals(currentPosition)) {
      currentPosition._1 += direction_array(directionMap(step))._1
      currentPosition._2 += direction_array(directionMap(step))._2
      if (obstacleMap(currentPosition._1)(currentPosition._2)) obstacleCount += 1
      step += 1
    }
    if (obstacleCount >= 0)
      step + PUNISH_FIRST_OBSTACLE + obstacleCount * PUNISH_PER_OBSTACLE
    else
      step
  }

  def getMaxStep: Long = {
    val distance = getOverallDirection
    Math.round(Math.pow(distance._1 * distance._1 + distance._2 * distance._2, 2))
  }

  def getOverallDirection: Point2D[Int] = {
    minus(destination, source)
  }
}
