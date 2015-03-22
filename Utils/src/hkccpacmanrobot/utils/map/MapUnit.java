package hkccpacmanrobot.utils.map;

public abstract class MapUnit implements Cloneable {
    public final MapKey key;
    public final MapContent value;


    public MapUnit(MapKey key) {
        this.key = key;
        this.value = new MapContent();
    }

    public MapUnit(MapKey key, MapContent value) {
        this.key = key;
        this.value = value;
    }

    public abstract long getLatestTime();

    public abstract void setTime(long Time, long preservedLong);
}
