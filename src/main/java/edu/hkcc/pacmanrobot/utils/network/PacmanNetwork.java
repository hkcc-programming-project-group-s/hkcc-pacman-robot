package edu.hkcc.pacmanrobot.utils.network;


import edu.hkcc.pacmanrobot.debug.Debug;
import edu.hkcc.pacmanrobot.utils.Config;
import edu.hkcc.pacmanrobot.utils.message.udpmessage.UDPMessengerSingleton;

import java.io.IOException;

/**
 * Created by beenotung on 4/19/15.
 * this class has lazy singleton
 */
public class PacmanNetwork {
    public static final String MESSAGE_SERVER = "PACMAN_ROBOT_GAME_SERVER";
    public static final String MESSAGE_CLIENT = "PACMAN_ROBOT_GAME_CLIENT";
    public static final int INTERVAL = 1000;

    public static Server_publisher publisherInstance = null;
    public static volatile boolean shouldRun = false;

    public static void startServerPulisher() {
        if (publisherInstance == null) {
            synchronized (PacmanNetwork.class) {
                synchronized (Server_publisher.class) {
                    if (publisherInstance == null) {
                        publisherInstance = new Server_publisher();
                        publisherInstance.start();
                    }
                }
            }
        }
    }

    public static class Server_publisher extends Thread {
        @Override
        public void run() {
            shouldRun = true;
            byte[] buffer = MESSAGE_SERVER.getBytes();
            while (shouldRun) {
                try {
                    UDPMessengerSingleton.getInstance().send(buffer, 0, buffer.length, Config.PORT_SERVER_DISCOVER);
                } catch (IOException e) {
                    Debug.getInstance().printMessage("Network error");
                    e.printStackTrace();
                }
                try {
                    Thread.sleep(INTERVAL);
                } catch (InterruptedException e) {
                    //   e.printStackTrace();
                    Debug.getInstance().printMessage("Interrupted while sleeping");
                }
            }
        }
    }
}
