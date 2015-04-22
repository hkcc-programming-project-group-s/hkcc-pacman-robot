package edu.hkcc.pacmanrobot.controller.pccontroller;

/**
 * Created by Winner on 18/4/2015.
 */

import edu.hkcc.pacmanrobot.controller.gamemonitor.gui.LogoJPanel;
import edu.hkcc.pacmanrobot.controller.pccontroller.core.PcControllerSAO;

import javax.swing.*;
import java.awt.*;

public class PcControllerJFrame extends JFrame {

    public PcControllerSAO sao = new PcControllerSAO();
    public PcControllerJFrame jframe = this;
    int WINDOW_WIDTH = 960;
    int WINDOW_HEIGHT = 720;
    LogoJPanel logoJPanel = new LogoJPanel();
    PcController_centerJPanel contentJPanel = new PcController_centerJPanel(this);
    PcController_buttomJPanel controlJPanel = new PcController_buttomJPanel(this);

    public PcControllerJFrame() {
        initialize();
    }

    public PcControllerJFrame(int width, int height) {
        WINDOW_WIDTH = width;
        WINDOW_HEIGHT = height;
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        setBounds(100, 100, WINDOW_WIDTH, WINDOW_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        getContentPane().add(logoJPanel, BorderLayout.NORTH);
        getContentPane().add(contentJPanel, BorderLayout.CENTER);
        getContentPane().add(controlJPanel, BorderLayout.SOUTH);
    }

    public boolean resumePage() {
        return contentJPanel.resumePage();
    }

    public boolean unresumePage() {
        return contentJPanel.unresumePage();
    }

    public boolean stopPage() {
        return contentJPanel.stopPage();
    }

    public boolean playing() {
        return !contentJPanel.canPrev();
    }

    public void unresume() {
        contentJPanel.pause();
    }

    public void reaume() {
        contentJPanel.resume();
    }

    public void palying() {
        contentJPanel.playing();
    }

}
