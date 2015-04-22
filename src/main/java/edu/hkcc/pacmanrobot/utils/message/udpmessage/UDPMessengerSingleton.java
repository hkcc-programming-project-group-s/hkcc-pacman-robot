package edu.hkcc.pacmanrobot.utils.message.udpmessage;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Semaphore;

import static edu.hkcc.pacmanrobot.utils.Config.PORT_UDP;


/**
 * Created by beenotung on 4/19/2015.
 * this is a lazy singleton
 */
public class UDPMessengerSingleton extends Thread {
    private static UDPMessengerSingleton instance = null;
    DatagramSocket socket;
    Queue<DatagramPacket> outputQueue = new ConcurrentLinkedQueue<DatagramPacket>();
    Queue<DatagramPacket> inputQueue = new ConcurrentLinkedQueue<DatagramPacket>();
    Semaphore deviceInfoSemaphore = new Semaphore(0);
    boolean shouldRun = false;
    OutputThread outputThread = new OutputThread();
    InputThread inputThread = new InputThread();
    private DatagramPacket deviceInfoPacket = null;

    private UDPMessengerSingleton(int port) throws SocketException {
        socket = new DatagramSocket(port);
        start();
    }

    public static UDPMessengerSingleton getInstance() throws SocketException {
        if (instance == null) {
            synchronized (Encoder.class) {
                if (instance == null)
                    instance = new UDPMessengerSingleton(PORT_UDP);
            }
        }
        return instance;
    }

    public void send(DatagramPacket packet) {
        outputQueue.add(packet);
    }

    public DatagramPacket getDeviceInfoPacket() throws InterruptedException {
        deviceInfoSemaphore.acquire();
        DatagramPacket result = deviceInfoPacket;
        deviceInfoPacket = null;
        return result;
    }

    public void setDeviceInfoPacket(DatagramPacket packet) {
        while () wait();
        while (deviceInfoSemaphore.tryAcquire())
            deviceInfoSemaphore.acquire();
        deviceInfoPacket = packet;
        deviceInfoSemaphore.release();
    }

    public DatagramPacket getNextPacket() {
        return inputQueue.poll();
    }

    @Override
    public void run() {
        shouldRun = true;
        inputThread.start();
        outputThread.start();
    }

    class OutputThread extends Thread {
    }

    class InputThread extends Thread {
        int buffer_length = 1024;
        byte[] buffer = new byte[buffer_length];

        @Override
        public void run() {
            while (shouldRun) {
                DatagramPacket packet = new DatagramPacket(buffer, buffer_length);
                socket.receive(packet);
                byte[] data = new byte[packet.getLength()];
                System.arraycopy(packet.getData(), packet.getOffset(), data, 0, packet.getLength());
                inputQueue.add(data);
                packet.getAddress().getHostAddress();
                packet.getAddress().getCanonicalHostName();
                socket.getReceiveBufferSize()
            }
        }
    }
}
