package edu.hkcc.pacmanrobot.controller.gamemonitor.core;

import edu.hkcc.pacmanrobot.utils.Config;
import edu.hkcc.pacmanrobot.utils.message.DeviceInfo;
import edu.hkcc.pacmanrobot.utils.message.messenger.Messenger;
import scala.Function1;
import scala.runtime.BoxedUnit;

/**
 * Created by beenotung on 4/15/15.
 */
public class SAO {
    public Function1<DeviceInfo, BoxedUnit> nothing = new Function1<DeviceInfo, BoxedUnit>();
    public Function1<DeviceInfo, BoxedUnit> autoget = nothing;
    public Messenger<DeviceInfo> deviceInfoMessenger = Messenger.create(Config.PORT_DEVICE_INFO, autoget, null);
    public void resetAutoget(){
        autoget=nothing;
    }
}
