package hkccpacmanrobot.robot.deadlinerobot;

import hkccpacmanrobot.robot.core.Robot;
import hkccpacmanrobot.robot.core.PositionDAO;
import hkccpacmanrobot.robot.utils.Position;


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