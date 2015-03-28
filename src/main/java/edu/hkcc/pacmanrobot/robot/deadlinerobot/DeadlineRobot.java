package edu.hkcc.pacmanrobot.robot.deadlinerobot;


import edu.hkcc.pacmanrobot.robot.core.Robot;
import edu.hkcc.pacmanrobot.utils.studentrobot.code.Position;

import java.sql.Timestamp;


public class DeadlineRobot extends Robot {


    @Override
    public void gameSetup() {

    }

    @Override
    public void gameStart() {

    }

    @Override
    public void gamePause() {

    }

    @Override
    public void gameResume() {

    }

    @Override
    public void gameStop() {

    }

    @Override
    public void setup() {
        // TODO Auto-generated method stub

    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        getTargetPosition();
    }



    // public void listenToRemoteController() {
    //It is false???
    //}

    public void getTargetPosition() {
        int studentID = 01;
        double x, y, z;
        x = y = z = 0;
        Position targetPosition = new Position(x, y, z,new Timestamp(System.currentTimeMillis()));
        targetPosition = null;//TODO student.getTargetPosition(studentID);
    }

    @Override
    public void loop() {

    }
}