package hkccpacmanrobot.utils.map;

import java.util.concurrent.Semaphore;
import java.util.function.BiConsumer;

/**
 * Created by 13058456a on 3/18/2015.
 */
public class ObstacleMapManager {
    public ObstacleMap map;
    private Semaphore semaphore = new Semaphore(1, true);
    private long lastUpdateTime = 0L;

    /*return cloned map*/
    public ObstacleMap getMap() {
        semaphore.tryAcquire();
        ObstacleMap bufferedMap = (ObstacleMap) map.clone();
        semaphore.release();
        return bufferedMap;
    }

    public ObstacleMap getDeltaMap() {
        semaphore.tryAcquire();
        ObstacleMap bufferedMap = new ObstacleMap();
        map.forEach(new BiConsumer<MapKey, MapContent>() {
            @Override
            public void accept(MapKey key, MapContent value) {
                if (value.time > lastUpdateTime)
                    bufferedMap.put(key, value);
            }
        });
        semaphore.release();
        return bufferedMap;
    }

    /*interact with other devices/server*/
    public void sendMap() {
        //TODO
        ObstacleMap bufferedMap = getDeltaMap();
        //remove duplicated date (only keep new data)
        for (MapKey key : bufferedMap.keySet()) {
            if (bufferedMap.get(key).time)
        }
        //send over network

    }

    /*local map += deltaObstacleMap*/
    public void addMap(ObstacleMap deltaObstacleMap) {
        semaphore.tryAcquire();
        map.putAll(deltaObstacleMap);
        semaphore.release();
    }

    public void remove(MapKey key) {
        semaphore.tryAcquire();
        map.remove(key);
        semaphore.release();
    }

    public void remove(ObstacleMap deltaObstacleMap) {
        semaphore.tryAcquire();
        deltaObstacleMap.keySet().forEach(map::remove);
        semaphore.release();
    }
}