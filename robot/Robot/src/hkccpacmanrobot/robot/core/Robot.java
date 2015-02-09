package hkccpacmanrobot.robot.core;

import hkccpacmanrobot.robot.utils.Map;

public abstract class Robot {
    public boolean gameOver = true;
    public PositionSAO positionSAO;
    public Map map = new Map();

    public Robot() {
        setup();
        loop();
        save();
    }

    public abstract void setup();

    public void loop() {
        do {
            if (gameOver)
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                }
            else
                run();
        } while (true);
    }


    /*
     checkGameOver();
     checkGamePause();
     save map;
     send the change of map to database;
     send the message to sever about change
     send the position to database
    */
    public abstract void run();

    public abstract void save();

    //listen to connect to database

    public void checkGameOver() {
/*
    listen to connect to sever
    if sever send game over message, [this.gameOver=!gameOver]
 */
    }

    public void checkGamePause() {
/*
    listen to connect to sever
    if sever send game pause message, [this.gameOver=!gameOver]
    when sever send game start, [this.gameOver=!gameOver]
 */

    }
}
