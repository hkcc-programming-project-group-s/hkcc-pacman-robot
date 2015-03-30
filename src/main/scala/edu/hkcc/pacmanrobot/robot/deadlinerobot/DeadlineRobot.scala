package edu.hkcc.pacmanrobot.robot.deadlinerobot

/**
 * Created by beenotung on 3/30/15.
 */

import edu.hkcc.pacmanrobot.robot.core.Robot
import edu.hkcc.pacmanrobot.robot.utils.{L298NAO, Mpu6050AO}
import edu.hkcc.pacmanrobot.utils.maths.Point2D


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
    L298NAO.both_stop()
  }

  override def run {
    L298NAO.move(getTargetPosition)
  }

  def loop: Unit = {
    L298NAO.move(getTargetPosition)
  }

  def getTargetPosition: Point2D = {
    new Point2D(Mpu6050AO.getZRotaion)
  }
}
