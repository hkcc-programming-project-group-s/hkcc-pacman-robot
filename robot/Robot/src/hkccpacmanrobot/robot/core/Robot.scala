package hkccpacmanrobot.robot.core

/**
 * Created by 13058536A on 3/25/2015.
 */
class Robot {
  var gameOver: Boolean = true
  var
  //var positionSAO: Nothing = null

  //public ObstacleMap<RobotMapUnit> map = new ObstacleMap<RobotMapUnit>();
  def this() {
    this()
    setup
    loop
    save
  }

  def setup

  def loop {
    do {
      if (gameOver) try {
        Thread.sleep(100)
      }
      catch {
        case e: InterruptedException => {
        }
      }
      else run
    } while (true)
  }

  def run

  def save

  //listen to connect to database
  def checkGameOver {
  }

  def checkGamePause {
  }
}
