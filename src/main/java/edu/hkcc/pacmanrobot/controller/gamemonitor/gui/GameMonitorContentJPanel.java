package edu.hkcc.pacmanrobot.controller.gamemonitor.gui;

import javax.swing.*;
import java.io.IOException;

/**
 * Created by 13058456a on 4/13/2015.
 */
public abstract class GameMonitorContentJPanel extends JPanel{
    public abstract boolean onLeave();
    public abstract void onEnter() throws IOException;
}
