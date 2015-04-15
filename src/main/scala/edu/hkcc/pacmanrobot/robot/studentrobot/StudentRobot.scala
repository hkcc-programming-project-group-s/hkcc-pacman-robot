package edu.hkcc.pacmanrobot.robot.studentrobot

import edu.hkcc.pacmanrobot.robot.core.Robot
import edu.hkcc.pacmanrobot.robot.utils.L298NAO
import edu.hkcc.pacmanrobot.utils.Config
import edu.hkcc.pacmanrobot.utils.Config.MOTOR_CYCLE_INTERVAL
import edu.hkcc.pacmanrobot.utils.message.messenger.Messenger

import edu.hkcc.pacmanrobot.utils.message.{DeviceInfo, MovementCommand}


/**
 * Created by beenotung on 3/26/15.
 */
class StudentRobot(name: String) extends Robot {
  var movementCommand:MovementCommand=MovementCommand.stop
  val movementCommandMessenger = new Messenger[MovementCommand](Config.PORT_MOVEMENT_COMMAND) {
    override def autoGet(message: MovementCommand): Unit = {movementCommand = message}
  }



  override def gameSetup: Unit = {
  }

  override def gameResume: Unit = ???

  override def gamePause: Unit = ???

  override def gameStart: Unit = ???

  override def gameStop: Unit = ???

  override def run = {
    movementCommandMessenger.start
    while (true) {
      loop
      Thread.sleep(MOTOR_CYCLE_INTERVAL)
    }
  }


  override def loop: Unit = {
    //val direction=in.readObject().asInstanceOf[java.lang.Double]
    if (movementCommand.mode.equals(MovementCommand.MODE_POLAR)) {
      L298NAO.move_pwm(movementCommand.point2D)
    } else {
      //TODO calculate polar command from current location
    }
  }

  override var deviceInfo: DeviceInfo = DeviceInfo.create(name, DeviceInfo.DEVICE_TYPE_STUDENT_ROBOT)
}
