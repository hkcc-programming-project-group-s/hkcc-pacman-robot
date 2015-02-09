/*
there is for connection to each other like Sever send message to Robot to let Robot start the game.
*/

public class ConnectionInfo {
    public static final String TYPE_POSITION_UPDATE;
    public static final String TYPE_PAUSE_NOTIFICATION;
    public static final String TYPE_GAME_START_NOTIFICATION;
    public static final String TYPE_GAME_OVER_NOTIFICATION;

    //IP + port number
    public final String srcAddr;
    public final String destAddr;
    public final String type;
}