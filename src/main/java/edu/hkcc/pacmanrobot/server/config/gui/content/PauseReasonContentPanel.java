package edu.hkcc.pacmanrobot.server.config.gui.content;

/**
 * Created by Winner on 19/4/2015.
 */

import edu.hkcc.pacmanrobot.server.config.core.GameMonitorSAO;
import edu.hkcc.pacmanrobot.server.config.gui.GameMonitorJFrame;
import edu.hkcc.pacmanrobot.utils.lang.StringUtils;
import edu.hkcc.pacmanrobot.utils.message.udpmessage.Encoder;
import edu.hkcc.pacmanrobot.utils.studentrobot.code.GameStatus;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PauseReasonContentPanel extends AbstractContentPanel {

    boolean change_pair = false;

    /**
     * Create the frame.
     */
    public PauseReasonContentPanel(GameMonitorJFrame gameMonitorJFrame) {
        super(gameMonitorJFrame);
        setBounds(100, 100, 450, 300);
        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));

        JLabel GameResumeLabel = new JLabel();
        GameResumeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        contentPane.add(GameResumeLabel, BorderLayout.NORTH);

        JTextPane textPane = new JTextPane();
        textPane.setText(StringUtils.fill(' ', Encoder.DEFAULT_STRING_LENGTH));
        contentPane.add(textPane, BorderLayout.CENTER);
        textPane.setEditable(false);

        JButton btnRepairRobot = new JButton("Pair Controller & Robot");
        contentPane.add(btnRepairRobot, BorderLayout.SOUTH);
        btnRepairRobot.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnRepairRobot.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                change_pair = true;
                master.contentJPanel.next();
                //TODO
            }
        });
    }

    @Override
    public boolean onLeave() {
        return GameMonitorSAO.gameStatus().status() != GameStatus.STATE_PAUSE();
    }

    @Override
    public void onEnter() {
    }

    /**
     * Launch the application.
     */
}
