package edu.hkcc.pacmanrobot.utils.message;

import edu.hkcc.pacmanrobot.utils.Config;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by beenotung on 4/20/15.
 * this class is a lazy Singleton
 */
public class UDPMessageSingleton {
    private static UDPMessageSingleton instance = null;
    final int DEFAULT_STRING_LENGTH = 256;
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

    // lazy init
    public int getLength(int messageType) {
        int length = -1;
        if (lengthHashMap.containsKey(strings[messageType]))
            length = lengthHashMap.get(strings[messageType]);
        else {
            switch (messageType) {
                case Config.PORT_DEVICE_INFO:
                    length = getDeviceInfo(null, null, (byte) 0, 0l, true).length;
                    break;
            }
            lengthHashMap.put(strings[messageType], length);
        }
        return length;
    }

    byte[] getDeviceInfo(String name, String ip, byte deviceType, long lastConnectionTime, boolean shouldSave) {
        a//TODO

        return null;
    }

    void saveToArray(byte[] array, int index_start, int length, String content) {
        for (int i = index_start; i < index_start + length; i++)
            array[i] = (byte) content.charAt(i - index_start);
    }

    void saveToArray(byte[] array, int index_start, String content) {
        saveToArray(array,index_start,DEFAULT_STRING_LENGTH,content);
    }
}
