package hkccpacmanrobot.server.map;



/**
 * Created by 13058456a on 3/17/2015.
 */
public class ObstacleMepServer {
    private double x,y;
    ServerMapUnit map = new ServerMapUnit(x,y);

    public void updateMap(double x,double y,long localTime,long robotID){
        this.x=x;
        this.y=y;
        map.setTime(localTime, robotID);
    }

    public String getMap(long localTime,long robotID){

        //use for-loop to loop the list

        if(map.getLatestTime()>=localTime){
            if(map.getRobotId()==robotID){
                //TODO get map data
            }
        }
        return null;//change null when yoe finish.
    }

    public void listData(){
        long localTime=0,robotID=0;//TODO !!! change 0 when you finish !!!

        sendMap(getMap(localTime,robotID));
    }

    public void sendMap(String sendMap){


    }
}
