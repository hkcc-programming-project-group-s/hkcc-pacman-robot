package edu.hkcc.pacmanrobot.controller.pccontroller.content;

import edu.hkcc.pacmanrobot.controller.pccontroller.PcControllerJFrame;
import edu.hkcc.pacmanrobot.controller.pccontroller.core.PcControllerSAO;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;

public class PauseResumable extends PcController_contentJPanel {
    private final PauseResumable current = this;

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

        Calendar now = Calendar.getInstance();
        int h = now.get(Calendar.HOUR_OF_DAY);
        int m = now.get(Calendar.MINUTE);
        int s = now.get(Calendar.SECOND);

        JLabel GameResumeLabel = new JLabel("Game Pause");
        GameResumeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        contentPane.add(GameResumeLabel, BorderLayout.NORTH);

        JTextPane textPane = new JTextPane();
        if (!PcControllerSAO.isControllerPause()) {
            textPane.setText("The time is " + h + ":" + m + ":" + s + "\n" + "The problem is solved. You can resume the game now.");
        } else if (PcControllerSAO.isControllerPause())
            textPane.setText("The time is " + h + ":" + m + ":" + s + "\n" + PcControllerSAO.getReason() + " You can resume the game if you want.");
        contentPane.add(textPane, BorderLayout.CENTER);
        textPane.setEditable(false);

        JPanel panel = new JPanel();
        contentPane.add(panel, BorderLayout.SOUTH);

        Component horizontalStrut = Box.createHorizontalStrut(20);
        panel.add(horizontalStrut);

        JButton btnStop = new JButton("Stop");
        btnStop.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int result = JOptionPane.showConfirmDialog(current, "Do you want to end the game?", "title", JOptionPane.YES_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (result == JOptionPane.YES_OPTION) {
                    //TODO send stop to server
                }
            }
        });
        panel.add(btnStop);
    }

}
