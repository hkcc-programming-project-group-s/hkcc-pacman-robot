package edu.hkcc.pacmanrobot.controller.gamemonitor.gui.utils;

import edu.hkcc.pacmanrobot.controller.gamemonitor.gui.content.PairControllerRobotJPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class DevicePairJPanel extends JPanel {

    /*public static final Color clicked_color = new Color(227, 242, 255);
    public static final Color unclicked_color = new Color(143, 202, 255);*/
    public static final Color clicked_color = DeviceInfoJPanel.clicked_color;
    public static final Color unclicked_color = DeviceInfoJPanel.unclicked_color;
    public final DeviceInfoJPanel controllerJPanel;
    public final DeviceInfoJPanel robotJPanel;
    private final PairControllerRobotJPanel master;
    public boolean isClicked = false;
    public boolean isSelected = false;
    LineBox verticalBox;

    /**
     * Create the panel.
     */
    public DevicePairJPanel(PairControllerRobotJPanel pairControllerRobotJPanel, DeviceInfoJPanel controllerJPanel, DeviceInfoJPanel robotJPanel) {
        master = pairControllerRobotJPanel;
        this.controllerJPanel = controllerJPanel;
        this.robotJPanel = robotJPanel;
        //controllerJPanel.setBackground(new Color(0, 0, 0, 0));
        while (controllerJPanel.getMouseListeners().length > 0)
            controllerJPanel.removeMouseListener(controllerJPanel.getMouseListeners()[0]);
        //robotJPanel.setBackground(new Color(0, 0, 0, 0));
        while (robotJPanel.getMouseListeners().length > 0)
            robotJPanel.removeMouseListener(robotJPanel.getMouseListeners()[0]);
        setLayout(new BorderLayout(0, 0));

        add(controllerJPanel, BorderLayout.WEST);
        add(robotJPanel, BorderLayout.EAST);

//        Box verticalBox = Box.createVerticalBox();
//        add(verticalBox, BorderLayout.CENTER);
//        verticalBox.setBackground(new Color(71, 101, 128));

        verticalBox = new LineBox();
        add(verticalBox, BorderLayout.CENTER);

        Component rigidArea = Box.createRigidArea(new Dimension(40, 40));
        verticalBox.add(rigidArea);
        verticalBox.setBackground(new Color(0, 0, 0, 0));
        verticalBox.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                click();
                super.mouseClicked(mouseEvent);
            }
        });

        unclick();
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                click();
                super.mouseClicked(mouseEvent);
            }
        });
    }

    /*public PairControllerRobotJPanel getMaster() {
        if (controllerJPanel.master_pair.equals(robotJPanel.master_pair))
            throw new IllegalComponentStateException();
        return controllerJPanel.master_pair;
    }*/

    public void click() {
        System.out.println("clicked " + this);
        isClicked = true;
        setBackground(clicked_color);
        //robotJPanel.setBackground(clicked_color);
        //controllerJPanel.setBackground(clicked_color);
        //verticalBox.setBackground(clicked_color);
        master.onPairedDeviceInfoPanelClicked(this);
        //ControllerRobotPairFrame.onUnpairedDeviceInfoPanelClicked(this);
    }

    public void unclick() {
        System.out.println("un-clicked " + this);
        isClicked = false;
        setBackground(unclicked_color);
        //robotJPanel.setBackground(unclicked_color);
        //controllerJPanel.setBackground(unclicked_color);
        //verticalBox.setBackground(unclicked_color);
    }

    public void update() {
        this.revalidate();
        this.updateUI();
        master.pair_panel.contentPanel.revalidate();
        master.pair_panel.contentPanel.updateUI();
        //master.pair_panel.revalidate();
        //master.pair_panel.updateUI();
        //master.contentPane.revalidate();
        //master.contentPane.updateUI();
//        master.getContentPane().revalidate();
    }

    public void pair() {
        master.clickedControllerJPanel = null;
        master.clickedRobotJPanel = null;
        controllerJPanel.unclick();
        robotJPanel.unclick();
        controllerJPanel.setBackground(new Color(0, 0, 0, 0));
        robotJPanel.setBackground(new Color(0, 0, 0, 0));
        controllerJPanel.setFocusable(false);
        robotJPanel.setFocusable(false);
        if (master.devicePairJPanels.contains(this))
            return;
        master.devicePairJPanels.add(this);
        controllerJPanel.deviceInfoContainer.remove(controllerJPanel);
        robotJPanel.deviceInfoContainer.remove(robotJPanel);
        //for safety
        System.out.println("pair! pair! pair!");
        Dimension preferedSize = getPreferredSize();
        master.pair_panel.contentPanel.remove(this);
        master.pair_panel.contentPanel.add(this);
        setPreferredSize(preferedSize);
        System.out.println(master.pair_panel);
        update();
        System.out.println("paired");
    }

    public void unPair() {
        System.out.println("un-pair! un-pair! un-pair!");
        if (!master.devicePairJPanels.contains(this))
            return;
        master.devicePairJPanels.remove(this);
        master.pair_panel.contentPanel.remove(this);
        master.controller_container.add(controllerJPanel);
        master.robot_container.add(robotJPanel);
        controllerJPanel.unPair();
        robotJPanel.unPair();
        update();
        System.out.println("un-paired");
    }

    static class LineBox extends Box {
        public LineBox(int i) {
            super(i);
            setBackground(new Color(0, 0, 0, 0));
        }

        public LineBox() {
            this(1);
        }

        @Override
        protected void paintComponent(Graphics graphics) {
            //super.paintComponent(graphics);
            graphics.setColor(new Color(71, 101, 128));
            graphics.fillRoundRect(0, getHeight() * 2 / 5, getWidth(), getHeight() / 5, 10, 10);
        }
    }
}
