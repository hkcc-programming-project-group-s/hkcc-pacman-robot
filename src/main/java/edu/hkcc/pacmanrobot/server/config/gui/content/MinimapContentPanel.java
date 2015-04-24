package edu.hkcc.pacmanrobot.server.config.gui.content;

import javax.swing.*;

/**
 * Created by beenotung on 4/24/15.
 */
public class MinimapContentPanel extends AbstractContentPanel {

    public MinimapContentPanel() {
        add(new JLabel("Enjoy the game~"));
    }

    @Override
    public boolean onLeave() {
        return false;
    }

    @Override
    public void onEnter() {

    }
}
