package edu.hkcc.pacmanrobot.controller.gamemonitor.gui;

import edu.hkcc.pacmanrobot.controller.gamemonitor.core.GameMonitorSAO;

import javax.swing.*;
import java.awt.*;

public class GameMonitorJFrame extends JFrame {

    public final GameMonitorSAO sao;
    public ContentJPanel contentJPanel = new ContentJPanel(this);
    public ControlJPanel controlJPanel = new ControlJPanel(this);
    int WINDOW_WIDTH;
    int WINDOW_HEIGHT;
    LogoJPanel logoJPanel = new LogoJPanel();

    public GameMonitorJFrame(GameMonitorSAO sao) {
        this(sao, 960, 720);
    }

    public GameMonitorJFrame(GameMonitorSAO sao, int width, int height) {
        this.sao = sao;
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
        if (contentJPanel.pairControllerRobotPage()) {
            contentJPanel.prev();
        } else {
            contentJPanel.next();
        }
        // TODO Auto-generated method stub
    }

    public boolean resumePage() {
        return !contentJPanel.resumePage();
    }

    public boolean hasNext() {
        return contentJPanel.hasNext();
    }

    public boolean hasPrev() {
        return contentJPanel.hasPrev();
    }

    public boolean canFinish() {
        return contentJPanel.finish();
    }
}
