package edu.hkcc.pacmanrobot.controller.gamemonitor.gui.utils;

import com.sun.istack.internal.NotNull;
import webs.layout.WrapLayout;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.Vector;

/**
 * Created by beenotung on 4/12/15.
 */
public class DeviceInfoContainer extends JScrollPane {
    public Vector<DeviceInfoJPanel> deviceInfoJPanels;
    public JPanel contentPanel = new JPanel();

    public DeviceInfoContainer(Vector<DeviceInfoJPanel> deviceInfoJPanels, String name) {
        setBorder(new TitledBorder(new LineBorder(new Color(184, 207, 229)), name, TitledBorder.CENTER, TitledBorder.TOP, null, new Color(51, 51, 51)));
        getViewport().setSize(1, 321654);
        this.deviceInfoJPanels = deviceInfoJPanels;
        //setBackground(new Color(0, 0, 0, 0));
        setBackground(new Color(198, 228, 255));
        setFocusable(false);

        contentPanel.setBackground(new Color(198, 228, 255));
        contentPanel.setLayout(new WrapLayout());

        getViewport().add(contentPanel);
    }

    public DeviceInfoContainer(String name) {
        this(new Vector<>(), name);
    }

    @NotNull
    public Component add(DeviceInfoJPanel comp) {
        if (comp.deviceInfoContainer != null)
            comp.deviceInfoContainer.remove(comp);
        deviceInfoJPanels.add(comp);
        comp.deviceInfoContainer = this;
        Dimension preferedSize = comp.getPreferredSize();
        Component result = contentPanel.add(comp);
        comp.setPreferredSize(preferedSize);
        contentPanel.revalidate();
        contentPanel.updateUI();
        revalidate();
        updateUI();
        return result;
    }

    @NotNull
    public void remove(DeviceInfoJPanel comp) {
        if (!deviceInfoJPanels.contains(comp)) return;
        deviceInfoJPanels.remove(comp);
        comp.deviceInfoContainer = null;
        contentPanel.remove(comp);
        contentPanel.revalidate();
        contentPanel.updateUI();
        revalidate();
        updateUI();
    }

    public void clear() {
        while (deviceInfoJPanels.size() > 0)
            remove(deviceInfoJPanels.get(0));
    }
}
