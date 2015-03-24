package SocketProgramming;

import java.io.IOException;

public class ServerTest {
    public static void main(String args[]) {
        try {
            new ClientA().start();

            RequestListener requestListener = new RequestListener();
            requestListener.start();
            requestListener.sleep(1000);

            MapDataListener mapDataListener = new MapDataListener();
            mapDataListener.start();
            mapDataListener.sleep(1000);

            MoveCommandListener moveCommandListener = new MoveCommandListener();
            moveCommandListener.start();
            moveCommandListener.sleep(1000);

            RegisterListener registerListener = new RegisterListener();
            registerListener.start();
            registerListener.sleep(1000);

        } catch (InterruptedException e) {
            System.out.println("Thread is interrupted in class ServerTest.");
        } catch (IOException e) {
            System.out.println("IO Exception has occurred on class ServerTest");
        }
    }
}
