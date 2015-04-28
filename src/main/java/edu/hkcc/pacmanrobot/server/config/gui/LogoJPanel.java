package edu.hkcc.pacmanrobot.server.config.gui;


import edu.hkcc.pacmanrobot.controller.utils.Utils;
import edu.hkcc.pacmanrobot.utils.Config;

import javax.swing.*;
import java.io.IOException;

public class LogoJPanel extends JPanel {

    /**
     * Create the panel.
     */


    public LogoJPanel() {
        JLabel lblLogoimage = null;
        try {
            lblLogoimage = new JLabel(Utils.getImageIcon(Config.URL_LOGO));
        } catch (IOException e) {
            e.printStackTrace();
        }
        add(lblLogoimage);
    }

}
