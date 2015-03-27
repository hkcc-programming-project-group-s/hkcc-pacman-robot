package studentrobot.code;

import java.io.Serializable;

/**
 * Created by beenotung on 3/26/15.
 */
public class Maths {
    public static final double F = 0d;
    public static final double F_R = Math.PI * 0.25;
    public static final double R = Math.PI * 0.5;
    public static final double B_R = Math.PI * 0.75;
    public static final double B = Math.PI;
    public static final double B_L = Math.PI * 1.25;
    public static final double L = Math.PI * 1.5;
    public static final double F_L = Math.PI * 1.75;
    public static final double PI2 = Math.PI * 2d;

    public static boolean isBetween(double lower, double target, double upper) {
        return ((lower <= target) && (target <= upper));
    }

    public static boolean isInRange(double a, double b, double range) {
        return Math.abs(a - b) <= range;
    }

    public static final double DEFAULT_RANGE = Math.PI / 8d;

    public static boolean isInRange(double a, double b) {
        return Math.abs(a - b) <= DEFAULT_RANGE;
    }


    /*
    * reserved for rectangular coordinate or polar coordinate
    * POLAR: d1 = degree (in radian), d2 = distance
    * RECTANGULAR:: d1 =x, d2 = y
    */
    public static class Point2D implements Cloneable, Serializable {
        public double d1;
        public double d2;

        public Point2D(double d1, double d2) {
            this.d1 = d1;
            this.d2 = d2;
        }

        @Override
        public Point2D clone() {
            return new Point2D(d1, d2);
        }

        @Override
        public String toString() {
            return String.format("[%.2f,%.2f]\n", d1, d2);
        }
    }
}
