package edu.hkcc.pacmanrobot.utils

/**
 * Created by beenotung on 4/9/15.
 */

import edu.hkcc.pacmanrobot.utils.Worker._

object Timer {
  def setTimeInterval(callback: => Unit, shouldDo: => Boolean, time: Long) {
    forkAndStart({
      while (shouldDo) {
        callback
        sleep(time)
      }
    })
  }

  def sleep(time: Long) = {
    Thread sleep time
  }

  def setTimeDelay(callback: => Unit, time: Long) {
    forkAndStart({
      sleep(time)
      callback
    })
  }
}
