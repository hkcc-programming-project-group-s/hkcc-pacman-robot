package edu.hkcc.pacmanrobot.utils

/**
 * Created by beenotung on 4/9/15.
 */
object Timer {
  def setTimeInterval(callback: => Unit, shouldDo: => Boolean, time: Long) {
    while (shouldDo) {
      callback
      sleep(time)
    }
  }

  def sleep(time: Long) = {
    Thread sleep time
  }

  def setTimeDelay(callback: => Unit, shouldDo: => Boolean, time: Long) {
    sleep(time)
    callback
  }
}
