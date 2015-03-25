package hkccpacmanrobot.server

import java.sql.Timestamp

import hkccpacmanrobot.server.listener.NewDeviceListener
import hkccpacmanrobot.utils.{GameReport, Messenger, Position}

/**
 * Created by beenotung on 2/10/15.
 */


class Server {
  val gameReportMessengers: List[Messenger[GameReport]] = List[Messenger[GameReport]]()
  val positionMessenger: Messenger[Position] = new Messenger[Position](sender = "remote controller ip", receiver = "student ip")
  private var gameStarted: Boolean = true
  private var gamePause: Boolean = false

  val gameReport:GameReport=new GameReport(GameReport.TYPE_SETUP)
  val newDeviceListener:NewDeviceListener=new NewDeviceListener(gameReport,gameReportMessengers)

  def setup: Unit = {
    newDeviceListener.start
    listenToRemoteController
  }

  //loop wait for incoming connection
  def pairUpNewDevice = {
    while (!gameStarted) {
      //getMap client socket
      //add to gameStatusMessengers
    }
  }

  //continuously getMap remote command
  //timeout check connection
  def listenToRemoteController = {
    // loop , check when controller is paired up
    while (true) {
      //wait start game message
      while (!gameStarted) {
        //wait game start message
        gameStarted = true
      }
      while (gameStarted && !gamePause) {
        val position = positionMessenger.getMessage()
        positionMessenger.sendMessage(position)
      }
      //wait for
    }
  }

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
}
