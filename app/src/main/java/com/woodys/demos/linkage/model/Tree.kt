package com.woodys.demos.linkage.model

/**
 * Created by woodys on 2017/12/7.
 */
class Tree<T>(var data: T?) {
    var level: Int = 0
    private var child: MutableList<Tree<T>>? = null
    var isExpand = false
    var isSelected = false

    init {
        child = ArrayList()
    }
    var list: MutableList<Tree<T>>
        get() = child as MutableList<Tree<T>>
        set(child) {
            this.child = child
        }

    fun addChild(children: Tree<T>) {
        children.level = level + 1
        child!!.add(children)
    }
}