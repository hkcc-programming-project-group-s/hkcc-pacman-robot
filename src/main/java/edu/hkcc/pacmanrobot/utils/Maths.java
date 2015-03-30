package edu.hkcc.pacmanrobot.utils;

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
    public static final double DEFAULT_RANGE = Math.PI / 8d;

    public static boolean isBetween(double lower, double target, double upper) {
        return ((lower <= target) && (target <= upper));
    }

    public static boolean isInRange(double a, double b, double range) {
        return Math.abs(a - b) <= range;
    }

    public static boolean isInRange(double a, double b) {
        return Math.abs(a - b) <= DEFAULT_RANGE;
    }

    public static double length(double x, double y) {
        return Math.sqrt((x * x) + (y * y));
    }





}
