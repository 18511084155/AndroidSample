package com.woodys.demos.flowlayout.adapter

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.StateListDrawable
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import org.jetbrains.anko.backgroundDrawable
import java.util.*


/**
 * Created by woodys on 2017/12/5.
 */
class TextAdapter(items:ArrayList<String>): IBaseAdapter<String>(items) {
    override fun bindView(view: View, position: Int) {
        val view= view as TextView
        view.text = "$position、${items?.get(position)}"
        view.setOnClickListener {
            Toast.makeText(view.context,"点击$position", Toast.LENGTH_SHORT).show()
        }
    }

    override fun getView(context: Context, parent: ViewGroup, position: Int): View {
        val view = TextView(context)
        val random = Random()
        val color = Color.argb(0xff, random.nextInt(0xFF), random.nextInt(0xFF), random.nextInt(0xFF))
        val pressColor = Color.argb(0xff, Math.min(0xff, Color.red(color) + 30), Math.min(0xff, Color.green(color) + 30), Math.min(0xff, Color.blue(color) + 30))
        val drawable = StateListDrawable()
        drawable.addState(intArrayOf(android.R.attr.state_empty), ColorDrawable(color))
        drawable.addState(intArrayOf(android.R.attr.state_pressed), ColorDrawable(pressColor))
        view.backgroundDrawable = drawable
        return view
    }
}