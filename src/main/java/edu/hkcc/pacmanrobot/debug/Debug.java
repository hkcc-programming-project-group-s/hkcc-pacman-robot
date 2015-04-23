package edu.hkcc.pacmanrobot.debug;

/**
 * Created by beenotung on 4/22/15.
 * This is lazy Singleton
 */
public class Debug {
    public static Debug instance = null;
    public final byte SERVER_NOT_FOUND = 0x01;

    private Debug() {
    }

    public static Debug getInstance() {
        if (instance == null) {
            synchronized (Debug.class) {
                if (instance == null)
                    instance = new Debug();
            }
        }
        return instance;
    }
}
