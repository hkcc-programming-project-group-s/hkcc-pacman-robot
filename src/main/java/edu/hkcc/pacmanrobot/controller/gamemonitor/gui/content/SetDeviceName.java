package edu.hkcc.pacmanrobot.controller.gamemonitor.gui.content;

import edu.hkcc.pacmanrobot.controller.gamemonitor.gui.GameMonitorContentJPanel;
import edu.hkcc.pacmanrobot.controller.gamemonitor.gui.GameMonitorJFrame;
import edu.hkcc.pacmanrobot.controller.gamemonitor.gui.utils.DeviceInfoContainer;
import edu.hkcc.pacmanrobot.controller.gamemonitor.gui.utils.DeviceInfoJPanel;
import edu.hkcc.pacmanrobot.controller.gamemonitor.gui.utils.DeviceInfoJPanelHandler;
import edu.hkcc.pacmanrobot.utils.message.DeviceInfo;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.Random;
import java.util.Vector;

public class SetDeviceName extends GameMonitorContentJPanel implements DeviceInfoJPanelHandler {
    public DeviceInfoJPanelHandler handler = this;
    DeviceInfoContainer controller_panel = new DeviceInfoContainer("controller");
    DeviceInfoContainer robot_panel = new DeviceInfoContainer("Robots");
    MyDispatcher myDispatcher = new MyDispatcher();
    DeviceInfoJPanel clicked = null;
    Vector<DeviceInfoContainer> deviceInfoContainers = new Vector<>();


    public SetDeviceName(GameMonitorJFrame gameMonitorJFrame) {
        super(gameMonitorJFrame);
        KeyboardFocusManager keyboardFocusManager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        keyboardFocusManager.addKeyEventDispatcher(myDispatcher);


        setBounds(100, 100, 800, 600);

        setBorder(new EmptyBorder(5, 5, 5, 5));

        setLayout(new BorderLayout(0, 0));

        JPanel center_panel = new JPanel();
        add(center_panel, BorderLayout.CENTER);
        center_panel.setLayout(new BoxLayout(center_panel, BoxLayout.Y_AXIS));

        robot_panel.setBackground(new Color(198, 228, 255));
        center_panel.add(robot_panel);

        Component verticalStrut = Box.createVerticalStrut(20);
        center_panel.add(verticalStrut);

        controller_panel.setBackground(new Color(198, 228, 255));
        center_panel.add(controller_panel);

        JPanel button_panel = new JPanel();
        add(button_panel, BorderLayout.SOUTH);

        JButton btnRename = new JButton("Rename");
        btnRename.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                rename();
            }
        });
        button_panel.add(btnRename);

        Component horizontalStrut = Box.createHorizontalStrut(20);
        button_panel.add(horizontalStrut);

        JButton btnRomove = new JButton("Romove");
        btnRomove.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                remove();
            }
        });
        button_panel.add(btnRomove);

        initView();
    }

    void initView() {
        controller_panel.deviceInfoJPanels.forEach(p -> controller_panel.add(p));
        controller_panel.updateUI();

        robot_panel.deviceInfoJPanels.forEach(p -> robot_panel.add(p));
        robot_panel.updateUI();
    }

    void remove() {
        if (clicked == null) return;
        clicked.deviceInfoContainer.remove(clicked);
        clicked.deviceInfo.deviceType_$eq(DeviceInfo.DEVICE_TYPE_DELETE());
        deviceInfoMessenger.sendMessage(clicked.deviceInfo);
        //TODO call messenger

    }

    void rename() {
        if (clicked == null) return;
        KeyboardFocusManager.getCurrentKeyboardFocusManager().removeKeyEventDispatcher(myDispatcher);
        Object name = JOptionPane.showInputDialog(this, "What is the new name?", "Device Name", JOptionPane.QUESTION_MESSAGE, null, null, null);
        System.out.println(name);
        if (name != null) {
            clicked.update(new DeviceInfo((String) name, clicked.deviceInfo.ip(), DeviceInfo.DEVICE_TYPE_ASSIGNMENT_ROBOT(), clicked.deviceInfo.lastConnectionTime(), true));
        }
    }

    @Override
    public void onDeviceInfoJPanelClicked(DeviceInfoJPanel deviceInfoJPanel) {
        System.out.println("this, here, there, right here");
        //update color
        if (clicked != null && !clicked.equals(deviceInfoJPanel))
            clicked.unclick();
        clicked = deviceInfoJPanel;
        //System.out.println("this, here, there, right here");
    }

    @Override
    public Vector<DeviceInfoContainer> getDeviceInfoContainers() {
        return deviceInfoContainers;
    }

    @Override
    public void receiveDeviceInfo(DeviceInfo deviceInfo) {
        addDeviceInfo(deviceInfo, handler);
//        controller_panel.add(new DeviceInfoJPanel(deviceInfo, handler));
        revalidate();
        updateUI();
    }

    boolean onKeyPressed(KeyEvent e) {
        return true;
    }

    boolean onKeyReleased(KeyEvent e) {
        return true;
    }

    @Override
    public boolean onLeave() {
        try {
            controller_panel.deviceInfoJPanels.forEach(p -> deviceInfoMessenger.sendMessage(p.deviceInfo));
            robot_panel.deviceInfoJPanels.forEach(p -> deviceInfoMessenger.sendMessage(p.deviceInfo));
            //TODO sent robot types to server
            // use messenger to send to server
            if (new Random().nextBoolean())
                throw new IOException();
        } catch (IOException e1) {
            //TODO network / server problem, retry
            JOptionPane.showConfirmDialog(this, "Cannot connect to server. It may be the problem of network or server. Please wait a minute.", "title", JOptionPane.YES_OPTION, JOptionPane.ERROR_MESSAGE);
            return false;
        } catch (Exception e2) {
            //TODO network / server problem, retry
            JOptionPane.showConfirmDialog(this, "Cannot connect to server. It may be the problem of network or server. Please wait a minute.", "title", JOptionPane.YES_OPTION, JOptionPane.ERROR_MESSAGE);
            //e2.printStackTrace();
            System.out.println(e2.toString());
            return false;
        }
        controller_panel.clear();
        robot_panel.clear();
        master.sao.deviceInfoJPanelHandler_$eq(null);
        return true;
    }


    @Override
    public void onEnter() {
        controller_panel.clear();
        robot_panel.clear();
        master.sao.deviceInfoJPanelHandler_$eq(this);
        //start request
        DeviceInfo.request(DeviceInfo.DEVICE_TYPE_UNCLASSED_ROBOT(), master.sao.deviceInfoMessenger());
        DeviceInfo.request(DeviceInfo.DEVICE_TYPE_STUDENT_ROBOT(), master.sao.deviceInfoMessenger());
        DeviceInfo.request(DeviceInfo.DEVICE_TYPE_DEADLINE_ROBOT(), master.sao.deviceInfoMessenger());
        DeviceInfo.request(DeviceInfo.DEVICE_TYPE_ASSIGNMENT_ROBOT(), master.sao.deviceInfoMessenger());
        DeviceInfo.request(DeviceInfo.DEVICE_TYPE_CONTROLLER(), master.sao.deviceInfoMessenger());
    }





    public void addDeviceInfo(DeviceInfo deviceInfo, DeviceInfoJPanelHandler handler) {
        if (DeviceInfo.isRobot(deviceInfo._deviceType()))
            robot_panel.add(new DeviceInfoJPanel(deviceInfo, handler));
        else if (deviceInfo._deviceType() == DeviceInfo.DEVICE_TYPE_CONTROLLER())
            controller_panel.add(new DeviceInfoJPanel(deviceInfo, handler));
    }

    class MyDispatcher implements KeyEventDispatcher {
        @Override
        public boolean dispatchKeyEvent(KeyEvent e) {
            grabFocus();
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
