package hkccpacmanrobot.utils

/**
 * Created by 13058456a on 3/21/2015.
 */
 object DeviceInfo {
val DEVICE_TYPE_CONTROLLER:Byte=1
  val DEVICE_TYPE_ASSIGNMENT_ROBOT:Byte=2
  val DEVICE_TYPE_STUDENT_ROBOT:Byte=3
  val DEVICE_TYPE_DEADLINE_ROBOT:Byte=4
  val DEVICE_TYPE_SERVER:Byte=5
}

class DeviceInfo (var name:String,var IP:String,var lastConnectionTime:Long=0)extends Serializable{


}