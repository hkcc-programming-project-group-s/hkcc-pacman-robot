package edu.hkcc.pacmanrobot.server.config.gui;

import javax.swing.*;
import java.awt.*;

public class GameMonitorJFrame extends JFrame {


    public ContentJPanel contentJPanel = new ContentJPanel(this);
    public ControlJPanel controlJPanel = new ControlJPanel(this);
    int WINDOW_WIDTH;
    int WINDOW_HEIGHT;
    LogoJPanel logoJPanel = new LogoJPanel();

    public GameMonitorJFrame() {
        this(960, 720);
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
        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);

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
