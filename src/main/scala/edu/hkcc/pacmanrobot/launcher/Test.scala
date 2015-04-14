package edu.hkcc.pacmanrobot.launcher

import java.net.InetAddress

/**
 * Created by beenotung on 4/14/15.
 */
object Test extends App{
override def main(args: Array[String]) {
  println(InetAddress.getLocalHost.getHostAddress)
  println(InetAddress.getByName("BeenoTung-ArchLinux-Home").getHostAddress)
}
}
