package edu.hkcc.pacmanrobot.server.config.gui;

import edu.hkcc.pacmanrobot.server.config.core.GameMonitorSAO;
import edu.hkcc.pacmanrobot.utils.studentrobot.code.GameStatus;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControlJPanel extends JPanel {

    private final ControlJPanel current = this;
    private final GameMonitorJFrame master;
    JButton btnPrevious;
    JButton btnNext;
    JButton btnFinish;
    JButton btnResume;
    JButton btnStop;

    /**
     * Create the panel.
     *
     * @param master
     */
    public ControlJPanel(GameMonitorJFrame master) {
        this.master = master;
        btnPrevious = new JButton("Previous");
        btnPrevious.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                GameMonitorJFrame.getInstance().prev();
                updateView();
            }
        });
        add(btnPrevious);

        btnNext = new JButton("Next");
        btnNext.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                GameMonitorJFrame.getInstance().next();
                updateView();
            }
        });
        add(btnNext);

        btnFinish = new JButton("Finish");
        btnFinish.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                GameMonitorJFrame.getInstance().finish();
                updateView();
            }
        });
        add(btnFinish);

        //TODO check logic (should compare, not hardcode disable)
        btnResume = new JButton("Resume");
        btnResume.setEnabled(false);
        btnResume.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                GameMonitorSAO.gameStatus_(GameStatus.STATE_RESUME());
            }
        });
        add(btnResume);

        btnStop = new JButton("Stop");
        btnStop.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int result = JOptionPane.showConfirmDialog(current, "Do you want to end the game?", "title", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (result == JOptionPane.YES_OPTION) {
                    GameMonitorSAO.gameStatus_(GameStatus.STATE_STOP());
                }
            }
        });
        add(btnStop);

        updateView();
    }

    public void updateView() {
        //Debug.getInstance().printMessage("control panel update view");
        btnPrevious.setVisible(master.canPrev());
        btnNext.setVisible(master.canNext());
        btnFinish.setVisible(master.canFinish());
        btnResume.setVisible(master.canResume());
        btnStop.setVisible(master.canStop());
        revalidate();
        updateUI();
    }

    public void reset() {
        btnPrevious.setVisible(false);
        btnNext.setVisible(true);
        btnFinish.setVisible(false);
        btnResume.setVisible(false);
        btnStop.setVisible(false);
        revalidate();
        updateUI();
    }
}
