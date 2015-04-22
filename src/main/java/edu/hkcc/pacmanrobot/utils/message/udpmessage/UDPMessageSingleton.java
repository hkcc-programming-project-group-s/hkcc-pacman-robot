package edu.hkcc.pacmanrobot.utils.message.udpmessage;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by beenotung on 4/20/15.
 * this class is a lazy Singleton
 */
@Deprecated
public class UDPMessageSingleton {
    private static UDPMessageSingleton instance = null;
    final int DEFAULT_STRING_LENGTH = 256;
    final byte BYTE_TRUE = 0x01;
    final byte BYTE_FALSE = 0x00;
    String[] strings = new String[Integer.MAX_VALUE - 4];
    ConcurrentHashMap<String, Integer> lengthHashMap = new ConcurrentHashMap<>();


    private UDPMessageSingleton() {
        //make sure hash map key is the same
        for (int i = 0; i < strings.length; i++)
            strings[i] = i + "";
    }

    public static UDPMessageSingleton getInstance() {
        if (instance == null) {
            synchronized (UDPMessageSingleton.class) {
                if (instance == null)
                    instance = new UDPMessageSingleton();
            }
        }
        return instance;
    }
}
