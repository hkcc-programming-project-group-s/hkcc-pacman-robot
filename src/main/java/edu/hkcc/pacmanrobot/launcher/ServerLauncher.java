package edu.hkcc.pacmanrobot.launcher;

import edu.hkcc.pacmanrobot.server.config.gui.GameMonitorJFrame;
import edu.hkcc.pacmanrobot.server.network.Server_NetworkThread;

import java.awt.*;

/**
 * Created by beenotung on 4/8/15.
 */
public class ServerLauncher {
    public static void main(String[] args) {
        //Start the server service thread
        Server_NetworkThread.start();
        //Launch the GUI configurator
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    //TODO this launcher will be deprecated
                    GameMonitorJFrame frame = new GameMonitorJFrame();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}