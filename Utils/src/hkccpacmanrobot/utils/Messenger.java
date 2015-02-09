package hkccpacmanrobot.utils;

import java.io.IOException;

//on server
public abstract class Messenger<Type> extends Thread {

    public static final Byte RESET = 0x01;

    public abstract void sendMessage(Type content) throws IOException;

    public abstract Type getMessage() throws IOException;

}