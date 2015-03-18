package hkccpacmanrobot.robot.core;
import hkccpacmanrobot.utils.map.MapUnit;
/**
 * Created by 13058456a on 3/14/2015.
 */
public class RobotMapUnit extends MapUnit {
    RobotMapUnit(double x, double y) {
        super(x, y);
    }

    public long getLocalTime() {
        return time;
    }

    public long getServerTime() {
        return preservedLong;
    }

    @Override
    public long getLatestTime() {
        return (getServerTime() < getLocalTime()) ? getServerTime() : getLocalTime();
    }

    public void setTime(long localTime,long serverTime){
        time=localTime;
        preservedLong=serverTime;
    };


}

