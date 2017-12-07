package com.woodys.demos

import android.app.Activity
import com.woodys.demos.model.SampleItem

/**
 * Created by woodys on 2017/7/24.
 */

class FuncTemplate {
    companion object {
        var items = mutableListOf<SampleItem<Activity>>()
        val groupItems = mutableMapOf<Int, List<SampleItem<Activity>>>()
        fun item(closure: SampleItem<Activity>.() -> Unit) {
            items.add(SampleItem<Activity>().apply(closure))
        }

        fun group(closure: () -> Unit) {
            closure.invoke()
            groupItems += items.groupBy { it.pid }
        }

        operator fun get(id: Int) = groupItems[id]

        operator fun contains(id: Int?): Boolean = groupItems.any { it.key == id }

        init {
            group {
                item {
                    id = 1
                    title = "动画效果"
                    desc = "android demo 集合"

                    item {
                        pid = 1
                        title = "打开信封动画"
                        desc = ""
                        clazz = OpenEnvelopeAnimActivity::class.java
                    }
                    item {
                        pid = 1
                        title = "三个小球交替动画"
                        desc = ""
                        clazz = CircleAnimActivity::class.java
                    }

                }
                item {
                    id = 2
                    title = "流布局自定义控件"
                    desc = ""
                    clazz = FlowLayout1Activity::class.java
                }
                item {
                    id = 3
                    title = "三级联动双向通信控制器"
                    desc = ""
                    clazz = LinkAge1Activity::class.java
                }
            }
        }
    }
}