package edu.hkcc.pacmanrobot.robot.core

import edu.hkcc.pacmanrobot.utils.Config
import edu.hkcc.pacmanrobot.utils.map.ObstacleMap
import edu.hkcc.pacmanrobot.utils.message.messenger.ObstacleMapMessenger


/**
 * Created by 13058536A on 3/25/2015.
 */
abstract class AbstractRobot extends Thread {
  val mapMessenger = new ObstacleMapMessenger()
  val bufferMap = new ObstacleMap
  val mapThread = new Thread(new Runnable {
    override def run(): Unit = {
      while (true) {
        mapMessenger.sendMessage(bufferMap)
        bufferMap.clear()
        Thread.sleep(Config.SYNC_MAP_CYCLE_INTERVAL)
      }
    }
  })
}
