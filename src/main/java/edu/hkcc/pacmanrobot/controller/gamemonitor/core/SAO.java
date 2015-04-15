package edu.hkcc.pacmanrobot.controller.gamemonitor.core;

import edu.hkcc.pacmanrobot.controller.gamemonitor.gui.PositionSetting;
import edu.hkcc.pacmanrobot.controller.gamemonitor.gui.utils.DeviceInfoJPanelHandler;
import edu.hkcc.pacmanrobot.utils.Config;
import edu.hkcc.pacmanrobot.utils.message.DeviceInfo;
import edu.hkcc.pacmanrobot.utils.message.FlashRequest;
import edu.hkcc.pacmanrobot.utils.message.messenger.Messenger;
import scala.Function1;
import scala.runtime.BoxedUnit;

/**
 * Created by beenotung on 4/15/15.
 */
public class SAO {
    public Messenger<DeviceInfo> deviceInfoMessenger = Messenger.create(Config.PORT_DEVICE_INFO, new Function1<DeviceInfo, BoxedUnit>() {
        @Override
        public BoxedUnit apply(DeviceInfo v1) {
            if (handler != null)
                handler.receivedDeviceInfo(v1);
            return null;
        }

        @Override
        public <A> Function1<A, BoxedUnit> compose(Function1<A, DeviceInfo> g) {
            //return super.compose(g);
            return null;
        }

        @Override
        public <A> Function1<DeviceInfo, A> andThen(Function1<BoxedUnit, A> g) {
            //    return super.andThen(g);
            return null;
        }
    }, null);

    public DeviceInfoJPanelHandler handler = null;

    public void setHandler(DeviceInfoJPanelHandler newHandler) {
        handler = newHandler;
    }

    public Messenger<FlashRequest> flashRequestMessenger = Messenger.create(Config.PORT_FLASH_REQUEST, new Function1<FlashRequest, BoxedUnit>() {
        @Override
        public BoxedUnit apply(FlashRequest v1) {
            return null;
        }

        @Override
        public <A> Function1<A, BoxedUnit> compose(Function1<A, FlashRequest> g) {
            return null;
        }

        @Override
        public <A> Function1<FlashRequest, A> andThen(Function1<BoxedUnit, A> g) {
            return null;
        }
    }, null);


}
