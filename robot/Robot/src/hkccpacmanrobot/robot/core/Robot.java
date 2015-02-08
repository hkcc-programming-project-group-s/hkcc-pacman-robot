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

    public abstract void run();

    public abstract void save();

    //listen to connect to database
    public void checkGameOver() {

    }

    public void checkGamePause() {

    }
}
