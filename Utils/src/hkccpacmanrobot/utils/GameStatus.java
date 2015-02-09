package hkccpacmanrobot.utils;

/**
 * Created by beenotung on 2/9/15.
 */
public class GameStatus {
    public static final byte TYPE_START_GAME = 1;
    public static final byte TYPE_WIN = 2;
    public static final byte TYPE_LOSE = 3;
    public static final byte TYPE_PAUSE = 4;
    public static final byte TYPE_RESUME = 5;

    public byte gameStatus;
    public String message;

    public GameStatus(byte gameStatus, String message) {
        this.gameStatus = gameStatus;
        this.message = message;
    }

    public GameStatus(byte gameStatus) {
        this.gameStatus = gameStatus;
        message = "";
    }
}
