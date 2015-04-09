package edu.hkcc.pacmanrobot.controller.gamecontroller

import java.util.function.BiConsumer

import edu.hkcc.pacmanrobot.utils.map.{MapKey, MapUnit, ObstacleMap}
import edu.hkcc.pacmanrobot.utils.message.Messenger
import edu.hkcc.pacmanrobot.utils.{Config, Timer}
import myutils.gui.opengl.{AbstractSimpleOpenGLApplication, SimpleOpenGL}
import org.lwjgl.opengl.GL11._

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
  val testThread: Thread = new Thread {
    val current = this

    override def run(): Unit = {
      val random = new Random(System.currentTimeMillis())
      Timer.setTimeInterval(obstacleMap.put(new MapUnit(new MapKey(random.nextInt(WINDOW_WIDTH), random.nextInt(WINDOW_HEIGHT)), System.currentTimeMillis())), true, 1000)
    }
  }

  override def run = {
    println("start obstacle map messenger on mini map")
    messenger.start()
    println("start opengl window")
    runnable.run
  }

  override def start = {
    super.start
    testThread.start
  }


  class MiniMapRunnable(WINDOW_WIDTH: Int, WINDOW_HEIGHT: Int, WINDOW_TITLE: String, backgroundColors: Array[Float] = Array.fill[Float](4)(0f))
    extends AbstractSimpleOpenGLApplication(WINDOW_WIDTH, WINDOW_HEIGHT, WINDOW_TITLE, backgroundColors = backgroundColors) {


    val DEFAULT_OBSTACLE_RADIUS = 10f
    private var _range: Float = DEFAULT_OBSTACLE_RADIUS
    protected var l: Float = 10f
    protected var l2: Float = 1f
    protected var xl: Float = l
    protected var yl: Float = l
    protected var zl: Float = l

    override protected def myInit: Unit = {
      super.myInit
      scrollSpeed = 1f
      //range = 100f
      //zEquilateral=true
      //isCameraOrtho=false
      zMax = 100f
      zMin = 100f
    }

    def range = _range

    def range_=(newValue: Float) {
      _range = newValue
      xRange = newValue
      yRange = newValue
      zRange = newValue
    }


    override protected def keyInvoke(window: Long, key: Int, scanCode: Int, action: Int, mode: Int): Unit = {}

    override protected def myKeyInvoke(window: Long, key: Int, scanCode: Int, action: Int, mode: Int): Unit = {}

    override protected def reshape: Unit = {}

    override protected def myTick: Unit = {
      super.myTick

      /*var count=0
       obstacleMap.forEach(new BiConsumer[MapKey,Long] {
         override def accept(k: MapKey, v: Long): Unit = {
           //println(k.toString+" : "+v)
           count+=1
         }
       })
       println(count)*/
    }

    override protected def debugInfo: Unit = {}

    override protected def myRender: Unit = {
      super.reshape
      var r: Float = 1f
      var g: Float = r * .5f
      var b: Float = r * .5f
      glColor3f(r, g, b)
      obstacleMap.forEach(new BiConsumer[MapKey, Long] {
        override def accept(k: MapKey, v: Long): Unit = {
          //println("render shpere: "+k.toString)
          //SimpleOpenGL.renderSpherePoint(k.x.toFloat, k.y.toFloat, 0f, DEFAULT_OBSTACLE_RADIUS, 10f)
          SimpleOpenGL.renderSpherePoint(0f, 0f, 0f, DEFAULT_OBSTACLE_RADIUS, 10f)
        }
      })
    }
  }

}