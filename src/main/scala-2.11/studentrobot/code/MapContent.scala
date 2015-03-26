package studentrobot.code

/**
 * Created by beenotung on 3/21/15.
 */
class MapContent(var time: Long = 0L, var preservedLong: Long = 0L) {

  def set(mapContent: MapContent) {
    time = mapContent.time
    preservedLong = mapContent.preservedLong
  }

  protected override def clone: AnyRef = {
    new MapContent(time, preservedLong)
  }
}
