package PCcontroller;

import pacmanrobot.Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.MalformedURLException;

public class Pc_controllerSetting extends JFrame {

    JPanel ballLocation;
    JPanel ballLastLocation;
    boolean setting = false;
    JPanel[][] ballJPanels;
    JLabel ball;
    int x = 0;
    int y = 0;
    /**
     * Create the frame.
     *
     * @throws IOException
     * @throws MalformedURLException
     */

    private int KEY_UP = KeyEvent.VK_UP;
    private int KEY_DOWN = KeyEvent.VK_DOWN;
    private int KEY_LEFT = KeyEvent.VK_LEFT;
    private int KEY_RIGHT = KeyEvent.VK_RIGHT;
    private JPanel contentPane;
    private JTextField textField;
    private JTextField textField_1;
    private JTextField textField_2;
    private JLabel ipAddressLbl;
    private JLabel controllerNameLbl;
    private JLabel downArrowLbl;
    private JLabel rightArrowLbl;
    private JLabel leftArrowLbl;
    private JLabel upArrowLbl;

    public Pc_controllerSetting() throws MalformedURLException, IOException {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(new BorderLayout(0, 0));

        KeyboardFocusManager keyboardFocusManager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        keyboardFocusManager.addKeyEventDispatcher(new MyDispatcher());

        JPanel panel_center = new JPanel();
        getContentPane().add(panel_center, BorderLayout.NORTH);
        GridBagLayout gbl_clockwiseTurmLbl = new GridBagLayout();
        gbl_clockwiseTurmLbl.columnWidths = new int[]{0, 0, 0, 0, 0};
        gbl_clockwiseTurmLbl.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0,
                0, 0};
        gbl_clockwiseTurmLbl.columnWeights = new double[]{0.0, 0.0, 1.0, 0.0};
        gbl_clockwiseTurmLbl.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0,
                0.0, 0.0, 0.0};
        panel_center.setLayout(gbl_clockwiseTurmLbl);

        JLabel ipLbl = new JLabel("IP");
        GridBagConstraints gbc_ipLbl = new GridBagConstraints();
        gbc_ipLbl.insets = new Insets(0, 0, 5, 5);
        gbc_ipLbl.gridx = 1;
        gbc_ipLbl.gridy = 1;
        panel_center.add(ipLbl, gbc_ipLbl);

        JLabel nameLbl = new JLabel("Name");
        GridBagConstraints gbc_nameLbl = new GridBagConstraints();
        gbc_nameLbl.insets = new Insets(0, 0, 5, 5);
        gbc_nameLbl.gridx = 1;
        gbc_nameLbl.gridy = 2;
        panel_center.add(nameLbl, gbc_nameLbl);

        ipAddressLbl = new JLabel("192.168.1.1");
        GridBagConstraints gbc_ipAddressLbl = new GridBagConstraints();
        gbc_ipAddressLbl.insets = new Insets(0, 0, 5, 5);
        gbc_ipAddressLbl.gridx = 2;
        gbc_ipAddressLbl.gridy = 1;
        panel_center.add(ipAddressLbl, gbc_ipAddressLbl);

        controllerNameLbl = new JLabel("Patrick Controller");
        GridBagConstraints gbc_controllerNameLbl = new GridBagConstraints();
        gbc_controllerNameLbl.insets = new Insets(0, 0, 5, 5);
        gbc_controllerNameLbl.gridx = 2;
        gbc_controllerNameLbl.gridy = 2;
        panel_center.add(controllerNameLbl, gbc_controllerNameLbl);

        JButton editNameBtn = new JButton("Edit");
        GridBagConstraints gbc_editNameBtn = new GridBagConstraints();
        gbc_editNameBtn.insets = new Insets(0, 0, 5, 5);
        gbc_editNameBtn.gridx = 3;
        gbc_editNameBtn.gridy = 2;
        panel_center.add(editNameBtn, gbc_editNameBtn);

        JPanel ballpanel = new JPanel();
        ballpanel.setLayout(new GridLayout(3, 3, 0, 0));
        GridBagConstraints gbc_ballpanel = new GridBagConstraints();
        gbc_ballpanel.gridwidth = 3;
        gbc_ballpanel.insets = new Insets(0, 0, 5, 5);
        gbc_ballpanel.fill = GridBagConstraints.BOTH;
        gbc_ballpanel.gridx = 1;
        gbc_ballpanel.gridy = 3;
        panel_center.add(ballpanel, gbc_ballpanel);

        ball = new JLabel(
                Utils.getImageIcon("https://dl.dropboxusercontent.com/u/13757442/htm/ball.png"));

        ballJPanels = new JPanel[3][3];
        for (int y = 0; y < 3; y++)
            for (int x = 0; x < 3; x++) {
                ballJPanels[x][y] = new JPanel();
                ballJPanels[x][y].setBackground(Color.LIGHT_GRAY);
                ballpanel.add(ballJPanels[x][y]);
            }

        JLabel commandLbl = new JLabel("Command");
        GridBagConstraints gbc_commandLbl = new GridBagConstraints();
        gbc_commandLbl.insets = new Insets(0, 0, 5, 5);
        gbc_commandLbl.gridx = 1;
        gbc_commandLbl.gridy = 4;
        panel_center.add(commandLbl, gbc_commandLbl);

        JLabel keyLbl = new JLabel("Key");
        GridBagConstraints gbc_keyLbl = new GridBagConstraints();
        gbc_keyLbl.insets = new Insets(0, 0, 5, 5);
        gbc_keyLbl.gridx = 2;
        gbc_keyLbl.gridy = 4;
        panel_center.add(keyLbl, gbc_keyLbl);

        JLabel forwardLbl = new JLabel("Forward");
        GridBagConstraints gbc_forwardLbl = new GridBagConstraints();
        gbc_forwardLbl.insets = new Insets(0, 0, 5, 5);
        gbc_forwardLbl.gridx = 1;
        gbc_forwardLbl.gridy = 5;
        panel_center.add(forwardLbl, gbc_forwardLbl);

        upArrowLbl = new JLabel("Up Arrow");
        GridBagConstraints gbc_upArrowLbl = new GridBagConstraints();
        gbc_upArrowLbl.insets = new Insets(0, 0, 5, 5);
        gbc_upArrowLbl.gridx = 2;
        gbc_upArrowLbl.gridy = 5;
        panel_center.add(upArrowLbl, gbc_upArrowLbl);

        JButton editUpBtn = new JButton("Edit");
        editUpBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            }
        });
        GridBagConstraints gbc_editUpBtn = new GridBagConstraints();
        gbc_editUpBtn.insets = new Insets(0, 0, 5, 5);
        gbc_editUpBtn.gridx = 3;
        gbc_editUpBtn.gridy = 5;
        panel_center.add(editUpBtn, gbc_editUpBtn);

        JLabel BackwardLbl = new JLabel("Bankward");
        GridBagConstraints gbc_BackwardLbl = new GridBagConstraints();
        gbc_BackwardLbl.insets = new Insets(0, 0, 5, 5);
        gbc_BackwardLbl.gridx = 1;
        gbc_BackwardLbl.gridy = 6;
        panel_center.add(BackwardLbl, gbc_BackwardLbl);

        downArrowLbl = new JLabel("Down Arrow");
        GridBagConstraints gbc_downArrowLbl = new GridBagConstraints();
        gbc_downArrowLbl.insets = new Insets(0, 0, 5, 5);
        gbc_downArrowLbl.gridx = 2;
        gbc_downArrowLbl.gridy = 6;
        panel_center.add(downArrowLbl, gbc_downArrowLbl);

        JButton editDownBtn = new JButton("Edit");
        GridBagConstraints gbc_editDownBtn = new GridBagConstraints();
        gbc_editDownBtn.insets = new Insets(0, 0, 5, 5);
        gbc_editDownBtn.gridx = 3;
        gbc_editDownBtn.gridy = 6;
        panel_center.add(editDownBtn, gbc_editDownBtn);

        JLabel clockwiseTurm = new JLabel("Clockwise Turm");
        GridBagConstraints gbc_clockwiseTurm = new GridBagConstraints();
        gbc_clockwiseTurm.insets = new Insets(0, 0, 5, 5);
        gbc_clockwiseTurm.gridx = 1;
        gbc_clockwiseTurm.gridy = 7;
        panel_center.add(clockwiseTurm, gbc_clockwiseTurm);

        rightArrowLbl = new JLabel("Right Arrow");
        GridBagConstraints gbc_rightArrowLbl = new GridBagConstraints();
        gbc_rightArrowLbl.insets = new Insets(0, 0, 5, 5);
        gbc_rightArrowLbl.gridx = 2;
        gbc_rightArrowLbl.gridy = 7;
        panel_center.add(rightArrowLbl, gbc_rightArrowLbl);

        JButton editRightBtn = new JButton("Edit");
        GridBagConstraints gbc_editRightBtn = new GridBagConstraints();
        gbc_editRightBtn.insets = new Insets(0, 0, 5, 5);
        gbc_editRightBtn.gridx = 3;
        gbc_editRightBtn.gridy = 7;
        panel_center.add(editRightBtn, gbc_editRightBtn);

        JLabel Anti_clockwiseTurnLbl = new JLabel("Anti-clockwise Turm");
        GridBagConstraints gbc_Anti_clockwiseTurnLbl = new GridBagConstraints();
        gbc_Anti_clockwiseTurnLbl.insets = new Insets(0, 0, 5, 5);
        gbc_Anti_clockwiseTurnLbl.gridx = 1;
        gbc_Anti_clockwiseTurnLbl.gridy = 8;
        panel_center.add(Anti_clockwiseTurnLbl, gbc_Anti_clockwiseTurnLbl);

        leftArrowLbl = new JLabel("Left Arrow");
        GridBagConstraints gbc_leftArrowLbl = new GridBagConstraints();
        gbc_leftArrowLbl.insets = new Insets(0, 0, 5, 5);
        gbc_leftArrowLbl.gridx = 2;
        gbc_leftArrowLbl.gridy = 8;
        panel_center.add(leftArrowLbl, gbc_leftArrowLbl);

        JButton editLeftBtn = new JButton("Edit");
        GridBagConstraints gbc_editLeftBtn = new GridBagConstraints();
        gbc_editLeftBtn.insets = new Insets(0, 0, 5, 5);
        gbc_editLeftBtn.gridx = 3;
        gbc_editLeftBtn.gridy = 8;
        panel_center.add(editLeftBtn, gbc_editLeftBtn);

        JPanel panel_buttom = new JPanel();
        getContentPane().add(panel_buttom, BorderLayout.SOUTH);

        JButton btnPause = new JButton("Pause");
        panel_buttom.add(btnPause);

        JButton btnStop = new JButton("Stop");
        panel_buttom.add(btnStop);


        ballLocation = ballJPanels[1][1];
        ballLocation.add(ball);

        pack();
    }

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Pc_controllerSetting frame = new Pc_controllerSetting();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    boolean onKeyPressed(int keycode) {
        if (setting) {
            return true;
        } else {
            if (keycode == KEY_UP)
                y = -1;
            else if (keycode == KEY_DOWN)
                y = 1;
            else if (keycode == KEY_RIGHT)
                x = 1;
            else if (keycode == KEY_LEFT)
                x = -1;
            else return false;
            updateBallLocation();
            return true;
        }
    }

    boolean onKeyReleased(int keycode) {
        if (setting) {
            return true;
        } else {
            if (keycode == KEY_UP)
                y = 0;
            else if (keycode == KEY_DOWN)
                y = 0;
            else if (keycode == KEY_RIGHT)
                x = 0;
            else if (keycode == KEY_LEFT)
                x = 0;
            else return false;
            updateBallLocation();
            return true;
        }
    }

    void updateBallLocation() {
        ballLastLocation = ballLocation;
        ballLocation = ballJPanels[x + 1][y + 1];
        ballLastLocation.remove(ball);
        ballLocation.add(ball);
        ballLocation.validate();
        ballLocation.repaint();
        ballLastLocation.validate();
        ballLastLocation.repaint();
    }


    class MyDispatcher implements KeyEventDispatcher {
        @Override
        public boolean dispatchKeyEvent(KeyEvent e) {
            if (e.getID() == KeyEvent.KEY_PRESSED)
                return onKeyPressed(e.getKeyCode());
            else if (e.getID() == KeyEvent.KEY_RELEASED)
                return onKeyReleased(e.getKeyCode());
            else if (e.getID() == KeyEvent.KEY_TYPED)
                ;
            return false;
        }
    }

}
