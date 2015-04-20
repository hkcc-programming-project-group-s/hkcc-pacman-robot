package edu.hkcc.pacmanrobot.utils.message;

import edu.hkcc.pacmanrobot.utils.Config;

import java.nio.ByteBuffer;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

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
                    length = 6 + DEFAULT_STRING_LENGTH + DEFAULT_STRING_LENGTH + 1 + Long.BYTES + 1;
                    break;
            }
            if (length != -1)
                lengthHashMap.put(strings[messageType], length);
        }
        return length;
    }

    byte[] getDeviceInfo(byte[] MAC_ADDRESS, String name, String ip, byte deviceType, long lastConnectionTime, boolean shouldSave) {
        byte[] result = new byte[getLength(Config.PORT_DEVICE_INFO)];
        int index = 0;
        index = saveToArray(result, index, MAC_ADDRESS);
        index = saveToArray(result, index, DEFAULT_STRING_LENGTH, name);
        index = saveToArray(result, index, DEFAULT_STRING_LENGTH, ip);
        index = saveToArray(result, index, deviceType);
        index = saveToArray(result, index, lastConnectionTime);
        index = saveToArray(result, index, shouldSave);
        return result;
    }

    DeviceInfo getDeviceInfo(byte[] array) {
        Byte[] macAddress;
        StringBuilder name;
        StringBuilder ip;
        byte deviceType;
        long lastConnectionTime;
        boolean shouldSave;
        int index = 0;
        new DeviceInfo(macAddress, name, ip, deviceType, lastConnectionTime, shouldSave);
    }
    //TODO movement, gamestatus


    int saveToArray(byte[] array, int index_start, int length, String content) {
        while (length > content.length())
            content += " ";
        return saveToArray(array, index_start, content);
    }

    int saveToArray(byte[] array, int index_start, String content) {
        System.arraycopy(content.getBytes(), 0, array, index_start, content.length());
        return index_start + content.length();
    }

    int loadFromArray(byte[] array, int index_start, int length, StringBuilder content) {
        content.setLength(0);
        content.append(new String(array, index_start, length));
        return index_start + length;
    }

    int saveToArray(byte[] array, int index_start, long content) {
        System.arraycopy(ByteBuffer.allocate(Long.BYTES).putLong(content).array(), 0, array, index_start, Long.BYTES);
        return index_start + Long.BYTES;
    }

    int loadFromArray(byte[] array, int index_start, AtomicLong content) {
        ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES).put(array, index_start, Long.BYTES);
        buffer.flip();
        content.set(buffer.getLong());
        return index_start + Long.BYTES;
    }

    int saveToArray(byte[] array, int index_start, int content) {
        System.arraycopy(ByteBuffer.allocate(Integer.BYTES).putInt(content).array(), 0, array, index_start, Integer.BYTES);
        return index_start + Integer.BYTES;
    }

    int loadFromArray(byte[] array, int index_start, AtomicInteger content) {
        ByteBuffer buffer = ByteBuffer.allocate(Integer.BYTES).put(array, index_start, Integer.BYTES);
        buffer.flip();
        content.set(buffer.getInt());
        return index_start + Integer.BYTES;
    }

    final byte BYTE_TRUE = 0x01;
    final byte BYTE_FALSE = 0x00;

    int saveToArray(byte[] array, int index_start, boolean content) {
        array[index_start] = (byte) (content ? BYTE_TRUE : BYTE_FALSE);
        return index_start + 1;
    }

    int loadFromArray(byte[] array, int index_start, AtomicBoolean content) {
        content.set(array[index_start] == BYTE_TRUE);
        return index_start + 1;
    }

    int saveToArray(byte[] array, int index_start, byte content) {
        array[index_start] = content;
        return index_start + 1;
    }

    int loadFromArray(byte[] array, int index_start, ByteBuffer content) {
        content.clear();
        content.put(array[index_start]);
        return index_start + 1;
    }

    int saveToArray(byte[] array, int index_start, byte[] content) {
        System.arraycopy(content, 0, array, index_start, content.length);
        return index_start + content.length;
    }

    int loadFromArray(byte[] array, int index_start, byte[] content) {
        System.arraycopy(array, index_start, content, 0, content.length);
        return index_start + content.length;
    }
}
