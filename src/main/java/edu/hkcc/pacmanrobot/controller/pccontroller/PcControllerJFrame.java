package edu.hkcc.pacmanrobot.controller.pccontroller;

/**
 * Created by Winner on 18/4/2015.
 */
import edu.hkcc.pacmanrobot.controller.gamemonitor.core.GameMonitorSAO;
import edu.hkcc.pacmanrobot.controller.gamemonitor.gui.LogoJPanel;
import  edu.hkcc.pacmanrobot.controller.pccontroller.PcControlJPanel;
import   edu.hkcc.pacmanrobot.controller.pccontroller.PcContentJPanel;

import javax.swing.*;
import java.awt.*;

public class PcControllerJFrame extends JFrame {

    int WINDOW_WIDTH = 960;
    int WINDOW_HEIGHT = 720;
    LogoJPanel logoJPanel = new LogoJPanel();
    PcContentJPanel contentJPanel = new PcContentJPanel(this);
    PcControlJPanel controlJPanel = new PcControlJPanel(this);

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

    public void next() {
        contentJPanel.pause();
    }

    public void prev() {
        contentJPanel.resume();
    }

    public void finish() {
        // TODO Auto-generated method stub

    }

    public boolean hasNext() {
        return contentJPanel.hasNext();
    }

    public boolean hasPrev() {
        return contentJPanel.hasPrev();
    }

    public boolean canFinish() {
        return !hasNext();
    }
    public GameMonitorSAO sao =new GameMonitorSAO();
}
