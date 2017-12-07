package com.woodys.demos.linkage

import android.content.Context
import com.woodys.demos.linkage.model.Tree

/**
 * Created by woodys on 2017/12/7.
 */

object ActionManager {
    private lateinit var actionConfig: ActionConfig<String>
    lateinit var context: Context
    var actionCallback: ((Tree<String>) -> Unit)? = null
        get() = actionConfig.actionCallback

    var convertDataCallback: ((id: Int,data : String) -> String)? = null
        get() = actionConfig.convertDataCallback

    fun init(closure: ActionConfig<String>.() -> Unit): ActionManager {
        actionConfig = ActionConfig<String>().apply(closure)
        return this
    }
}