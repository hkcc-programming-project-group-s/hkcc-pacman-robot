package edu.hkcc.pacmanrobot.utils.message.udpmessage;

//import edu.hkcc.pacmanrobot.debug.Debug;


import edu.hkcc.pacmanrobot.debug.Debug;
import edu.hkcc.pacmanrobot.utils.lang.ConcurrencyDrawer;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;
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
    private final ReceiveActor receiveActor;
    private final int port;
    private final String name;
    public ConcurrencyDrawer<PacketWrapper> deviceInfoBytesDrawer = new ConcurrencyDrawer<>();
    public ConcurrencyDrawer<PacketWrapper> movementCommandBytesDrawer = new ConcurrencyDrawer<>();
    public ConcurrencyDrawer<PacketWrapper> gameStatusBytesDrawer = new ConcurrencyDrawer<>();
    public ConcurrencyDrawer<String> serverAddressDrawer = new ConcurrencyDrawer<>();
    //DatagramSocket datagramSocket;
    MulticastSocket multicastSocket;
    boolean shouldRun = false;
    InputThread inputThread = new InputThread();

    private UDPMessengerSingleton(int port, String name, ReceiveActor receiveActor) throws IOException {
        //datagramSocket = new DatagramSocket(port);
        multicastSocket = new MulticastSocket(port);
        multicastSocket.joinGroup(InetAddress.getByName(name));
        multicastSocket.setLoopbackMode(true);
        this.port = port;
        this.name = name;
        this.receiveActor = receiveActor;
        start();
    }

    //for lazy get access
    public static UDPMessengerSingleton getInstance() throws NotImplementedException {
        if (instance == null)
            throw new NotImplementedException();
        else
            return instance;
    }

    public static UDPMessengerSingleton getInstance(ReceiveActor receiveActor) throws IOException {
        return getInstance(PORT_UDP, IP_UDP_GROUP_NAME, receiveActor);
    }

    public static UDPMessengerSingleton getInstance(int port, String name, ReceiveActor receiveActor) throws IOException {
        if (instance == null) {
            synchronized (Encoder.class) {
                if (instance == null)
                    instance = new UDPMessengerSingleton(port, name, receiveActor);
            }
        }
        return instance;
    }

    public void send(byte[] buffer, int offset, int length, int messageType) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    InetAddress[] group = InetAddress.getAllByName(name);
                    byte[] outgoingBuffer = new byte[length + Integer.BYTES];
                    int index = 0;
                    index = Encoder.getInstance().saveToArray(outgoingBuffer, index, messageType);
                    System.arraycopy(buffer, offset, outgoingBuffer, index, length);
                    for (int i = 0; i < group.length; i++) {
                        DatagramPacket packet = new DatagramPacket(outgoingBuffer, 0, outgoingBuffer.length, group[i], port);
                        //Debug.getInstance().printMessage("size 1: " + buffer.length);
                        //Debug.getInstance().printMessage("size 2: " + outgoingBuffer.length);
                        //Debug.getInstance().printMessage("size 3: " + packet.getLength());
                        Debug.getInstance().printMessage("send UDP package, socket: " + packet.getSocketAddress() + " size: " + packet.getLength() + " messagetype: " + messageType);
                        //Debug.getInstance().printMessage("^- socket: " +packet.getSocketAddress());
                        //Debug.getInstance().printMessage("^- InetAddress: " + InetAddress.getByName("230.0.0.1"));
                        //Debug.getInstance().printMessage("^- NetworkInterface: " + NetworkInterface.getByInetAddress(InetAddress.getByName("230.0.0.1")));

                        //datagramSocket.send(packet);
                        try {
                            multicastSocket.send(packet);
                        } catch (IOException e) {
                            Debug.getInstance().printMessage("Network access is lost!!!!");
                        }
                    }
                } catch (UnknownHostException e) {
                    //  Debug.getInstance().printMessage("UDP multi cast not supported");
                    System.out.println("UDP multi cast not supported");
                    e.printStackTrace();
                } catch (IOException e) {
                    //   Debug.getInstance().printMessage("Network error");
                    System.out.println("Network error");
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
                    byte[] buffer = new byte[packet.getLength()];
                    System.arraycopy(packet.getData(), packet.getOffset(), buffer, 0, packet.getLength());
                    int index = 0;
                    AtomicInteger messageType = new AtomicInteger();
                    index = Decoder.getInstance().loadFromArray(buffer, index, messageType);

                    /*
                       //ByteBuffer data = ByteBuffer.wrap(buffer, index, buffer.length - index);
                       //index = Decoder.getInstance().loadFromArray(data, index, messageType);
                        this method is deprecated as the index (offset) might change after one read
                        to support multi time read, it's better to copy a new just fit byte []
                     */
                    byte[] data = new byte[buffer.length - index];
                    System.arraycopy(buffer, index, data, 0, data.length);

                    boolean knownType = true;
                    //Debug.getInstance().printMessage("checking message (type): " + messageType.get());
                    PacketWrapper packetWrapper = new PacketWrapper(packet.getAddress(), data);
                    switch (messageType.get()) {
                        case PORT_DEVICE_INFO:
                            Debug.getInstance().printMessage("DeviceInfo UDP packet received");
                            deviceInfoBytesDrawer.update(packetWrapper);
                            break;
                        case PORT_MOVEMENT_COMMAND:
                            Debug.getInstance().printMessage("MovementCommand UDP packet received");
                            movementCommandBytesDrawer.update(packetWrapper);
                            //Debug.getInstance().printMessage("MovementCommand UDP packet put(ed)!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                            break;
                        case PORT_GAME_STATUS:
                            Debug.getInstance().printMessage("GameStatus UDP packet received");
                            gameStatusBytesDrawer.update(packetWrapper);
                            break;
                        default:
                            Debug.getInstance().printMessage("Unknown UDP packet received");
                            knownType = false;
                            break;
                    }
                    if ((receiveActor != null) && knownType) receiveActor.apply(packet.getAddress().getHostAddress());
                }
            }
        }).start();
    }

    public static interface ReceiveActor {
        void apply(String ip);
    }

    public static class PacketWrapper {
        final public InetAddress senderAddress;
        final public byte[] data;

        public PacketWrapper(InetAddress senderAddress, byte[] data) {
            this.senderAddress = senderAddress;
            this.data = data;
        }
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
