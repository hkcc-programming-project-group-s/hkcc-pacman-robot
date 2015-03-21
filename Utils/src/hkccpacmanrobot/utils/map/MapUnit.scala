package hkccpacmanrobot.utils.map

/**
 * Created by beenotung on 3/21/15.
 */
abstract class MapUnit (val key: MapKey,val  value: MapContent=new MapContent) extends Cloneable{

  abstract  def getLatestTime: Long


  def setTime(Time: Long, preservedLong: Long)
}
