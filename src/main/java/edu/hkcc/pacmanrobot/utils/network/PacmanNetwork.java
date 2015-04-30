package edu.hkcc.pacmanrobot.utils.network;


import edu.hkcc.pacmanrobot.debug.Debug;
import edu.hkcc.pacmanrobot.utils.Config;
import edu.hkcc.pacmanrobot.utils.message.udpmessage.UDPMessengerSingleton;

import java.io.IOException;
import java.net.BindException;

/**
 * Created by beenotung on 4/19/15.
 * this class has lazy singleton
 */
public class PacmanNetwork {
    public static final String MESSAGE_SERVER = "PACMAN_ROBOT_GAME_SERVER";
    public static final String MESSAGE_CLIENT = "PACMAN_ROBOT_GAME_CLIENT";
    public static final int INTERVAL = 1000;

    public static volatile boolean shouldRun = false;
}
