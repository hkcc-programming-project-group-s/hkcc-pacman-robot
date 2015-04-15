package edu.hkcc.pacmanrobot.utils.lang

import java.util.concurrent.Semaphore

import scala.collection.parallel.mutable.ParArray

/**
 * Created by beenotung on 4/15/15.
 */
class MutableParArray[Type] extends {
  val semaphore = new Semaphore(1)

  var _self: ParArray[Type] = ParArray.empty[Type]

  def add(contents: ParArray[Type]) :Unit= {
    contents.foreach(m => add(m))
  }

  def add(content: Type) :Unit= {
    semaphore.acquire()
    self_(self :+ content)
    semaphore.release()
  }

  def remove(contents: ParArray[Type]) :Unit= {
    contents.foreach(m => remove(m))
  }

  def remove(newContent: Type) :Unit= {
    semaphore.acquire()
    self_(self.filter(m => newContent.equals(m)))
    semaphore.release()
  }

  def self_(newParArray: ParArray[Type]) = {
    _self = newParArray
  }

  def self: ParArray[Type] = _self
}
