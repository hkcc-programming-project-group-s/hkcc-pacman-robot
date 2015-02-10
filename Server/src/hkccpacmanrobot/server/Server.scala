package hkccpacmanrobot.server

import java.sql.Timestamp

import hkccpacmanrobot.utils.{GameStatus, Messenger, Position}

/**
 * Created by beenotung on 2/10/15.
 */
class Server {
  val gameStatusMessenger: Messenger[GameStatus]
  val positionMessenger: Messenger[Position]
  private var gameRun: Boolean = true
  private var gamePause: Boolean = false

  def setup: Unit = {
    pairUpNewDevice
    listenToRemoteController
  }

  //wait for incoming connection
  def pairUpNewDevice = {}

  //continuously get remote command
  //timeout check connection
  def listenToRemoteController: Unit = {
    // loop , check when controller is paired up
    while (true) {
      //wait start game message
      //kill pairUpNew Device
      while(gameRun && !gamePause){
        
      }
      //wait for
    }
  }

  def loop: Unit = {}

  def save: Unit = {}

  def sendNextPosition {
    val nextPosition: Position = null
  }


  def isNear(p1:Position,p2:Position): Boolean = {
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
}
