package edu.hkcc.pacmanrobot.robot.deadlinerobot;

import edu.hkcc.pacmanrobot.robot.deadlinerobot.DeadlineRobot;
import edu.hkcc.pacmanrobot.robot.studentrobot.StudentRobot;
import edu.hkcc.pacmanrobot.robot.utils.L298NAO;
import edu.hkcc.pacmanrobot.robot.utils.Mpu6050AO;

/**
 * Created by beenotung on 3/27/15.
 */
public class Launcher {
    public static void main(String[] args) {
        L298NAO.both_stop();
        Mpu6050AO.start();
        DeadlineRobot deadlineRobot=new DeadlineRobot();
        deadlineRobot.start();
    }
}
