package hkccpacmanrobot.server.map;

import hkccpacmanrobot.utils.map.MapContent;
import hkccpacmanrobot.utils.map.MapKey;
import hkccpacmanrobot.utils.map.MapUnit;

/**
 * Created by 13058456a on 3/14/2015.
 */
public class ServerMapUnit extends MapUnit {


    public ServerMapUnit(MapKey key) {
        super(key);
    }

    public ServerMapUnit(MapKey key, MapContent value) {
        super(key, value);
    }

    @Override

    public long getLatestTime() {return value.time;}

//    public long getServerTime() {
  //      return time;
//    }

    public long getRobotId() {
        return value.preservedLong;
    }

    //public long getMap(){return x,y;}

    public void setTime(long localTime,long robotId){
        value.time=localTime;
        value.preservedLong=robotId;
    };


    @Override
    protected Object clone() throws CloneNotSupportedException {
        ServerMapUnit result=new ServerMapUnit(key,value);
        result.setTime(getLatestTime(),getRobotId());
        return result;
    }
}

