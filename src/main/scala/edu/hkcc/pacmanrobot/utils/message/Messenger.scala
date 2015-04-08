package edu.hkcc.pacmanrobot.utils.message

import java.io.{ObjectInputStream, ObjectOutputStream}
import java.net._
import java.rmi.server.ServerNotActiveException
import java.util.concurrent.{ConcurrentLinkedQueue, Semaphore}

import edu.hkcc.pacmanrobot.server.MessengerManager
import edu.hkcc.pacmanrobot.utils.Config
import edu.hkcc.pacmanrobot.utils.exception.ClientSocketClosedException


/**
 * Created by beenotung on 2/10/15.
 */

object Messenger {
  def create[Type](port: Int, autoGetFunc: (Type) => Unit = (message: Type) => {}, messengerManager: MessengerManager[Type]): Messenger[Type] = {
    println("creating messenger on port: " + port)
    val messenger = new Messenger[Type](port, messengerManager) {
      override def autoGet(message: Type): Unit = {
        autoGetFunc(message)
      }
    }
    println("created messenger on port: " + port)
    messenger
  }


  @throws(classOf[ServerNotActiveException])
  def connect(port: Int): Socket = {
    var socket: Socket = new Socket()
    try {
      println("try to connect to " + Config.serverAddress + ":" + port)
      socket = new Socket(Config.serverAddress, port)
    }
    catch {
      case e: ConnectException => {
        throw new ServerNotActiveException("Server is not available")
      }
    }
    println("connected to " + Config.serverAddress + ":" + port)
    socket
  }
}

abstract class Messenger[Type](var socket: Socket, val port: Int, val messengerManager: MessengerManager[Type],
                               val SEND_INTERVAL: Long = 50, val GET_INTERVAL: Long = 50)
  extends Thread {
  val currentMessenger = this
  var inputStream: ObjectInputStream = null
  var outputStream: ObjectOutputStream = null
  val inputThread: Thread = new Thread(new Runnable {

    override def run: Unit = {
      while (true) {
        receiveMessage
      }
    }
  })
  val outputThread: Thread = new Thread(new Runnable {
    override def run = {
      while (true) {
        sendMessage
        Thread.sleep(SEND_INTERVAL)
      }
    }
  })

  val outputQueue: ConcurrentLinkedQueue[Type] = new ConcurrentLinkedQueue[Type]
  val inputQueue: ConcurrentLinkedQueue[Type] = new ConcurrentLinkedQueue[Type]
  var active: Boolean = false

  @throws(classOf[ClientSocketClosedException[Type]])
  def reconnect: Unit = {
    try
      inputStream.close()
    catch {
      case e: Exception => {}
    }
    try
      outputStream.close()
    catch {
      case e: Exception => {}
    }
    try
      socket.close()
    catch {
      case e: Exception => {}
    }
    do {
      try
        socket = Messenger.connect(socket.getPort)
      catch {
        case e: ServerNotActiveException =>
          if (messengerManager != null)
            throw e
      }
    } while (!socket.isConnected || socket.isClosed)
    inputStream = new ObjectInputStream(socket.getInputStream)
    outputStream = new ObjectOutputStream(socket.getOutputStream)
  }

  def this(port: Int, messengerManager: MessengerManager[Type]) = {
    this(Messenger.connect(port), port, messengerManager)
  }

  val socketSemaphore: Semaphore = new Semaphore(1)

  def checkConnection: Unit = {
    println("check connection on port: " + port)
    socketSemaphore.tryAcquire()
    try {
      if (socket.isClosed)
        throw new SocketException("socket is closed (exception) on port: " + port)
      if (inputStream == null)
        inputStream = new ObjectInputStream(socket.getInputStream)
      if (outputStream == null)
        outputStream = new ObjectOutputStream(socket.getOutputStream)
    } catch {
      case e: SocketException => {
        println(e.toString)
        reconnect
      }
      case e: Exception => {
        println(e.toString)
        reconnect
      }
    }
    if (!running)
      try
        run
      catch {
        case e: SocketException => {
          println(e.toString)
        }
        case e: Exception => {
          println(e.toString)
        }
      }
    socketSemaphore.release()
  }

  def autoGet(message: Type): Unit

  var running = false

  @throws(classOf[ClientSocketClosedException[Type]])
  override def run :Unit= {
    running = true
    println("init messenger on port:" + port)
    try
      checkConnection
    catch {
      case e: ClientSocketClosedException[Type] => {messengerManager.remove(currentMessenger);return}
      case e:ServerNotActiveException=>if(messengerManager!=null){messengerManager.remove(currentMessenger);return}
    }
    try {
      println("start to read from port:" + port)
      inputThread.start
    }
    catch {
      case e: IllegalThreadStateException => {}
    }
    try {
      println("start to write to port:" + port)
      outputThread.start
    }
    catch {
      case e: IllegalThreadStateException => {}
    }
    println("started messenger on port:" + port)
  }

  override def interrupt = {
    inputThread.interrupt
    outputThread.interrupt
  }

  def sendMessage(content: Type): Unit = {
    outputQueue.add(content)
  }

  def getMessage: Type = {
    while (inputQueue.isEmpty)
      Thread.sleep(GET_INTERVAL)
    inputQueue.poll
  }

  def hasMessage: Boolean = {
    !inputQueue.isEmpty
  }

  private def sendMessage: Unit = {
    if (!outputQueue.isEmpty) {
      val message: Type = outputQueue.poll
      outputStream.writeObject(message)
      println("sent " + message.toString)
    }
  }

  private def receiveMessage: Unit = {
    val message: Type = inputStream.readObject.asInstanceOf[Type]
    inputQueue.add(message)
    //println("received " + message.toString)
    autoGet(getMessage)
  }

  def getRemoteMacAddress: Array[Byte] = {
    NetworkInterface.getByInetAddress(socket.getInetAddress).getHardwareAddress
  }

}