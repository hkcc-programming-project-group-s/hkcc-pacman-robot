package edu.hkcc.pacmanrobot.utils.message;

import java.io.Serializable;

/**
 * Created by beenotung on 3/26/15.
 */
public interface Message extends Serializable {
    public abstract int port();
}
