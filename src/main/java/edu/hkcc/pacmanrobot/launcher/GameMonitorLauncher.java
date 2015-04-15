package edu.hkcc.pacmanrobot.launcher;

import edu.hkcc.pacmanrobot.controller.gamemonitor.gui.GameMonitorJFrame;

import java.awt.*;

/**
 * Created by 13058456a on 4/15/2015.
 */
public class GameMonitorLauncher {


    /**
     * Create the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            /**
             * Launch the application.
             */
            public void run() {
                try {
                    GameMonitorJFrame frame = new GameMonitorJFrame();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
