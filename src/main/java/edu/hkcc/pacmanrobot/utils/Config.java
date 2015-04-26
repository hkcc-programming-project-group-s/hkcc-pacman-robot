package edu.hkcc.pacmanrobot.utils;

import edu.hkcc.pacmanrobot.debug.Debug;
import edu.hkcc.pacmanrobot.utils.message.udpmessage.UDPMessengerSingleton;

import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * Created by beenotung on 3/26/15.
 * this is a singleton
 */
public class Config {
    public static Config instance = null;

    public Config(boolean isServer) {
        Config.isServer = isServer;
        init();
    }

    public static Config getInstance(boolean isServer) {
        if (instance == null) {
            synchronized (Config.class) {
                if (instance == null)
                    instance = new Config(isServer);
            }
        }
        return instance;
    }

    static {
        Debug.getInstance().printMessage("static class Config init 0%");
    }

    //40152-65535
    public static final int PORT_MAP = 40211;
    public static final int PORT_GAME_STATUS = 40212;
    public static final int PORT_MOVEMENT_COMMAND = 40213;

    static {
        Debug.getInstance().printMessage("static class Config init 10%");
    }

    public static final int PORT_DEVICE_INFO = 40214;

    static {
        Debug.getInstance().printMessage("static class Config init 15%");
    }

    //public static final int PORT_POSITION = 40215;
    public static final int PORT_ROBOT_POSITION = 40216;
    //public static final int PORT_CONNECTION_INFO = 4219;
    public static final int PORT_CONTROLLER_ROBOT_PAIR = 40217;
    public static final int PORT_FLASH_REQUEST = 40218;
    public static final int PORT_SERVER_DISCOVER = 40219;
    public static final int PORT_UDP = 40220;

    static {
        Debug.getInstance().printMessage("static class Config init 30%");
    }

    public String serverAddress = null;
    //public static String serverAddress = "192.168.1.3";
    //public static String serverAddress = "172.26.3.180";
    //public static String serverAddress = "172.25.56.109";
    public static long RECONNECTION_INTERVAL = 500;
    public static long MOVEMENT_COMMAND_INTERVAL = 50;
    public static long MOTOR_CYCLE_INTERVAL = 50;
    public static long SYNC_MAP_CYCLE_INTERVAL = 1000;
    public static long SAVE_INTERVAL = 10000L;

    public static boolean isServer = false;

    static {
        Debug.getInstance().printMessage("static class Config init 50%");
    }

    private void init() {
        if (isServer)
            try {
                serverAddress = InetAddress.getLocalHost().getHostAddress();
            } catch (UnknownHostException e) {
                //Never happen
            }
        else
            try {
                Debug.getInstance().printMessage("listening udp to get server address");
                serverAddress = UDPMessengerSingleton.getInstance().serverAddressDrawer.getContent();
                System.out.println("server ip: " + serverAddress);
            } catch (SocketException e) {
                // the program is already launched
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

    static {
        Debug.getInstance().printMessage("static class Config init 100%");
    }
}
