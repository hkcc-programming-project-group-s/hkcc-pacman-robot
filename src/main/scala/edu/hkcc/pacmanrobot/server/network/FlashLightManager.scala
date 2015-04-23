package edu.hkcc.pacmanrobot.server.network

import edu.hkcc.pacmanrobot.utils.Config._
import edu.hkcc.pacmanrobot.utils.lang.ConcurrencyDrawer
import edu.hkcc.pacmanrobot.utils.message.FlashRequest

/**
 * Created by beenotung on 4/23/15.
 */
class FlashLightManager() {
  val messengerManager = new MessengerManager[FlashRequest](PORT_FLASH_REQUEST, initMessenger_func = messenger => {
    messenger.sendMessage(new FlashRequest(messenger.getRemoteMacAddress, shouldFlash = false))
  }, autoGet_func = (macAddress, position) => {})
  val lastTarget: ConcurrencyDrawer[Array[Byte]] = new ConcurrencyDrawer[Array[Byte]]

  def clear = {
    messengerManager.foreach(op = {
      messenger => messenger.sendMessage(new FlashRequest(messenger.getRemoteMacAddress, shouldFlash = false))
    })
    lastTarget.clear
  }

  def flash(macAddress: Array[Byte]) = {
    val lastOne = lastTarget.update(macAddress)
    messengerManager.sendByMacAddress(macAddress, new FlashRequest(macAddress, shouldFlash = true))
    if (lastOne != null)
      messengerManager.sendByMacAddress(lastOne, new FlashRequest(lastOne, shouldFlash = false))
  }
}
