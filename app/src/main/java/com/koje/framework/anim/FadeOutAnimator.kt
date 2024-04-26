package com.koje.framework.anim

import android.animation.Animator
import android.animation.ObjectAnimator
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator

class FadeOutAnimator(val view: View, val group: ViewGroup) : Animator.AnimatorListener {

    fun start() {
        with(ObjectAnimator.ofFloat(view, View.ALPHA, 1f, 0f)) {
            duration = 200
            startDelay = 0
            interpolator = DecelerateInterpolator()
            addListener(this@FadeOutAnimator)
            start()
        }
    }

    override fun onAnimationStart(animation: Animator) {
    }

    override fun onAnimationEnd(animation: Animator) {
        group.removeView(view)
    }

    override fun onAnimationCancel(animation: Animator) {
    }

    override fun onAnimationRepeat(animation: Animator) {
    }


}