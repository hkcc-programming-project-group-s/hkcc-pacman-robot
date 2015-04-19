package edu.hkcc.pacmanrobot.utils;

import com.sun.istack.internal.NotNull;

import java.io.Serializable;

/**
 * Created by beenotung on 3/31/15.
 */
public class Point2D<Type> implements Serializable, Cloneable {
    public Type _1, _2;

    public Point2D(@NotNull Type _1, @NotNull Type _2) {
        this._1 = _1;
        this._2 = _2;
    }

    public static Point2D<Double> create(double _1, double _2) {
        return new Point2D<Double>(_1, _2);
    }

    public void set(Type _1, Type _2) {
        this._1 = _1;
        this._2 = _2;
    }

    public void set_1(Type _1) {
        this._1 = _1;
    }

    public void set_2(Type _2) {
        this._2 = _2;
    }

    @Override
    public Point2D<Type> clone() {
        return new Point2D<Type>(_1, _2);
    }

    @Override
    public String toString() {
        return "[" + _1 + "," + _2 + "]";
    }
}
