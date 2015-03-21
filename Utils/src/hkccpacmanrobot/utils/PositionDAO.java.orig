package hkccpacmanrobot.utils;

import java.sql.Timestamp;

//Sever Access Object
public class PositionDAO extends Thread {


    public Position planPosition;


    /*
    send the position(x,y) and time to database per ?? s
	*/
    public void sendPosition(double x_axis, double y_axis, double z_axis, int robotID, Timestamp time) {
        sendPosition(robotID, new Position(x_axis, y_axis, z_axis, time));
    }

    public void sendPosition(int robotID, Position position) {
        //TODO send to database
        //TODO tell server
    }

    /*
        It is for deadline and sever
		getMap position of this robot ID from database
	*/
    public Position getTargetPosition(int robotID) {
        double x, y, z;
        x = y = z = 0;

        Position result = new Position(x, y, z);

        return result;
    }

    /*
        It is for sever
        getMap the time of this robot ID from database
     */
    public Timestamp getTime(int robotID) {
        Timestamp time = null;


        return time;
    }

}
