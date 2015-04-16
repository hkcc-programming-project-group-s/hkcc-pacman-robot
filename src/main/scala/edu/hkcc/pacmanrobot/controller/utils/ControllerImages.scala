package edu.hkcc.pacmanrobot.controller.utils

import java.io.IOException
import java.net.MalformedURLException
import javax.swing._

import edu.hkcc.pacmanrobot.utils.message.DeviceInfo

/**
 * Created by 13058536A on 4/8/2015.
 */
object ControllerImages {
  val ROBOT_UNCLASSED: String = "https://dl.dropboxusercontent.com/u/13757442/htm/robocode-tank.png"
  val CONTROLLER: String = "https://dl.dropboxusercontent.com/u/13757442/htm/controller.png"
  val ROBOT_STUDENT: String = "https://dl.dropboxusercontent.com/u/13757442/htm/student.png"
  val ROBOT_DEADLINE: String = "https://dl.dropboxusercontent.com/u/13757442/htm/deadline.png"
  val ROBOT_ASSIGNMENT: String = "https://dl.dropboxusercontent.com/u/13757442/htm/assignment.png"
  var ICON_CONTROLLER: ImageIcon = null
  var ICON_ROBOT_UNCLASSED: ImageIcon = null

  @throws(classOf[MalformedURLException])
  @throws(classOf[IOException])
  def getIconByDeviceType(deviceType: Byte): ImageIcon = {
    deviceType match {
      case DeviceInfo.DEVICE_TYPE_UNCLASSED_ROBOT =>
        if (ICON_ROBOT_UNCLASSED == null) ICON_ROBOT_UNCLASSED = Utils.getImageIcon(ROBOT_UNCLASSED)
        return ICON_ROBOT_UNCLASSED
      case DeviceInfo.DEVICE_TYPE_CONTROLLER =>
        if (ICON_CONTROLLER == null) ICON_CONTROLLER = Utils.getImageIcon(CONTROLLER)
        return ICON_CONTROLLER
      case DeviceInfo.DEVICE_TYPE_ASSIGNMENT_ROBOT =>
        if (ICON_CONTROLLER == null) ICON_CONTROLLER = Utils.getImageIcon(ROBOT_ASSIGNMENT)
        return ICON_CONTROLLER
      case DeviceInfo.DEVICE_TYPE_DEADLINE_ROBOT =>
        if (ICON_CONTROLLER == null) ICON_CONTROLLER = Utils.getImageIcon(ROBOT_DEADLINE)
        return ICON_CONTROLLER
      case DeviceInfo.DEVICE_TYPE_STUDENT_ROBOT =>
        if (ICON_CONTROLLER == null) ICON_CONTROLLER = Utils.getImageIcon(ROBOT_STUDENT)
        return ICON_CONTROLLER
      case _ =>
        return null
    }
  }
}

