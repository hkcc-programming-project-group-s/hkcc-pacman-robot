package edu.hkcc.pacmanrobot.launcher;

import edu.hkcc.pacmanrobot.debug.Debug;
import edu.hkcc.pacmanrobot.server.config.gui.GameMonitorJFrame;
import edu.hkcc.pacmanrobot.server.network.Server_NetworkThread;

import java.awt.*;
import java.net.BindException;

/**
 * Created by beenotung on 4/8/15.
 */
public class ServerLauncher {
    public static void main(String[] args) {
        try {
            //Start the server service thread
            System.out.println("Start the server service thread");
            Server_NetworkThread.getInstance();

            //Launch the GUI configurator
            EventQueue.invokeLater(new Runnable() {
                @Override
                public void run() {
                    System.out.println("Launch the GUI configurator");
                    GameMonitorJFrame.getInstance().setVisible(true);
                }
            });
        } catch (BindException e) {
            System.out.println("Server is already launched on this computer!!!");
            e.printStackTrace();
            System.exit(Debug.getInstance().DUPLICATED_SERVER_LAUNCH);
        }
    }
}