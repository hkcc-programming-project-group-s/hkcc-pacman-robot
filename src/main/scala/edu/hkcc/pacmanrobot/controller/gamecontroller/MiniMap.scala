package edu.hkcc.pacmanrobot.controller.gamecontroller

import edu.hkcc.pacmanrobot.utils.map.{MapKey, MapUnit, ObstacleMap}
import edu.hkcc.pacmanrobot.utils.message.Messenger
import edu.hkcc.pacmanrobot.utils.{Config, Timer}
import myutils.gui.opengl.AbstractSimpleOpenGLApplication
import org.lwjgl.opengl.GL11._

import scala.collection.parallel.mutable.ParArray
import scala.util.Random

/**
 * Created by beenotung on 4/8/15.
 */
class MiniMap(WINDOW_WIDTH: Int = 800, WINDOW_HEIGHT: Int = 600)
  extends Thread {
  val WINDOW_TITLE = "Pacman Mini Map"
  val runnable = new MiniMapRunnable(WINDOW_WIDTH, WINDOW_HEIGHT, WINDOW_TITLE = "Pacman Mini Map")
  val obstacleMap: ObstacleMap = new ObstacleMap
  val messenger: Messenger[ObstacleMap] = Messenger.create(Config.PORT_MAP, map => {
    obstacleMap.merge(map)
    updated = true
  }, null)
  val testThread: Thread = new Thread {
    val current = this
    override def run(): Unit = {
      val random = new Random(System.currentTimeMillis())
      Timer.setTimeInterval({
        //println("random put")
        obstacleMap.put(new MapUnit(new MapKey(random.nextInt(WINDOW_WIDTH), random.nextInt(WINDOW_HEIGHT)), System.currentTimeMillis()))
        updated = true
      }, true, 100)
    }
  }
  var updated = false
  var binaryMap: ParArray[ParArray[Boolean]] = null

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
    protected var l: Float = 10f
    protected var l2: Float = 1f
    protected var xl: Float = l
    protected var yl: Float = l
    protected var zl: Float = l
    private var _range: Float = DEFAULT_OBSTACLE_RADIUS

    def range = _range

    //    override protected def keyInvoke(window: Long, key: Int, scanCode: Int, action: Int, mode: Int): Unit = {
    //      super.keyInvoke()
    //    }

    def range_=(newValue: Float) {
      _range = newValue
      xRange = newValue
      yRange = newValue
      zRange = newValue
    }

    override protected def myInit: Unit = {
      super.myInit
      scrollSpeed = 1f
      //range = 100f
      zEquilateral = true
      //isCameraOrtho=false
      zMax = -10f
      zMin = 100f
      cxr=180f
    }

    override protected def myKeyInvoke(window: Long, key: Int, scanCode: Int, action: Int, mode: Int): Unit = {}

    override protected def reshape: Unit = {}

    override protected def myTick: Unit = {
      super.myTick
      //println("check tick")
      if (!updated) return
      //println("tick")
      binaryMap = obstacleMap.to2DParArrayBoolean
      //range = DEFAULT_OBSTACLE_RADIUS * Math.max(binaryMap.length, binaryMap(0).length)
      range = Math.max(binaryMap.length, binaryMap(0).length) / 2f
      cx= range /2f
      cy= - range /2f
    }

    override protected def debugInfo: Unit = {}

    override protected def myRender: Unit = {
      super.reshape
      //println("check render")
      if (binaryMap == null) return
      //println("render")
      val r: Float = 1f
      val g: Float = r * .5f
      val b: Float = r * .5f
      glColor3f(r, g, b)
      //glBegin(GL_POINT)
      Range(0, binaryMap.length).foreach(x => Range(0, binaryMap(x).length).foreach(y =>
        if (binaryMap(x)(y)) {
          //println("drawing sphere: "+x+", "+y)
          //renderSpherePoint(x, y, 0f, DEFAULT_OBSTACLE_RADIUS, 10f)
          //glVertex3f(x,y,0)
        }
      ))
      //glEnd()

      glColor3f(r, g, b)
      renderSphereLine(0f, 0f, 0f, DEFAULT_OBSTACLE_RADIUS, 10f)
      var edge:Float=range
      edge*=0.5f
      glColor3f( g, b,r)
      renderSphereLine(edge,edge, 0f, DEFAULT_OBSTACLE_RADIUS, 10f)
      println("range is "+edge)
    }
  }

}