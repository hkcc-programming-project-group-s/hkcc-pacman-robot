package studentrobot

import studentrobot.code.StudentRobot

/**
 * Created by beenotung on 3/27/15.
 */
object MyApp extends App {
  override def main(args: Array[String]) {
    val studentRobot: StudentRobot = new StudentRobot()
    studentRobot.start
  }
}
