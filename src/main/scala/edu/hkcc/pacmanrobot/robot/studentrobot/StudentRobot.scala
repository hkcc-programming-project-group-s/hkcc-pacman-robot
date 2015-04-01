package edu.hkcc.pacmanrobot.robot.studentrobot

import java.io.ObjectInputStream
import java.net.Socket

import edu.hkcc.pacmanrobot.robot.core.Robot
import edu.hkcc.pacmanrobot.robot.edu.hkcc.pacmanrobot.utils.studentrobot.MovementCommandMessenger
import edu.hkcc.pacmanrobot.robot.utils.L298NAO
import edu.hkcc.pacmanrobot.utils.Config
import edu.hkcc.pacmanrobot.utils.Config.MOTOR_CYCLE_INTERVAL
import edu.hkcc.pacmanrobot.utils.maths.Point2D


/**
 * Created by beenotung on 3/26/15.
 */
class StudentRobot extends Robot {
  val movementCommandMessenger: MovementCommandMessenger = null //new MovementCommandMessenger



  override def gameSetup: Unit = {
//    movementCommandMessenger.start
  }

  override def gameResume: Unit = ???

  override def gamePause: Unit = ???

  override def gameStart: Unit = ???

  override def gameStop: Unit = ???

  val socket=new Socket(Config.serverAddress,Config.PORT_MOVEMENT_COMMAND)
  var in :ObjectInputStream= null
  override def run = {
    in=new ObjectInputStream(socket.getInputStream)
    while (true) {
      //movementCommandMessenger.checkConnection
      loop
      Thread.sleep(MOTOR_CYCLE_INTERVAL)
    }
  }

  override def loop: Unit = {
    //val movementCommand: MovementCommand = movementCommandMessenger.getMessage
    val direction=in.readObject().asInstanceOf[java.lang.Double]
    L298NAO.move_pwm(new Point2D(direction,1))
//    if (movementCommand.mode.equals(MovementCommand.MODE_POLAR)) {
//      L298NAO.move(movementCommand.point2D)
//    }else{
//      //TODO calculate polar command from current location
//    }
  }
}
