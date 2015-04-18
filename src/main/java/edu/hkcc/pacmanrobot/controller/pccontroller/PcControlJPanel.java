package edu.hkcc.pacmanrobot.controller.pccontroller;

/**
 * Created by Winner on 18/4/2015.
 */
import edu.hkcc.pacmanrobot.controller.gamemonitor.gui.GameMonitorJFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PcControlJPanel extends JPanel {

    private final PcControllerJFrame master;
    JButton btnPrevious;
    JButton btnNext;
    JButton btnFinish;

    /**
     * Create the panel.
     */
    public PcControlJPanel(final PcControllerJFrame master) {
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
