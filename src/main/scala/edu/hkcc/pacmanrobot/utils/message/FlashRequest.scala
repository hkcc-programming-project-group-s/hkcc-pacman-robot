package edu.hkcc.pacmanrobot.utils.message

import edu.hkcc.pacmanrobot.utils.Config

/**
 * Created by 13058456a on 4/15/2015.
 */

/**
 *
 * @param macAddress
 * @param shouldFlash
 * true => server tell robot to light on
 * false -> server tell robot to reset position
 */
class FlashRequest(val macAddress: Array[Byte], val shouldFlash: Boolean = false) extends Message {
  override def port(): Int = Config.PORT_FLASH_REQUEST
}
