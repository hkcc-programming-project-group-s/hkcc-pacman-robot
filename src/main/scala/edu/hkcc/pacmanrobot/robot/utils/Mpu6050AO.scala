package edu.hkcc.pacmanrobot.robot.utils

import java.io.IOException

import com.pi4j.io.i2c.{I2CBus, I2CDevice, I2CFactory}
import edu.hkcc.pacmanrobot.utils.{Point3D, Maths}

/**
 * Created by beenotung on 3/30/15.
 */
object Mpu6050AO extends Thread {
  private val power_mgmt_1: Int = 0x6b
  private val power_mgmt_2: Int = 0x6c
  private val ONE_SECOND: Long = 1000000000L
  //default +- 2g per second
  private val ACCEL_RATIO = 1.0 / (32768 / 2 / 9.80665 * 100)
  // in unit of cm
  //default +- 250 deg per second
  private val GYRO_RATION = 1.0 / (32768 / 250 / 180 * Math.PI)
  // in unit of rad
  var ready: Boolean = false
  private var bg_z: Double = 0
  private var acceleration: Point3D = new Point3D()
  private var angularAcceleration: Point3D = new Point3D()
  private var rotation: Point3D = new Point3D()
  private var displacement: Point3D = new Point3D()
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
    acceleration * ACCEL_RATIO
  }

  def getZRotaion: Double = {
    getRotation.z - (bg_z * GYRO_RATION)
  }

  def getRotation: Point3D = {
    rotation * GYRO_RATION
  }

  @throws(classOf[IOException])
  def readAccel {
    acceleration = new Point3D(readWord2C(0x3b), readWord2C(0x3d), readWord2C(0x3f))
    accelTimer.getDelta
    if (accelTimer.last != 0) {
      acceleration.y = y * cos(radian) - x * sin(radian);
      acceleration.x = y * sin(radian) + x * cos(radian);
      displacement += acceleration
    }
  }

  @throws(classOf[IOException])
  def readGyro {
    angularAcceleration = new Point3D(readWord2C(0x43), readWord2C(0x45), readWord2C(0x47))
    gyroTimer.getDelta
    if (gyroTimer.last != 0) {
      rotation += angularAcceleration
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
    val rad: Double = Math.atan2(x, Maths.length(y, z))
    -Math.toDegrees(rad)
  }

  private def getYRotation(x: Double, y: Double, z: Double): Double = {
    val rad: Double = Math.atan2(y, Maths.length(x, z))
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
    val last_time: Long = 0
    val now: Long = 0L
    Mpu6050AO.TIME0 = System.nanoTime
    var TIME1: Long = 0L
    while (({
      TIME1 = System.nanoTime;
      TIME1
    } - Mpu6050AO.TIME0) < 2147483647) {
      writeMpu6050(0x01.toByte)
      Mpu6050AO.bg_z += 1.0 * readWord2C(0x47)
      writeMpu6050(0xff.toByte)
    }
    Mpu6050AO.bg_z /= 1.0 * TIME1 - Mpu6050AO.TIME0
    System.out.println("bg_z=" + Mpu6050AO.bg_z)
    ready = true
  }

  private def real(shiftedValue: Double, time1: Long): Double = {
    shiftedValue - Mpu6050AO.bg_z * (time1 - Mpu6050AO.TIME0)
  }

  private class Timer {
    var last: Long = 0
    var now: Long = 0
    var delta: Long = 0L

    def getDelta: Long = {
      last = now
      now = System.nanoTime
      delta = now - last
      delta
    }
  }

}
