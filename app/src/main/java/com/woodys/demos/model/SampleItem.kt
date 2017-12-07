package com.woodys.demos.model

/**
 * Created by woodys on 2017/7/24.
 */
data class SampleItem<T : Any?>(var id:Int?, var pid:Int=0, var clazz:Class<out T>?,var title:String?,var desc:String?){
    constructor() : this(null,0,null,null,null)
}