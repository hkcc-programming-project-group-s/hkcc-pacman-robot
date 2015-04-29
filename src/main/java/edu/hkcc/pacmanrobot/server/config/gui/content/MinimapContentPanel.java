package edu.hkcc.pacmanrobot.server.config.gui.content;

import edu.hkcc.pacmanrobot.controller.gamecontroller.MiniMap;
import edu.hkcc.pacmanrobot.controller.utils.Utils;
import edu.hkcc.pacmanrobot.debug.Debug;
import edu.hkcc.pacmanrobot.utils.Config;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/**
 * Created by beenotung on 4/24/15.
 */
public class MinimapContentPanel extends AbstractContentPanel {
    MiniMap miniMap = new MiniMap(600, 600);

    public MinimapContentPanel() {
        ImageIcon icon = null;
        try {
            icon = Utils.getImageIcon(Config.URL_LOGO_ENJOY);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (icon == null) {
            JLabel label = new JLabel("> Enjoy The Game <");
            Font font = label.getFont();
            label.setFont(new Font(font.getName(), font.BOLD, 50));
            add(label);
        } else {
            add(new JLabel(icon));
        }
    }

    @Override
    public boolean onLeave() {
        return false;
    }

    @Override
    public void onEnter() {
        Debug.getInstance().printMessage("Pop out Mini Map (OpenGL) Window");
        try {
            //MiniMap.instance().start();
            miniMap.start();
        } catch (NullPointerException e) {
            //should never happen
            Debug.getInstance().printMessage("MiniMap (OpenGL) class is not ready");
        }
    }
}
