package edu.hkcc.pacmanrobot.controller.pccontroller.content;

import edu.hkcc.pacmanrobot.controller.pccontroller.PcControllerJFrame;
import edu.hkcc.pacmanrobot.controller.utils.Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.HashMap;
import java.util.Vector;

import static edu.hkcc.pacmanrobot.controller.pccontroller.content.PcControllerSetting.Range.isBetween;
import static java.awt.event.KeyEvent.*;

public class PcControllerSetting extends PcController_contentJPanel {
    public static Vector<Range> direction_keys = new Vector<>();
    static HashMap<Integer, String> keyNames = new HashMap<>();
    PcControllerSetting jFrame = this;
    JPanel ballLocation;
    JPanel ballLastLocation;
    boolean setting = false;
    JPanel[][] ballJPanels;
    JLabel ball;
    int x = 0;
    int y = 0;
    MyDispatcher myDispatcher = new MyDispatcher();
    Direction_Lable_Key[] direction_lable_keys;
    JPanel panel_center;

    private JLabel controllerNameLbl;
    private Direction_Lable_Key key_to_edit = null;

    public PcControllerSetting(PcControllerJFrame pcControllerJFrame) {
        super(pcControllerJFrame);

        KeyboardFocusManager keyboardFocusManager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        keyboardFocusManager.addKeyEventDispatcher(myDispatcher);

        panel_center = new JPanel();
        add(panel_center, BorderLayout.NORTH);
        GridBagLayout gbl_clockwiseTurmLbl = new GridBagLayout();
        gbl_clockwiseTurmLbl.columnWidths = new int[]{0, 0, 0, 0, 0};
        gbl_clockwiseTurmLbl.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0,
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

        JLabel ipAddressLbl = new JLabel(master.sao.deviceInfo().ip());
        GridBagConstraints gbc_ipAddressLbl = new GridBagConstraints();
        gbc_ipAddressLbl.insets = new Insets(0, 0, 5, 5);
        gbc_ipAddressLbl.gridx = 2;
        gbc_ipAddressLbl.gridy = 1;
        panel_center.add(ipAddressLbl, gbc_ipAddressLbl);

        controllerNameLbl = new JLabel(master.sao.deviceInfo().name());
        GridBagConstraints gbc_controllerNameLbl = new GridBagConstraints();
        gbc_controllerNameLbl.insets = new Insets(0, 0, 5, 5);
        gbc_controllerNameLbl.gridx = 2;
        gbc_controllerNameLbl.gridy = 2;
        panel_center.add(controllerNameLbl, gbc_controllerNameLbl);

        JButton editNameBtn = new JButton("Edit");
        editNameBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                KeyboardFocusManager.getCurrentKeyboardFocusManager().removeKeyEventDispatcher(myDispatcher);
                Object name = JOptionPane.showInputDialog(jFrame, "What is the new name?", "Device Name", JOptionPane.QUESTION_MESSAGE, null, null, null);
                System.out.println(name);
                if (name != null) {
                    controllerNameLbl.setText((String) name);
                    //TODO message(send new name)
                }
                KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(myDispatcher);
            }
        });
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

        try {
            ball = new JLabel(
                    Utils.getImageIcon("https://dl.dropboxusercontent.com/u/13757442/htm/ball.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

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

        JLabel upArrowLbl = new JLabel("Up Arrow");
        GridBagConstraints gbc_upArrowLbl = new GridBagConstraints();
        gbc_upArrowLbl.insets = new Insets(0, 0, 5, 5);
        gbc_upArrowLbl.gridx = 2;
        gbc_upArrowLbl.gridy = 5;
        panel_center.add(upArrowLbl, gbc_upArrowLbl);

        JButton editUpBtn = new JButton("Edit");
        editUpBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                key_to_edit = direction_lable_keys[0];
                setting = true;
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

        JLabel downArrowLbl = new JLabel("Down Arrow");
        GridBagConstraints gbc_downArrowLbl = new GridBagConstraints();
        gbc_downArrowLbl.insets = new Insets(0, 0, 5, 5);
        gbc_downArrowLbl.gridx = 2;
        gbc_downArrowLbl.gridy = 6;
        panel_center.add(downArrowLbl, gbc_downArrowLbl);

        JButton editDownBtn = new JButton("Edit");
        editDownBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                key_to_edit = direction_lable_keys[1];
                setting = true;
            }
        });
        GridBagConstraints gbc_editDownBtn = new GridBagConstraints();
        gbc_editDownBtn.insets = new Insets(0, 0, 5, 5);
        gbc_editDownBtn.gridx = 3;
        gbc_editDownBtn.gridy = 6;
        panel_center.add(editDownBtn, gbc_editDownBtn);

        JLabel Anti_clockwiseTurnLbl = new JLabel("Anti-clockwise Turm");
        GridBagConstraints gbc_Anti_clockwiseTurnLbl = new GridBagConstraints();
        gbc_Anti_clockwiseTurnLbl.insets = new Insets(0, 0, 5, 5);
        gbc_Anti_clockwiseTurnLbl.gridx = 1;
        gbc_Anti_clockwiseTurnLbl.gridy = 7;
        panel_center.add(Anti_clockwiseTurnLbl, gbc_Anti_clockwiseTurnLbl);

        JLabel leftArrowLbl = new JLabel("Left Arrow");
        GridBagConstraints gbc_leftArrowLbl = new GridBagConstraints();
        gbc_leftArrowLbl.insets = new Insets(0, 0, 5, 5);
        gbc_leftArrowLbl.gridx = 2;
        gbc_leftArrowLbl.gridy = 7;
        panel_center.add(leftArrowLbl, gbc_leftArrowLbl);

        JButton editLeftBtn = new JButton("Edit");
        editLeftBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                key_to_edit = direction_lable_keys[2];
                setting = true;
            }
        });
        GridBagConstraints gbc_editLeftBtn = new GridBagConstraints();
        gbc_editLeftBtn.insets = new Insets(0, 0, 5, 5);
        gbc_editLeftBtn.gridx = 3;
        gbc_editLeftBtn.gridy = 7;
        panel_center.add(editLeftBtn, gbc_editLeftBtn);

        JLabel clockwiseTurm = new JLabel("Clockwise Turm");
        GridBagConstraints gbc_clockwiseTurm = new GridBagConstraints();
        gbc_clockwiseTurm.insets = new Insets(0, 0, 0, 5);
        gbc_clockwiseTurm.gridx = 1;
        gbc_clockwiseTurm.gridy = 8;
        panel_center.add(clockwiseTurm, gbc_clockwiseTurm);

        JLabel rightArrowLbl = new JLabel("Right Arrow");
        GridBagConstraints gbc_rightArrowLbl = new GridBagConstraints();
        gbc_rightArrowLbl.insets = new Insets(0, 0, 0, 5);
        gbc_rightArrowLbl.gridx = 2;
        gbc_rightArrowLbl.gridy = 8;
        panel_center.add(rightArrowLbl, gbc_rightArrowLbl);

        JButton editRightBtn = new JButton("Edit");
        editRightBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                key_to_edit = direction_lable_keys[3];
                setting = true;
            }
        });
        GridBagConstraints gbc_editRightBtn = new GridBagConstraints();
        gbc_editRightBtn.insets = new Insets(0, 0, 0, 5);
        gbc_editRightBtn.gridx = 3;
        gbc_editRightBtn.gridy = 8;
        panel_center.add(editRightBtn, gbc_editRightBtn);

        JPanel panel_buttom = new JPanel();
        add(panel_buttom, BorderLayout.SOUTH);

        ballLocation = ballJPanels[1][1];
        ballLocation.add(ball);


        setLocation(100, 100);

        direction_keys.add(new Range('a', 'z'));
        direction_keys.add(new Range('0', '9'));
        direction_keys.add(new Range(KeyEvent.VK_KP_UP));
        direction_keys.add(new Range(KeyEvent.VK_KP_DOWN));
        direction_keys.add(new Range(KeyEvent.VK_KP_LEFT));
        direction_keys.add(new Range(KeyEvent.VK_KP_RIGHT));
        direction_keys.add(new Range(KeyEvent.VK_UP));
        direction_keys.add(new Range(KeyEvent.VK_DOWN));
        direction_keys.add(new Range(KeyEvent.VK_LEFT));
        direction_keys.add(new Range(KeyEvent.VK_RIGHT));
        direction_keys.add(new Range(32, 127));

        keyNames.put(VK_UP, "Up Arrow");
        keyNames.put(VK_DOWN, "Down Arrow");
        keyNames.put(VK_LEFT, "Left Arrow");
        keyNames.put(VK_RIGHT, "Right Arrow");
        keyNames.put(VK_KP_UP, "Keypad Up Arrow");
        keyNames.put(VK_KP_DOWN, "Keypad Down Arrow");
        keyNames.put(VK_KP_LEFT, "Keypad Left Arrow");
        keyNames.put(VK_KP_RIGHT, "Keypad Right Arrow");

        keyNames.put(VK_F1, "F1");
        keyNames.put(VK_F2, "F2");
        keyNames.put(VK_F3, "F3");
        keyNames.put(VK_F4, "F4");
        keyNames.put(VK_F5, "F5");
        keyNames.put(VK_F6, "F6");
        keyNames.put(VK_F7, "F7");
        keyNames.put(VK_F8, "F8");
        keyNames.put(VK_F9, "F9");
        keyNames.put(VK_F10, "F10");
        keyNames.put(VK_F11, "F11");
        keyNames.put(VK_F12, "F12");

        keyNames.put(VK_INSERT, "Insert");
        keyNames.put(VK_HOME, "Home");
        keyNames.put(VK_PAGE_UP, "Page up");
        keyNames.put(VK_DELETE, "Delete");
        keyNames.put(VK_END, "End");
        keyNames.put(VK_PAGE_DOWN, "Page down");

        direction_lable_keys = new Direction_Lable_Key[4];
        direction_lable_keys[0] = new Direction_Lable_Key(upArrowLbl, VK_UP);
        direction_lable_keys[1] = new Direction_Lable_Key(downArrowLbl, VK_DOWN);
        direction_lable_keys[2] = new Direction_Lable_Key(leftArrowLbl, VK_LEFT);
        direction_lable_keys[3] = new Direction_Lable_Key(rightArrowLbl, VK_RIGHT);
    }

    /**
     * Launch the application.
     */
    boolean onKeyPressed(KeyEvent e) {
        if (setting) {
            boolean result;
            System.out.println(key_to_edit == null);
            if (key_to_edit != null)
                result = key_to_edit.updateLabel(e.getKeyCode());
            else
                result = false;
            setting = false;
            return result;
        } else {
            if (e.getKeyCode() == direction_lable_keys[0].keycode)
                y = -1;
            else if (e.getKeyCode() == direction_lable_keys[1].keycode)
                y = 1;
            else if (e.getKeyCode() == direction_lable_keys[2].keycode)
                x = -1;
            else if (e.getKeyCode() == direction_lable_keys[3].keycode)
                x = 1;
            else return false;
            master.sao.sendMovementCommand(x, y);
            updateBallLocation();
            return true;
        }
    }

    boolean onKeyReleased(KeyEvent e) {
        if (setting) {
            return true;
        } else {
            if (e.getKeyCode() == direction_lable_keys[0].keycode)
                y = 0;
            else if (e.getKeyCode() == direction_lable_keys[1].keycode)
                y = 0;
            else if (e.getKeyCode() == direction_lable_keys[2].keycode)
                x = 0;
            else if (e.getKeyCode() == direction_lable_keys[3].keycode)
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

    public static class Range {
        int high, low;

        public Range(int low, int high) {
            this.high = high;
            this.low = low;
        }

        public Range(int range) {
            this.high = range;
            this.low = range;
        }

        public Range(char low, char high) {
            this.high = high;
            this.low = low;
        }

        public static boolean isBetween(int target, Vector<Range> ranges) {
            boolean isBetween = false;
            System.out.println("comapring: " + target);
            for (Range range : ranges) {
                {
                    System.out.println(range.low + "----" + range.high);
                    isBetween |= range.isBetween(target);
                }

            }
            return isBetween;
        }

        public static boolean isBetween(int low, int target, int high) {
            return (low <= target && target <= high);
        }

        public boolean isBetween(int target) {
            return isBetween(low, target, high);
        }

    }

    public class Direction_Lable_Key {
        public JLabel label;
        public int keycode;

        public Direction_Lable_Key(JLabel label, int keycode) {
            this.label = label;
            this.keycode = keycode;
            update();
        }

        public Direction_Lable_Key getDuplicated(int newVal) {
            for (Direction_Lable_Key delta : direction_lable_keys) {
                if (!delta.label.equals(label) && delta.keycode == newVal)
                    return delta;
            }
            return null;
        }

        public boolean updateLabel(int newVal) {
            System.out.println("new value: " + newVal);
            if (!isBetween(newVal, direction_keys)) {
                System.out.println("not between");
                return false;
            }
            System.out.println("is between");
            Direction_Lable_Key duplicated = getDuplicated(newVal);
            int oldVal = keycode;
            keycode = newVal;
            update();
            System.out.println("duplicated=" + duplicated);
            if (duplicated != null) {
                duplicated.updateLabel(oldVal);
            }
            return true;
        }

        private void update() {
            if (keyNames.containsKey(keycode))
                label.setText(keyNames.get(keycode));
            else
                label.setText(String.valueOf((char) keycode));
        }

        public void getControllerInfo() {

        }
    }

    class MyDispatcher implements KeyEventDispatcher {
        @Override
        public boolean dispatchKeyEvent(KeyEvent e) {
            panel_center.grabFocus();
            if (e.getID() == KeyEvent.KEY_PRESSED)
                return onKeyPressed(e);
            else if (e.getID() == KeyEvent.KEY_RELEASED)
                return onKeyReleased(e);
            else if (e.getID() == KeyEvent.KEY_TYPED)
                ;
            return (e.getKeyChar() != ' ');
        }
    }
}
