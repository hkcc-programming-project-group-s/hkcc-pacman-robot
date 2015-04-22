package edu.hkcc.pacmanrobot.controller.gamemonitor.gui.utils;

import com.sun.istack.internal.NotNull;
import edu.hkcc.pacmanrobot.controller.utils.ControllerImages;
import edu.hkcc.pacmanrobot.controller.utils.Utils;
import edu.hkcc.pacmanrobot.utils.message.DeviceInfo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.MalformedURLException;

public class DeviceInfoJPanel extends JPanel {
    /**
     * Create the panel.
     *
     * @throws IOException
     * @throws MalformedURLException
     */
    public static Color unclicked_color = new Color(227, 242, 255);
    public static Color clicked_color = new Color(143, 202, 255);
    public static boolean setup = false;
    public static ImageIcon ROBOT_UNCLASSED;
    public static ImageIcon ROBOT_STUDENT;
    public static ImageIcon ROBOT_DEADLINE;
    public static ImageIcon ROBOT_ASSIGNMENT;
    public final DeviceInfoJPanelHandler handler;
    // TODO </use DeviceInfo class from project>

    public DeviceInfoContainer deviceInfoContainer;
    public DeviceInfoJPanel current = this;
    public DeviceInfo deviceInfo;
    public boolean isClicked = false;
    public boolean isSelected = false;
    public DeviceInfoJPanelHandler deviceInfoJPanelHandler = null;
    public Component emptyBox = Box.createRigidArea(new Dimension(64, 64));
    JLabel lblIcon;
    JLabel lblIp;
    JLabel lblName;

    public DeviceInfoJPanel(DeviceInfo deviceInfo, DeviceInfoJPanelHandler handler) {
        this.deviceInfo = deviceInfo;
        this.handler = handler;
        init();
    }

    public static void setup() {
        if (setup)
            return;
        try {
            ROBOT_UNCLASSED = Utils.getImageIcon(ControllerImages.ROBOT_UNCLASSED());
            ROBOT_STUDENT = Utils.getImageIcon(ControllerImages.ROBOT_STUDENT());
            ROBOT_DEADLINE = Utils.getImageIcon(ControllerImages.ROBOT_DEADLINE());
            ROBOT_ASSIGNMENT = Utils.getImageIcon(ControllerImages.ROBOT_ASSIGNMENT());
        } catch (IOException e) {
            // Cannot load image
            e.printStackTrace();
        }
        setup = true;
    }

    public void init() {
        setup();
        unPair();
        setLayout(new BorderLayout(0, 0));

        try {
            lblIcon = new JLabel(ControllerImages.getIconByDeviceType(deviceInfo._deviceType()));
            add(lblIcon, BorderLayout.WEST);
        } catch (IOException e) {
            add(emptyBox, BorderLayout.WEST);
        }


        JPanel panel = new JPanel();
        add(panel, BorderLayout.CENTER);
        panel.setLayout(new BorderLayout(0, 0));
        panel.setBackground(new Color(0, 0, 0, 0));

        lblIp = new JLabel(deviceInfo.ip());
        panel.add(lblIp, BorderLayout.NORTH);

        lblName = new JLabel(deviceInfo.name());
        panel.add(lblName, BorderLayout.SOUTH);

        System.out.println("new DeviceInfoJPanel: " + toString());
    }

    public void refreshView() {
        revalidate();
        updateUI();
    }

    public void update(DeviceInfo newDeviceInfo) {
        deviceInfo = newDeviceInfo;
        lblIp.setText(deviceInfo.ip());
        remove(lblIcon);
        remove(emptyBox);
        try {
            lblIcon = new JLabel(ControllerImages.getIconByDeviceType(deviceInfo._deviceType()));
            add(lblIcon, BorderLayout.WEST);
        } catch (IOException e) {
            add(emptyBox, BorderLayout.WEST);
        }
        lblName.setText(deviceInfo.name());
        revalidate();
        updateUI();
    }

    public void click() {
        isClicked = true;
        setBackground(clicked_color);
        System.out.println("clicked DeviceInfoJPanel: " + this);
        handler.onDeviceInfoJPanelClicked(this);
    }

    public void unclick() {
        System.out.println("un-click");
        isClicked = false;
        setBackground(unclicked_color);
    }

    public String toString() {
        return deviceInfo._deviceType() + "," + deviceInfo.name() + "," + deviceInfo.ip();
    }

    public void unPair() {
        isSelected = false;
        unclick();
        setActionListener();
        setFocusable(true);
    }

    private void setActionListener() {
        while (getMouseListeners().length > 0)
            removeMouseListener(getMouseListeners()[0]);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                click();
            }

            @Override
            public void mousePressed(MouseEvent mouseEvent) {
                click();
            }

            @Override
            public void mouseReleased(MouseEvent mouseEvent) {
                click();
            }
        });
    }

    @NotNull
    public void transfer(DeviceInfoContainer newDeviceInfoContainer) {
        if (newDeviceInfoContainer == deviceInfoContainer) return;
        if (deviceInfoContainer != null)
            deviceInfoContainer.remove(this);
        newDeviceInfoContainer.add(this);
    }


}
