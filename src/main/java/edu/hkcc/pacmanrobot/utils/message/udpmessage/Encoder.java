package edu.hkcc.pacmanrobot.utils.message.udpmessage;

import edu.hkcc.pacmanrobot.debug.Debug;

import java.nio.ByteBuffer;
import java.util.concurrent.ConcurrentHashMap;

import static edu.hkcc.pacmanrobot.utils.Config.*;

/**
 * Created by beenotung on 4/20/15.
 * this class is a lazy Singleton
 */
public class Encoder {
    public static final int DEFAULT_STRING_LENGTH = 256;
    private static Encoder instance = null;
    final int MAC_ADDRESS_BYTES = 6;
    final byte BYTE_TRUE = 0x01;
    final byte BYTE_FALSE = 0x00;
    ConcurrentHashMap<String, Integer> lengthHashMap = new ConcurrentHashMap<>();

    private Encoder() {
    }

    public static Encoder getInstance() {
        if (instance == null) {
            synchronized (Encoder.class) {
                if (instance == null)
                    instance = new Encoder();
            }
        }
        return instance;
    }

    // lazy init
    public int getLength(int messageType) {
        int length = -1;
        //make sure hash map key is the same
        String key = (messageType + "").intern();
        if (lengthHashMap.containsKey(key))
            length = lengthHashMap.get(key);
        else {
            switch (messageType) {
                case PORT_DEVICE_INFO:
                    length = MAC_ADDRESS_BYTES + DEFAULT_STRING_LENGTH + DEFAULT_STRING_LENGTH + 1 + Long.BYTES + 1;
                    break;
                case PORT_MOVEMENT_COMMAND:
                    length = 1 + Double.BYTES + Double.BYTES;
                    break;
                case PORT_GAME_STATUS:
                    length = 1 + DEFAULT_STRING_LENGTH + 1;
                    break;
            }
            if (length != -1)
                lengthHashMap.put(key, length);
        }
        return length;
    }

    public byte[] getDeviceInfo(byte[] MAC_ADDRESS, String name, String ip, byte deviceType, long lastConnectionTime, boolean shouldSave) {
        byte[] result = new byte[getLength(PORT_DEVICE_INFO)];
        int index = 0;
        index = saveToArray(result, index, MAC_ADDRESS);
        index = saveToArray(result, index, DEFAULT_STRING_LENGTH, name);
        index = saveToArray(result, index, DEFAULT_STRING_LENGTH, ip);
        index = saveToArray(result, index, deviceType);
        index = saveToArray(result, index, lastConnectionTime);
        index = saveToArray(result, index, shouldSave);
        return result;
    }

    public byte[] getMovementCommand(byte mode, double p1, double p2) {
        byte[] result = new byte[getLength(PORT_MOVEMENT_COMMAND)];
        int index = 0;
        index = saveToArray(result, index, mode);
        index = saveToArray(result, index, p1);
        index = saveToArray(result, index, p2);
        return result;
    }

    public byte[] getGameStatus(byte status, String message, byte furtherInfo) {
        byte[] result = new byte[getLength(PORT_GAME_STATUS)];
        int index = 0;
        index = saveToArray(result, index, status);
        index = saveToArray(result, index, message);
        index = saveToArray(result, index, furtherInfo);
        return result;
    }

    int saveToArray(byte[] array, int index_start, int length, String content) {
        while (length > content.length())
            content += " ";
        return saveToArray(array, index_start, content);
    }

    int saveToArray(byte[] array, int index_start, String content) {
        Debug.getInstance().printMessage("content length = " + content.length());
        Debug.getInstance().printMessage("content bytes length = " + content.getBytes().length);
        System.arraycopy(content.getBytes(), 0, array, index_start, content.length());
        return index_start + content.length();
    }

    int saveToArray(byte[] array, int index_start, long content) {
        System.arraycopy(ByteBuffer.allocate(Long.BYTES).putLong(content).array(), 0, array, index_start, Long.BYTES);
        return index_start + Long.BYTES;
    }

    int saveToArray(byte[] array, int index_start, double content) {
        System.arraycopy(ByteBuffer.allocate(Long.BYTES).putLong(Double.doubleToLongBits(content)).array(), 0, array, index_start, Long.BYTES);
        return index_start + Long.BYTES;
    }

    int saveToArray(byte[] array, int index_start, int content) {
        System.arraycopy(ByteBuffer.allocate(Integer.BYTES).putInt(content).array(), 0, array, index_start, Integer.BYTES);
        return index_start + Integer.BYTES;
    }

    int saveToArray(byte[] array, int index_start, float content) {
        System.arraycopy(ByteBuffer.allocate(Integer.BYTES).putInt(Float.floatToIntBits(content)).array(), 0, array, index_start, Integer.BYTES);
        return index_start + Integer.BYTES;
    }

    int saveToArray(byte[] array, int index_start, boolean content) {
        array[index_start] = (content ? BYTE_TRUE : BYTE_FALSE);
        return index_start + 1;
    }

    int saveToArray(byte[] array, int index_start, byte content) {
        array[index_start] = content;
        return index_start + 1;
    }


    int saveToArray(byte[] array, int index_start, byte[] content) {
        System.arraycopy(content, 0, array, index_start, content.length);
        return index_start + content.length;
    }


}
