package edu.hkcc.pacmanrobot.controller.gamemonitor.gui;

import edu.hkcc.pacmanrobot.utils.message.DeviceInfo;
import edu.hkcc.pacmanrobot.utils.message.messenger.Messenger;
import scala.Function1;
import scala.runtime.BoxedUnit;

import javax.swing.*;

/**
 * Created by 13058456a on 4/13/2015.
 */
public abstract class GameMonitorContentJPanel extends JPanel {
    public final GameMonitorJFrame master;

    public abstract boolean onLeave();

    public abstract void onEnter();

    public GameMonitorContentJPanel(GameMonitorJFrame gameMonitorJFrame) {
        this.master = gameMonitorJFrame;
        deviceInfoMessenger = master.sao.deviceInfoMessenger;
    }

    public final Messenger<DeviceInfo> deviceInfoMessenger;
}
