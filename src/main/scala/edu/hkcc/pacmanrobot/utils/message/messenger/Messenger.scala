package edu.hkcc.pacmanrobot.utils.message.messenger

import java.io.{EOFException, ObjectInputStream, ObjectOutputStream}
import java.net._
import java.rmi.server.ServerNotActiveException
import java.util.concurrent.{ConcurrentLinkedQueue, Semaphore}

import edu.hkcc.pacmanrobot.server.MessengerManager
import edu.hkcc.pacmanrobot.utils.Config
import edu.hkcc.pacmanrobot.utils.Worker.forkAndStart
import edu.hkcc.pacmanrobot.utils.exception.ClientSocketClosedException


/**
 * Created by beenotung on 2/10/15.
 */

object Messenger {
  def create[Type](port: Int, autoGetFunc: (Type) => Unit = (message: Type) => {}, messengerManager: MessengerManager[Type]): Messenger[Type] = {
    println("creating messenger on port: " + port)
    val messenger = new Messenger[Type](connect(port, messengerManager != null), port, messengerManager) {
      override def autoGet(message: Type): Unit = {
        autoGetFunc(message)
      }
    }
    println("created messenger on port: " + port)
    messenger
  }


  @throws(classOf[ServerNotActiveException])
  def connect(port: Int, isServer: Boolean): Socket = {
    var socket: Socket = new Socket()
    do {
      try {
        println("try to connect to " + Config.serverAddress + ":" + port)
        socket = new Socket(Config.serverAddress, port)
      }
      catch {
        case e: ConnectException => {
          if (isServer)
            throw new ServerNotActiveException("Server is not available")
          else
            Thread.sleep(Config.RECONNECTION_TIMEOUT)
        }
      }
    } while (socket.isClosed || !socket.isConnected)
    println("connected to " + Config.serverAddress + ":" + port)
    socket
  }
}

abstract class Messenger[Type](var socket: Socket, val port: Int, val messengerManager: MessengerManager[Type],
                               val SEND_INTERVAL: Long = 50, val GET_INTERVAL: Long = 50)
  extends Thread {

  def stopThread = {
    inputThread.interrupt
    inputThread.stop
    outputThread.interrupt
    outputThread.stop
    currentMessenger.interrupt
    currentMessenger.stop
  }

  val currentMessenger = this
  var inputStream: ObjectInputStream = null
  var outputStream: ObjectOutputStream = null
  val inputThread: Thread = new Thread(new Runnable {
    override def run: Unit = {
      while (!isInterrupted) {
        try {
          receiveMessage
        }
        catch {
          case e: SocketException => checkConnection(e)
          case e: EOFException => checkConnection(e)
          case e: InterruptedException => interrupt
          case e: Exception => e.printStackTrace()
        }
      }
    }
  })
  val outputThread: Thread = new Thread(new Runnable {
    override def run = {
      while (!isInterrupted) {
        try {
          sendMessage
          Thread.sleep(SEND_INTERVAL)
        } catch {
          case e: SocketException => checkConnection(e)
          case e: EOFException => checkConnection(e)
          case e: InterruptedException => interrupt
          case e: Exception => e.printStackTrace()
        }
      }
    }
  })

  val outputQueue: ConcurrentLinkedQueue[Type] = new ConcurrentLinkedQueue[Type]
  val inputQueue: ConcurrentLinkedQueue[Type] = new ConcurrentLinkedQueue[Type]
  var active: Boolean = false

  @throws(classOf[ClientSocketClosedException[Type]])
  def reconnect: Unit = {
    println("reconnecting on: " + socket.getRemoteSocketAddress + "(" + port + ")")
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
        socket = Messenger.connect(socket.getPort, messengerManager != null)
      catch {
        case e: ServerNotActiveException =>
          if (messengerManager != null)
            throw e
      }
    } while (!socket.isConnected || socket.isClosed)
    inputStream = new ObjectInputStream(socket.getInputStream)
    outputStream = new ObjectOutputStream(socket.getOutputStream)
    println("reconnected on: " + socket.getRemoteSocketAddress + "(" + port + ")")
  }

  def this(port: Int) = {
    this(Messenger.connect(port, isServer = false), port, null)
  }

  val socketSemaphore: Semaphore = new Semaphore(1)


  def checkConnection(lastException: Exception = null): Unit = {
    if (lastException != null)
      println("checkConnection: last exception=" + lastException)
    if (lastException != null && messengerManager != null) {
      messengerManager.remove(this)
      //println("new size=" + messengerManager.messengers.size())
      return
    }
    println("checking connection on: " + socket.getRemoteSocketAddress + "(" + port + ")")
    Thread.sleep(500)
    socketSemaphore.acquire()
    try {
      println("check if socket is closed")
      if (socket.isClosed || !socket.isConnected)
        throw new SocketException("socket is closed (exception) on port: " + port)
      var inOk = true
      val inThread = forkAndStart({
        println("check if input stream is null")
        if (inputStream == null || (lastException!=null && lastException.isInstanceOf[EOFException])) {
          println("get input stream")
          val in = socket.getInputStream
          println("creating object input stream")
          try {
            inputStream = new ObjectInputStream(in)
            println("created object input stream")
          }
          catch {
            case e: EOFException => inOk = false
          }
        }
      })
      var outOk = true
      val outThread = forkAndStart({
        println("check if output stream is null")
        if (outputStream == null || (lastException!=null && lastException.isInstanceOf[EOFException])) {
          println("get output stream")
          val out = socket.getOutputStream
          println("creating object output stream")
          try {
            outputStream = new ObjectOutputStream(out)
            println("created object output stream")
          }
          catch {
            case e: EOFException => outOk = false
          }
        }
      })
      inThread.join
      outThread.join
      if (!(inOk && outOk))
        throw new EOFException()
      if (socket.getLocalSocketAddress.toString.equals(socket.getRemoteSocketAddress.toString)) {
        println("connected to itself?!")
        throw new IllegalArgumentException
      }
    } catch {
      case e: SocketException => {
        println(e.toString)
        reconnect
      }
      case e: EOFException => {
        println("IO stream is not ready")
        reconnect
      }
      case e: IllegalArgumentException => messengerManager.remove(this)
      case e: Exception => {
        println(e.toString)
        reconnect
      }
    }
    /* if (!running)
       try
         run
       catch {
         case e: SocketException => {
           println(e.toString)
         }
         case e: Exception => {
           println(e.toString)
         }
       }*/
    socketSemaphore.release()
    println("checked connection on: " + socket.getRemoteSocketAddress + "(" + port + ")")
  }

  def autoGet(message: Type): Unit

  //def autoGet(controllerMacAddress:Array[Byte],message: Type): Unit

  //var running = false

  //  @throws(classOf[ClientSocketClosedException[Type]])
  override def run: Unit = {
    //running = true
    println("init messenger on port:" + port)
    try
      checkConnection()
    catch {
      case e: ClientSocketClosedException[Type] => {
        messengerManager.remove(currentMessenger)
        return
      }
      case e: ServerNotActiveException => if (messengerManager != null) {
        messengerManager.remove(currentMessenger)
        return
      }
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
    super.interrupt
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

  protected def sendMessage: Unit = {
    if (!outputQueue.isEmpty) {
      val message: Type = outputQueue.poll
      outputStream.writeObject(message)
      //println("sent " + message.toString)
    }
  }

  private def receiveMessage: Unit = {
    val message: Type = inputStream.readObject.asInstanceOf[Type]
    inputQueue.add(message)
    println("received " + message.toString)
    autoGet(getMessage)
  }

  def getRemoteMacAddress: Array[Byte] = {
    NetworkInterface.getByInetAddress(socket.getInetAddress).getHardwareAddress
  }

}