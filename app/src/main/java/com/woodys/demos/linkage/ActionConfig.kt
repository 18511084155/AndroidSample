package com.woodys.demos.linkage

import com.woodys.demos.linkage.model.Tree

/**
 * Created by woodys on 2017/12/7.
 */
class ActionConfig<T> {
    internal var actionCallback:((Tree<T>)->Unit)?=null
    var convertDataCallback: ((id: Int,data : T) -> String)? = null

    fun actionCallback(actionCallback: (Tree<T>) -> Unit) {
        this.actionCallback = actionCallback
    }
    fun convertDataCallback(actionCallback: (Int, T) -> String) {
        this.convertDataCallback = actionCallback
    }
}