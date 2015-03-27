package edu.hkcc.pacmanrobot.robot.deadlinerobot;

import edu.hkcc.pacmanrobot.robot.Robot;
import hkccpacmanrobot.robot.utils.Position;
import hkccpacmanrobot.utils.PositionDAO;
import hkccpacmanrobot.utils.PositionMessageManager;

import static hkccpacmanrobot.utils.PositionMessageManager.MODE_DEADLINE;


public class DeadlineRobot extends Robot{
    public PositionDAO student= new PositionDAO();

    @Override
    public void setup() {
        // TODO Auto-generated method stub

    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
       getTargetPosition();
    }

    @Override
    public void save() {
        // TODO Auto-generated method stub

    }

   // public void listenToRemoteController() {
        //It is false???
    //}

  public void getTargetPosition() {
        int studentID =01;
        double x,y,z;
      x=y=z=0;
        Position targetPosition = new position(x,y,z) ;
       targetPosition = student.getTargetPosition(studentID);

   }

}