package SocketProgramming;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class RequestListener extends AbstractServer {
    private ServerSocket serverSocket;
    private boolean serverIsStop = false;
    public static final int SERVER_PORT_NUMBER = 4219;

    public RequestListener() throws IOException {
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
            System.out.println("Request Listener starts running...");
            while (!serverIsStop) {
                synchronized (serverSocket) {
                    socket = serverSocket.accept();
                    serverSocket.close();
                    System.out.println("Request listener gets connection:  Address = " + socket.getInetAddress());
                    socket.setSoTimeout(15000);

                    ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
                    FrameData data;
                    data = (FrameData) in.readObject();
                    System.out.println("Get data: " + data.toString());
                    serverIsStop = true;
                }
            }
        } catch (IOException e) {
            System.out.println("IO Exception has occurred on Request Listener.");
        } catch (ClassNotFoundException e) {
            System.out.println("Class Not Found Exception has occurred on Request Listener.");
        }
    }
}
