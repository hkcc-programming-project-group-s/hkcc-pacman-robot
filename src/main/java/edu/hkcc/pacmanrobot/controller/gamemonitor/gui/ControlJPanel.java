package edu.hkcc.pacmanrobot.controller.gamemonitor.gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControlJPanel extends JPanel {

    private final GameMonitorJFrame master;
    JButton btnPrevious;
    JButton btnNext;
    JButton btnFinish;

    /**
     * Create the panel.
     */
    public ControlJPanel(final GameMonitorJFrame master) {
        this.master = master;
        btnPrevious = new JButton("Previous");
        btnPrevious.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                master.prev();
                updateView();
            }
        });
        add(btnPrevious);

        btnNext = new JButton("Next");
        btnNext.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                master.next();
                updateView();
            }
        });
        add(btnNext);

        btnFinish = new JButton("Finish");
        btnFinish.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                master.finish();
                updateView();
            }
        });
        add(btnFinish);

        updateView();
    }

    public void updateView() {
        // TODO Auto-generated method stub
        btnPrevious.setVisible(master.hasPrev());
        btnNext.setVisible(master.hasNext());
        btnFinish.setVisible(master.canFinish());
    }
}
