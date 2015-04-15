package edu.hkcc.pacmanrobot.controller.gamemonitor.gui.utils;

import com.sun.istack.internal.NotNull;
import edu.hkcc.pacmanrobot.utils.message.DeviceInfo;

import java.util.Vector;

/**
 * Created by beenotung on 4/12/15.
 */
public interface DeviceInfoJPanelHandler {
    @NotNull
    void onDeviceInfoJPanelClicked(DeviceInfoJPanel deviceInfoJPanel);

    Vector<DeviceInfoContainer> getDeviceInfoContainers();

    public void receivedDeviceInfo(DeviceInfo deviceInfo);
}
