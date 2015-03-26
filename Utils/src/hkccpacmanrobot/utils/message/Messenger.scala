package hkccpacmanrobot.utils.message

import java.io.{ObjectInputStream, ObjectOutputStream}
import java.net.{InetSocketAddress, Socket}
import java.util.concurrent.ConcurrentLinkedQueue

import com.sun.prism.impl.ManagedResource
import hkccpacmanrobot.utils.Config

/**
 * Created by beenotung on 2/10/15.
 */

object Messenger {
  def create[MessageType:Message](message: Message, autoGetFunc: (MessageType) => Unit = message => {}): Messenger[MessageType] = {
    new Messenger[MessageType](message.port) {
      override def autoGet(message: MessageType): Unit = {
        autoGetFunc(message)
      }
    }
  }
}

abstract class Messenger[MessageType:Message](val socket: Socket) extends Thread {
  val SEND_INTERVAL: Long = 1
  val GET_INTERVAL: Long = 1
  val inputStream: ObjectInputStream = new ObjectInputStream(socket.getInputStream)
  val outputStream: ObjectOutputStream = new ObjectOutputStream(socket.getOutputStream)
  val inputThread: Thread = new Thread(new Runnable {
    override def run = {
      receiveMessage
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
  val outputQueue: ConcurrentLinkedQueue[MessageType] = new ConcurrentLinkedQueue[MessageType]
  val inputQueue: ConcurrentLinkedQueue[MessageType] = new ConcurrentLinkedQueue[MessageType]
  var active: Boolean = false

  def this(port: Int) = {
    this(new Socket(Config.serverAddress, port))
  }

  def this(message: Message) = {
    this(message.port)
  }
  

  abstract def autoGet(message: MessageType): Unit

  override def start: Unit = {
    inputThread.start
    outputThread.start
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
    if (!outputQueue.isEmpty)
      outputStream.writeObject(outputQueue.poll)
  }

  private def receiveMessage: Unit = {
    inputQueue.add(inputStream.readObject.asInstanceOf[MessageType])
    autoGet(getMessage)
  }
}