package hkccpacmanrobot.utils;

/**
 * Created by beenotung on 2/9/15.
 */
public class GameReport {
    public static final byte TYPE_SETUP = 1;
    public static final byte TYPE_START_GAME = 2;
    public static final byte TYPE_WIN = 3;
    public static final byte TYPE_LOSE = 4;
    public static final byte TYPE_PAUSE = 5;
    public static final byte TYPE_RESUME = 6;

    public byte gameStatus;
    public String message;

    public GameReport(byte gameStatus, String message) {
        this.gameStatus = gameStatus;
        this.message = message;
    }

    public GameReport(byte gameStatus) {
        this.gameStatus = gameStatus;
        message = "";
    }
}
