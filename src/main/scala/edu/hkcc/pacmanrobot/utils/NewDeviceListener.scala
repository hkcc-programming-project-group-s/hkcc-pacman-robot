package hkccpacmanrobot.server.listener

import hkccpacmanrobot.utils.{GameReport, Messenger}

/**
 * Created by beenotung on 2/10/15.
 */

class NewDeviceListener(val gameReport: GameReport, val gameReportMessengers: List[Messenger[GameReport]]) extends Thread {
  override def run(): Unit = {
    while (gameReport.gameStatus == GameReport.TYPE_SETUP) {
      //accept
      //add to list
    }
  }
}