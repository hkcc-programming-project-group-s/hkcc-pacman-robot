package edu.hkcc.pacmanrobot.controller.gamecontroller

import java.util.concurrent.ConcurrentHashMap
import java.util.function.BiConsumer

import edu.hkcc.pacmanrobot.server.network.Server_NetworkThread
import edu.hkcc.pacmanrobot.utils.message.{DeviceInfo, Position}

/**
 * Created by beenotung on 4/29/15.
 */
object MiniMapSAO {
  //def map=Server_NetworkThread.getInstance().obstacleMapManager.m
  /*def map :ObstacleMap= {
    val result=    ObstacleMapManager.obstacleMap
    //Debug.getInstance().printMessage("obstacleMap size: "+result.size())
    result
  }*/

  def positions: ConcurrentHashMap[DeviceInfo, Position] = {
    val result = new ConcurrentHashMap[DeviceInfo, Position]()
    val manager = Server_NetworkThread.getInstance().deviceInfoManager
    Server_NetworkThread.getInstance().robotPositions.forEach(new BiConsumer[Array[Byte], Position] {
      override def accept(k: Array[Byte], v: Position): Unit = {
        result.put(manager.getDeviceInfoByMacAddress(k), v)
      }
    })
    result
  }
}
