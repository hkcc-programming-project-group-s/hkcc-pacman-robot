package edu.hkcc.pacmanrobot.robot.deadlinerobot

/**
 * Created by beenotung on 3/30/15.
 */

import edu.hkcc.pacmanrobot.robot.core.Robot
import edu.hkcc.pacmanrobot.robot.utils.{L298NAO, Mpu6050AO}
import edu.hkcc.pacmanrobot.utils.Maths._
import edu.hkcc.pacmanrobot.utils.Point2D


class DeadlineRobot extends Robot {
  def gameSetup {
  }

  def gameStart {
  }

  def gamePause {
  }

  def gameResume {
  }

  def gameStop {
  }

  override def setup: Unit = {
    L298NAO.both_stop
    Mpu6050AO.start
  }

  override def run {
    while (!Mpu6050AO.ready)
      Thread.sleep(10)
    while (true) {
      loop
      Thread.sleep(100)
    }
  }

  def loop: Unit = {
    val dir: Point2D = getTargetPosition
    if (dir._1 > 0)
      dir._1 = R
    else if (dir._1 < 0)
      dir._1 = L
    L298NAO.move(dir)
  }

  def getTargetPosition: Point2D = {
    new Point2D(range(Mpu6050AO.getZRotaion),5)
  }

  def range(d: Double) = {
    println("\t turn to: " + d)
    d
  }
}