package hkccpacmanrobot.robot.utils;

public class GyroscopeAdjuster {
    // default radian
    private double positionX, positionY;
    private double x,y;
    private double angle;
    private double velocity;
    private int initialQuadrant;
    private double acceleration;

    public GyroscopeAdjuster(float x, float y) {
        positionX = x;
        positionY = y;

        if (x > 0 && y > 0)
            initialQuadrant = 1;
        else if (x < 0 && y > 0)
            initialQuadrant = 2;
        else if (x < 0 && y < 0)
            initialQuadrant = 3;
        else if (x > 0 && y < 0)
            initialQuadrant = 4;
    }

    public void run() {
        velocity = velocity + acceleration;

    }
}
