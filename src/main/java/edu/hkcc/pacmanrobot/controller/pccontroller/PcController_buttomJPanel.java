package edu.hkcc.pacmanrobot.controller.pccontroller;

/**
 * Created by Winner on 18/4/2015.
 */

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PcController_buttomJPanel extends JPanel {

    private final PcControllerJFrame master;
    private final PcController_buttomJPanel current = this;
    JButton btnResume;
    JButton btnStop;
    JButton btnRecheck;
    JButton btnPause;

    /**
     * Create the panel.
     */
    public PcController_buttomJPanel(final PcControllerJFrame master) {
        this.master = master;

        btnPause = new JButton("Pause");
        btnPause.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //TODO send pause to server
                updateView();
            }
        });
        add(btnPause);

        btnRecheck = new JButton("Recheck");
        btnRecheck.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (master.sao.canResume()) master.reaume();
                updateView();
            }
        });
        add(btnRecheck);

        btnResume = new JButton("Resume");
        btnResume.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //TODO send resume to server
                updateView();
            }
        });
        add(btnResume);

        btnStop = new JButton("Stop");
        btnStop.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int result = JOptionPane.showConfirmDialog(current, "Do you want to end the game?", "title", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (result == JOptionPane.YES_OPTION) {
                    //TODO send stop to server
                }
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
        btnPause.setVisible(master.playing());
    }
}
