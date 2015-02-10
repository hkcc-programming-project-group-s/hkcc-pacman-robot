package hkccpacmanrobot.utils

import java.io.IOException

/**
 * Created by beenotung on 2/10/15.
 */

class Messenger[Type](val sender: String, val receiver: String, val decoder: Array[Byte] => Type) {
  val RESET: Byte = 0x01
  setup

  def setup: Unit = {
    //setup server socket
    //setup client socket
  }

  @throws(classOf[IOException])
  def sendMessage(content: Type): Unit = {}

  @throws(classOf[IOException])
  def getMessage(): Type = {
    val rawMessage: Array[Byte] = Array[Byte](1)
    decoder(rawMessage)
  }
}

trait Client[Type] {
  def decoder: Array[Byte] => Type
}