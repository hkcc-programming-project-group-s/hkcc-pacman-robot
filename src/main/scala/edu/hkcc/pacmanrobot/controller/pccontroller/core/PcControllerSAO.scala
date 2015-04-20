package edu.hkcc.pacmanrobot.controller.pccontroller.core

import edu.hkcc.pacmanrobot.controller.pccontroller.content.PauseUnresumable
import edu.hkcc.pacmanrobot.controller.pccontroller.{PcControllerJFrame, PcController_centerJPanel}
import edu.hkcc.pacmanrobot.utils.studentrobot.code.GameStatus
import edu.hkcc.pacmanrobot.utils.{Config, GameDevice}
import edu.hkcc.pacmanrobot.utils.message.DeviceInfo
import edu.hkcc.pacmanrobot.utils.message.messenger.Messenger


/**
 * Created by 13058456a on 4/15/2015.
 */
class PcControllerSAO extends GameDevice {
  val PcControllerJFrame = new PcControllerJFrame()
  var reason: String = null
  var canResume:Boolean=true;
  var isControllerPause:Boolean=false;


  override var deviceInfo: DeviceInfo = DeviceInfo.create("PC Controller", DeviceInfo.DEVICE_TYPE_CONTROLLER)

  override def loop: Unit = ???

  override def gameResume: Unit = {PcControllerJFrame.palying}

  override def gamePause: Unit = {
    reason=gameStatus.message
    if(!(gameStatus.furtherInfo.equals(DeviceInfo.DEVICE_TYPE_CONTROLLER)&&gameStatus.furtherInfo.equals(0))){
   PcControllerJFrame.unresume()
    canResume=false;
    }
    else if(gameStatus.furtherInfo.equals(DeviceInfo.DEVICE_TYPE_CONTROLLER)) {
      canResume = true
      PcControllerJFrame.reaume()
      isControllerPause=true
    }
    else if(gameStatus.furtherInfo.equals(0)){
      canResume = true
      reason=null
    }
  }

  override def setup: Unit = ???

  override def gameStart: Unit = ???

  override def gameStop: Unit = ???

  override def gameSetup: Unit = ???

  def fetchDeviceInfos:java.util.Collection[DeviceInfo]= {
    null
    //TODO get deviceinfo and save it
  }

  def sendMovementCommand(x:Int,y:Int): Unit ={
    //TODO send movement command
  }

  //def fetchGameStatus:

  def getReason:String={
    reason
  }

}
