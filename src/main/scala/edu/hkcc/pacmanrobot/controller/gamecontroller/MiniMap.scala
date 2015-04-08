package edu.hkcc.pacmanrobot.controller.gamecontroller

import edu.hkcc.pacmanrobot.utils.Config
import edu.hkcc.pacmanrobot.utils.map.ObstacleMap
import edu.hkcc.pacmanrobot.utils.message.Messenger
import myutils.gui.opengl.AbstractOpenGLApplication

/**
 * Created by beenotung on 4/8/15.
 */
class MiniMap extends Thread {

  class MiniMapRunnable extends AbstractOpenGLApplication {
    override def keyInvoke(window: Long, key: Int, scanCode: Int, action: Int, mode: Int): Unit = {}

    override def myInit(): Unit = {}

    override def reshape(): Unit = {}

    override def myTick(): Unit = {}

    override def debugInfo(): Unit = {}

    override def myRender(): Unit = {}
  }

  val runnable = new MiniMapRunnable
  val obstacleMap: ObstacleMap = new ObstacleMap
  val messenger: Messenger[ObstacleMap] = Messenger.create(Config.PORT_MAP, map => obstacleMap.merge(map),null)


  override def run = {
    println("start obstacle map messenger on mini map")
    messenger.start()
    println("start opengl window")
    runnable.run
  }
}