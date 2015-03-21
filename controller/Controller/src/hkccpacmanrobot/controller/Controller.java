package hkccpacmanrobot.controller;

import hkccpacmanrobot.utils.message.Messenger;
import hkccpacmanrobot.utils.message.MovementCommand;

import java.io.IOException;
import java.net.Socket;

public class Controller {
    public final Messenger<MovementCommand> messenger;

    public Controller(Socket socket) {
        messenger = new Messenger<MovementCommand>(socket);
    }

    public Controller(String hostname, int port) {
        try {
            Socket socket = new Socket(hostname, port);
            messenger = new Messenger<MovementCommand>(socket);
        } catch (IOException e) {
            throw new RuntimeException(e.toString());
        }
    }

    public void sendCommand(MovementCommand command) {
        messenger.sendMessage(command);
    }
}
