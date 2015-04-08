package edu.hkcc.pacmanrobot.launcher;

import edu.hkcc.pacmanrobot.server.Server;

/**
 * Created by beenotung on 4/8/15.
 */
public class ServerLauncher {
    public static void main(String[] args) {
        Server server = new Server();
        server.start();
    }
}
