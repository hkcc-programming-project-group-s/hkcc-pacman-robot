package hkccpacmanrobot.utils.map;

/**
 * Created by 13058456a on 3/18/2015.
 */
public class MapKey implements Cloneable{
    public final long x, y;

    public MapKey(long x, long y) {
        this.x = x;
        this.y = y;
    }

    public MapKey(double x, double y) {
        this.x = Math.round(x / 5);
        this.y = Math.round(y / 5);
    }

    @Override
    protected Object clone() {
        return new MapKey(x,y);
    }
}
