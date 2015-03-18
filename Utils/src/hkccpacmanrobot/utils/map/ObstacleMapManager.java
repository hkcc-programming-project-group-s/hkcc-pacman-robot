package hkccpacmanrobot.utils.map;

/**
 * Created by 13058456a on 3/18/2015.
 */
public class ObstacleMapManager {
    public ObstacleMap map;
    private boolean locked;

    public synchronized void getKey() {
        //TODO
        while (locked)
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
            }
        locked = true;
    }

    public void releaseKey() {
        //TODO
        locked = false;
    }

    public ObstacleMap getMap() {
        return map;
    }

    /*local map += deltaObstacleMap*/
    public void setMap(ObstacleMap deltaObstacleMap) {
        getKey();
        map.forEach(map::put);
        releaseKey();
    }

    public void removeMapContent(MapKey key) {
        getKey();
        map.remove(key);
        releaseKey();
    }
}
