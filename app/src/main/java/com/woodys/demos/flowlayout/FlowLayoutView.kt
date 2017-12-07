package com.woodys.demos.flowlayout

import android.content.Context
import android.support.v4.widget.ScrollerCompat
import android.util.AttributeSet
import android.view.*
import com.woodys.demos.R
import com.woodys.demos.flowlayout.adapter.IAdapter
import com.woodys.demos.flowlayout.adapter.IBaseAdapter
import java.util.*

/**
 * Created by woodys on 2017/11/29.
 */

class FlowLayoutView(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
        ViewGroup(context, attrs, defStyleAttr) {

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context) : this(context, null, 0)

    //手势滑动
    private val scroller = ScrollerCompat.create(context)
    private var lastMoveY: Float = 0f
    private var isInterceptMoveTouch: Boolean = false

    private var touchSlop: Int = 0
    private var velocityTracker: VelocityTracker? = null
    private var scaledMinimumFlingVelocity: Int = 0
    private var scaledMaximumFlingVelocity: Int = 0

    private var columnsCount: Int = 0
    private var horizontalSpacing: Float = 0f
    private var verticalSpacing: Float = 0f
    //当前屏幕显示区域
    private val views = ArrayList<View>()
    private var adapter: IAdapter? = null

    init {
        //onDraw()方法不被执行的解决方法
        setWillNotDraw(false)
        val configuration = ViewConfiguration.get(context)
        touchSlop = configuration.scaledTouchSlop
        scaledMaximumFlingVelocity = configuration.scaledMaximumFlingVelocity
        scaledMinimumFlingVelocity = configuration.scaledMinimumFlingVelocity

        context.obtainStyledAttributes(attrs, R.styleable.FlowLayoutView).apply {
            setColumnsCount(getInt(R.styleable.FlowLayoutView_fl_columnsCount, 0))
            setHorizontalSpacing(getDimension(R.styleable.FlowLayoutView_fl_horizontalSpacing, 0f))
            setVerticalSpacing(getDimension(R.styleable.FlowLayoutView_fl_verticalSpacing, 0f))
            recycle()
        }
    }

    fun <E> setAdapter(adapter: IBaseAdapter<E>) {
        this.adapter = adapter
        if (null != adapter) {
            removeAllViews()
            (0..adapter.getCount()-1).forEach { position ->
                var view = adapter.getView(context, this, position)
                adapter.bindView(view, position)
                addView(view,position,ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT))
            }

        }
    }

    fun setColumnsCount(columnsCount: Int) {
        this.columnsCount = columnsCount
        requestLayout()
    }

    fun setHorizontalSpacing(spacing: Float) {
        this.horizontalSpacing = spacing
        requestLayout()
    }

    fun setVerticalSpacing(spacing: Float) {
        this.verticalSpacing = spacing
        requestLayout()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        (0..childCount - 1).map { index -> getChildAt(index) }.forEach {
            measureChildWithMargins(it, MeasureSpec.getMode(widthMeasureSpec), MeasureSpec.getMode(heightMeasureSpec))
        }

    }

    fun measureChildWithMargins(child: View, widthMode: Int, heightMode: Int) {
        val lp = child.layoutParams
        val widthSpec = getChildWidthMeasureSpec(measuredWidth, widthMode, paddingLeft + paddingRight, lp.width)
        val heightSpec =  MeasureSpec.makeMeasureSpec(lp.height, MeasureSpec.AT_MOST)//  getChildHeightMeasureSpec(measuredHeight, heightMode, paddingTop + paddingBottom, lp.height)
        child.measure(widthSpec, heightSpec)
    }

    /**
     * 获取child的width的MeasureSpec
     */
    fun getChildWidthMeasureSpec(parentSize: Int, parentMode: Int, padding: Int, childDimension: Int): Int {
        //获取margin属性
        val lp = layoutParams as MarginLayoutParams
        //计算item的宽度
        val width = (parentSize - padding - lp.leftMargin - lp.rightMargin - (columnsCount - 1) * horizontalSpacing.toInt()) / columnsCount

        var resultSize = 0
        var resultMode = 0
        if (childDimension >= 0) {
            resultSize = childDimension
            resultMode = MeasureSpec.EXACTLY
        } else {
            if (childDimension == LayoutParams.MATCH_PARENT) {
                resultSize = width
                resultMode = parentMode
            } else if (childDimension == LayoutParams.WRAP_CONTENT) {
                resultSize = Math.min(width, childDimension)
                if (parentMode == MeasureSpec.AT_MOST || parentMode == MeasureSpec.EXACTLY) {
                    resultMode = MeasureSpec.AT_MOST
                } else {
                    resultMode = MeasureSpec.UNSPECIFIED
                }
            }
        }
        return MeasureSpec.makeMeasureSpec(resultSize, resultMode)
    }

    /**
     * 获取child的height的MeasureSpec
     */
    fun getChildHeightMeasureSpec(parentSize: Int, parentMode: Int, padding: Int, childDimension: Int): Int {
        val size = Math.max(0, parentSize - padding)
        var resultSize = 0
        var resultMode = 0
        if (childDimension >= 0) {
            resultSize = childDimension
            resultMode = MeasureSpec.EXACTLY
        } else {
            if (childDimension == LayoutParams.MATCH_PARENT) {
                resultSize = size
                resultMode = parentMode
            } else if (childDimension == LayoutParams.WRAP_CONTENT) {
                resultSize = size
                if (parentMode == MeasureSpec.AT_MOST || parentMode == MeasureSpec.EXACTLY) {
                    resultMode = MeasureSpec.AT_MOST
                } else {
                    resultMode = MeasureSpec.UNSPECIFIED
                }
            }
        }
        return MeasureSpec.makeMeasureSpec(resultSize, resultMode)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        //记录每一列对应的left属性
        var lastLeftArray = IntArray(columnsCount)
        //获取margin属性
        val lp = layoutParams as MarginLayoutParams
        val itemWidth = (width - paddingLeft - paddingRight - lp.leftMargin - lp.rightMargin - (columnsCount - 1) * horizontalSpacing.toInt()) / columnsCount
        (0..columnsCount - 1).forEach { index -> lastLeftArray[index] = index * (itemWidth + horizontalSpacing.toInt()) }
        //记录每一列最后一个view的top属性
        var lastTopArray = IntArray(columnsCount)
        var column: Int = 0
        (0..childCount - 1).map { index -> getChildAt(index) }.forEach { childView ->
            childView.layout(lastLeftArray[column] + paddingLeft, lastTopArray[column] + paddingTop, lastLeftArray[column] + paddingLeft + childView.measuredWidth, lastTopArray[column] + paddingTop + measuredHeight)
            lastTopArray[column] += (childView.measuredHeight + verticalSpacing).toInt()
            //获取每列中最小的top值对应的column
            column = getMinIndex(lastTopArray)
        }

    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        when (ev.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                lastMoveY = ev.y
                scroller.abortAnimation()
                postInvalidate()
            }
            MotionEvent.ACTION_MOVE -> {
                val offsetY = (lastMoveY - ev.y).toInt()
                // 当手指拖动值大于TouchSlop值时，认为应该进行滚动，拦截子控件的事件
                if (Math.abs(offsetY) > touchSlop) {
                    isInterceptMoveTouch = true
                }
            }
            MotionEvent.ACTION_UP -> {
            }
            MotionEvent.ACTION_CANCEL -> {
            }
        }
        return isInterceptMoveTouch
    }

    override fun computeScroll() {
        super.computeScroll()
        if (!scroller.isFinished && scroller.computeScrollOffset()) {
            scrollTo(scroller.currX, scroller.currY)
            //必须调用View的postInvalidate()/invalidate()，如果不加会导致View的移动只会第一帧。
            invalidate()
        }
    }

    override fun onTouchEvent(ev: MotionEvent): Boolean {
        if (null == velocityTracker) {
            //创建VelocityTracker实例对象
            velocityTracker = VelocityTracker.obtain()
        }
        //把事件 MotionEvent 对象传递给VelocityTracker类实例
        velocityTracker?.addMovement(ev)
        when (ev.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                lastMoveY = ev.y
            }
            MotionEvent.ACTION_MOVE -> {
                val offsetY = (lastMoveY - ev.y).toInt()
                lastMoveY = ev.y
                scrollBy(0, offsetY)
            }
            MotionEvent.ACTION_UP -> {
                isInterceptMoveTouch = false
                val velocityTracker = velocityTracker
                if (null != velocityTracker) {
                    //求伪瞬时速度
                    velocityTracker?.computeCurrentVelocity(10000, scaledMaximumFlingVelocity.toFloat())
                    val yVelocity = velocityTracker.yVelocity
                    if (Math.abs(yVelocity) > scaledMinimumFlingVelocity) {
                        scroller.fling(scrollX, scrollY, 0, -yVelocity.toInt(), 0, width, 0, getChildAt(childCount - 1).bottom)
                        postInvalidate()
                    }
                    velocityTracker.clear()
                    velocityTracker.recycle()
                }
                this.velocityTracker = null
            }
            MotionEvent.ACTION_CANCEL -> {
                isInterceptMoveTouch = false
                velocityTracker?.clear()
                velocityTracker?.recycle()
                velocityTracker = null
            }
        }
        return true
    }

    /**
     * 获取数组中的最小值的下标
     */
    fun getMinIndex(arr: IntArray): Int {
        var index: Int = 0
        (0..arr?.size - 1)
                .asSequence()
                .filter { arr[it] < arr[index] }
                .forEach { index = it }
        return index

    }


    /**
     * 需要支持margin,直接使用系统的MarginLayoutParams
     */
    override fun generateLayoutParams(attrs: AttributeSet): LayoutParams {
        return MarginLayoutParams(context, attrs)
    }

}