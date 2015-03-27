package edu.hkcc.pacmanrobot.utils.studentrobot.code

/**
 * Created by beenotung on 3/21/15.
 */
abstract class MapUnit(val key: MapKey, val value: MapContent = new MapContent) extends Cloneable {
  def getLatestTime: Long

  def setTime(Time: Long, preservedLong: Long)
}
