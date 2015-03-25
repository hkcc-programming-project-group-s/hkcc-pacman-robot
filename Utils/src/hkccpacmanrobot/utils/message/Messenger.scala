package hkccpacmanrobot.utils.message

import java.io.{ObjectInputStream, ObjectOutputStream}
import java.net.Socket
import java.util.concurrent.ConcurrentLinkedQueue

import hkccpacmanrobot.utils.Config

/**
 * Created by beenotung on 2/10/15.
 */

object Messenger {
  def create[Type](message: Message): Messenger[Type] = {
    def autoGet():Unit={}
    new Messenger[Type](message.port,autoGet)
  }
  def create[Type](message: Message,autoGet:()=>Unit): Messenger[Type] = {
    new Messenger[Type](message.port,autoGet)
  }
}

class Messenger[Type](val socket: Socket,var autoGet:()=>Unit) extends Thread {
  val sendInterval:Long=1
  val autoGetInterval:Long=1
  val inputStream: ObjectInputStream = new ObjectInputStream(socket.getInputStream)
  val outputStream: ObjectOutputStream = new ObjectOutputStream(socket.getOutputStream)
  val inputThread: Thread = new Thread(new Runnable {
    override def run = {
      receiveMessage
    }
  })
  val outputThread: Thread = new Thread(new Runnable {
    override def run = {
      while (true){
        sendMessage
        Thread.sleep(sendInterval)
      }
    }
  })
  val outputQueue: ConcurrentLinkedQueue[Type] = new ConcurrentLinkedQueue[Type]
  val inputQueue: ConcurrentLinkedQueue[Type] = new ConcurrentLinkedQueue[Type]
  var active: Boolean = false

  def this(port: Int,autoGet:()=>Unit) = {
    this(new Socket(Config.serverAddress, port),autoGet)
  }

  override def start: Unit = {
    inputThread.start
    outputThread.start
  }

  override def interrupt = {
    inputThread.interrupt
    outputThread.interrupt
  }

  private def sendMessage: Unit = {
    if (!outputQueue.isEmpty)
      outputStream.writeObject(outputQueue.poll)
  }

  private def receiveMessage: Unit = {
    inputQueue.add(inputStream.readObject.asInstanceOf[Type])
    autoGet()
  }

  def sendMessage(content: Type): Unit = {
    outputQueue.add(content)
  }


  def getMessage: Type = {
    while (inputQueue.isEmpty)
      Thread.sleep(1)
    inputQueue.poll
  }
  def hasMessage:Boolean={!inputQueue.isEmpty}
}