package hkccpacmanrobot.utils.map;

import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.function.BiConsumer;

/**
 * Created by 13058456a on 3/18/2015.
 */
public class ObstacleMapManager {
    public ObstacleMap map;
    private Semaphore semaphore = new Semaphore(1, true);
    private long lastSendMapTime, lastReceiveMapTime;

    /*return cloned map (only when a robot forget all map data)*/
    public ObstacleMap getMap() {
        semaphore.tryAcquire();
        ObstacleMap bufferedMap = (ObstacleMap) map.clone();
        semaphore.release();
        return bufferedMap;
    }

    public static interface ShouldUpdateOperator {
        public boolean mapCompare(ObstacleMapManager obstacleMapManager, MapKey key, MapContent value);
    }

    /*return new date (between current and last update)*/
    protected ObstacleMap getDeltaMap(ShouldUpdateOperator operator) {
        semaphore.tryAcquire();
        ObstacleMap bufferedMap = new ObstacleMap();
        map.forEach(new BiConsumer<MapKey, MapContent>() {
            @Override
            public void accept(MapKey key, MapContent value) {
                if (operator.mapCompare(ObstacleMapManager.this, key, value))
                    bufferedMap.put(key, value);
            }
        });
        semaphore.release();
        return bufferedMap;
    }

    /*interact with other devices/server*/
    protected void sendMap(ShouldUpdateOperator operator) {
        ObstacleMap bufferedMap = getDeltaMap(operator);
        lastSendMapTime = System.currentTimeMillis();
        //send over network
        //TODO
    }

    /*local map += deltaObstacleMap*/
    public void addMap(ObstacleMap deltaObstacleMap) {
        semaphore.tryAcquire();
        map.putAll(deltaObstacleMap);
        semaphore.release();
    }

    public void addMap(List<MapUnit> mapUnits) {
        semaphore.tryAcquire();
        mapUnits.forEach(map::put);
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
