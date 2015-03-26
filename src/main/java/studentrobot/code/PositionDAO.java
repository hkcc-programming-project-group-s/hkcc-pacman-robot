package studentrobot.code;

import java.sql.Timestamp;

//Sever Access Object
public class PositionDAO extends Thread {


    public DAO.Position planPosition;


    /*
    send the position(x,y) and time to database per ?? s
	*/
    public void sendPosition(double x_axis, double y_axis, double z_axis, int robotID, Timestamp time) {
        sendPosition(robotID, new DAO.Position(x_axis, y_axis, z_axis, time));
    }

    public void sendPosition(int robotID, DAO.Position position) {
        //TODO send to database
        //TODO tell server
    }

    /*
        It is for deadline and sever
		getMap position of this robot ID from database
	*/
    public DAO.Position getTargetPosition(int robotID) {
        double x, y, z;
        x = y = z = 0;

        DAO.Position result = new DAO.Position(x, y, z);

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
