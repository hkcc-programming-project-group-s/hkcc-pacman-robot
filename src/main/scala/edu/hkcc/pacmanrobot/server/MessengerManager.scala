package edu.hkcc.pacmanrobot.server

import java.net.ServerSocket

import edu.hkcc.pacmanrobot.utils.studentrobot.code.Messenger

import scala.collection.parallel.mutable.ParArray

/**
 * Created by beenotung on 4/5/15.
 */
class MessengerManager[Type](val servicePort: Int, autoGet_func: Type => Unit) {
  val thread = new Thread(new Runnable {
    override def run(): Unit = {
      while (true) {
        messengers :+= new Messenger[Type](serverSocket.accept(), servicePort) {
          override def autoGet(message: Type): Unit = {
            autoGet_func
          }
        }
      }
    }
  })
  var messengers = ParArray.empty[Messenger[Type]]
  var serverSocket = new ServerSocket(servicePort)
}
