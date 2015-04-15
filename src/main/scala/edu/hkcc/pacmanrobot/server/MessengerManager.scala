package edu.hkcc.pacmanrobot.server

import java.net.{ServerSocket, Socket}
import java.util.concurrent.{ConcurrentHashMap, Semaphore}
import java.util.function.BiConsumer

import edu.hkcc.pacmanrobot.utils.Worker
import edu.hkcc.pacmanrobot.utils.message.messenger.Messenger

/**
 * Created by beenotung on 4/5/15.
 */
class MessengerManager[Type](val servicePort: Int, autoGet_func: (Array[Byte], Type) => Unit)
  extends Thread {

  val messengers = new ConcurrentHashMap[Array[Byte], Messenger[Type]]()
  val semaphore = new Semaphore(1)
  var serverSocket = new ServerSocket(servicePort)

  override def run(): Unit = {
    //    Timer.setTimeInterval({
    //      foreach(m => {
    //        println("checking m: " + m.toString)
    //        if (m.socket.isClosed || !m.socket.isConnected && m.messengerManager != null) {
    //          println("removing lost subscriber from: " + m.socket.getRemoteSocketAddress + "(" + m.port + ")")
    //          remove(m)
    //        }
    //      })
    //    }, isAlive, 1000)
    while (true) {
      println("waiting incoming request from port: " + servicePort)
      genMessenger(serverSocket.accept())
    }
  }

  def genMessenger(socket: Socket) = {
    val newMessenger = new Messenger[Type](socket, servicePort, this) {
      override def autoGet(message: Type): Unit = {
        autoGet_func(getRemoteMacAddress, message)
      }
    }
    println("client connected: " + newMessenger.socket.getInetAddress.getHostAddress + ":" + servicePort)
    newMessenger.start
    add(newMessenger)
  }

  def add(newMessenger: Messenger[Type]) = {
    messengers.put(newMessenger.getRemoteMacAddress, newMessenger)
  }

  def remove(removeTarget: Messenger[Type]) = {
    Worker.forkAndStart({
      semaphore.acquire()
      println("removing client: " + removeTarget.socket.getInetAddress.getHostAddress + ":" + removeTarget.socket.getPort + "(" + removeTarget.port + ")")
      val key = getKey(removeTarget)
      if (key != null) {
        messengers.remove(key)
        removeTarget.stopThread
      }
      semaphore.release()
    })
  }

  def getKey(messenger: Messenger[Type]): Array[Byte] = {
    var key: Array[Byte] = null
    messengers.forEach(new BiConsumer[Array[Byte], Messenger[Type]] {
      override def accept(k: Array[Byte], v: Messenger[Type]): Unit = {
        if (messenger.equals(v))
          key = k
      }
    })
    key
  }

  def foreach(op: Messenger[Type] => Unit) = {
    messengers.forEach(new BiConsumer[Array[Byte], Messenger[Type]] {
      override def accept(macAddress: Array[Byte], messenger: Messenger[Type]): Unit = {
        op(messenger)
      }
    })
  }

  def sendByMacAddress(macAddress: Array[Byte], message: Type) = {
    if (macAddress != null)
      if (messengers.containsKey(macAddress))
        messengers.get(macAddress).sendMessage(message)
  }
}
