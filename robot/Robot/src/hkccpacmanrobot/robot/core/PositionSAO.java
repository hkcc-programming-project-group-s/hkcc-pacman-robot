package hkccpacmanrobot.robot.core;

import hkccpacmanrobot.robot.utils.Position;

//Sever Access Object
public class PositionSAO extends Thread {

	/*
	 * deadline
	 * 
	 * student
	 * 
	 * assignment
	 */
	private final String mode;
	/*
     * Deadline: Student location
	 *
	 * Student: remote pointed location
	 *
	 * Assignment: Student location
	 */
	public Position targetPosition;
	public Position planPosition;

    public PositionSAO(String mode) {
        this.mode = mode;
    }

	public void sendPosition() {
	/*
	send the position(x,y) and time to database
	*/
	}

	public void getTargetPosition() {
		
		targetPosition=null;
		/*
		It is for deadline
		get student's position
		*/
	}
	
	

}
