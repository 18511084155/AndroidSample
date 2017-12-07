package com.woodys.demos

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.woodys.demos.linkage.ActionManager
import kotlinx.android.synthetic.main.activity_linkage_a.*
import org.jetbrains.anko.sdk25.coroutines.onClick

class LinkAge1Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_linkage_a)
        ActionManager.init {
            actionCallback {}
            convertDataCallback { id, data ->
                titleNameA.text ="id:$id;data:$data"
                "我就给你修改了你也没有办法"
            }
        }
        titleNameA.onClick { startActivity(Intent(this@LinkAge1Activity,LinkAge2Activity::class.java))}
    }
}
