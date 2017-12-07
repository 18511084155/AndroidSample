package com.woodys.demos

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.woodys.demos.linkage.ActionManager
import kotlinx.android.synthetic.main.activity_linkage_b.*
import org.jetbrains.anko.sdk25.coroutines.onClick

class LinkAge2Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_linkage_b)
        titleNameB.onClick {
            val title = ActionManager.convertDataCallback?.invoke(200,"就是个标题")
            (it as TextView).text=title
        }

    }
}
