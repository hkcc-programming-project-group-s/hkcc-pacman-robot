package edu.hkcc.pacmanrobot.utils.message;

import edu.hkcc.pacmanrobot.utils.Config;

import java.io.IOException;
import java.net.Socket;

/**
 * Created by 13058536A on 4/19/2015.
 */
public class Messenger_Java<T> extends Thread {

    //will not call by server
    public static Socket connect(int port) {
        Socket socket = null;
        do {
            try {
                socket = new Socket(Config.serverAddress, port);
            } catch (IOException e) {
                socket = null;
                System.out.println("server is not ready");
                try {
                    Thread.sleep(Config.RECONNECTION_TIMEOUT);
                } catch (InterruptedException e1) {

                }
            }
        } while (socket == null);
        return socket;
    }

    public Messenger_Java(int port, boolean isServer) {

    }

    public Messenger_Java(Socket socket, boolean isServer) {
        final boolean isServer;
    }
}
