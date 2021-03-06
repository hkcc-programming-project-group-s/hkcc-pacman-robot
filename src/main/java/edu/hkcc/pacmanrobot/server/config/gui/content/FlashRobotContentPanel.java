package edu.hkcc.pacmanrobot.server.config.gui.content;

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
import java.util.Vector;

public class FlashRobotContentPanel extends AbstractContentPanel implements DeviceInfoJPanelHandler {
    public DeviceInfoJPanelHandler handler = this;
    DeviceInfoContainer pending_robot_panel = new DeviceInfoContainer("Pending Robot");
    DeviceInfoContainer moving_robot_panel = new DeviceInfoContainer("Moving Robots");
    DeviceInfoJPanel clicked = null;
    Vector<DeviceInfoContainer> deviceInfoContainers = null;

    /*public void onRobotSettingJPPanelsclick(DeviceInfoJPanel clickedPanel) {
        //System.out.print("onRobotSettingJPPanelsclick");
        try {
            if (clickedPanel.deviceInfo.deviceType == DeviceInfo.ROBOT_UNCLASSED) {
                pendingRobotJPanels.forEach(p -> checkClick(p, clickedPanel));
            }
        } catch (ConcurrentModificationException e) {
            System.out.println(e.toString());
        }

    }*/

    /**
     * Create the frame.
     */
    public FlashRobotContentPanel() {
        setBounds(100, 100, 800, 600);
        setBorder(new EmptyBorder(5, 5, 5, 5));
        setLayout(new GridLayout(0, 1, 0, 10));

        //pendingRobotJPanels.add(new DeviceInfoJPanel(this, DeviceInfo.ROBOT_UNCLASSED, "192.168.1.4", "Robot 1"));
        //pendingRobotJPanels.add(new DeviceInfoJPanel(this, DeviceInfo.ROBOT_UNCLASSED, "192.168.1.5", "Robot 2"));

        JPanel unseted_robot_panel = new JPanel();
        add(unseted_robot_panel);
        unseted_robot_panel.setLayout(new BoxLayout(unseted_robot_panel, BoxLayout.X_AXIS));

        unseted_robot_panel.add(pending_robot_panel);
        pending_robot_panel.setBackground(new Color(198, 228, 255));
        //robot_panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

        JPanel button_panel = new JPanel();
        unseted_robot_panel.add(button_panel);
        button_panel.setLayout(new BoxLayout(button_panel, BoxLayout.Y_AXIS));

        JButton btnLightOn = new JButton("Light on");
        btnLightOn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnLightOn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setting(true);
            }
        });
        button_panel.add(btnLightOn);

        Component rigidArea = Box.createRigidArea(new Dimension(20, 20));
        button_panel.add(rigidArea);

        JButton settingbutton = new JButton("Setting");
        settingbutton.setAlignmentX(Component.CENTER_ALIGNMENT);
        button_panel.add(settingbutton);
        settingbutton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setting(false);
            }
        });

        moving_robot_panel.setBackground(new Color(143, 202, 255));
        add(moving_robot_panel);
        //moving_robot_panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        initView();
    }

    void initView() {
        pending_robot_panel.deviceInfoJPanels.forEach(p -> pending_robot_panel.add(p));

        moving_robot_panel.deviceInfoJPanels.forEach(p -> moving_robot_panel.add(p));
    }

    private void checkClick(DeviceInfoJPanel checkPanel, DeviceInfoJPanel clickedPanel) {
        System.out.println("checkUnclickDeviceInfoJPanel");
        if (!clickedPanel.equals(checkPanel))
            checkPanel.unclick();
    }

    void setting(boolean light_on) {
        DeviceInfoJPanel settingRobot = null;
        for (DeviceInfoJPanel pendingRobotDeviceJPanel : pending_robot_panel.deviceInfoJPanels)
            if (pendingRobotDeviceJPanel.isClicked && !pendingRobotDeviceJPanel.isSelected) {
                settingRobot = pendingRobotDeviceJPanel;
                sendRequest(settingRobot.deviceInfo, light_on);
            }
        pending_robot_panel.remove(settingRobot);

        moving_robot_panel.add(settingRobot);

    }

    @Override
    public void onDeviceInfoJPanelClicked(DeviceInfoJPanel deviceInfoJPanel) {
        System.out.println("this, here, there, right here");
        //update color
        if (clicked != null && !clicked.equals(deviceInfoJPanel)) {
            System.out.println("unclick");
            clicked.unclick();
        }
        clicked = deviceInfoJPanel;
    }

    @Override
    public Vector<DeviceInfoContainer> getDeviceInfoContainers() {
        if (deviceInfoContainers == null) {
            deviceInfoContainers = new Vector<DeviceInfoContainer>();
            deviceInfoContainers.add(pending_robot_panel);
            deviceInfoContainers.add(moving_robot_panel);
        }
        return deviceInfoContainers;
    }

    public void loadDeviceInfo() {
        Vector<DeviceInfo> deviceInfos = new Vector<DeviceInfo>(GameMonitorSAO.fetchDeviceInfos());
        for (DeviceInfo deviceInfo : deviceInfos) {
            if (DeviceInfo.isRobot(deviceInfo.deviceType()))
                pending_robot_panel.add(new DeviceInfoJPanel(deviceInfo, handler));
        }
    }

    @Override
    public boolean onLeave() {
        if (pending_robot_panel.deviceInfoJPanels.size() > 0) {
            JOptionPane.showConfirmDialog(this, "Some robot have not setting. Please set the position of the robot.", "title", JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE);
            return false;
        }

        //clear flashing robot (if any)
        GameMonitorSAO.clearFlash();

        pending_robot_panel.clear();
        moving_robot_panel.clear();
        return true;
    }

    @Override
    public void onEnter() {
        pending_robot_panel.clear();
        moving_robot_panel.clear();
        /*robot_panel.add(new DeviceInfoJPanel(new DeviceInfo(DeviceInfo.ROBOT_UNCLASSED, "192.168.1.4", "Robot 1"), this));
        robot_panel.add(new DeviceInfoJPanel(new DeviceInfo(DeviceInfo.ROBOT_UNCLASSED, "192.168.1.5", "Robot 2"), this));
        robot_panel.add(new DeviceInfoJPanel(new DeviceInfo(DeviceInfo.ROBOT_UNCLASSED, "192.168.1.6", "Robot 3"), this));
        robot_panel.add(new DeviceInfoJPanel(new DeviceInfo(DeviceInfo.ROBOT_UNCLASSED, "192.168.1.7", "Robot 4"), this));
        robot_panel.add(new DeviceInfoJPanel(new DeviceInfo(DeviceInfo.ROBOT_UNCLASSED, "192.168.1.8", "Robot 5"), this));
        robot_panel.add(new DeviceInfoJPanel(new DeviceInfo(DeviceInfo.ROBOT_UNCLASSED, "192.168.1.9", "Robot 6"), this));
        robot_panel.add(new DeviceInfoJPanel(new DeviceInfo(DeviceInfo.ROBOT_UNCLASSED, "192.168.155.132", "Robot 7"), this));
        robot_panel.add(new DeviceInfoJPanel(new DeviceInfo(DeviceInfo.ROBOT_UNCLASSED, "192.168.155.131", "Robot 8"), this));*/
        loadDeviceInfo();
    }

    public void sendRequest(DeviceInfo deviceInfo, boolean lightOn) {
        GameMonitorSAO.requestFlash(deviceInfo.MAC_ADDRESS(), lightOn);
    }
}
