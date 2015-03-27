package edu.hkcc.pacmanrobot.utils;

/*
there is for connection to each other like Sever send message to Robot to let Robot start the game.
*/
@Deprecated
public class ConnectionInfo {
    public static final byte TYPE_POSITION_UPDATE = 1;
    public static final byte TYPE_PAUSE_NOTIFICATION = 2;
    public static final byte TYPE_GAME_START_NOTIFICATION = 3;
    public static final byte TYPE_GAME_OVER_NOTIFICATION = 4;

    //IP + port number
    public final String srcAddr;
    public final String destAddr;
    public final byte type;

    public ConnectionInfo(String srcAddr, String destAddr, byte type) {
        this.srcAddr = srcAddr;
        this.destAddr = destAddr;
        this.type = type;
    }
}