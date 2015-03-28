package edu.hkcc.pacmanrobot.robot.studentrobot;

import edu.hkcc.pacmanrobot.robot.utils.L298NAO;

/**
 * Created by beenotung on 3/27/15.
 */
public class Launcher {
    public static void main(String[] args) {
        L298NAO.both_stop();
        StudentRobot studentRobot = new StudentRobot();
        studentRobot.start();
    }
}
