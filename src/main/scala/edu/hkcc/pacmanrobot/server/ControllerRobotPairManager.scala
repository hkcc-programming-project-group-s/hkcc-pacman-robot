package edu.hkcc.pacmanrobot.server

import java.util.concurrent.{ConcurrentHashMap, Semaphore}
import java.util.function.BiConsumer

/**
 * Created by beenotung on 4/5/15.
 */
class ControllerRobotPairManager {
  val semaphore = new Semaphore(1)
  /**
   * key : controller
   * value : student robot
   */
  val controllerRobotPairs = new ConcurrentHashMap[Array[Byte], Array[Byte]]()

  def setControllerRobotPair(controller_macAddress: Array[Byte], robot_macAddress: Array[Byte]) = {
    semaphore.acquire()
    removeControllerRobotPair(controller_macAddress, robot_macAddress)
    controllerRobotPairs.put(controller_macAddress, robot_macAddress)
    semaphore.release()
  }

  def removeControllerRobotPair(controller_macAddress: Array[Byte], robot_macAddress: Array[Byte]) = {
    removeController(controller_macAddress)
    removeRobot(robot_macAddress)
  }

  def removeController(controllerId: Array[Byte]) = {
    controllerRobotPairs.remove(controllerId)
  }

  def removeRobot(robot_macAddress: Array[Byte]) = {
    controllerRobotPairs.forEach(new BiConsumer[Array[Byte], Array[Byte]] {
      override def accept(key: Array[Byte], value: Array[Byte]): Unit = {
        if (value.equals(robot_macAddress))
          controllerRobotPairs.remove(key)
      }
    })
  }

  def getRobot_macAddress(controller_macAddress: Array[Byte]): Array[Byte] = {
    controllerRobotPairs.get(controller_macAddress)
  }
}

