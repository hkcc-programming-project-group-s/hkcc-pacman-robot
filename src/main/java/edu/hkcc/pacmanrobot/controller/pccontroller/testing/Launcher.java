package edu.hkcc.pacmanrobot.controller.pccontroller.testing;

import edu.hkcc.pacmanrobot.controller.gamemonitor.gui.GameMonitorJFrame;
import edu.hkcc.pacmanrobot.controller.pccontroller.PcControllerJFrame;

/**
 * Created by Winner on 18/4/2015.
 */
public class Launcher {
    public static void main(String[] args) {
        GameMonitorJFrame gameMonitorJFrame = new GameMonitorJFrame();
        gameMonitorJFrame.setVisible(true);
        PcControllerJFrame pcControllerJFrame = new PcControllerJFrame();
        pcControllerJFrame.setVisible(false);
    }
}
