package edu.hkcc.pacmanrobot.server.config.gui.content;

import com.sun.istack.internal.NotNull;
import edu.hkcc.pacmanrobot.server.config.core.GameMonitorSAO;
import edu.hkcc.pacmanrobot.server.config.gui.utils.DeviceInfoContainer;
import edu.hkcc.pacmanrobot.server.config.gui.utils.DeviceInfoJPanel;
import edu.hkcc.pacmanrobot.server.config.gui.utils.DeviceInfoJPanelHandler;
import edu.hkcc.pacmanrobot.utils.message.DeviceInfo;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Vector;

public class SetDeviceNameContentPanel extends AbstractContentPanel implements DeviceInfoJPanelHandler {
    public DeviceInfoJPanelHandler handler = this;
    DeviceInfoContainer controller_panel = new DeviceInfoContainer("controller");
    DeviceInfoContainer robot_panel = new DeviceInfoContainer("Robots");
    MyDispatcher myDispatcher = new MyDispatcher();
    DeviceInfoJPanel clicked = null;
    Vector<DeviceInfoContainer> deviceInfoContainers = new Vector<>();


    public SetDeviceNameContentPanel() {
        super();
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
                if (clicked != null) rename(clicked);
            }
        });
        button_panel.add(btnRename);

        Component horizontalStrut = Box.createHorizontalStrut(20);
        button_panel.add(horizontalStrut);

        JButton btnRemove = new JButton("Remove");
        btnRemove.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (clicked != null) remove(clicked);
            }
        });
        button_panel.add(btnRemove);

        initView();
    }

    void initView() {
        controller_panel.deviceInfoJPanels.forEach(p -> controller_panel.add(p));
        controller_panel.updateUI();

        robot_panel.deviceInfoJPanels.forEach(p -> robot_panel.add(p));
        robot_panel.updateUI();
    }

    void remove(@NotNull DeviceInfoJPanel target) {
        target.deviceInfoContainer.remove(target);
        target.deviceInfo.deviceType_$eq(DeviceInfo.DEVICE_TYPE_DELETE());
        GameMonitorSAO.updateDeviceInfo(target.deviceInfo);
    }

    void rename(@NotNull DeviceInfoJPanel target) {
        KeyboardFocusManager.getCurrentKeyboardFocusManager().removeKeyEventDispatcher(myDispatcher);
        Object name = JOptionPane.showInputDialog(this, "What is the new name?", "Device Name", JOptionPane.QUESTION_MESSAGE, null, null, null);
        try {
            System.out.println(name);
            String newName = (String) name;
            if (newName != null && newName.length() > 0 && newName.length() < DeviceInfo.NAME_MIN_LENGTH()) {
                target.deviceInfo.name_(newName);
                target.refreshView();
            } else
                JOptionPane.showConfirmDialog(this, "The name size is more than " + DeviceInfo.NAME_MIN_LENGTH() + ". Please enter again.", "Name Error", JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {

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

    boolean onKeyPressed(KeyEvent e) {
        return true;
    }

    boolean onKeyReleased(KeyEvent e) {
        return true;
    }

    @Override
    public boolean onLeave() {
        controller_panel.deviceInfoJPanels.forEach(p -> GameMonitorSAO.updateDeviceInfo(p.deviceInfo));
        robot_panel.deviceInfoJPanels.forEach(p -> GameMonitorSAO.updateDeviceInfo(p.deviceInfo));
        controller_panel.clear();
        robot_panel.clear();
        return true;
    }


    @Override
    public void onEnter() {
        controller_panel.clear();
        robot_panel.clear();
        loadDeviceInfo();
    }


    public void loadDeviceInfo() {
        Vector<DeviceInfo> deviceInfos = new Vector<DeviceInfo>(GameMonitorSAO.fetchDeviceInfos());
        for (DeviceInfo deviceInfo : deviceInfos) {
            if (DeviceInfo.isRobot(deviceInfo.deviceType()))
                robot_panel.add(new DeviceInfoJPanel(deviceInfo, handler));
            else if (deviceInfo.deviceType() == DeviceInfo.DEVICE_TYPE_CONTROLLER())
                controller_panel.add(new DeviceInfoJPanel(deviceInfo, handler));
        }
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
