package edu.hkcc.pacmanrobot.utils;

import edu.hkcc.pacmanrobot.debug.Debug;
import edu.hkcc.pacmanrobot.server.network.Server_NetworkThread;
import edu.hkcc.pacmanrobot.utils.message.udpmessage.UDPMessengerSingleton;
import edu.hkcc.pacmanrobot.utils.network.NetworkUtils;

import java.io.IOException;
import java.net.BindException;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * Created by beenotung on 3/26/15.
 * this is a singleton
 */
public class Config {
    //40152-65535
    public static final int PORT_MAP = 40211;
    public static final int PORT_GAME_STATUS = 40212;
    public static final int PORT_MOVEMENT_COMMAND = 40213;
    public static final int PORT_DEVICE_INFO = 40214;
    //public static final int PORT_POSITION = 40215;
    public static final int PORT_ROBOT_POSITION = 40216;
    //public static final int PORT_CONNECTION_INFO = 4219;
    public static final int PORT_CONTROLLER_ROBOT_PAIR = 40217;
    public static final int PORT_FLASH_REQUEST = 40218;
    public static final int PORT_SERVER_DISCOVER = 40219;
    public static final int PORT_UDP = 40220;
    public static final String IP_UDP_GROUP_NAME = "230.0.0.1";
    public static final String URL_LOGO = "https://dl.dropboxusercontent.com/u/13757442/htm/pacman-logo-desktop.png";
    public static Config instance = null;
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
        Debug.getInstance().printMessage("static class Config init 0%");
    }

    static {
        Debug.getInstance().printMessage("static class Config init 10%");
    }

    static {
        Debug.getInstance().printMessage("static class Config init 15%");
    }

    static {
        Debug.getInstance().printMessage("static class Config init 30%");
    }

    static {
        Debug.getInstance().printMessage("static class Config init 50%");
    }

    static {
        Debug.getInstance().printMessage("static class Config init 100%");
    }

    public String serverAddress = null;

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

    private void init() {
        if (isServer)
            try {
                serverAddress = NetworkUtils.getOnlineInetAddress().getHostAddress();
            } catch (SocketException e) {
                // logically ever happen
                e.printStackTrace();
            }
        else
            try {
                Debug.getInstance().printMessage("listening udp to get server address");
                serverAddress = UDPMessengerSingleton.getInstance(isServer?new UDPMessengerSingleton.ReceiveActor() {
                    @Override
                    public void apply(String ip) {
                        try {
                            Server_NetworkThread.getInstance().deviceInfoManager().update(ip);
                        } catch (BindException e) {
                            e.printStackTrace();
                        }
                    }
                }:null).serverAddressDrawer.getContent();
                System.out.println("server ip: " + serverAddress);
            } catch (IOException e) {
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
}
