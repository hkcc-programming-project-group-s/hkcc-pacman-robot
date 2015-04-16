package edu.hkcc.pacmanrobot.utils.map

/**
 * Created by beenotung on 3/21/15.
 */
class MapKey(val x: Int, val y: Int) extends Cloneable with Serializable {
  override def clone: AnyRef = {
    new MapKey(x, y)
  }

  override def equals(o: Any): Boolean = {
    if (o.isInstanceOf[MapKey])
      equals(o.asInstanceOf[MapKey])
    else false
  }

  def equals(mapKey: MapKey): Boolean = {
    x.equals(mapKey.x) && y.equals(mapKey.y)
  }

  override def toString: String = {
    "(" + x + "," + y + ")"
  }
}
