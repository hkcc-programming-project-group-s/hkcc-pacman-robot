package edu.hkcc.pacmanrobot.server.config.gui.content;

//import com.sun.istack.internal.NotNull;

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
import java.io.IOException;
import java.util.Vector;

//import edu.hkcc.pacmanrobot.controller.gamemonitor.gui.GameMonitorJFrame;

public class SetRobotTypeContentPanel extends AbstractContentPanel implements DeviceInfoJPanelHandler {
    public static final Color DEFAULT_BACKGROUND_COLOR = new Color(198, 228, 255);
    public DeviceInfoJPanelHandler handler = this;
    /*public Vector<DeviceInfoJPanel> controllerJPanels = new Vector<DeviceInfoJPanel>();
    public Vector<DeviceInfoJPanel> unclassesRobotJPanels = new Vector<DeviceInfoJPanel>();
    public Vector<DeviceInfoJPanel> studentJPanels = new Vector<DeviceInfoJPanel>();
    public Vector<DeviceInfoJPanel> deadlineJPanels = new Vector<DeviceInfoJPanel>();
    public Vector<DeviceInfoJPanel> assignmentJPanels = new Vector<DeviceInfoJPanel>();*/
    DeviceInfoContainer controller_panel = new DeviceInfoContainer("Controllers");
    DeviceInfoContainer unclasses_panel = new DeviceInfoContainer("Unclassed Robots");
    DeviceInfoContainer assignment_robot_panel = new DeviceInfoContainer("Assignment Robots");
    DeviceInfoContainer student_robot_panel = new DeviceInfoContainer("Student Robots");
    DeviceInfoContainer deadline_robot_panel = new DeviceInfoContainer("Deadline Robots");
    JPanel panel_center;
    MyDispatcher myDispatcher = new MyDispatcher();
    DeviceInfoJPanel clicked = null;
    Vector<DeviceInfoJPanel> unclassedJPanels = new Vector<>();
    Vector<DeviceInfoContainer> deviceInfoContainers = new Vector<>();

    /**
     * Create the frame.
     */
    public SetRobotTypeContentPanel() {
        KeyboardFocusManager keyboardFocusManager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        keyboardFocusManager.addKeyEventDispatcher(myDispatcher);

        setBounds(100, 100, 1080, 700);
        setBorder(new EmptyBorder(5, 5, 5, 5));
        setLayout(new BorderLayout(0, 0));

        JPanel panel = new JPanel();
        add(panel, BorderLayout.NORTH);
        panel.setBackground(DEFAULT_BACKGROUND_COLOR);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel lblNewLabel = new JLabel("Please choose one robot and enter [A], [S] or [D] keyword.");
        lblNewLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(lblNewLabel);

        JLabel lblNewLabel_1 = new JLabel("A : Assignment Robot");
        lblNewLabel_1.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(lblNewLabel_1);

        JLabel lblNewLabel_2 = new JLabel("S : Student Robot");
        lblNewLabel_2.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(lblNewLabel_2);

        JLabel lblNewLabel_3 = new JLabel("D : Deadline Robot");
        lblNewLabel_3.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(lblNewLabel_3);

        Component verticalStrut = Box.createVerticalStrut(20);
        panel.add(verticalStrut);


        panel_center = new JPanel();
        panel_center.setBorder(null);
        add(panel_center);
        panel_center.setLayout(new BorderLayout(0, 0));

        panel_center.add(unclasses_panel, BorderLayout.NORTH);
        unclasses_panel.setBackground(DEFAULT_BACKGROUND_COLOR);

        JPanel classed_robot = new JPanel();
        panel_center.add(classed_robot, BorderLayout.CENTER);
        classed_robot.setLayout(new GridLayout(1, 3, 10, 0));

        classed_robot.add(assignment_robot_panel);
        assignment_robot_panel.setBackground(DEFAULT_BACKGROUND_COLOR);

        classed_robot.add(student_robot_panel);
        student_robot_panel.setBackground(DEFAULT_BACKGROUND_COLOR);

        classed_robot.add(deadline_robot_panel);
        deadline_robot_panel.setBackground(DEFAULT_BACKGROUND_COLOR);

        JPanel controller = new JPanel();
        panel_center.add(controller, BorderLayout.SOUTH);
        controller.setLayout(new BoxLayout(controller, BoxLayout.Y_AXIS));

        controller_panel.setBackground(DEFAULT_BACKGROUND_COLOR);
        controller.add(controller_panel);

        JPanel panel_bottom = new JPanel();
        add(panel_bottom, BorderLayout.SOUTH);
        panel_bottom.setLayout(new BoxLayout(panel_bottom, BoxLayout.X_AXIS));

        Component horizontalGlue_1 = Box.createHorizontalGlue();
        panel_bottom.add(horizontalGlue_1);

        JButton deletButton = new JButton("Delete & Disconnect");
        deletButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                remove();
            }
        });
        deletButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel_bottom.add(deletButton);

        Component horizontalGlue = Box.createHorizontalGlue();
        panel_bottom.add(horizontalGlue);
        initView();

        deviceInfoContainers.add(unclasses_panel);
    }

    void initView() {
        controller_panel.deviceInfoJPanels.forEach(p -> controller_panel.add(p));
        controller_panel.updateUI();

        unclasses_panel.deviceInfoJPanels.forEach(p -> unclasses_panel.add(p));
        unclasses_panel.updateUI();

        student_robot_panel.deviceInfoJPanels.forEach(p -> student_robot_panel.add(p));
        student_robot_panel.updateUI();

        deadline_robot_panel.deviceInfoJPanels.forEach(p -> deadline_robot_panel.add(p));
        deadline_robot_panel.updateUI();

        assignment_robot_panel.deviceInfoJPanels.forEach(p -> assignment_robot_panel.add(p));
        assignment_robot_panel.updateUI();
    }

    /* public void onControllerJPPanelsclick(DeviceInfoJPanel clickedPanel) {
         //System.out.print("onControllerJPPanelsclick");
         try {
             if (clickedPanel.deviceInfo.deviceType == DeviceInfo.CONTROLLER) {
                 controllerJPanels.forEach(p -> checkClick(p, clickedPanel));
             } else if (clickedPanel.deviceInfo.deviceType == DeviceInfo.ROBOT_UNCLASSED) {
                 unclassesRobotJPanels.forEach(p -> checkClick(p, clickedPanel));
             } else if (clickedPanel.deviceInfo.deviceType == DeviceInfo.ROBOT_STUDENT) {
                 studentJPanels.forEach(p -> checkClick(p, clickedPanel));
             } else if (clickedPanel.deviceInfo.deviceType == DeviceInfo.ROBOT_ASSIGNMENT) {
                 assignmentJPanels.forEach(p -> checkClick(p, clickedPanel));
             } else if (clickedPanel.deviceInfo.deviceType == DeviceInfo.ROBOT_DEADLINE) {
                 deadlineJPanels.forEach(p -> checkClick(p, clickedPanel));
             }
         } catch (ConcurrentModificationException e) {
             System.out.println(e.toString());
         }
     }
 */
    public void checkClick(DeviceInfoJPanel checkPanel, DeviceInfoJPanel clickedPanel) {
        if (!clickedPanel.equals(checkPanel))
            checkPanel.unclick();
    }

    void remove() {
        if (clicked == null) return;
        //System.out.println("REMOVE HERE!!!");
        clicked.deviceInfoContainer.remove(clicked);
        clicked.deviceInfo.deviceType_$eq(DeviceInfo.DEVICE_TYPE_DELETE());
        GameMonitorSAO.updateDeviceInfo(clicked.deviceInfo);
    }

    //@NotNull
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
        return deviceInfoContainers;
    }

    /**
     * fetch DeviceInfo from server
     */
    public void loadDeviceInfo() {
        Vector<DeviceInfo> deviceInfos = new Vector<DeviceInfo>(GameMonitorSAO.fetchDeviceInfos());
        for (DeviceInfo deviceInfo : deviceInfos) {
            if (DeviceInfo.isRobot(deviceInfo.deviceType()))
                unclasses_panel.add(new DeviceInfoJPanel(deviceInfo, handler));
            else if (deviceInfo.deviceType() == DeviceInfo.DEVICE_TYPE_CONTROLLER())
                controller_panel.add(new DeviceInfoJPanel(deviceInfo, handler));
            else if (deviceInfo.deviceType() == DeviceInfo.DEVICE_TYPE_ASSIGNMENT_ROBOT())
                assignment_robot_panel.add(new DeviceInfoJPanel(deviceInfo, handler));
            else if (deviceInfo.deviceType() == DeviceInfo.DEVICE_TYPE_DEADLINE_ROBOT())
                deadline_robot_panel.add(new DeviceInfoJPanel(deviceInfo, handler));
            else if (deviceInfo.deviceType() == DeviceInfo.DEVICE_TYPE_STUDENT_ROBOT())
                student_robot_panel.add(new DeviceInfoJPanel(deviceInfo, handler));
        }
    }


    @Override
    public boolean onLeave() {
        int num_controller = controller_panel.deviceInfoJPanels.size();
        int num_student = student_robot_panel.deviceInfoJPanels.size();

        if (num_student > num_controller)
            JOptionPane.showConfirmDialog(this, "Too many student. Please remove some of student robot",
                    "Oops", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
        else if (num_student < num_controller)
            JOptionPane.showConfirmDialog(this, "Too many controller. Please remove controller or change robot to student robot",
                    "Oops", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
        if (num_controller != num_student)
            return false;

        controller_panel.deviceInfoJPanels.forEach(p -> GameMonitorSAO.updateDeviceInfo(p.deviceInfo));
        student_robot_panel.deviceInfoJPanels.forEach(p -> GameMonitorSAO.updateDeviceInfo(p.deviceInfo));
        deadline_robot_panel.deviceInfoJPanels.forEach(p -> GameMonitorSAO.updateDeviceInfo(p.deviceInfo));
        assignment_robot_panel.deviceInfoJPanels.forEach(p -> GameMonitorSAO.updateDeviceInfo(p.deviceInfo));

        unclasses_panel.clear();
        assignment_robot_panel.clear();
        deadline_robot_panel.clear();
        student_robot_panel.clear();
        controller_panel.clear();

        return true;
    }

    @Override
    public void onEnter() {
        unclasses_panel.clear();
        student_robot_panel.clear();
        assignment_robot_panel.clear();
        deadline_robot_panel.clear();
        controller_panel.clear();
        loadDeviceInfo();
        /* testing
        controller_panel.add(new DeviceInfoJPanel(new DeviceInfo(DeviceInfo.CONTROLLER, "192.168.1.3", "Controller 1"), this));
        controller_panel.add(new DeviceInfoJPanel(new DeviceInfo(DeviceInfo.CONTROLLER, "192.168.1.1", "Controller 2"), this));
        controller_panel.add(new DeviceInfoJPanel(new DeviceInfo(DeviceInfo.CONTROLLER, "192.168.1.2", "Controller 3"), this));
        unclasses_panel.add(new DeviceInfoJPanel(new DeviceInfo(DeviceInfo.ROBOT_UNCLASSED, "192.168.1.4", "Robot 1"), this));
        unclasses_panel.add(new DeviceInfoJPanel(new DeviceInfo(DeviceInfo.ROBOT_UNCLASSED, "192.168.1.5", "Robot 2"), this));
        unclasses_panel.add(new DeviceInfoJPanel(new DeviceInfo(DeviceInfo.ROBOT_UNCLASSED, "192.168.1.6", "Robot 3"), this));
        unclasses_panel.add(new DeviceInfoJPanel(new DeviceInfo(DeviceInfo.ROBOT_UNCLASSED, "192.168.1.7", "Robot 4"), this));
        unclasses_panel.add(new DeviceInfoJPanel(new DeviceInfo(DeviceInfo.ROBOT_UNCLASSED, "192.168.1.8", "Robot 5"), this));
        unclasses_panel.add(new DeviceInfoJPanel(new DeviceInfo(DeviceInfo.ROBOT_UNCLASSED, "192.168.1.9", "Robot 6"), this));
        unclasses_panel.add(new DeviceInfoJPanel(new DeviceInfo(DeviceInfo.ROBOT_UNCLASSED, "192.168.155.132", "Robot 7"), this));
        unclasses_panel.add(new DeviceInfoJPanel(new DeviceInfo(DeviceInfo.ROBOT_UNCLASSED, "192.168.155.131", "Robot 8"), this));*/
        revalidate();
        updateUI();
    }

    boolean onKeyReleased(KeyEvent e) throws IOException {
        if (clicked == null) return false;
        if (DeviceInfo.isRobot(clicked.deviceInfo._deviceType())) {
            if (e.getKeyCode() == KeyEvent.VK_A) {
                clicked.transfer(assignment_robot_panel);
                clicked.deviceInfo.deviceType_$eq(DeviceInfo.DEVICE_TYPE_ASSIGNMENT_ROBOT());
                System.out.println("changed to assignment.");
            } else if (e.getKeyCode() == KeyEvent.VK_D) {
                clicked.transfer(deadline_robot_panel);
                clicked.deviceInfo.deviceType_$eq(DeviceInfo.DEVICE_TYPE_DEADLINE_ROBOT());
                System.out.println("changed to deadline.");
            } else if (e.getKeyCode() == KeyEvent.VK_S) {
                clicked.transfer(student_robot_panel);
                clicked.deviceInfo.deviceType_$eq(DeviceInfo.DEVICE_TYPE_STUDENT_ROBOT());
                System.out.println("changed to student.");
            } else return false;
            clicked.refreshView();
        }
        //return true; testing (input dialog keydispatcher overridden)
        return false;
    }

    boolean onKeyPressed(KeyEvent e) {
        return false;
    }

    class MyDispatcher implements KeyEventDispatcher {
        @Override
        public boolean dispatchKeyEvent(KeyEvent e) {
            panel_center.grabFocus();
            if (e.getID() == KeyEvent.KEY_PRESSED)
                return onKeyPressed(e);
            else if (e.getID() == KeyEvent.KEY_RELEASED)
                try {
                    return onKeyReleased(e);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            else if (e.getID() == KeyEvent.KEY_TYPED)
                ;
            //return (e.getKeyChar() != ' '); testing (input dialog keydispatcher overridden)
            return false;
        }
    }
}



