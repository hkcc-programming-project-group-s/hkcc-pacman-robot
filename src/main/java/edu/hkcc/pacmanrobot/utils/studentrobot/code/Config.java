package edu.hkcc.pacmanrobot.utils.studentrobot.code;

/**
 * Created by beenotung on 3/26/15.
 */
public class Config {
    public static int PORT_MAP = 4211;
    public static int PORT_GAME_STATUS = 4212;
    public static int PORT_MOVEMENT_COMMAND = 4218;
    public static int PORT_DEVICE_INFO = 4213;
    public static int PORT_POSITION = 4214;
    public static String serverAddress = "192.168.43.1";

    public static long RECONNECTION_TIMEOUT = 500;
    public static long MOVEMENT_COMMAND_INTERVAL = 50;
    public static long MOTOR_CYCLE_INTERVAL = 500;
}
