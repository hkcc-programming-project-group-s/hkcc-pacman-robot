package edu.hkcc.pacmanrobot.controller.pccontroller.content;

import edu.hkcc.pacmanrobot.controller.pccontroller.PcControllerJFrame;
import edu.hkcc.pacmanrobot.controller.pccontroller.PcControllerJPanel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PauseResumable extends PcControllerJPanel {

    private JPanel contentPane;

    /**
     * Create the frame.
     */
    public PauseResumable(PcControllerJFrame pcControllerJFrame) {
        super(pcControllerJFrame);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));

        JLabel GameResumeLabel = new JLabel("Game Pause");
        GameResumeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        contentPane.add(GameResumeLabel, BorderLayout.NORTH);

        JTextPane textPane = new JTextPane();
        textPane.setText("The problem is solved. You can resume the game now.");
        contentPane.add(textPane, BorderLayout.CENTER);
        textPane.setEditable(false);

        JPanel panel = new JPanel();
        contentPane.add(panel, BorderLayout.SOUTH);

        JButton btnPause = new JButton("Resume");
        btnPause.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            }
        });
        panel.add(btnPause);

        Component horizontalStrut = Box.createHorizontalStrut(20);
        panel.add(horizontalStrut);

        JButton btnStop = new JButton("Stop");
        btnStop.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            }
        });
        panel.add(btnStop);
    }

}
