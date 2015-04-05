package edu.hkcc.pacmanrobot.utils

import java.io.Serializable

/**
 * Created by beenotung on 3/30/15.
 */

class Point3D(var x: Double = 0d, var y: Double = 0d, var z: Double = 0d) extends Cloneable with Serializable {
  def set(x: Double, y: Double, z: Double) = {
    this.x = x
    this.y = y
    this.z = z
  }

  override def clone: Point3D = {
    new Point3D(x, y, z)
  }

  override def toString: String = {
    "[%.2f,%.2f.%.2f]\n".format(x, y, z)
  }


  implicit def +(point3D: Point3D): Point3D = {
    new Point3D(x + point3D.x, y + point3D.y, z + point3D.z)
  }

  implicit def -(point3D: Point3D): Point3D = {
    new Point3D(x - point3D.x, y - point3D.y, z - point3D.z)
  }
  implicit def *(multiple: Double): Point3D = {
    new Point3D(x * multiple, y * multiple, z * multiple)
  }

  implicit def -(d: Double): Point3D = {
    new Point3D(x - d, y - d, z - d)
  }

  implicit def *(multiply: Double): Point3D = {
    new Point3D(x * multiply, y * multiply, z * multiply)
  }

  implicit def /(divider: Double): Point3D = {
    new Point3D(x / divider, y / divider, z / divider)
  }


}