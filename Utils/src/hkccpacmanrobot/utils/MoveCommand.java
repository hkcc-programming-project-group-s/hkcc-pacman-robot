package SocketProgramming;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class MoveCommandListener extends AbstractServer {
    private ServerSocket serverSocket;
    private boolean serverIsStop = false;
    public static final int SERVER_PORT_NUMBER = 4214;

    private double positionX;
    private double positionY;
    private int quadrant;

    public MoveCommandListener() throws IOException {
        try {
            serverSocket = new ServerSocket(SERVER_PORT_NUMBER);
        } catch (IOException e) {
            System.out.println("Cannot build Request Listener.");
        }
    }

    @Override
    public void run() {
        Socket socket;
        try {
            System.out.println("Move Command Listener starts running...");
            while (!serverIsStop) {
                synchronized (serverSocket) {
                    socket = serverSocket.accept();
                    serverSocket.close();
                    System.out.println("Move Command Listener gets connection: Address" + socket.getInetAddress());
                    socket.setSoTimeout(15000);

                    ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
                    FrameData frameData = (FrameData) objectInputStream.readObject();
                    System.out.println("Get data: " + frameData.toString());
                    serverIsStop = true;
                }
            }
        } catch (IOException e) {
            System.out.println("IO Exception has occurred on Move Command Listener.");
        } catch (ClassNotFoundException e) {
            System.out.println("Class Not Found Exception has occurred on Move Command Listener.");
        }
    }

    public void setPosition(double x, double y) {
        positionX = x;
        positionY = y;
    }
}
