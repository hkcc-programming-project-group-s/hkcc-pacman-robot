package edu.hkcc.pacmanrobot.server

import java.util.concurrent.{ConcurrentHashMap, Semaphore}
import java.util.function.BiConsumer

/**
 * Created by beenotung on 4/5/15.
 */
class ControllerRobotPairManager {
  val semaphore = new Semaphore(1)
  val controllerRobotPairs = new ConcurrentHashMap[Int, Int]()

  def setControllerRobotPair(controllerId: Int, robotId: Int) = {
    semaphore.acquire()
    removeControllerRobotPair(controllerId, robotId)
    controllerRobotPairs.put(controllerId, robotId)
    semaphore.release()
  }

  def removeControllerRobotPair(controllerId: Int, robotId: Int) = {
    removeController(controllerId)
    removeRobot(robotId)
  }

  def removeController(controllerId: Int) = {
    controllerRobotPairs.remove(controllerId)
  }

  def removeRobot(robotId: Int) = {
    controllerRobotPairs.forEach(new BiConsumer[Int, Int] {
      override def accept(key: Int, value: Int): Unit = {
        if (value.equals(robotId))
          controllerRobotPairs.remove(key)
      }
    })
  }

  def getRobotId(controllerId: Int): Int = {
    controllerRobotPairs.get(controllerId)
  }
}

