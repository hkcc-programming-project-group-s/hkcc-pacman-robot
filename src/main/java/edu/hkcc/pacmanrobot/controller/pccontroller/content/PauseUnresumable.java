package edu.hkcc.pacmanrobot.controller.pccontroller.content;

import edu.hkcc.pacmanrobot.controller.pccontroller.PcControllerJFrame;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.Calendar;

public class PauseUnresumable extends PcController_contentJPanel {


    public String reason = null;
    private JPanel contentPane;

    /**
     * Create the frame.
     */
    public PauseUnresumable(PcControllerJFrame pcControllerJFrame) {
        super(pcControllerJFrame);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));

        JLabel GameResumeLabel = new JLabel("Game Pause Reason");
        GameResumeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        contentPane.add(GameResumeLabel, BorderLayout.NORTH);

        JTextPane textPane = new JTextPane();
        Calendar now = Calendar.getInstance();
        int h = now.get(Calendar.HOUR_OF_DAY);
        int m = now.get(Calendar.MINUTE);
        int s = now.get(Calendar.SECOND);
        textPane.setText("The time is " + h + ":" + m + ":" + s + "\n" + master.sao.getReason() + " Please check the device. If the problem is solved, click recheck and server will check the problem again.");
        contentPane.add(textPane, BorderLayout.CENTER);
        textPane.setEditable(false);

        JPanel panel = new JPanel();
        contentPane.add(panel, BorderLayout.SOUTH);

        Component horizontalStrut = Box.createHorizontalStrut(20);
        panel.add(horizontalStrut);

    }

}
