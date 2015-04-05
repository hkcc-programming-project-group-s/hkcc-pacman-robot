package edu.hkcc.pacmanrobot.utils.map

/**
 * Created by beenotung on 3/21/15.
 */
class MapKey(x: Int, y: Int) extends Cloneable {
  override def clone: AnyRef = {
    new MapKey(x, y)
  }
}
