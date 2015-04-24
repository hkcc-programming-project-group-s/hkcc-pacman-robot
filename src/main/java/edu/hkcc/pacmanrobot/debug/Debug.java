package edu.hkcc.pacmanrobot.debug;

import java.util.Calendar;

/**
 * Created by beenotung on 4/22/15.
 * This is lazy Singleton
 */
public class Debug {
    public static Debug instance = null;
    public final byte SERVER_NOT_FOUND = 0x01;
    public final byte DUPLICATED_SERVER_LAUNCH = 0x02;
    public boolean activated = true;

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

    public void printMessage(String message) {
        if (activated)
            System.out.println(Calendar.getInstance().getTime().toString() + "\t" + message);
    }
}
