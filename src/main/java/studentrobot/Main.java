package studentrobot;


import studentrobot.code.L298NAO;
import studentrobot.code.StudentRobot;

/**
 * Created by beenotung on 3/27/15.
 */
public class Main {
    public static void main(String[] args) {
        L298NAO.both_stop();
        StudentRobot studentRobot = new StudentRobot();
        studentRobot.start();
    }
}
