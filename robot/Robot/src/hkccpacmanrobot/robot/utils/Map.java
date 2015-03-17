package hkccpacmanrobot.robot.utils;

import hkccpacmanrobot.robot.core.ObstacleMapUnit;

import java.util.Vector;

public class Map {
    private int size = 20;
    //private boolean axis[][] = new boolean[size][size];
    private long x_axis;
    private long y_axis;
    private long time;
    private Vector<ObstacleMapUnit> obstacleMap = new Vector<ObstacleMapUnit>();

    public void updateObstacle(double x, double y, boolean isObstacle,
                               long time) {
        updateObstacle(x, y, isObstacle, true, time);
    }

    public void updateObstacle(double x, double y, boolean isObstacle,
                               boolean shouldUpdateDatabase, long time) {
        x_axis = Math.round(x / 5) * 5;
        y_axis = Math.round(y / 5) * 5;
        // this.axis[x_axis][y_axis] = isObstacle;
        ObstacleMapUnit unit = null;
        for (ObstacleMapUnit iObstacleMapUnit : obstacleMap) {
            if ((iObstacleMapUnit.x == x_axis)
                    && (iObstacleMapUnit.y == y_axis)) {
                unit = iObstacleMapUnit;
                break;
            }
        }
        if ((unit == null) && (isObstacle))

            obstacleMap.add(new ObstacleMapUnit(x, y, time));
            //update to database(insect)
        else if ((unit != null) && (!isObstacle))
            obstacleMap.remove(unit);
            //update to database(update)
        else
            shouldUpdateDatabase = false;
        if (shouldUpdateDatabase) {
            //update to database(no obstacle in here)
        }
    }

    public boolean checkObstacle(double x, double y) {
        long xLong = Math.round(x);
        long yLong = Math.round(y);
        for (ObstacleMapUnit iObstacleMapUnit : obstacleMap) {
            if ((iObstacleMapUnit.x == xLong) && (iObstacleMapUnit.y == yLong))
                return true;
        }
        return false;
    }
//
//   public void showMap() {
//       for (int i = 0; i < size; i++) {
//           for (int j = 0; j < size; j++) {
//               if (checkObstacle(i, j))
//                    //if (axis[i][j])
//                    System.out.println(1);
//                else
//                    System.out.println(0);
//            }
//            System.out.print("\n");
//        }
//    }

    public long getLastServerTime() {

        return time;
    }

    public long getLastLocalTime() {
    }

    public long getLastTime() {
    }

}