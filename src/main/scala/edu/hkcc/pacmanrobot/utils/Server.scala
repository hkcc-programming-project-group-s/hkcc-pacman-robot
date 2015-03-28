package hkccpacmanrobot.server

import java.sql.Timestamp

import edu.hkcc.pacmanrobot.utils.GameDevice
import edu.hkcc.pacmanrobot.utils.studentrobot.code.Position

/**
 * Created by beenotung on 2/10/15.
 */


class Server extends GameDevice {

  def loop = {}

  def save = {}

  def sendNextPosition {
    val nextPosition: Position = null
  }


  def isNear(p1: Position, p2: Position): Boolean = {
    return true
  }

  //private void switchGameStatus(){
  //	gameOver=!gameOver;
  //}
  def compareUpdateTime {
  }

  def getUpdateTime(robotNo: Int): Timestamp = {
    return null
  }

  override def gameSetup: Unit = {
    //TODO
    //....
    worker = new ServerWorker
  }

  override def gameResume: Unit = {
    worker.gameResume
  }

  override def gamePause: Unit = {
    worker.gamePause
  }

  override def gameStart: Unit = {
    worker.start
  }

  override def gameStop: Unit = ???

  var worker: ServerWorker = _

  override def setup: Unit = ???
}

class ServerWorker extends Thread {
  var shouldRun: Boolean = false

  override def run: Unit = {
    while (true) {
      if (shouldRun) {
        //TODO
      }
      Thread.sleep(100)
    }
  }

  def gamePause = {
    shouldRun = false
  }

  def gameResume = {
    shouldRun = true
  }


  override def start: Unit = {
    shouldRun = true
    super.start
  }
}