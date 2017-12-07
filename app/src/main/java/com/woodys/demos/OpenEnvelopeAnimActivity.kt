package com.woodys.demos

import android.animation.*
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.ViewTreeObserver
import android.view.animation.*
import android.view.animation.Animation.REVERSE
import kotlinx.android.synthetic.main.activity_animation.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import java.util.*


class OpenEnvelopeAnimActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_animation)
        window.decorView.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                val height = rl_cardContent.height
                if (0 != height) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        window.decorView.viewTreeObserver.removeOnGlobalLayoutListener(this)
                    } else {
                        window.decorView.viewTreeObserver.removeGlobalOnLayoutListener(this)
                    }

                    val location1 = IntArray(2)
                    stvLoanTime.getLocationInWindow(location1)

                    val location2 = IntArray(2)
                    ll_container.getLocationInWindow(location2)

                    val bottom = location2[1]-location1[1]
                    val translationY = bottom*1f

                    runAnimator(translationY)
                }
            }
        })

        applyNowView.onClick {
            val location1 = IntArray(2)
            stvLoanTime.getLocationInWindow(location1)

            val location2 = IntArray(2)
            ll_container.getLocationInWindow(location2)

            val bottom = location2[1]-location1[1]
            val translationY = bottom*1f

            runAnimator(translationY)
        }
    }

    fun runAnimator(translationY:Float) {

        rl_cardContent.translationY = translationY

        // view 移动
        val animator1 = ObjectAnimator.ofFloat(rl_cardContent, "translationY", translationY, 0f)
        animator1.interpolator = AccelerateInterpolator()
        animator1.duration = 800

        val animator2 = ObjectAnimator.ofFloat(rlCardContent, "translationY", 0f, 30f,20f,0f)
        animator2.interpolator = OvershootInterpolator()
        //animator2.duration = 1500
        animator2.duration = 1500
        animator2.repeatMode = ValueAnimator.REVERSE


        val animator3 = ObjectAnimator.ofFloat(image1, "translationY", 0f, -10f, 0f)
        animator3.interpolator = BounceInterpolator()
        //animator3.duration = 2200
        animator3.duration = 2000
        animator2.repeatMode = ValueAnimator.REVERSE

        val animatorSet = AnimatorSet()
        animatorSet.play(animator1).before(animator2).before(animator3)
        animatorSet.start()
    }

}
