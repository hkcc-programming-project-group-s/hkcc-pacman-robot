package SocketProgramming;
import static java.lang.Math.*;

public class MoveCommandProcessor2D {
    private double x = 0;
    private double y = 0;

    private long lastTime = System.nanoTime();
    private long nowTime;
    private long timeInterval;
    private double degree = 0;
    private double radian = 0;

    private MoveCommandProcessor2D acceleration, velocity, displacement;

    public MoveCommandProcessor2D() {
        lastTime = System.nanoTime();
        this.acceleration.x = 0;
        this.acceleration.y = 0;
        velocity.x = 0;
        velocity.y = 0;
        displacement.x = 0;
        displacement.y = 0;
    }

    public void onSensorUpdate(double x, double y, double degree) {
        nowTime = System.nanoTime();
        timeInterval = nowTime - lastTime;
        lastTime = nowTime;

        // Convert degree to appropriate form
        if (degree < 0)
            this.degree = 360 - abs(degree);
        else if (degree == -180)
            this.degree = abs(degree);
        else
            this.degree = degree;

        convertToRadian();
        form2DRotation(x, y);

        // Update acceleration, velocity, displacement
        acceleration.x /= 1d * timeInterval;
        acceleration.y /= 1d * timeInterval;
        velocity.x += acceleration.x * timeInterval;
        velocity.y += acceleration.y * timeInterval;
        displacement.x += velocity.x * timeInterval;
        displacement.y += velocity.y * timeInterval;
    }

    private void form2DRotation(double x, double y) {
        acceleration.y = y * cos(radian) - x * sin(radian);
        acceleration.x = y * sin(radian) + x * cos(radian);
    }

    private void convertToRadian() {
        radian = degree * (PI / 180);
    }
}
