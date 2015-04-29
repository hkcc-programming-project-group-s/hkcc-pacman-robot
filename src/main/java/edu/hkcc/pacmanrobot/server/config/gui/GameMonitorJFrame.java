package edu.hkcc.pacmanrobot.server.config.gui;

import edu.hkcc.pacmanrobot.debug.Debug;

import javax.swing.*;
import java.awt.*;

/**
 * this class is lazy singleton
 */
public class GameMonitorJFrame extends JFrame {
    public static GameMonitorJFrame instance = null;

    public ContentJPanel contentJPanel;
    public ControlJPanel controlJPanel;
    int WINDOW_WIDTH;
    int WINDOW_HEIGHT;
    LogoJPanel logoJPanel;

    private GameMonitorJFrame(int width, int height) {
        WINDOW_WIDTH = width;
        WINDOW_HEIGHT = height;
        initialize();
    }

    public static GameMonitorJFrame getInstance(int width, int height) {
        if (instance == null)
            synchronized (GameMonitorJFrame.class) {
                if (instance == null) {
                    instance = new GameMonitorJFrame(width, height);
                }
            }
        return instance;
    }

    public static GameMonitorJFrame getInstance() {
        return getInstance(960, 720);
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        contentJPanel = new ContentJPanel(this);
        controlJPanel = new ControlJPanel(this);
        logoJPanel = new LogoJPanel();

        Debug.getInstance().printMessage("Game MonitorJFrame initialize");
        setBounds(100, 100, WINDOW_WIDTH, WINDOW_HEIGHT);
        //setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

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


    public boolean canNext() {
        return contentJPanel.canNext();
    }

    public boolean canFinish() {
        return contentJPanel.canFinish();
    }

    public boolean canResume() {
        return contentJPanel.canResume();
    }

    public boolean canStop() {
        return contentJPanel.canStop();
    }

    public boolean canPrev() {
        return contentJPanel.canPrev();
    }

    public boolean canPause() {
        return contentJPanel.canPause();
    }


    public void reset() {
        contentJPanel.switchToCard(0);
        controlJPanel.updateView();
        //controlJPanel.reset();
        setVisible(true);
    }
}

