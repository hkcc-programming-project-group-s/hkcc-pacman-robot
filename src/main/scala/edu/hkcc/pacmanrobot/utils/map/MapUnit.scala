package edu.hkcc.pacmanrobot.utils.map

/**
 * Created by beenotung on 3/21/15.
 */
class MapUnit(val location: MapKey, var time: Long = System.currentTimeMillis()) extends Cloneable with Serializable {
}
