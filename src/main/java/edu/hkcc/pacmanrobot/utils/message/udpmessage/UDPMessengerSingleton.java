package edu.hkcc.pacmanrobot.utils.message.udpmessage;

import edu.hkcc.pacmanrobot.utils.lang.Drawer;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.util.concurrent.atomic.AtomicInteger;

import static edu.hkcc.pacmanrobot.utils.Config.*;


/**
 * Created by beenotung on 4/19/2015.
 * this is a lazy singleton
 */
public class UDPMessengerSingleton extends Thread {
    private static UDPMessengerSingleton instance = null;
    final String MESSAGE_SERVER = "PACMAN_ROBOT_GAME_SERVER";
    final String MESSAGE_CLIENT = "PACMAN_ROBOT_GAME_CLIENT";
    public Drawer<ByteBuffer> deviceInfoPacketDrawer = new Drawer<>();
    public Drawer<ByteBuffer> movementCommandPacketDrawer = new Drawer<>();
    public Drawer<ByteBuffer> gameStatusPacketDrawer = new Drawer<>();
    public Drawer<String> serverAddressDrawer = new Drawer<String>();
    DatagramSocket socket;
    boolean shouldRun = false;
    InputThread inputThread = new InputThread();

    private UDPMessengerSingleton(int port) throws SocketException {
        socket = new DatagramSocket(port);
        start();
    }

    public static UDPMessengerSingleton getInstance() throws SocketException {
        return getInstance(PORT_UDP);
    }

    public static UDPMessengerSingleton getInstance(int port) throws SocketException {
        if (instance == null) {
            synchronized (Encoder.class) {
                if (instance == null)
                    instance = new UDPMessengerSingleton(port);
            }
        }
        return instance;
    }

    public void send(DatagramPacket packet) {
        sendPacket(packet);
    }

    @Override
    public void run() {
        shouldRun = true;
        inputThread.start();
    }

    private void sendPacket(DatagramPacket packet) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    socket.send(packet);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void decodePacket(DatagramPacket packet) {
        //TODO
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (new String(packet.getData(), packet.getOffset(), packet.getLength()).equals(MESSAGE_SERVER))
                    serverAddressDrawer.update(packet.getAddress().getHostAddress());
                else {
                    byte[] data = new byte[packet.getLength()];
                    System.arraycopy(packet.getData(), packet.getOffset(), data, 0, packet.getLength());
                    int index = 0;
                    AtomicInteger messageType = new AtomicInteger();
                    ByteBuffer buffer = ByteBuffer.wrap(data, index, data.length - index);
                    index = Decoder.getInstance().loadFromArray(data, index, messageType);
                    switch (messageType.get()) {
                        case PORT_DEVICE_INFO:
                            deviceInfoPacketDrawer.update(buffer);
                            break;
                        case PORT_MOVEMENT_COMMAND:
                            movementCommandPacketDrawer.update(buffer);
                            break;
                        case PORT_GAME_STATUS:
                            gameStatusPacketDrawer.update(buffer);
                            break;
                        default:
                            System.out.println("Unknown UDP packet received");
                    }
                }
            }
        }).start();
    }

    class InputThread extends Thread {
        int buffer_length = 1024;
        byte[] buffer = new byte[buffer_length];

        @Override
        public void run() {
            while (shouldRun) {
                try {
                    DatagramPacket packet = new DatagramPacket(buffer, buffer_length);
                    socket.receive(packet);
                    decodePacket(packet);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
