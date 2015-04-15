package edu.hkcc.pacmanrobot.utils.message

import edu.hkcc.pacmanrobot.utils.Config

/**
 * Created by 13058456a on 4/15/2015.
 */
class FlashRequest(val macAddress:Array[Byte],val shouldFlash:Boolean) extends Message{
  override def port(): Int = Config.PORT_FLASH_REQUEST
}
