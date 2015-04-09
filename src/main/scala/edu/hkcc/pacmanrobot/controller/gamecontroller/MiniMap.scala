package edu.hkcc.pacmanrobot.controller.gamecontroller

import java.util.function.BiConsumer

import edu.hkcc.pacmanrobot.utils.{Timer, Config}
import edu.hkcc.pacmanrobot.utils.map.{MapUnit, MapKey, ObstacleMap}
import edu.hkcc.pacmanrobot.utils.message.Messenger
import myutils.gui.opengl.AbstractOpenGLApplication

import scala.util.Random

/**
 * Created by beenotung on 4/8/15.
 */
class MiniMap(WINDOW_WIDTH: Int = 800, WINDOW_HEIGHT: Int = 600)
  extends Thread {
  val WINDOW_TITLE = "Pacman Mini Map"
  val runnable = new MiniMapRunnable(WINDOW_WIDTH, WINDOW_HEIGHT, WINDOW_TITLE = "Pacman Mini Map")
  val obstacleMap: ObstacleMap = new ObstacleMap
  val messenger: Messenger[ObstacleMap] = Messenger.create(Config.PORT_MAP, map => obstacleMap.merge(map), null)

  override def run = {
    println("start obstacle map messenger on mini map")
    messenger.start()
    println("start opengl window")
    runnable.run
  }

  val testThread:Thread = new Thread {
    val current=this
    override def run(): Unit = {
      println("added random")
      val random = new Random(System.currentTimeMillis())
      Timer.setTimeInterval(obstacleMap.put(new MapUnit(new MapKey(random.nextInt(WINDOW_WIDTH), random.nextInt(WINDOW_HEIGHT)), System.currentTimeMillis())),true,1000)
    }
  }

  override def start={
    super.start
    testThread.start
  }


  class MiniMapRunnable(WINDOW_WIDTH: Int, WINDOW_HEIGHT: Int, WINDOW_TITLE: String, backgroundColors: Array[Float] = Array.fill[Float](4)(0f))
    extends AbstractOpenGLApplication(WINDOW_WIDTH, WINDOW_HEIGHT, WINDOW_TITLE, backgroundColors = backgroundColors) {
    override protected def myInit: Unit = {}

    override protected def keyInvoke(window: Long, key: Int, scanCode: Int, action: Int, mode: Int): Unit = {}

    override protected def reshape: Unit = {}

    override protected def myTick: Unit = {
     // println("mytick")
      obstacleMap.forEach(new BiConsumer[MapKey,Long] {
        override def accept(k: MapKey, v: Long): Unit = {
          println(k.toString+" : "+v)
        }
      })
    }

    override protected def debugInfo: Unit = {}

    override protected def myRender: Unit = {}
  }


}