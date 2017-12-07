package com.woodys.demos.widget

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator
import com.woodys.demos.R

/**
 * Created by woodys on 2017/11/2.
 */
class CircleAnimView(context: Context, attrs: AttributeSet?, defAttrStyle: Int) : View(context, attrs, defAttrStyle) {
    // drawable偏移量offset
    private var offset: Float = 0f
    // 交换执行动画的颜色数组
    private var colors = intArrayOf(resources.getColor(R.color.yellow), resources.getColor(R.color.red), resources.getColor(R.color.blue))
    private var paint : Paint? = null
    private var currentX: Float = 0f
    private var radius: Float = 0f
    private var valueAnimator: ValueAnimator? = null
    constructor(context:Context) : this(context,null,0)
    constructor(context:Context, attrs: AttributeSet?) : this(context,attrs,0)
    init {
        paint = Paint(Paint.ANTI_ALIAS_FLAG)
        context.obtainStyledAttributes(attrs, R.styleable.CircleAnimView,defAttrStyle,R.style.CircleAnimView).apply {
            setDrawableSizeOffset(getDimension(R.styleable.CircleAnimView_cal_offset,0f))
            setDrawableRadius(getDimension(R.styleable.CircleAnimView_cal_radius,0f))
            recycle()
        }
        initAnimator()
    }

    fun setDrawableRadius(radius: Float) {
        this.radius = radius
        invalidate()
    }

    fun setDrawableSizeOffset(offset: Float) {
        this.offset = offset
        invalidate()
    }

    fun setDrawableColors(colors: IntArray) {
        this.colors = colors
        invalidate()
    }

    fun initAnimator(){
        valueAnimator = ValueAnimator.ofFloat(0f,offset,0f)
        valueAnimator?.addUpdateListener({ animation ->
            currentX = animation.animatedValue as Float
            invalidate()
        })
        valueAnimator?.addListener(object:AnimatorListenerAdapter(){
            override fun onAnimationRepeat(animation: Animator?) {
                super.onAnimationEnd(animation)
                val sweepIndex = tag as? Int?:0
                val temp = colors[2]
                colors[2] = colors[sweepIndex]
                colors[sweepIndex] = temp
                if (sweepIndex == 0) tag = 1 else tag = 0
            }
        })
        valueAnimator?.interpolator = LinearInterpolator()
        valueAnimator?.repeatMode = ValueAnimator.REVERSE
        valueAnimator?.repeatCount = -1
        valueAnimator?.duration = 1000
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val centerX:Float = width/2 * 1f
        val centerY:Float = height/2 * 1f

        //画左边圆圈
        paint?.color = colors[0]
        canvas.drawCircle(centerX-currentX,centerY,radius,paint)

        //画右边圆圈
        paint?.color = colors[1]
        canvas.drawCircle(centerX+currentX,centerY,radius,paint)

        //画中间圆圈
        paint?.color = colors[2]
        canvas.drawCircle(centerX,centerY,radius,paint)
    }

    override fun onWindowFocusChanged(hasWindowFocus: Boolean) {
        super.onWindowFocusChanged(hasWindowFocus)
        if(!hasWindowFocus){
            if(valueAnimator?.isRunning?:false) valueAnimator?.cancel()
        }else{
            valueAnimator?.start()
        }
    }

    /**
     * 在View销毁时停止动画
     */
    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        valueAnimator?.cancel()
    }

}