package edu.hkcc.pacmanrobot.controller.pccontroller;

/**
 * Created by Winner on 18/4/2015.
 */

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PcControlJPanel extends JPanel {

    private final PcControllerJFrame master;
    JButton btnResume;
    JButton btnStop;
    JButton btnRecheck;

    /**
     * Create the panel.
     */
    public PcControlJPanel(final PcControllerJFrame master) {
        this.master = master;

        btnRecheck = new JButton("Recheck");
        btnRecheck.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

            }
        });
        add(btnRecheck);

        btnResume = new JButton("Resume");
        btnResume.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateView();
            }
        });
        add(btnResume);

        btnStop = new JButton("Stop");
        btnStop.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

            }
        });
        add(btnStop);

        updateView();
    }

    public void updateView() {
        // TODO Auto-generated method stub
        btnRecheck.setVisible(master.unresumePage());
        btnStop.setVisible(master.stopPage());
        btnResume.setVisible(master.resumePage());
    }
}
