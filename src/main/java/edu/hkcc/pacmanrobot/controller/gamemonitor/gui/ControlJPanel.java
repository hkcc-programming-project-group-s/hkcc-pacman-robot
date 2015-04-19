package edu.hkcc.pacmanrobot.controller.gamemonitor.gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControlJPanel extends JPanel {

    private final GameMonitorJFrame master;
    JButton btnPrevious;
    JButton btnNext;
    JButton btnFinish;
    JButton btnResume;
    JButton btnStop;

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


    btnResume = new JButton("Resume");
        btnResume.setEnabled(false);
    btnResume.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            master.finish();
            updateView();
        }
    });
    add(btnResume);

    updateView();


btnStop = new JButton("Stop");
        btnStop.addActionListener(new ActionListener() {
public void actionPerformed(ActionEvent e) {
    //JOptionPane.showConfirmDialog(this, "Do you want to end the game?", "title", JOptionPane.YES_OPTION, JOptionPane.ERROR_MESSAGE);
        updateView();
        }
        });
        add(btnStop);

        updateView();
        }

    public void updateView() {
        // TODO Auto-generated method stub
        btnPrevious.setVisible(master.hasPrev());
        btnNext.setVisible(master.hasNext());
        btnFinish.setVisible(master.canFinish());
        btnResume.setVisible(master.resumePage());
        btnStop.setVisible(master.resumePage());
    }
}
