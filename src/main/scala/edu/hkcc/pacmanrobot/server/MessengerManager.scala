package edu.hkcc.pacmanrobot.server

import java.net.{ServerSocket, Socket}
import java.util.concurrent.Semaphore

import edu.hkcc.pacmanrobot.utils.message.messenger.Messenger
import edu.hkcc.pacmanrobot.utils.{Timer, Worker}

import scala.collection.mutable.ArrayBuffer

/**
 * Created by beenotung on 4/5/15.
 */
class MessengerManager[Type](val servicePort: Int, autoGet_func: (Array[Byte], Type) => Unit)
  extends Thread {
  val semaphore = new Semaphore(1)

  val _messengers = new ArrayBuffer[Messenger[Type]]
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
    semaphore.acquire()
    messengers :+ newMessenger
    semaphore.release()
  }

  def messengers = _messengers

  def remove(removeTarget: Messenger[Type]) = {
    Worker.forkAndStart({
      semaphore.acquire()
      println("removing client: " + removeTarget.socket.getInetAddress.getHostAddress + ":" + removeTarget.socket.getPort + "(" + removeTarget.port + ")")
      removeTarget.stopThread
      messengers -= removeTarget
      semaphore.release()
    })
  }

  def foreach(op: Messenger[Type] => Unit) = {
    messengers.foreach(messenger => op(messenger))
  }

  def sendByMacAddress(macAddress: Array[Byte], message: Type) = {
    if (macAddress != null)
      messengers.filter(m => macAddress.equals(m.getRemoteMacAddress)).foreach(m =>
        m.sendMessage(message))
  }
}
