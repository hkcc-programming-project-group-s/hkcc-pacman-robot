package hkccpacmanrobot.robot.core;

class ObstacleMapUnitFactory {
    public ObstacleMapUnit createFromServer(double x, double y, long time) {
        ObstacleMapUnit result = new ObstacleMapUnit(x, y);
        result.serverTime = time;
        return result;
    }

    public ObstacleMapUnit createFromRobot(double x, double y, long time) {
        ObstacleMapUnit result = new ObstacleMapUnit(x, y);
        result.localTime = time;
        return result;
    }

    public ObstacleMapUnit createToServer(double x, double y, long time, long robotID) {
        ObstacleMapUnit result = new ObstacleMapUnit(x, y);
        result.localTime = time;
        result.serverTime = robotID;
        return result;
    }

}

public class ObstacleMapUnit {
    public long x, y, localTime, serverTime;


    ObstacleMapUnit(double x, double y) {
        this.x = Math.round(x / 5);
        this.y = Math.round(y / 5);
    }

    public long getLatestTime() {
        return (serverTime < localTime) ? serverTime : localTime;
    }
}
