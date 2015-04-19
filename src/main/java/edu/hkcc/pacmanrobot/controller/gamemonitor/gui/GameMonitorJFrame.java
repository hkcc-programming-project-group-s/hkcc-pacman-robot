package edu.hkcc.pacmanrobot.controller.gamemonitor.gui;

import edu.hkcc.pacmanrobot.controller.gamemonitor.core.GameMonitorSAO;

import javax.swing.*;
import java.awt.*;

public class GameMonitorJFrame extends JFrame {

    public GameMonitorSAO sao = new GameMonitorSAO();
    int WINDOW_WIDTH = 960;
    int WINDOW_HEIGHT = 720;
    LogoJPanel logoJPanel = new LogoJPanel();
    ContentJPanel contentJPanel = new ContentJPanel(this);
    ControlJPanel controlJPanel = new ControlJPanel(this);

    public GameMonitorJFrame() {
        initialize();
    }

    public GameMonitorJFrame(int width, int height) {
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
        contentJPanel.next();
    }

    public void prev() {
        contentJPanel.prev();
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
}
