package edu.hkcc.pacmanrobot.utils;

import edu.hkcc.pacmanrobot.debug.Debug;
import edu.hkcc.pacmanrobot.utils.message.udpmessage.UDPMessengerSingleton;

import java.net.InetAddress;
import java.net.SocketException;
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
    public static final int PORT_UDP = 40220;


    public static String serverAddress = null;
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
            serverAddress = UDPMessengerSingleton.getInstance().serverAddressDrawer.getContent();
            System.out.println("server ip: " + Config.serverAddress);
        } catch (SocketException e) {
            //This is server
            try {
                serverAddress = InetAddress.getLocalHost().getHostAddress();
            } catch (UnknownHostException e1) {
                //Never happen
            }
        } catch (InterruptedException e) {
            System.out.println("cannot get server ip");
            System.exit(Debug.getInstance().SERVER_NOT_FOUND);
        }
    }
}
