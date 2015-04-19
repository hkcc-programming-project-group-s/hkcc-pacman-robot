package edu.hkcc.pacmanrobot.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by beenotung on 3/26/15.
 */
public class Config {
    //40152-65535
    public static final int PORT_MAP = 40211;
    public static final int PORT_GAME_STATUS = 40212;
    public static final int PORT_MOVEMENT_COMMAND = 40213;
    public static final int PORT_DEVICE_INFO = 40214;
    public static final int PORT_POSITION = 40215;
    public static final int PORT_ROBOT_POSITION = 40016;
    //public static final int PORT_CONNECTION_INFO = 4219;
    public static final int PORT_CONTROLLER_ROBOT_PAIR = 40217;
    public static final int PORT_FLASH_REQUEST = 40218;
    public static final int PORT_SERVER_DISCOVER = 40219;


    public static String serverAddress = "192.168.43.1";
    //public static String serverAddress = "192.168.1.3";
    //public static String serverAddress = "172.26.3.180";
    //public static String serverAddress = "172.25.56.109";
    public static long RECONNECTION_TIMEOUT = 500;
    public static long MOVEMENT_COMMAND_INTERVAL = 50;
    public static long MOTOR_CYCLE_INTERVAL = 500;
    public static long SYNC_MAP_CYCLE_INTERVAL = 1000;
    public static long SAVE_INTERVAL = 2000L;

    static {
        try {
            serverAddress = InetAddress.getByName("BeenoTung_Archlinux_Home").getHostAddress();
            //serverAddress = InetAddress.getByName("BeenoTung_ArchLinux_Notebook").getHostAddress();
            System.out.println("server ip: " + Config.serverAddress);
        } catch (UnknownHostException e) {
            //e.printStackTrace();
            System.out.println("cannot get server ip");
        }
    }
}
