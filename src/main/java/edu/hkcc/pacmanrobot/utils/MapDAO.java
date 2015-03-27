package edu.hkcc.pacmanrobot.utils;

import edu.hkcc.pacmanrobot.utils.DAO;

public class MapDAO {
    public static final String TABLE_NAME = "map";
    public static final String TABLE_COL_X = "x";
    public static final String TABLE_COL_Y = "y";
    public static final String TABLE_COL_UPDATE_TIME = "update_time";
    public static final String TABLE_COL_ISOBSTABLE = "isobstacle";
    // create table and index
    public static final String TABLE_CREATE = "CREATE TABLE IF NOT EXISTS "
            + TABLE_NAME + " ( " + TABLE_COL_X + " INTEGER PRIMARY KEY,"
            + TABLE_COL_Y + " INTEGER PRIMARY KEY," + TABLE_COL_ISOBSTABLE
            + " BOOLEAN, " + TABLE_COL_UPDATE_TIME + " TIMESTAMP);"

            + "CREATE INDEX index_" + TABLE_NAME + "_timestamp ON "
            + TABLE_NAME + " (" + TABLE_COL_UPDATE_TIME + ");";
    private final DAO dao;

    public MapDAO(DAO dao) {
        this.dao = dao;
    }

    public void update(double x, double y, boolean isObstacle) {
    }

    public void insert(double x, double y, boolean isObstacle) {

    }
}
