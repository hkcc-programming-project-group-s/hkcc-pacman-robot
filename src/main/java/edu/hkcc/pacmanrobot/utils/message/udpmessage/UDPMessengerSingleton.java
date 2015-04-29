package edu.hkcc.pacmanrobot.utils.message.udpmessage;

import edu.hkcc.pacmanrobot.debug.Debug;
import edu.hkcc.pacmanrobot.utils.lang.ConcurrencyDrawer;

import java.io.IOException;
import java.net.*;
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
    private final int port;
    private final String name;
    public ConcurrencyDrawer<ByteBuffer> deviceInfoPacketDrawer = new ConcurrencyDrawer<>();
    public ConcurrencyDrawer<ByteBuffer> movementCommandPacketDrawer = new ConcurrencyDrawer<>();
    public ConcurrencyDrawer<ByteBuffer> gameStatusPacketDrawer = new ConcurrencyDrawer<>();
    public ConcurrencyDrawer<String> serverAddressDrawer = new ConcurrencyDrawer<String>();
    //DatagramSocket datagramSocket;
    MulticastSocket multicastSocket;
    boolean shouldRun = false;
    InputThread inputThread = new InputThread();

    private UDPMessengerSingleton(int port, String name) throws IOException {
        //datagramSocket = new DatagramSocket(port);
        multicastSocket=new MulticastSocket(port);
        multicastSocket.joinGroup(InetAddress.getByName(name));
        multicastSocket.setLoopbackMode(true);
        this.port = port;
        this.name = name;
        start();
    }

    public static UDPMessengerSingleton getInstance() throws IOException {
        return getInstance(PORT_UDP, IP_UDP_GROUP_NAME);
    }

    public static UDPMessengerSingleton getInstance(int port, String name) throws IOException {
        if (instance == null) {
            synchronized (Encoder.class) {
                if (instance == null)
                    instance = new UDPMessengerSingleton(port, name);
            }
        }
        return instance;
    }

    public void send(byte[] buffer, int offset, int length) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    InetAddress[] group = InetAddress.getAllByName(name);
                    for (int i = 0; i < group.length; i++) {
                        DatagramPacket packet = new DatagramPacket(buffer, offset, length, group[i], port);
                        Debug.getInstance().printMessage("send UDP package, socket: "+packet.getSocketAddress()+" size: " + packet.getLength());
                        //Debug.getInstance().printMessage("^- socket: " +packet.getSocketAddress());
                        //Debug.getInstance().printMessage("^- InetAddress: " + InetAddress.getByName("230.0.0.1"));
                        //Debug.getInstance().printMessage("^- NetworkInterface: " + NetworkInterface.getByInetAddress(InetAddress.getByName("230.0.0.1")));

                        //datagramSocket.send(packet);
                        multicastSocket.send(packet);
                    }
                } catch (UnknownHostException e) {
                    Debug.getInstance().printMessage("UDP multi cast not supported");
                    e.printStackTrace();
                } catch (IOException e) {
                    Debug.getInstance().printMessage("Network error");
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public void run() {
        shouldRun = true;
        inputThread.start();
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
                    //datagramSocket.receive(packet);
                    multicastSocket.receive(packet);
                    Debug.getInstance().printMessage("Received UDP package, source: " + packet.getAddress().getHostAddress() + " size: " + packet.getLength());
                    decodePacket(packet);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
