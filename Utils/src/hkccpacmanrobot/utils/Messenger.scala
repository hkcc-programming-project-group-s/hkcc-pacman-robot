package hkccpacmanrobot.utils

import java.io.{ObjectInputStream, ObjectOutputStream}
import java.net.Socket
import java.util.concurrent.ConcurrentLinkedQueue

/**
 * Created by beenotung on 2/10/15.
 */

object Messenger {
  val RESET: Byte = 0x01
}


class Messenger[Type](val socket: Socket) extends Thread {
  val inputStream: ObjectInputStream = new ObjectInputStream(socket.getInputStream)
  val outputStream: ObjectOutputStream = new ObjectOutputStream(socket.getOutputStream)
  var active: Boolean = false

  val inputThread:Thread=new Thread(new Runnable{override def run={receiveMessage}})
  val outputThread:Thread=new Thread(new Runnable{override def run={sendMessage}})


  override def  start: Unit = {
  inputThread.start
    outputThread.start
  }
  override def interrupt={
    inputThread.interrupt
    outputThread.interrupt
  }

  val outputQueue: ConcurrentLinkedQueue[Type] = new ConcurrentLinkedQueue[Type]
  val inputQueue: ConcurrentLinkedQueue[Type] = new ConcurrentLinkedQueue[Type]

  def sendMessage(): Unit = {
    if (!outputQueue.isEmpty)
      outputStream.writeObject(outputQueue.poll())
  }

  def receiveMessage(): Unit = {
    inputQueue.add(inputStream.readObject().asInstanceOf[Type])
  }


  def sendMessage(content: Type): Unit = {
    outputQueue.add(content)
  }


  def getMessage(): Type = {
    while (inputQueue.isEmpty)
      Thread.sleep(1)
    inputQueue.poll
  }
}