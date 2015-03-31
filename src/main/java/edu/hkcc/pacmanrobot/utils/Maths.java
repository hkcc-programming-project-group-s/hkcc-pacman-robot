package edu.hkcc.pacmanrobot.utils;

/**
 * Created by beenotung on 3/26/15.
 */
public class Maths {
    public static final double F = 0d;
    public static final double F_R = java.lang.Math.PI * 0.25;
    public static final double R = java.lang.Math.PI * 0.5;
    public static final double B_R = java.lang.Math.PI * 0.75;
    public static final double B = java.lang.Math.PI;
    public static final double B_L = java.lang.Math.PI * 1.25;
    public static final double L = java.lang.Math.PI * 1.5;
    public static final double F_L = java.lang.Math.PI * 1.75;
    public static final double PI2 = java.lang.Math.PI * 2d;
    public static final double DEFAULT_RANGE = java.lang.Math.PI / 8d;

    public static boolean isBetween(double lower, double target, double upper) {
        return ((lower <= target) && (target <= upper));
    }

    public static boolean isInRange(double a, double b, double range) {
        return java.lang.Math.abs(a - b) <= range;
    }

    public static boolean isInRange(double a, double b) {
        return java.lang.Math.abs(a - b) <= DEFAULT_RANGE;
    }

    public static double length(double x, double y) {
        return java.lang.Math.sqrt((x * x) + (y * y));
    }

}
