package edu.hkcc.pacmanrobot.utils.map

/**
 * Created by beenotung on 3/21/15.
 */
class MapKey(val x: Long, val y: Long) extends Cloneable {
  override def clone: AnyRef = {
    new MapKey(x, y)
  }

  def equal(mapKey: MapKey): Boolean = {
    x.equals(mapKey.x) && y.equals(mapKey.y)
  }
}
