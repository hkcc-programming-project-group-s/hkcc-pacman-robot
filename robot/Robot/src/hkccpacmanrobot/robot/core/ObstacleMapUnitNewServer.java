package hkccpacmanrobot.robot.core;

/**
 * Created by 13058456a on 3/14/2015.
 */
public class ObstacleMapUnitNewServer extends ObstacleMapUnitNew {


    ObstacleMapUnitNewServer(double x, double y) {
        super(x, y);
    }

    @Override
    public long getLatestTime() {
        return getServerTime();
    }

    public long getServerTime(){return time;}

    public long getRobotId(){return preservedLong;}

}
