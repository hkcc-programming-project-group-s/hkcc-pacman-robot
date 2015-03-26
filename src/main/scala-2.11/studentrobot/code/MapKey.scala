package studentrobot.code

/**
 * Created by beenotung on 3/21/15.
 */
class MapKey(x: Long, y: Long) extends Cloneable {
  override def clone: AnyRef = {
    new MapKey(x, y)
  }
}
