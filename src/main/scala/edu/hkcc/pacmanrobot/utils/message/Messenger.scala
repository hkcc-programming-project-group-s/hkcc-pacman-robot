package edu.hkcc.pacmanrobot.utils.studentrobot.code


import java.io.{IOException, ObjectInputStream, ObjectOutputStream}
import java.net.{Socket, SocketException}
import java.util.Calendar
import java.util.concurrent.ConcurrentLinkedQueue

import edu.hkcc.pacmanrobot.utils.studentrobot.code.Config.RECONNECTION_TIMEOUT

/**
 * Created by beenotung on 2/10/15.
 */

object Messenger {
  def create[MessageType](message: Message, autoGetFunc: (MessageType) => Unit = (message: MessageType) => {}): Messenger[MessageType] = {
    println("try to connect to " + Config.serverAddress + ":" + message.port)
    new Messenger[MessageType](message.port) {
      override def autoGet(message: MessageType): Unit = {
        autoGetFunc(message)
      }
    }
  }

  def connect(port: Int): Socket = {
    var socket: Socket = null
    var ok: Boolean = false
    do {
      try {
        println("try to reconnect to " + Config.serverAddress + ":" + port)
        socket = new Socket(Config.serverAddress, port)
        ok = true
      }
      catch {
        case e: SocketException => {
          printf("\n%s\n%s\n", Calendar.getInstance().getTime.toString, e.toString)
          Thread.sleep(RECONNECTION_TIMEOUT)
        }
      }
    } while (!ok)
    socket
  }
}

abstract class Messenger[MessageType](var socket: Socket, val port: Int) extends Thread {
  val SEND_INTERVAL: Long = 50
  val GET_INTERVAL: Long = 50
  var inputStream: ObjectInputStream = new ObjectInputStream(socket.getInputStream)
  var outputStream: ObjectOutputStream = new ObjectOutputStream(socket.getOutputStream)
  val inputThread: Thread = new Thread(new Runnable {
    override def run = {
      while (true) {
        try {
          receiveMessage
        }
        catch {
          case e: IOException => socket.close()
        }
      }
    }
  })
  val outputThread: Thread = new Thread(new Runnable {
    override def run = {
      while (true) {
        try {
          sendMessage
          Thread.sleep(SEND_INTERVAL)
        }
        catch {
          case e: IOException => socket.close()
        }

      }
    }
  })
  val outputQueue: ConcurrentLinkedQueue[MessageType] = new ConcurrentLinkedQueue[MessageType]
  val inputQueue: ConcurrentLinkedQueue[MessageType] = new ConcurrentLinkedQueue[MessageType]
  var active: Boolean = false

  def this(port: Int) = {
    this(Messenger.connect(port), port)
  }

  def checkConnection = {
    if (socket.isClosed) {
      socket = Messenger.connect(socket.getPort)
      inputStream = new ObjectInputStream(socket.getInputStream)
      outputStream = new ObjectOutputStream(socket.getOutputStream)
    }
    try
      start
    catch {
      case e: IllegalThreadStateException => {}
    }
  }

  def autoGet(message: MessageType): Unit

  override def run: Unit = {
    try
      inputThread.start
    catch {
      case e: IllegalThreadStateException => {}
    }
    try
      outputThread.start
    catch {
      case e: IllegalThreadStateException => {}
    }
  }

  override def interrupt = {
    inputThread.interrupt
    outputThread.interrupt
  }

  def sendMessage(content: MessageType): Unit = {
    outputQueue.add(content)
  }

  def getMessage: MessageType = {
    while (inputQueue.isEmpty)
      Thread.sleep(GET_INTERVAL)
    inputQueue.poll
  }

  def hasMessage: Boolean = {
    !inputQueue.isEmpty
  }

  private def sendMessage: Unit = {
    if (!outputQueue.isEmpty) {
      val message: MessageType = outputQueue.poll
      outputStream.writeObject(message)
      println("sent " + message.toString)
    }
  }

  private def receiveMessage: Unit = {
    val message: MessageType = inputStream.readObject.asInstanceOf[MessageType]
    inputQueue.add(message)
    //println("received " + message.toString)
    autoGet(getMessage)
  }
}