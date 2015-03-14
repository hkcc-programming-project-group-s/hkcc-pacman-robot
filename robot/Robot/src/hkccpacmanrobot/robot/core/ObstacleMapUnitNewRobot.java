package hkccpacmanrobot.robot.core;

/**
 * Created by 13058456a on 3/14/2015.
 */
public class ObstacleMapUnitNewRobot extends ObstacleMapUnitNew {
    ObstacleMapUnitNewRobot(double x, double y) {
        super(x, y);
    }

    public long getLocalTime(){return time;}
    public long getServerTime(){return preservedLong;}

    @Override
    public long getLatestTime() {
        return (getServerTime() < getLocalTime()) ? getServerTime() : getLocalTime();
    }
}
