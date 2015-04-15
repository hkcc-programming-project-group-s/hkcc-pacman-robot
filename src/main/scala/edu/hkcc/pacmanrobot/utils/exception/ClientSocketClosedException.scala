package edu.hkcc.pacmanrobot.utils.exception

import edu.hkcc.pacmanrobot.utils.message.messenger.Messenger

/**
 * Created by beenotung on 4/8/15.
 */
class ClientSocketClosedException[MessengerType](val messenger: Messenger[MessengerType]) extends Exception {
  override def toString: String = {
    "client is close"
  }
}
