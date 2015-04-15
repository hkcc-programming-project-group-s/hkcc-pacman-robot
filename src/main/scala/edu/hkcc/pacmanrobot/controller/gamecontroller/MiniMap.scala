package edu.hkcc.pacmanrobot.controller.gamecontroller

import java.awt.Color
import java.util.function.BiConsumer

import edu.hkcc.pacmanrobot.utils.map.{MapKey, ObstacleMap}
import edu.hkcc.pacmanrobot.utils.message.messenger.Messenger
import edu.hkcc.pacmanrobot.utils.{Config, Point2D, Utils}
import myutils.gui.opengl.AbstractSimpleOpenGLApplication
import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL11._

import scala.collection.mutable.ArrayBuffer
import scala.util.Random


/**
 * Created by beenotung on 4/8/15.
 */
class MiniMap(WINDOW_WIDTH: Int = 800, WINDOW_HEIGHT: Int = 800)
  extends Thread {
  val WINDOW_TITLE = "Pacman Mini Map"
  val runnable = new MiniMapRunnable(WINDOW_WIDTH, WINDOW_HEIGHT, WINDOW_TITLE = "Pacman Mini Map")
  val obstacleMap: ObstacleMap = new ObstacleMap
  val messenger: Messenger[ObstacleMap] = Messenger.create(Config.PORT_MAP, map => {
    obstacleMap.merge(map)
    updated = true
  }, null)
  val random = new Random(System.currentTimeMillis())

  var updated = false
  var binaryMap: Array[Array[Long]] = null

  override def run = {
    println("start obstacle map messenger on mini map")
    messenger.start()
    println("start opengl window")
    runnable.run
  }

  override def start = {
    super.start
  }


  class MiniMapRunnable(WINDOW_WIDTH: Int, WINDOW_HEIGHT: Int, WINDOW_TITLE: String, backgroundColors: Array[Float] = Array.fill[Float](4)(0f))
    extends AbstractSimpleOpenGLApplication(WINDOW_WIDTH, WINDOW_HEIGHT, WINDOW_TITLE, backgroundColors = backgroundColors) {
    val DEFAULT_OBSTACLE_RADIUS = 10f
    val minPixel = 10
    val obstacles = new ArrayBuffer[OpenglObstacle]()
    val emptyColor = new Color(204, 230, 255)
    val obstacleColor = new Color(0, 0, 0)
    var obstacle_radius = 0.8f
    var range: Point2D[Point2D[Int]] = null
    var x_range: Float = 1f
    var y_range: Float = 1f
    protected var l: Float = 10f
    protected var l2: Float = 1f
    protected var xl: Float = l
    protected var yl: Float = l
    // override protected def keyInvoke(window: Long, key: Int, scanCode: Int, action: Int, mode: Int): Unit = {
    //   super.keyInvoke()
    // }
    protected var zl: Float = l

    def SetXYZRange_=(newValue: Float) {
      xRange = newValue
      yRange = newValue
      zRange = newValue
    }

    def debugXYZ = {
      println("cx=" + cx + ", cy=" + cy + ", cz=" + cz)
      println("cxr=" + cxr + ", ryr=" + cyr + ", czr=" + czr)
    }

    override protected def myInit: Unit = {
      ObstacleMap.estimated_game_duration_in_minutes_=(1d / 6d)
      super.myInit
      scrollSpeed = 1f
      rollSpeed = 10f
      // range = 100f
      // zEquilateral = true
      // isCameraOrtho=false
      zMax = 100f
      zMin = -100f
      cxr = 180f
    }

    override protected def myKeyInvoke(window: Long, key: Int, scanCode: Int, action: Int, mode: Int): Unit = {}

    override protected def reshape: Unit = {}

    override protected def myTick: Unit = {
      super.myTick
    }

    override protected def debugInfo: Unit = {}

    override protected def myRender: Unit = {
      //println("rendering")
      if (!updated) return
      range = Utils.getObstacleMapRange(obstacleMap)
      x_range = range._1._2 - range._1._1
      y_range = range._2._2 - range._2._1
      obstacle_radius = Math.min(
        Math.max(
          0.8f / x_range, 1f / WINDOW_WIDTH * minPixel),
        Math.max(
          0.8f / y_range, 1f / WINDOW_HEIGHT * minPixel
        ))
      val now = System.currentTimeMillis
      var ratio = 1f
      obstacles.clear()
      //  println("here")
      obstacleMap.forEach(new BiConsumer[MapKey, Long] {
        override def accept(k: MapKey, v: Long): Unit = {
          ratio = ObstacleMap.prob(v, now).toFloat
          obstacles += new OpenglObstacle(getXForOpenGL(k.x), getYForOpenGL(k.y), ratio)
        }
      })
      //println("there")
      obstacles.toArray.sorted.foreach(o => render_obstacle(o))
      //  println("rendered")
    }

    def getXForOpenGL(x: Int): Float = {
      ((x - range._1._1) / x_range * 2 - 1) * 0.8f
    }

    def getYForOpenGL(y: Int): Float = {
      ((y - range._2._1) / y_range * 2 - 1) * 0.8f
    }

    def render_obstacle(obstacle: OpenglObstacle): Unit = {
      glColor3f(obstacle.R, obstacle.G, obstacle.B)
      render_obstacle(obstacle.x, obstacle.y, 0, obstacle_radius)
    }

    /**
     *
     * @param cx
     * x-center
     * @param cy
     * y-center
     * @param cz
     * z-center
     * @param r
     * half of side length
     */
    def render_obstacle(cx: Float, cy: Float, cz: Float, r: Float = DEFAULT_OBSTACLE_RADIUS): Unit = {
      glBegin(GL11.GL_QUADS)
      glVertex3f(cx - r, cy - r, cz)
      glVertex3f(cx + r, cy - r, cz)
      glVertex3f(cx + r, cy + r, cz)
      glVertex3f(cx - r, cy + r, cz)
      glEnd()
    }

    class OpenglObstacle(val x: Float, val y: Float, ratio: Float) extends Comparable[OpenglObstacle] {
      val R = obstacleColor.getRed / 256f * ratio + emptyColor.getRed / 256f * (1 - ratio)
      val G = obstacleColor.getGreen / 256f * ratio + emptyColor.getGreen / 256f * (1 - ratio)
      val B = obstacleColor.getBlue / 256f * ratio + emptyColor.getBlue / 256f * (1 - ratio)
      println(ratio)
      println(Vector(R,G,B))
      val luma: Float = (R + R + R + B + G + G + G + G) / 6f
      //println(ratio + "\t" + luma)

      override def compareTo(o: OpenglObstacle): Int = {
        luma.compareTo(o.luma)
      }
    }

  }


}