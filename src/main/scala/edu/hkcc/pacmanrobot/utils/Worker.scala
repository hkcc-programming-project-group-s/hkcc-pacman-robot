package edu.hkcc.pacmanrobot.utils

/**
 * Created by beenotung on 4/14/15.
 */
object Worker {
  def fork(op: =>Unit): Thread = {
    val thread = new Thread(new Runnable {
      override def run(): Unit = {
        op
      }
    })
    thread
  }

  def forkAndStart(op: => Unit): Thread = {
    val thread = fork(op)
    thread.start
    thread
  }
}
