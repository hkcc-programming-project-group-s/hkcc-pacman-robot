package hkccpacmanrobot.utils.map;

import java.util.HashMap;

/**
 * Created by 13058456a on 3/17/2015.
 */
public class ObstacleMap extends HashMap<MapKey, MapContent> {
    public boolean isExist(MapUnit target) {
        return keySet().contains(target.key);
    }

    public void put(MapUnit mapUnit) {
        put(mapUnit.key, mapUnit.value);
    }

    public void get(MapUnit mapUnit) {
        mapUnit.value.set(get(mapUnit.key));
    }
}
