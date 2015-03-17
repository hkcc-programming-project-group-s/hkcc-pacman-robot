package hkccpacmanrobot.utils.map;

public abstract class ObstacleMapUnitNew implements  Cloneable{
    public long x, y, time, preservedLong;

    public ObstacleMapUnitNew(double x, double y) {
        this.x = Math.round(x / 5);
        this.y = Math.round(y / 5);
    }

    public abstract long getLatestTime();
    public abstract void setTime(long Time,long preservedLong);
}
