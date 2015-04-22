package edu.hkcc.pacmanrobot.controller.pccontroller.content;

import edu.hkcc.pacmanrobot.controller.pccontroller.PcControllerJFrame;

import javax.swing.*;

/**
 * Created by Winner on 18/4/2015.
 */
public abstract class PcController_contentJPanel extends JPanel {
    public final PcControllerJFrame master;


    public PcController_contentJPanel(PcControllerJFrame master) {
        this.master = master;
    }
}
