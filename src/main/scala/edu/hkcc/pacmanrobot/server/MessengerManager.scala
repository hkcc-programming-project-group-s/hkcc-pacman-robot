package edu.hkcc.pacmanrobot.server

import java.net.ServerSocket
import java.util.concurrent.Semaphore

import edu.hkcc.pacmanrobot.utils.{Worker, Timer}
import edu.hkcc.pacmanrobot.utils.message.Messenger

import scala.collection.mutable.ArrayBuffer
import scala.collection.parallel.mutable.ParArray

/**
 * Created by beenotung on 4/5/15.
 */
class MessengerManager[Type](val servicePort: Int, autoGet_func: (Array[Byte], Type) => Unit)
  extends Thread {
  val semaphore = new Semaphore(1)
  var messengers = ParArray.empty[Messenger[Type]]
  var serverSocket = new ServerSocket(servicePort)

  override def run(): Unit = {
    Timer.setTimeInterval({messengers.foreach(m=>
      if(m.socket.isClosed|| !m.socket.isConnected && m.messengerManager!=null)
      {
        println("removing lost subscriber from: "+m.socket.getRemoteSocketAddress+"("+m.port+")")
        remove(m)
      }
    )},isAlive,1000)
    while (true) {
      println("waiting incoming request from port: " + servicePort)
      val newMessenger = new Messenger[Type](serverSocket.accept(), servicePort, this) {
        override def autoGet(message: Type): Unit = {
          autoGet_func(getRemoteMacAddress, message)
        }
      }
      println("client connected: " + newMessenger.socket.getInetAddress.getHostAddress + ":" + servicePort)
      newMessenger.start
      add(newMessenger)
    }
  }

  def add(newMessenger: Messenger[Type]) = {
    semaphore.acquire()
    messengers :+= newMessenger
    semaphore.release()
  }

  def remove(removeTarget: Messenger[Type]) = {
    Worker.forkAndStart({
      println("removeing client: " + removeTarget.socket.getInetAddress.getHostAddress + ":" +removeTarget.socket.getPort +"("+removeTarget.port+")")
      removeTarget.stopThread
      semaphore.acquire()
      val newMessengers = new ArrayBuffer[Messenger[Type]]
      messengers.toArray.foreach(messenger => if (!removeTarget.equals(messenger)) newMessengers += messenger)
      messengers = newMessengers.toParArray
      semaphore.release()
    })
  }
}
