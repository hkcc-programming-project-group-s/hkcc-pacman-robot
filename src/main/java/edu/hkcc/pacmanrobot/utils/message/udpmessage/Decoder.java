package edu.hkcc.pacmanrobot.utils.message.udpmessage;

import edu.hkcc.pacmanrobot.utils.Point2D;
import edu.hkcc.pacmanrobot.utils.message.DeviceInfo;
import edu.hkcc.pacmanrobot.utils.message.MovementCommand;
import edu.hkcc.pacmanrobot.utils.studentrobot.code.GameStatus;

import java.nio.ByteBuffer;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by beenotung on 4/20/15.
 * this class is a lazy Singleton
 */
public class Decoder {
    private static Decoder instance = null;
    final int DEFAULT_STRING_LENGTH = 256;
    final byte BYTE_TRUE = 0x01;
    final byte BYTE_FALSE = 0x00;


    private Decoder() {
    }

    public static Decoder getInstance() {
        if (instance == null) {
            synchronized (Decoder.class) {
                if (instance == null)
                    instance = new Decoder();
            }
        }
        return instance;
    }

    public DeviceInfo getDeviceInfo(byte[] array) {
        byte[] macAddress = new byte[DeviceInfo.MAC_ADDRESS_BYTES()];
        StringBuilder name = new StringBuilder();
        StringBuilder ip = new StringBuilder();
        ByteBuffer deviceType = ByteBuffer.allocate(1);
        AtomicLong lastConnectionTime = new AtomicLong();
        AtomicBoolean shouldSave = new AtomicBoolean();
        int index = 0;
        index = loadFromArray(array, index, macAddress);
        index = loadFromArray(array, index, DEFAULT_STRING_LENGTH, name);
        index = loadFromArray(array, index, DEFAULT_STRING_LENGTH, ip);
        index = loadFromArray(array, index, deviceType);
        index = loadFromArray(array, index, lastConnectionTime);
        index = loadFromArray(array, index, shouldSave);
        return new DeviceInfo(macAddress, name.toString().trim(), ip.toString().trim(), deviceType.get(), lastConnectionTime.get(), shouldSave.get());
    }

    public MovementCommand getMovementCommand(byte[] array) {
        ByteBuffer mode = ByteBuffer.allocate(1);
        AtomicLong p1 = new AtomicLong();
        AtomicLong p2 = new AtomicLong();
        int index = 0;
        index = loadFromArray(array, index, mode);
        index = loadFromArray(array, index, p1);
        index = loadFromArray(array, index, p2);
        return new MovementCommand(mode.get(), new Point2D<Double>(Double.longBitsToDouble(p1.get()), Double.longBitsToDouble(p2.get())));
    }

    public GameStatus getGameStatus(byte[] array) {
        ByteBuffer status = ByteBuffer.allocate(1);
        StringBuilder message = new StringBuilder();
        ByteBuffer furtherInfo = ByteBuffer.allocate(1);
        int index = 0;
        index = loadFromArray(array, index, status);
        index = loadFromArray(array, index, DEFAULT_STRING_LENGTH, message);
        index = loadFromArray(array, index, furtherInfo);
        return new GameStatus(status.get(), message.toString(), furtherInfo.get());
    }

    int loadFromArray(byte[] array, int index_start, int length, StringBuilder content) {
        content.setLength(0);
        content.append(new String(array, index_start, length));
        return index_start + length;
    }

    int loadFromArray(byte[] array, int index_start, AtomicLong content) {
        ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES).put(array, index_start, Long.BYTES);
        buffer.flip();
        content.set(buffer.getLong());
        return index_start + Long.BYTES;
    }

    int loadFromArray(byte[] array, int index_start, AtomicInteger content) {
        ByteBuffer buffer = ByteBuffer.allocate(Integer.BYTES).put(array, index_start, Integer.BYTES);
        buffer.flip();
        content.set(buffer.getInt());
        return index_start + Integer.BYTES;
    }

    int loadFromArray(byte[] array, int index_start, AtomicBoolean content) {
        content.set(array[index_start] == BYTE_TRUE);
        return index_start + 1;
    }


    int loadFromArray(byte[] array, int index_start, ByteBuffer content) {
        content.clear();
        content.put(array[index_start]);
        return index_start + 1;
    }


    int loadFromArray(byte[] array, int index_start, byte[] content) {
        System.arraycopy(array, index_start, content, 0, content.length);
        return index_start + content.length;
    }
}
