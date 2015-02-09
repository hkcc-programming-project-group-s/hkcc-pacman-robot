package hkccpacmanrobot.server;

import hkccpacmanrobot.utils.Position;

import java.sql.Timestamp;


public class Sever {
    private boolean gameRun = true;

    public void listenToRemotecontroller(){
        /*
        listen to android control
        if android say start the game, gameStart()
         */
    }

    public void sendNextPosition() {
        Position nextPosition = null;
        /*
        Listen to remote control
        get the Next Position of student robot
         */
    }



    public void gameStart() {
    /*
    send message to robot to start game which function is in connection class
    */
    }

    public void gameEnd() {
    /*
    get the position of the robot from database in ?? time.
    if(compareThePosition)
      using function which is in connection class send message to robot to stop the game.
    */
    }

    public boolean compareThePosition() {
    /*
    get the position of different robot from database(x1:student x2=deadline x3=assignment...)
    compare the position

    If deadline is near then student( |x1-x2| <= ?? && |y1-y2| <= ??),
    then the game over and send the loss message to user.
     [sendEndMessage(false);]-->Message Class

    return true;

    else If assignment is near then student(|x3-x2| <= ?? && |y3-y2| <= ??),
     then the end of the game and send the win message to user.
      [sendEndMessage(true)]-->Message Class



     return true;

     else  return false;
    */
        return true;
    }

//private void switchGameStatus(){
//	gameOver=!gameOver;	
//}

    public void compareUpdateTime() {
    /*
    get the update time of different robot from database
    do{
    int robotNo=1;
    Timestamp time2 = getUpdateTime();(robotNo=1 student robotNo=2 deadline robotNo=3 assignment)
    Timestamp time1 = (function to get a now time);
    compare the time to real time
    if( time1 - time2 >= ??)
    { [sendSpecificMessage()]----> Message Class
     using function which is in connection class send message to robot to stop the game.
     gameRun=false;
     else robotNo++;
    }while(!gameRun && i<=3);

    */
    }

    public Timestamp getUpdateTime(int robotNo) {
        /*
        It is for sever
		
		Timestamp time;
		get the update time of different robot to sever from database use SQL;
		(robotNo=1 student robotNo=2 deadline robotNo=3 assignment)
		 
		return time;
		*/
        return null;
    }


}