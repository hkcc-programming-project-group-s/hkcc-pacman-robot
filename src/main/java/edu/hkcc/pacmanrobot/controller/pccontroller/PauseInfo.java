package edu.hkcc.pacmanrobot.controller.pccontroller;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PauseInfo extends JFrame {

    private JPanel contentPane;

    /**
     * Create the frame.
     */
    public PauseInfo() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(0, 0));

        JLabel GameResumeLabel = new JLabel("Game Pause Reason");
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

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    PauseInfo frame = new PauseInfo();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
