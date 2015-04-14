package edu.hkcc.pacmanrobot.server

import java.net.ServerSocket
import java.util.concurrent.{ConcurrentHashMap, Semaphore}
import java.util.function.BiConsumer

import edu.hkcc.pacmanrobot.utils.message.Messenger
import edu.hkcc.pacmanrobot.utils.{Timer, Worker}

import scala.collection.JavaConverters._
import scala.collection.immutable.HashMap

/**
 * Created by beenotung on 4/5/15.
 */
class MessengerManager[Type](val servicePort: Int, autoGet_func: (Array[Byte], Type) => Unit)
  extends Thread {
  val semaphore = new Semaphore(1)
  var messengers = new ConcurrentHashMap[Messenger[Type], Messenger[Type]]()
  var serverSocket = new ServerSocket(servicePort)

  override def run(): Unit = {
    Timer.setTimeInterval({
      foreach(m => {
        if (m.socket.isClosed || !m.socket.isConnected && m.messengerManager != null) {
          println("removing lost subscriber from: " + m.socket.getRemoteSocketAddress + "(" + m.port + ")")
          remove(m)
        }
      })
    }, isAlive, 1000)
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
    messengers.put(newMessenger, newMessenger)
    semaphore.release()
  }

  def remove(removeTarget: Messenger[Type]) = {
    Worker.forkAndStart({
      semaphore.acquire()
      if(messengers.containsKey(removeTarget)) {
        println("removeing client: " + removeTarget.socket.getInetAddress.getHostAddress + ":" + removeTarget.socket.getPort + "(" + removeTarget.port + ")")
        removeTarget.stopThread
        messengers.remove(removeTarget)
      }
      semaphore.release()
    })
  }

  def foreach(op: Messenger[Type] => Unit) = {
    messengers.forEach(new BiConsumer[Messenger[Type], Messenger[Type]] {
      override def accept(t: Messenger[Type], u: Messenger[Type]): Unit = {
        op(t)
      }
    })
  }
}
