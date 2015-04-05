package edu.hkcc.pacmanrobot.robot.deadlinerobot;

/**
 * Created by beenotung on 3/27/15.
 */
public class Launcher {
    public static void main(String[] args) {
        DeadlineRobot deadlineRobot = new DeadlineRobot("test deadline");
        deadlineRobot.start();
    }
}
