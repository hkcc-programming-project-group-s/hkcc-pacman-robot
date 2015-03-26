package studentrobot.code;


import studentrobot.code.Maths.Point2D;

/**
 * Created by beenotung on 3/26/15.
 */
public class MovementCommand implements Cloneable, Message {
    public static final int port = Config.PORT_MOVEMENT_COMMAND;
    public static final byte MODE_POLAR = 0x01;
    public static final byte MODE_RECTANGULAR = 0x02;
    public Point2D point2D;
    public byte mode;

    public MovementCommand(byte mode, Point2D point2D) {
        this.mode = mode;
        this.point2D = point2D;
    }


    public MovementCommand(Point2D point2D) {
        this(MODE_POLAR, point2D);
    }

    public static MovementCommand stop() {
        return new MovementCommand(new Point2D(0d, 0d));
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
