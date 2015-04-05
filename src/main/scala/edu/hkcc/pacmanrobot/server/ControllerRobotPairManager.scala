package edu.hkcc.pacmanrobot.server

import java.util.concurrent.{ConcurrentHashMap, Semaphore}
import java.util.function.BiConsumer

/**
 * Created by beenotung on 4/5/15.
 */
class ControllerRobotPairManager {
  val semaphore = new Semaphore(1)
  val controllerRobotPairs = new ConcurrentHashMap[Array[Byte], Array[Byte]]()

  def setControllerRobotPair(controllerId: Array[Byte], robotId: Array[Byte]) = {
    semaphore.acquire()
    removeControllerRobotPair(controllerId, robotId)
    controllerRobotPairs.put(controllerId, robotId)
    semaphore.release()
  }

  def removeControllerRobotPair(controllerId: Array[Byte], robotId: Array[Byte]) = {
    removeController(controllerId)
    removeRobot(robotId)
  }

  def removeController(controllerId: Array[Byte]) = {
    controllerRobotPairs.remove(controllerId)
  }

  def removeRobot(robotId: Array[Byte]) = {
    controllerRobotPairs.forEach(new BiConsumer[Array[Byte], Array[Byte]] {
      override def accept(key: Array[Byte], value: Array[Byte]): Unit = {
        if (value.equals(robotId))
          controllerRobotPairs.remove(key)
      }
    })
  }

  def getRobotId(controllerId: Array[Byte]): Array[Byte] = {
    controllerRobotPairs.get(controllerId)
  }
}

