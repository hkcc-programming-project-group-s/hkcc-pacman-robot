package hkccpacmanrobot.utils.message

import java.io.{ObjectInputStream, ObjectOutputStream}
import java.net.Socket
import java.util.concurrent.ConcurrentLinkedQueue

import hkccpacmanrobot.utils.Config

/**
 * Created by beenotung on 2/10/15.
 */

object Messenger {
  def create[T](message: Message): Messenger[T] = {
    new Messenger[T](message.port)
  }
}

class Messenger[Type](val socket: Socket) extends Thread {
  val inputStream: ObjectInputStream = new ObjectInputStream(socket.getInputStream)
  val outputStream: ObjectOutputStream = new ObjectOutputStream(socket.getOutputStream)
  val inputThread: Thread = new Thread(new Runnable {
    override def run = {
      receiveMessage
    }
  })
  val outputThread: Thread = new Thread(new Runnable {
    override def run = {
      sendMessage
    }
  })
  val outputQueue: ConcurrentLinkedQueue[Type] = new ConcurrentLinkedQueue[Type]
  val inputQueue: ConcurrentLinkedQueue[Type] = new ConcurrentLinkedQueue[Type]
  var active: Boolean = false

  def this(port: Int) = {
    this(new Socket(Config.serverAddress, port))
  }

  override def start: Unit = {
    inputThread.start
    outputThread.start
  }

  override def interrupt = {
    inputThread.interrupt
    outputThread.interrupt
  }

  def sendMessage: Unit = {
    if (!outputQueue.isEmpty)
      outputStream.writeObject(outputQueue.poll)
  }

  def receiveMessage: Unit = {
    inputQueue.add(inputStream.readObject.asInstanceOf[Type])
  }


  def sendMessage(content: Type): Unit = {
    outputQueue.add(content)
  }


  def getMessage: Type = {
    while (inputQueue.isEmpty)
      Thread.sleep(1)
    inputQueue.poll
  }
}