package edu.hkcc.pacmanrobot.server.config.gui.utils;

import com.sun.istack.internal.NotNull;

import java.util.Vector;

/**
 * Created by beenotung on 4/12/15.
 */
public interface DeviceInfoJPanelHandler {
    @NotNull
    void onDeviceInfoJPanelClicked(DeviceInfoJPanel deviceInfoJPanel);

    Vector<DeviceInfoContainer> getDeviceInfoContainers();

    //public void receiveDeviceInfo(DeviceInfo deviceInfo);
}
