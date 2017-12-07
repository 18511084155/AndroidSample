package com.woodys.demos.flowlayout.adapter

/**
 * Created by woodys on 2017/12/5.
 */
abstract class IBaseAdapter<E>(var items:ArrayList<E>?) : IAdapter{
    fun getItem(position: Int):E? = items?.get(position)
    fun getCount(): Int = (items?.size as? Int)?:0
}