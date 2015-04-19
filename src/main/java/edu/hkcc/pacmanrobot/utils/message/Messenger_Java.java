package edu.hkcc.pacmanrobot.utils.message;

import edu.hkcc.pacmanrobot.utils.Config;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

/**
 * Created by 13058536A on 4/19/2015.
 */
public class Messenger_Java<T> extends Thread {

    final boolean isServer;
    Socket socket;
    static final int MOVEMENT_COMMAND=16;

    public Messenger_Java(int port) {
        this(connect(port), false);
    }

    public Messenger_Java(Socket socket, boolean isServer) {
        this.isServer = isServer;
        this.socket = socket;
        init();
    }

    private void init() {
        //new ObjectInputStream(socket.getInputStream())
    }


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

}
