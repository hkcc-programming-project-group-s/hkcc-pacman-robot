package hkccpacmanrobot.robot.studentrobot

import javax.annotation.processing.Messager

import hkccpacmanrobot.utils.message.{MovementCommand, Messenger}
import sun.plugin2.message.Message

/**
 * Created by beenotung on 3/26/15.
 */
class StudentRobot extends hkccpacmanrobot.robot.core.Robot{
  val movementCommandMessenger:Messenger[MovementCommand]=Messenger.create[MovementCommand]()
  override def gameSetup: Unit = ???

  override def gameResume: Unit = ???

  override def gamePause: Unit = ???

  override def setup: Unit = ???

  override def gameStart: Unit = ???

  override def gameStop: Unit = ???
}
