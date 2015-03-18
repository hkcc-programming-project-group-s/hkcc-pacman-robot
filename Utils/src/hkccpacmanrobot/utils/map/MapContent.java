package hkccpacmanrobot.utils.map;

/**
 * Created by 13058456a on 3/18/2015.
 */
public class MapContent {
    public long time, preservedLong;

    public MapContent() {
    }

    public MapContent(long time, long preservedLong) {
        this.time = time;
        this.preservedLong = preservedLong;
    }

    public void set(MapContent mapContent) {
        this.time = mapContent.time;
        this.preservedLong = mapContent.preservedLong;
    }
}
