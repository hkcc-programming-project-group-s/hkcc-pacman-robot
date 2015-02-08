package hkccpacmanrobot.robot.utils;

import java.sql.Timestamp;

public class Position {
    public double x, y, z;
    public Timestamp time;

    public Position(double x, double y, double z, Timestamp time) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.time = time;
    }


}
