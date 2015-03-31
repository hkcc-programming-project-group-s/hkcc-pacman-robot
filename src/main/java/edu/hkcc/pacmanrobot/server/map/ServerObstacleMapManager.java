package edu.hkcc.pacmanrobot.server.map;


import edu.hkcc.pacmanrobot.utils.map.MapContent;
import edu.hkcc.pacmanrobot.utils.map.MapKey;
import edu.hkcc.pacmanrobot.utils.map.ObstacleMapManager;

/**
 * Created by 13058456a on 3/17/2015.
 */

//TODO change into scala
public class ServerObstacleMapManager extends ObstacleMapManager {


    @Override
    public boolean shouldUpdate(MapKey key, MapContent value) {
        return false;
    }

    @Override
    public void myReceiveMap() {

    }

    public String getMap(long localTime, long robotID) {

        //use for-loop to loop the list

//        if (map.getLatestTime() >= localTime) {
//            if (map.getRobotId() == robotID) {
//                //TODO getMap map data
//            }
//        }
        return null;//change null when yoe finish.
    }

    public void listData() {
        long localTime = 0, robotID = 0;//TODO !!! change 0 when you finish !!!

        //sendMap(getMap(localTime, robotID));
    }


}