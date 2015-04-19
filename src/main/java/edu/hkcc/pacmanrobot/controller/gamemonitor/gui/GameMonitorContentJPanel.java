package edu.hkcc.pacmanrobot.controller.gamemonitor.gui;

import edu.hkcc.pacmanrobot.utils.message.DeviceInfo;
import edu.hkcc.pacmanrobot.utils.message.messenger.Messenger;

import javax.swing.*;

/**
 * Created by 13058456a on 4/13/2015.
 */
public abstract class GameMonitorContentJPanel extends JPanel {
    public final GameMonitorJFrame master;
    public final Messenger<DeviceInfo> deviceInfoMessenger;

    public GameMonitorContentJPanel(GameMonitorJFrame gameMonitorJFrame) {
        this.master = gameMonitorJFrame;
        deviceInfoMessenger = master.sao.deviceInfoMessenger();
    }

    public abstract boolean onLeave();

    public abstract void onEnter();
}
