package com.koje.framework.anim

import android.animation.Animator
import android.animation.ObjectAnimator
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator

class FadeInAnimator(val view: View, val group: ViewGroup) : Animator.AnimatorListener {

    fun start() {
        with(ObjectAnimator.ofFloat(view, View.ALPHA, 0f, 1f)) {
            duration = 200
            startDelay = 0
            interpolator = DecelerateInterpolator()
            addListener(this@FadeInAnimator)
            start()
        }
    }

    override fun onAnimationStart(animation: Animator) {
        group.addView(view)
    }

    override fun onAnimationEnd(animation: Animator) {
    }

    override fun onAnimationCancel(animation: Animator) {
    }

    override fun onAnimationRepeat(animation: Animator) {
    }
}