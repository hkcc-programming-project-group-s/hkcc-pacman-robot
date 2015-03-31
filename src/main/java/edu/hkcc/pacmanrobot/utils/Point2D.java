package edu.hkcc.pacmanrobot.utils;

import java.io.Serializable;

/**
 * Created by beenotung on 3/31/15.
 */
public class Point2D implements Serializable, Cloneable {
    public double _1, _2;

    @Override
    public Point2D clone(){
        return new Point2D(_1, _2);
    }

    public Point2D(double _1, double _2) {
        this._1 = _1;
        this._2 = _2;
    }

    @Override
    public String toString() {
        return String.format("[%.2f,%.2f]", _1, _2);
    }
}
