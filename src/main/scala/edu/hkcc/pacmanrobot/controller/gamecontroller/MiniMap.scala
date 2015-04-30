package edu.hkcc.pacmanrobot.controller.gamecontroller

import java.awt.Color
import java.util
import java.util.function.BiConsumer

import edu.hkcc.pacmanrobot.controller.gamecontroller.MiniMap._
import edu.hkcc.pacmanrobot.server.network.ObstacleMapManager
import edu.hkcc.pacmanrobot.utils.map.{MapKey, ObstacleMap}

//import edu.hkcc.pacmanrobot.utils.message.messenger.{Messenger, ObstacleMapMessenger}

import edu.hkcc.pacmanrobot.server.network.ObstacleMapManager.obstacleMap
import edu.hkcc.pacmanrobot.utils.message.{DeviceInfo, Position}
import edu.hkcc.pacmanrobot.utils.{Point2D, Utils, Worker}
import myutils.gui.opengl.AbstractSimpleOpenGLApplication
import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL11.{glColor3f, _}

import scala.collection.JavaConverters._
import scala.collection.mutable.ArrayBuffer
import scala.util.Random

/**
 * Created by beenotung on 4/8/15.
 * this class is a logical singleton
 */
object MiniMap {
  val emptyColor = new Color(204, 230, 255)
  val obstacleColor = new Color(0, 0, 0)
  val deadlineColor = new Color(255, 255, 0)
  val studentColor = new Color(0, 255, 0)
  val assignmentColor = new Color(255, 0, 0)
  var instance: MiniMap = null
}

class MiniMap(WINDOW_WIDTH: Int = 800, WINDOW_HEIGHT: Int = 800)
  extends Thread {
  instance = this
  val WINDOW_TITLE = "Pacman Mini Map"
  val runnable = new MiniMapRunnable(WINDOW_WIDTH, WINDOW_HEIGHT, WINDOW_TITLE = "Pacman Mini Map")
  //val obstacleMap: ObstacleMap = new ObstacleMap
  /*val positionMessenger = Messenger.create[RobotPosition](Config.PORT_ROBOT_POSITION, message => {
    positions.put(message.deviceInfo, message.position)
  }
    , null)*/
  /*val positions = new ConcurrentHashMap[DeviceInfo, Position]()
  val mapMessenger = new ObstacleMapMessenger() {
    override def autoGet(message: ObstacleMap): Unit = {
      super.autoGet(message)
      updated = true
      //println("received")
    }
  }*/
  //  val messenger: Messenger[ObstacleMap] = Messenger.create(Config.PORT_MAP, map => {
  //    obstacleMap.merge(map)
  //    updated = true
  //  }, null)
  val random = new Random(System.currentTimeMillis())

  var lastUpdateTime: Long = System.currentTimeMillis()
  var binaryMap: Array[Array[Long]] = null
  var running = false

  def updated: Boolean = {
    lastUpdateTime >= ObstacleMapManager.lastUpdateTime
  }

  def update: Unit = {
    lastUpdateTime = ObstacleMapManager.lastUpdateTime
  }

  override def run = {
    //println("start obstacle map messenger on mini map")
    //mapMessenger.start()
    running = true
    println("start opengl window")
    //runnable.run
    Worker.forkAndStart(
      runnable.run()
    )
  }

  override def start = {
    if (!running)
      super.start
  }


  class MiniMapRunnable(WINDOW_WIDTH: Int, WINDOW_HEIGHT: Int, WINDOW_TITLE: String,
                        backgroundColors: Array[Float] = Array(emptyColor.getRed / 256f, emptyColor.getGreen / 256f, emptyColor.getBlue / 256f, 0))
    extends AbstractSimpleOpenGLApplication(WINDOW_WIDTH, WINDOW_HEIGHT, WINDOW_TITLE, backgroundColors = backgroundColors) {
    val DEFAULT_OBSTACLE_RADIUS = 10f
    val minPixel = 10
    val obstacles = new ArrayBuffer[OpenglObstacle]()
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
      //val obstacleMap = mapMessenger.getMap
      if (!updated) return
      update
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

      //var ratio:Float = 1

      obstacles.clear()

      // generate and render OpenGL objects according to the obstacles location and last discoved time
      obstacleMap.forEach(new BiConsumer[MapKey, Long] {
        override def accept(k: MapKey, v: Long): Unit = {
          val ratio: Float = ObstacleMap.prob(v, now).toFloat
          //Debug.getInstance().printMessage(ratio.toString)
          obstacles += new OpenglObstacle(getXForOpenGL(k.x), getYForOpenGL(k.y), ratio)
        }
      })
      /*
      obstacles.sorted
      the above method might lead to JVM level fatal error, therefore deprecated in this case
       */
      // convert the scala collection to java collection, then use java API to sort the elements
      val javaCollection: java.util.Collection[OpenglObstacle] = obstacles.toVector.asJavaCollection
      util.Arrays.sort(javaCollection.toArray())
      javaCollection.toArray.foreach(u => render_obstacle(u.asInstanceOf[OpenglObstacle]))

      // generate and render OpenGL objects according to the robots location
      val positions = MiniMapSAO.positions
      positions.forEach(new BiConsumer[DeviceInfo, Position] {
        override def accept(t: DeviceInfo, u: Position): Unit = {
          t.deviceType match {
            case DeviceInfo.DEVICE_TYPE_STUDENT_ROBOT => render_robot(u.x, u.y, studentColor)
            case DeviceInfo.DEVICE_TYPE_ASSIGNMENT_ROBOT => render_robot(u.x, u.y, assignmentColor)
            case DeviceInfo.DEVICE_TYPE_DEADLINE_ROBOT => render_robot(u.x, u.y, deadlineColor)
          }
        }
      })
    }

    def render_robot(x: Int, y: Int, color: Color) = {
      glColor3f(color.getRed / 256f, color.getGreen / 256f, color.getBlue / 256f)
      render_square(getXForOpenGL(x), getYForOpenGL(y), 0, obstacle_radius * 2)
    }

    def getXForOpenGL(x: Int): Float = {
      ((x - range._1._1) / x_range * 2 - 1) * 0.8f
    }

    def getYForOpenGL(y: Int): Float = {
      ((y - range._2._1) / y_range * 2 - 1) * 0.8f
    }

    def render_obstacle(obstacle: OpenglObstacle): Unit = {
      //Debug.getInstance().printMessage("render obstacle: x= " + obstacle.x + "\ty= " + obstacle.y)
      glColor3f(obstacle.R, obstacle.G, obstacle.B)
      render_square(obstacle.x, obstacle.y, 0, obstacle_radius)
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
    def render_square(cx: Float, cy: Float, cz: Float, r: Float): Unit = {
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
      val luma: Float = (R + R + R + B + G + G + G + G) / 6f

      override def compareTo(o: OpenglObstacle): Int = {
        o.luma.compareTo(luma)
        //luma.compareTo(o.luma)
      }
    }

  }


}