package studentrobot.code

/**
 * Created by 13058536A on 3/25/2015.
 */
abstract class Robot extends Device {
  //public ObstacleMap<RobotMapUnit> map = new ObstacleMap<RobotMapUnit>();

  override def setup = {
    L298NAO.both_stop()
  }
}
