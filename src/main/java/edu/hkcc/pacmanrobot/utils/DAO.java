package edu.hkcc.pacmanrobot.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Timestamp;

//import com.mysql.jdbc.ConnectionInfo;

//Database Access Object
public class DAO {
    public Connection connection;
    public MapDAO mapDAO;

    public DAO() throws SQLException {
        connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306",
                "root", "rootpw");
    }

    public static class Position {
        public double x, y, z;
        public Timestamp time;

        public Position(double x, double y, double z, Timestamp time) {
            this.x = x;
            this.y = y;
            this.z = z;
            this.time = time;
        }

        public Position(double x, double y, double z) {
            this.x = x;
            this.y = y;
            this.z = z;
            time = null;
        }

    }

    //Sever Access Object
    public static class PositionManager extends Thread {


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
            get position of this robot ID from database
        */
        public Position getTargetPosition(int robotID) {
            double x, y, z;
            x = y = z = 0;

            Position result = new Position(x, y, z);

            return result;
        }

        /*
            It is for sever
            get the time of this robot ID from database
         */
        public Timestamp getTime(int robotID) {
            Timestamp time = null;


            return time;
        }

    }
}
