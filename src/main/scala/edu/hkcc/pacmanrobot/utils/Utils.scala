package edu.hkcc.pacmanrobot.utils

/**
 * Created by beenotung on 4/1/15.
 */
object Utils {
  def getRangeLong[Type](array: Array[Type],getValue:Type=>Long): Long = {
    if (array.length < 1) 0
    else {
      var min:Long=0L
      var max:Long=0L
      (min, max) = array.foldLeft[(Long, Long)](getValue(array(0)),getValue(array(0)))
      ((accum: (Long, Long), content: Type) => {
        ( {
          if (accum._1 < getValue(content) ) accum._1; else getValue(content)
        }, {
          if (accum._2 > getValue(content)) accum._2; else getValue(content)
        })
      })
      max - min + 1
    }
  }
}
