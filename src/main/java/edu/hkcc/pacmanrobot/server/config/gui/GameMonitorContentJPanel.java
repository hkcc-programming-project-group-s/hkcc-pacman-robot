package edu.hkcc.pacmanrobot.server.config.gui;

import javax.swing.*;

/**
 * Created by 13058456a on 4/13/2015.
 */
public abstract class GameMonitorContentJPanel extends JPanel {
    public final GameMonitorJFrame master;

    public GameMonitorContentJPanel(GameMonitorJFrame gameMonitorJFrame) {
        this.master = gameMonitorJFrame;
    }

    /**
     * @return boolean
     * true: success to leave
     * false: failed to leave
     */
    public abstract boolean onLeave();

    /**
     * init routine
     */
    public abstract void onEnter();
}
