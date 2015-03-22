package hkccpacmanrobot.utils.message

import hkccpacmanrobot.utils.Config

/**
 * Created by beenotung on 3/22/15.
 */
object ObstacleMap extends  AbstractMessage{
  override val port: Int = Config.PORT_MAP
}
