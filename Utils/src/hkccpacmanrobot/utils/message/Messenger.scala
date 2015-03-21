package hkccpacmanrobot.utils.message

import java.io.{IOException, ObjectInputStream, ObjectOutputStream}
import java.net.Socket

/**
 * Created by beenotung on 2/10/15.
 */

object Messenger {
  val RESET: Byte = 0x01
}


class Messenger[Type](val socket: Socket) extends Thread {
  val objectInputStream: ObjectInputStream = new ObjectInputStream(socket.getInputStream)
  val objectOutputStream: ObjectOutputStream = new ObjectOutputStream(socket.getOutputStream)
  var active: Boolean = false


  override def run(): Unit = {
    while (active) {

    }
  }

  override def start: Unit = {
    if (active)
      return
    active = true
    super.start()
  }


  @throws(classOf[IOException])
  def sendMessage(content: Type): Unit = {}

  @throws(classOf[IOException])
  def getMessage(): Type = {
    val rawMessage: Array[Byte] = Array[Byte](1)
    def decoder: Array[Byte] => Type = null
    decoder(rawMessage)
  }
}