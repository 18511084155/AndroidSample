package com.woodys.demos.flowlayout.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup

/**
 * Created by woodys on 2017/12/1.
 */
interface IAdapter {
    fun getView(context:Context,parent: ViewGroup, position: Int): View
    fun bindView(view: View, position: Int)
}