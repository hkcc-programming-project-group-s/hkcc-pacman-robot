package hkccpacmanrobot.utils;

/**
 * Created by beenotung on 2/9/15.
 */
public class RemoteControllInfo {
    public static final byte MODE_DIRECTION = 1;
    public static final byte MODE_POSITION = 2;

    public Position content;
    public byte mode;
    public int studentId;

    public RemoteControllInfo(Position content, byte mode, int studentId) {
        this.content = content;
        this.mode = mode;
        this.studentId = studentId;
    }

    public RemoteControllInfo(Position content, byte mode) {
        this.content = content;
        this.mode = mode;
    }
}
