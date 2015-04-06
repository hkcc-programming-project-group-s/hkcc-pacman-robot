package edu.hkcc.pacmanrobot.robot.deadlinerobot

/**
 * Created by beenotung on 3/30/15.
 */

import edu.hkcc.pacmanrobot.robot.core.Robot
import edu.hkcc.pacmanrobot.robot.utils.{L298NAO, Mpu6050AO}
import edu.hkcc.pacmanrobot.utils.Maths._
import edu.hkcc.pacmanrobot.utils.Point2D
import edu.hkcc.pacmanrobot.utils.studentrobot.code.DeviceInfo


class DeadlineRobot(name: String) extends Robot {
  def gameSetup {
  }

  override def gameStart {
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
    val dir: Point2D[java.lang.Double] = getTargetPosition
    if (dir._1 > 0)
      dir._1 = R
    else if (dir._1 < 0)
      dir._1 = L
    L298NAO.move_pwm(dir)
  }

  def getTargetPosition: Point2D[java.lang.Double] = {
    new Point2D[java.lang.Double](range(Mpu6050AO.getZRotaion), 5)
  }

  def range(d: java.lang.Double): java.lang.Double = {
    println("\t turn to: " + d)
    d
  }
}
