package edu.hkcc.pacmanrobot.robot.utils

import java.io.IOException

import com.pi4j.io.i2c.{I2CBus, I2CDevice, I2CFactory}
import edu.hkcc.pacmanrobot.utils.Maths._
import edu.hkcc.pacmanrobot.utils.maths.Point3D

import scala.math.{cos, sin}

/**
 * Created by beenotung on 3/30/15.
 */
object Mpu6050AO extends Thread {
  private val power_mgmt_1: Int = 0x6b
  private val power_mgmt_2: Int = 0x6c
  private val ONE_SECOND: Long = 1000000000L
  //default +- 2g per second
  // in unit of cm
  private val ACCEL_RATIO: Double = 1.0 / 32768 * 2 * 9.80665 * 100
  //default +- 250 deg per second
  // in unit of rad
  private val GYRO_RATION: Double = 1.0 / 32768 * 250 / 180 * Math.PI
  var linearAcceleration: Point3D = _
  var angularAcceleration: Point3D = _
  var linearVelocity: Point3D = new Point3D()
  var angularVelocity: Point3D = new Point3D()
  var linearDisplacement: Point3D = new Point3D()
  var angularDisplacement: Point3D = new Point3D()
  var ready: Boolean = false
  var bufferRadian: Double = _
  var bufferAngular: Point3D = _
  private var bufferLinear: Point3D = _
  private var bufferLinearAcceleration: Point3D = _
  private var backgroundLinearAcceleration: Point3D = new Point3D()
  private var backgroundAngularAcceleration: Point3D = new Point3D()
  private var TIME0: Long = 0L
  private var bus: I2CBus = null
  private var mpu6050: I2CDevice = null
  private var gyroTimer: Timer = new Timer()
  private var accelTimer: Timer = new Timer()
  private var bufferDouble: Double = .0

  @throws(classOf[IOException])
  def loop {
    writeMpu6050(0x01.toByte)
    readGyro
    readAccel
    writeMpu6050(0xff.toByte)
  }

  def getAcceleration: Point3D = {
    //linearAcceleration.$div(16384.0)
    linearAcceleration / 16384.0
  }

  def getZRotaion: Double = {
    getRotation.z
  }

  def getRotation: Point3D = {
    angularDisplacement
  }

  @throws(classOf[IOException])
  def readAccel {
    bufferLinearAcceleration = new Point3D(readWord2C(0x3b), readWord2C(0x3d), readWord2C(0x3f)) * ACCEL_RATIO - backgroundLinearAcceleration
    val t = accelTimer.getDeltaSecond
    if (accelTimer.last != 0) {
      bufferRadian = getZRotaion
      linearAcceleration.z = bufferLinearAcceleration.z
      linearAcceleration.y = bufferLinearAcceleration.y * cos(bufferRadian) - bufferLinearAcceleration.x * sin(bufferRadian)
      linearAcceleration.x = bufferLinearAcceleration.y * sin(bufferRadian) + bufferLinearAcceleration.x * cos(bufferRadian)
      bufferLinear = linearAcceleration * t
      linearDisplacement += linearVelocity * t + bufferLinear * t * 0.5d
      linearVelocity += bufferLinear
    }
  }

  @throws(classOf[IOException])
  def readGyro = {
    angularAcceleration = new Point3D(readWord2C(0x43), readWord2C(0x45), readWord2C(0x47)) * GYRO_RATION - backgroundLinearAcceleration
    val t = gyroTimer.getDeltaSecond
    if (gyroTimer.last != 0) {
      bufferAngular = angularAcceleration * t
      angularDisplacement += angularVelocity * t + bufferAngular * t * 0.5d
      angularVelocity += bufferAngular
    }
  }

  @throws(classOf[IOException])
  def writeMpu6050(data: Byte) {
    Mpu6050AO.mpu6050.write(0x0d, data)
  }

  @throws(classOf[IOException])
  def readWord(addr: Int): Short = {
    val high: Byte = Mpu6050AO.readByte(addr)
    val low: Byte = Mpu6050AO.readByte(addr + 1)
    ((high << 8) + low).toShort
  }

  @throws(classOf[IOException])
  def readWord2C(addr: Int): Int = {
    val value: Short = readWord(addr)
    if (value >= 0x8000) -((65535 - value) + 1)
    else value
  }

  override def run {
    System.out.println("Mpu6050AO start")
    try {
      setup
      while (true) {
        loop
        Thread.sleep(10)
      }
    }
    catch {
      case e: IOException => {
      }
    }
    System.out.println("Mpu6050AO end")
  }

  private def getXRotation(x: Double, y: Double, z: Double): Double = {
    val rad: Double = Math.atan2(x, length(y, z))
    -Math.toDegrees(rad)
  }

  private def getYRotation(x: Double, y: Double, z: Double): Double = {
    val rad: Double = Math.atan2(y, length(x, z))
    -Math.toDegrees(rad)
  }

  @throws(classOf[IOException])
  private def readByte(addr: Int): Byte = {
    mpu6050.read(addr).toByte
  }

  @throws(classOf[IOException])
  private def setup {
    Mpu6050AO.bus = I2CFactory.getInstance(I2CBus.BUS_1)
    Mpu6050AO.mpu6050 = Mpu6050AO.bus.getDevice(0x68)
    Mpu6050AO.mpu6050.write(Mpu6050AO.power_mgmt_1, 0x00.toByte)
    Mpu6050AO.TIME0 = System.nanoTime
    var TIME1: Long = 0L
    do {
      writeMpu6050(0x01.toByte)
      backgroundLinearAcceleration += new Point3D(readWord2C(0x3b), readWord2C(0x3d), readWord2C(0x3f))
      backgroundAngularAcceleration += new Point3D(readWord2C(0x43), readWord2C(0x45), readWord2C(0x47))
      writeMpu6050(0xff.toByte)
      TIME1 = System.nanoTime()
    } while ((TIME1 - TIME0) < 2147483647)
    backgroundLinearAcceleration /= (TIME1 - TIME0) * GYRO_RATION
    backgroundAngularAcceleration /= (TIME1 - TIME0) * ACCEL_RATIO
    System.out.println("bg_z=" + backgroundAngularAcceleration.z)
    ready = true
  }

  class Timer {
    var last: Long = 0L
    var now: Long = 0L
    var delta: Long = 0L
    var deltaSecond: Double = 0d

    def getDeltaSecond: Double = {
      deltaSecond = getDelta / 1e9d
      deltaSecond
    }

    def getDelta: Long = {
      last = now
      now = System.nanoTime
      delta = now - last
      delta
    }
  }


}