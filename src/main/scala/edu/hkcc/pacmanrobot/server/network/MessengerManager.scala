package edu.hkcc.pacmanrobot.server.network

import java.net.{BindException, ServerSocket, Socket}
import java.util.concurrent.{ConcurrentHashMap, Semaphore}
import java.util.function.BiConsumer

import edu.hkcc.pacmanrobot.debug.Debug
import edu.hkcc.pacmanrobot.utils.Worker
import edu.hkcc.pacmanrobot.utils.message.messenger.Messenger

/**
 * Created by beenotung on 4/5/15.
 */
object MessengerManager {
  def nothing[Type](messenger: Messenger[Type]): Unit = {}
}

@throws(classOf[BindException])
class MessengerManager[Type](val servicePort: Int, initMessenger_func: (Messenger[Type] => Unit), autoGet_func: ((Array[Byte], Type) => Unit))
  extends Thread {
  Debug.getInstance().printMessage("MessengerManager [" + servicePort + "] init 0%")
  val messengers = new ConcurrentHashMap[Array[Byte], Messenger[Type]]()
  Debug.getInstance().printMessage("MessengerManager [" + servicePort + "] init 20%")
  val semaphore = new Semaphore(1)
  Debug.getInstance().printMessage("MessengerManager [" + servicePort + "] init 30%")
  var serverSocket = new ServerSocket(servicePort)
  Debug.getInstance().printMessage("MessengerManager [" + servicePort + "] init 50%")
  var running = false

  Debug.getInstance().printMessage("MessengerManager [" + servicePort + "] init 55%")

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
    running = true
    while (running) {
      println("waiting incoming request from port: " + servicePort)
      genMessenger(serverSocket.accept())
    }
  }

  Debug.getInstance().printMessage("MessengerManager [" + servicePort + "] init 60%")

  def genMessenger(socket: Socket) = {
    val newMessenger = new Messenger[Type](socket, servicePort, this) {
      override def autoGet(message: Type): Unit = {
        autoGet_func(getRemoteMacAddress, message)
      }
    }
    println("client connected: " + newMessenger.socket.getInetAddress.getHostAddress + ":" + servicePort)
    newMessenger.start
    add(newMessenger)
    initMessenger_func(newMessenger)
  }

  Debug.getInstance().printMessage("MessengerManager [" + servicePort + "] init 65%")

  def add(newMessenger: Messenger[Type]) = {
    messengers.put(newMessenger.getRemoteMacAddress, newMessenger)
  }

  Debug.getInstance().printMessage("MessengerManager [" + servicePort + "] init 70%")

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

  Debug.getInstance().printMessage("MessengerManager [" + servicePort + "] init 75%")

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

  Debug.getInstance().printMessage("MessengerManager [" + servicePort + "] init 80%")

  def sendByMacAddress(macAddress: Array[Byte], message: Type) = {
    if (macAddress != null)
      if (messengers.containsKey(macAddress))
        messengers.get(macAddress).sendMessage(message)
  }

  Debug.getInstance().printMessage("MessengerManager [" + servicePort + "] init 85%")

  def sendToAll(message: Type) = {
    foreach(op = {
      messenger => messenger.sendMessage(message)
    })
  }

  Debug.getInstance().printMessage("MessengerManager [" + servicePort + "] init 90%")

  def foreach(op: Messenger[Type] => Unit) = {
    messengers.forEach(new BiConsumer[Array[Byte], Messenger[Type]] {
      override def accept(macAddress: Array[Byte], messenger: Messenger[Type]): Unit = {
        op(messenger)
      }
    })
  }

  Debug.getInstance().printMessage("MessengerManager [" + servicePort + "] init 95%")

  override def start() = {
    if (!running)
      super.start()
  }

  Debug.getInstance().printMessage("MessengerManager [" + servicePort + "] init 100%")
}

