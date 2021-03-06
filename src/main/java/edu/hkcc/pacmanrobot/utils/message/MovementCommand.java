package edu.hkcc.pacmanrobot.utils.message;


import edu.hkcc.pacmanrobot.utils.Config;
import edu.hkcc.pacmanrobot.utils.Point2D;

/**
 * Created by beenotung on 2/10/15.
 */
public class MovementCommand implements Cloneable, Message {
    public static final int port = Config.PORT_MOVEMENT_COMMAND;
    public static final byte MODE_POLAR = 0x01;
    public static final byte MODE_RECTANGULAR = 0x02;
    public Point2D<Double> point2D;
    public byte mode;

    public MovementCommand(byte mode, Point2D<Double> point2D) {
        this.mode = mode;
        this.point2D = point2D;
    }


    public MovementCommand(Point2D<Double> point2D) {
        this(MODE_POLAR, point2D);
    }

    public static MovementCommand stop() {
        return new MovementCommand(new Point2D<Double>(0d, 0d));
    }

    @Override
    public MovementCommand clone() {
        return new MovementCommand(mode, point2D.clone());
    }


    @Override
    public int port() {
        return port;
    }

    @Override
    public String toString() {
        return ((mode == MODE_POLAR) ? "Polar" : "Position ") + point2D.toString();
    }
}
