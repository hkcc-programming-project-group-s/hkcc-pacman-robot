package studentrobot.code

/**
 * Created by 13058536A on 3/25/2015.
 */
class Position(var x: Double = 0d, var y: Double = 0d) extends Message {
  override val port: Int = Config.PORT_POSITION
}
