package edu.hkcc.pacmanrobot.server.config.gui.content;

import edu.hkcc.pacmanrobot.server.config.core.GameMonitorSAO;
import edu.hkcc.pacmanrobot.server.config.gui.utils.DeviceInfoContainer;
import edu.hkcc.pacmanrobot.server.config.gui.utils.DeviceInfoJPanel;
import edu.hkcc.pacmanrobot.server.config.gui.utils.DeviceInfoJPanelHandler;
import edu.hkcc.pacmanrobot.server.config.gui.utils.DevicePairJPanel;
import edu.hkcc.pacmanrobot.utils.message.ControllerRobotPair;
import edu.hkcc.pacmanrobot.utils.message.DeviceInfo;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Vector;

public class PairControllerRobotContentPanel extends AbstractContentPanel implements DeviceInfoJPanelHandler {
    private static final int MAX_WRONG_ATTEMPT = 3;
    public final DeviceInfoContainer controller_container = new DeviceInfoContainer("Controllers");
    public final DeviceInfoContainer robot_container = new DeviceInfoContainer("Robots");
    public final DeviceInfoContainer pair_panel = new DeviceInfoContainer("Controller-Robot pair");
    public Vector<DevicePairJPanel> devicePairJPanels = new Vector<>();
    public DeviceInfoJPanel clickedControllerJPanel = null;
    public DeviceInfoJPanel clickedRobotJPanel = null;
    public Vector<DeviceInfoContainer> deviceInfoContainers = null;
    int makePairAttempt = 0;
    DevicePairJPanel clickedPairJPanel = null;

    /*public void checkUnclickOnPairedDeviceInfoJPanel(DevicePairJPanel checkPanel, DevicePairJPanel clickedPanel) {
        if (!clickedPanel.equals(checkPanel))
            checkPanel.unclick();
    }*/

    /**
     * Create the frame.
     *
     * @throws IOException
     * @throws MalformedURLException
     */
    public PairControllerRobotContentPanel() {
        setBounds(100, 100, 800, 600);
        setBorder(new EmptyBorder(5, 5, 5, 5));
        //getContentPane().add(contentPane);

        // TODO load from server (messenger)

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JPanel controlrobot_panel = new JPanel();
        add(controlrobot_panel);
        controlrobot_panel.setLayout(new GridLayout(0, 2, 10, 0));

        //controller_container.setBackground(new Color(198, 228, 255));
        // controller_panel.setBackground(new Color(22,22,22));
        controlrobot_panel.add(controller_container);

        //robot_container.setBorder(new TitledBorder(null, "Robots", TitledBorder.CENTER, TitledBorder.TOP, null, null));
        //robot_container.setBackground(new Color(198, 228, 255));
        controlrobot_panel.add(robot_container);

        Component rigidArea_1 = Box.createRigidArea(new Dimension(5, 5));
        //contentPane.add(rigidArea_1);

        JPanel panel_upper = new JPanel();
        add(panel_upper);
        panel_upper.setLayout(new BoxLayout(panel_upper, BoxLayout.X_AXIS));

        JButton setbutton = new JButton("Set Pair");
        setbutton.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel_upper.add(setbutton);
        setbutton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                makePair();
            }
        });

        Component rigidArea = Box.createRigidArea(new Dimension(20, 36));
        panel_upper.add(rigidArea);

        JButton removeButton = new JButton("Remove Pair");
        panel_upper.add(removeButton);
        removeButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        removeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (clickedPairJPanel != null)
                    clickedPairJPanel.unPair();
            }
        });

        Component rigidArea_2 = Box.createRigidArea(new Dimension(5, 5));
        //contentPane.add(rigidArea_2);
        add(pair_panel);
        //pair_panel.setBackground(new Color(143, 202, 255));
        //pair_panel.setBorder(new TitledBorder(new LineBorder(new Color(184, 207, 229)), "Controller Robot Pair",
//                TitledBorder.CENTER, TitledBorder.TOP, null, new Color(51, 51, 51)));


        initView();
    }

    public void onPairedDeviceInfoPanelClicked(DevicePairJPanel clickedPanel) {
        //update color
        if (clickedPairJPanel != null && !clickedPairJPanel.equals(clickedPanel))
            clickedPairJPanel.unclick();
        this.clickedPairJPanel = clickedPanel;
    }

    void initView() {
        controller_container.deviceInfoJPanels.forEach(p -> controller_container.add(p));
        controller_container.updateUI();

        robot_container.deviceInfoJPanels.forEach(p -> robot_container.add(p));
        robot_container.updateUI();

        /*pair_panel.deviceInfoContainer.deviceInfoJPanels.forEach(p -> pair_panel.add(p));
        pair_panel.updateUI();*/
    }

    /*private void checkUnclickDeviceInfoJPanel(DeviceInfoJPanel checkPanel, DeviceInfoJPanel clickedPanel) {
        //System.out.println("checkUnclickDeviceInfoJPanel");
        if (!clickedPanel.equals(checkPanel))
            checkPanel.unclick();
    }*/

    @Override
    public void onDeviceInfoJPanelClicked(DeviceInfoJPanel clickedDeviceInfoJPanel) {
        //System.out.println("this, here, there, right here");
        //update color
        if (DeviceInfo.isRobot(clickedDeviceInfoJPanel.deviceInfo._deviceType())) {
            if (clickedControllerJPanel != null && !clickedControllerJPanel.equals(clickedDeviceInfoJPanel))
                clickedControllerJPanel.unclick();
            clickedControllerJPanel = clickedDeviceInfoJPanel;
        } else {
            if (clickedRobotJPanel != null && !clickedRobotJPanel.equals(clickedDeviceInfoJPanel))
                clickedRobotJPanel.unclick();
            clickedRobotJPanel = clickedDeviceInfoJPanel;
        }
    }

    @Override
    public Vector<DeviceInfoContainer> getDeviceInfoContainers() {
        if (deviceInfoContainers == null) {
            deviceInfoContainers = new Vector<DeviceInfoContainer>();
            deviceInfoContainers.add(controller_container);
            deviceInfoContainers.add(robot_container);
        }
        return deviceInfoContainers;
    }


    public void makePair() {
        for (DeviceInfoJPanel controllerPanel : controller_container.deviceInfoJPanels)
            if (controllerPanel.isClicked && !controllerPanel.isSelected) {
                for (DeviceInfoJPanel robotPanel : robot_container.deviceInfoJPanels)
                    if (robotPanel.isClicked && !robotPanel.isSelected) {
                        makePair(controllerPanel, robotPanel);
                        makePairAttempt = 0;
                        break;
                    }
                break;
            }
        if (++makePairAttempt > MAX_WRONG_ATTEMPT) {
            JOptionPane.showMessageDialog(this, "Please select both controller and robot to make a pair");
            makePairAttempt = 0;
        }
    }

    private void makePair(DeviceInfoJPanel controllerInfo, DeviceInfoJPanel robotInfo) {
        DevicePairJPanel newPair = new DevicePairJPanel(this, controllerInfo, robotInfo);
        newPair.pair();
    }

   /* private void removePair(DevicePairJPanel pairedJPanels) {
        Boolean controllerExist = false;
        for (DeviceInfoJPanel controllerDeviceInfoJPanel : controller_panel.deviceInfoJPanels) {
            if (pairedJPanels.controllerJPanel.equals(controllerDeviceInfoJPanel)) {
                controllerExist = true;
                break;
            }
        }
        if (!controllerExist)
            controller_panel.add(pairedJPanels.controllerJPanel);

        Boolean robotExist = false;
        for (DeviceInfoJPanel robotDeviceInfoJPanel : robot_panel.deviceInfoJPanels) {
            if (pairedJPanels.robotJPanel.equals(robotDeviceInfoJPanel)) {
                robotExist = true;
                break;
            }
        }
        if (!robotExist)
            robot_panel.add(pairedJPanels.robotJPanel);


        pairedJPanels.remove(pairedJPanels);
        try {
            pair_panel.remove(pairedJPanels);
            pair_panel.updateUI();
        } catch (Exception e) {
            System.out.println(e.toString());
        }

    }*/

    /*void remove() {
        for (DevicePairJPanel pairDeviceInfoJPanel : pair_panel.deviceInfoContainer)
            if (pairDeviceInfoJPanel.isClicked && !pairDeviceInfoJPanel.isSelected) {
                removePair(pairDeviceInfoJPanel);
                break;
            }
    }*/


    @Override
    public boolean onLeave() {
        if (robot_container.deviceInfoJPanels.size() * controller_container.deviceInfoJPanels.size() > 0) {
            JOptionPane.showConfirmDialog(this, "Too many controller. Please remove controller or change robot to student robot",
                    "Oops", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
            return false;
        } else {
            //save on server
            for (DevicePairJPanel pairControllerRobot : devicePairJPanels) savePair(pairControllerRobot);

            pair_panel.clear();
            controller_container.clear();
            robot_container.clear();
            return true;
        }
    }

    @Override
    public void onEnter() {
        controller_container.clear();
        robot_container.clear();
        pair_panel.clear();
        /*controller_container.add(new DeviceInfoJPanel(new DeviceInfo(DeviceInfo.CONTROLLER, "192.168.1.3", "Controller 1"), this));
        controller_container.add(new DeviceInfoJPanel(new DeviceInfo(DeviceInfo.CONTROLLER, "192.168.1.1", "Controller 2"), this));
        controller_container.add(new DeviceInfoJPanel(new DeviceInfo(DeviceInfo.CONTROLLER, "192.168.1.2", "Controller 3"), this));
        robot_container.add(new DeviceInfoJPanel(new DeviceInfo(DeviceInfo.ROBOT_UNCLASSED, "192.168.1.4", "Robot 1"), this));
        robot_container.add(new DeviceInfoJPanel(new DeviceInfo(DeviceInfo.ROBOT_UNCLASSED, "192.168.1.5", "Robot 2"), this));
        robot_container.add(new DeviceInfoJPanel(new DeviceInfo(DeviceInfo.ROBOT_UNCLASSED, "192.168.1.6", "Robot 3"), this));
        robot_container.add(new DeviceInfoJPanel(new DeviceInfo(DeviceInfo.ROBOT_UNCLASSED, "192.168.1.7", "Robot 4"), this));
        */
        updateView();
    }

    public void savePair(DevicePairJPanel clickedPairJPanel) {
        GameMonitorSAO.savePair(new ControllerRobotPair(clickedPairJPanel.controllerJPanel.deviceInfo.MAC_ADDRESS(), clickedPairJPanel.robotJPanel.deviceInfo.MAC_ADDRESS(), true));
    }

    public void updateView() {
        Vector<ControllerRobotPair> pairs = new Vector<ControllerRobotPair>(GameMonitorSAO.fetchControllerRobotPairs());
        Vector<DeviceInfo> deviceInfos = new Vector<DeviceInfo>(GameMonitorSAO.fetchDeviceInfos());
        DeviceInfo controller = null;
        DeviceInfo robot = null;
        for (ControllerRobotPair pair : pairs) {
            for (DeviceInfo deviceInfo : deviceInfos) {
                if (pair.controller_macAddress().equals(deviceInfo.MAC_ADDRESS()))
                    controller = deviceInfo;
                else if (pair.robot_macAddress().equals(deviceInfo.MAC_ADDRESS()))
                    robot = deviceInfo;
            }

            if (controller != null && robot != null) {
                //valid pair
                makePair(new DeviceInfoJPanel(controller, this), new DeviceInfoJPanel(robot, this));
                deviceInfos.remove(controller);
                deviceInfos.remove(robot);
                controller = null;
                robot = null;
            } else {
                if (controller == null)
                    GameMonitorSAO.savePair(new ControllerRobotPair(controller.MAC_ADDRESS(), controller.MAC_ADDRESS(), true));
                if (robot == null)
                    GameMonitorSAO.savePair(new ControllerRobotPair(robot.MAC_ADDRESS(), robot.MAC_ADDRESS(), true));
            }
        }
        for (DeviceInfo deviceInfo : deviceInfos) {
            if (deviceInfo.deviceType() == DeviceInfo.DEVICE_TYPE_CONTROLLER())
                controller_container.add(new DeviceInfoJPanel(deviceInfo, this));
            else if (deviceInfo.deviceType() == DeviceInfo.DEVICE_TYPE_UNCLASSED_ROBOT())
                robot_container.add(new DeviceInfoJPanel(deviceInfo, this));
        }
        //master.sao.controllerRobotPairMessenger().sendMessage(new ControllerRobotPair(null,null,false));
        revalidate();
        updateUI();
    }


}
