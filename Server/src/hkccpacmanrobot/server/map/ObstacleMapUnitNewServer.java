package hkccpacmanrobot.server.map;

import hkccpacmanrobot.utils.map.ObstacleMapUnitNew;

/**
 * Created by 13058456a on 3/14/2015.
 */
public class ObstacleMapUnitNewServer extends ObstacleMapUnitNew {


    ObstacleMapUnitNewServer(double x, double y) {
        super(x, y);
    }


    @Override

    public long getLatestTime() {return time;}

//    public long getServerTime() {
  //      return time;
//    }

    public long getRobotId() {
        return preservedLong;
    }

    //public long getMap(){return x,y;}

    public void setTime(long localTime,long robotId){
        time=localTime;
        preservedLong=robotId;
    };


    @Override
    protected Object clone() throws CloneNotSupportedException {
        ObstacleMapUnitNewServer result=new ObstacleMapUnitNewServer(x,y);
        result.setTime(getLatestTime(),getRobotId());
        return result;
    }
}

