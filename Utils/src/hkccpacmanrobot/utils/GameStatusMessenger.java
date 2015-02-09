package hkccpacmanrobot.utils;

import java.io.IOException;

/**
 * Created by beenotung on 2/9/15.
 */
public class GameStatusMessenger extends Messenger<GameStatus> {


    /*
        send the message to user
        if (win) send the win message to user
        else send the loss message to user
    */
    /*
        send the message to user
        when robot's position is not change in ??? time.
    */


    @Override
    public void sendMessage(GameStatus content) throws IOException {

    }

    @Override
    public GameStatus getMessage() throws IOException {
        return null;
    }
}
