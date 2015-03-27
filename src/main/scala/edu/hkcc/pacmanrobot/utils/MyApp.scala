package edu.hkcc.pacmanrobot.robot.studentrobot

import edu.hkcc.pacmanrobot.utils.studentrobot.code.StudentRobot

/**
 * Created by beenotung on 3/27/15.
 */
object MyApp extends App {
  override def main(args: Array[String]) {
    val studentRobot: StudentRobot = new StudentRobot()
    studentRobot.start
  }
}
