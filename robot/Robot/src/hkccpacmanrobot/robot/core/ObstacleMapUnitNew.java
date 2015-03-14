package hkccpacmanrobot.robot.core;

public abstract class ObstacleMapUnitNew {
    public long x, y, time, preservedLong;


    ObstacleMapUnitNew(double x, double y) {
        this.x = Math.round(x / 5);
        this.y = Math.round(y / 5);
    }

    public abstract long getLatestTime();
    //public abstract void setLatestTime();
}
