package edu.hkcc.pacmanrobot.utils.network;


import edu.hkcc.pacmanrobot.utils.Config;

import java.io.IOException;
import java.net.*;

/**
 * Created by beenotung on 4/19/15.
 */
@Deprecated
public class PacmanNetwork_Deprecated {
    public static final String MESSAGE_SERVER = "PACMAN_ROBOT_GAME_SERVER";
    public static final String MESSAGE_CLIENT = "PACMAN_ROBOT_GAME_CLIENT";
    public static final int PORT = Config.PORT_SERVER_DISCOVER;
    public static final String GROUP_NAME = "230.0.0.1";
    public static final int INTERVAL = 1000;

    public static class Server_publisher extends Thread {
        @Override
        public void run() {
            while (!isInterrupted()) {
                try {
                    Config.serverAddress = InetAddress.getLocalHost().getHostAddress();
                    final byte[] MAC_ADDRESS = NetworkInterface.getByInetAddress(InetAddress.getLocalHost()).getHardwareAddress();
                    DatagramSocket socket = new DatagramSocket(PacmanNetwork_Deprecated.PORT);
                    byte[] buffer = PacmanNetwork_Deprecated.MESSAGE_SERVER.getBytes();
                    while (!isInterrupted()) {
                        InetAddress[] inetAddresses = InetAddress.getAllByName(PacmanNetwork_Deprecated.GROUP_NAME);

                        for (InetAddress inetAddress : inetAddresses) {
                            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, inetAddress, PacmanNetwork_Deprecated.PORT);
                            try {
                                socket.send(packet);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        try {
                            Thread.sleep(PacmanNetwork_Deprecated.INTERVAL);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (SocketException e) {
                    //server is on the same machine
                    System.exit(0);
                } catch (UnknownHostException e) {
                    //Not connect to the network
                    System.out.println("Please connect to the network");
                }
            }
        }
    }

    public static class Server_subscriber extends Thread {
        @Override
        public void run() {

        }
    }

}
