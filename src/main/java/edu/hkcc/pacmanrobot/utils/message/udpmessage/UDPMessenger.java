package edu.hkcc.pacmanrobot.utils.message.udpmessage;

import edu.hkcc.pacmanrobot.utils.Config;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

/**
 * Created by beenotung on 4/19/2015.
 */
public class UDPMessenger extends Thread {

    final boolean isServer;
    final int messageType;
    Socket socket;

    public UDPMessenger(int messageType) {
        this(connect(Config.PORT_UDP), messageType, false);
    }


    public UDPMessenger(Socket socket, int messageType, boolean isServer) {
        this.socket = socket;
        this.messageType = messageType;
        this.isServer = isServer;
        init();
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

    final int byte_length() {
        return UDPMessageSingleton.getInstance().getLength(messageType);
    }

    private void init() {
        new ObjectInputStream(socket.getInputStream())
    }

}
