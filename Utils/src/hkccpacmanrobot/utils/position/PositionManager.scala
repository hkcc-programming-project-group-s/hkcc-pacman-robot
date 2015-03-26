package hkccpacmanrobot.utils.position

import hkccpacmanrobot.utils.Maths.Point2D
import hkccpacmanrobot.utils.message.Messenger

/**
 * Created by 13058536A on 3/25/2015.
 */
class PositionManager {
val messenger:Messenger[Position]=Messenger.create[Position](new Position())
}
