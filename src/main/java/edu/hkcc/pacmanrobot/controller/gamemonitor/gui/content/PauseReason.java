package edu.hkcc.pacmanrobot.controller.gamemonitor.gui.content;

/**
 * Created by Winner on 19/4/2015.
 */

import edu.hkcc.pacmanrobot.controller.gamemonitor.gui.GameMonitorContentJPanel;
import edu.hkcc.pacmanrobot.controller.gamemonitor.gui.GameMonitorJFrame;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PauseReason extends GameMonitorContentJPanel {

    /**
     * Create the frame.
     */
    public PauseReason(GameMonitorJFrame gameMonitorJFrame) {
        super(gameMonitorJFrame);
        setBounds(100, 100, 450, 300);
        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));

        JLabel GameResumeLabel = new JLabel();
        GameResumeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        contentPane.add(GameResumeLabel, BorderLayout.NORTH);

        JTextPane textPane = new JTextPane();
        contentPane.add(textPane, BorderLayout.CENTER);
        textPane.setEditable(false);

        JButton btnRepairRobot = new JButton("Pair Controller & Robot");
        contentPane.add(btnRepairRobot, BorderLayout.SOUTH);
        btnRepairRobot.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnRepairRobot.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            }
        });
    }

    @Override
    public boolean onLeave() {
        return false;
    }

    @Override
    public void onEnter() {
    }

    /**
     * Launch the application.
     */
}
