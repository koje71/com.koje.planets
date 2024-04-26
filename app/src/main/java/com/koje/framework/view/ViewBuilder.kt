package com.koje.framework.view

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.StateListDrawable
import android.os.Build
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import com.koje.framework.App
import com.koje.framework.events.Notifier
import com.koje.framework.events.Receiver

open class ViewBuilder(open val view: View) {

    interface Editor : ViewEditor<ViewBuilder>

    val receivers = mutableListOf<Receiver<*>>()

    fun <T> add(editor: ViewEditor<T>) {
        editor.edit(this as T)
    }

    fun getDpPx(value: Int): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, value.toFloat(), view.resources.displayMetrics
        ).toInt()
    }

    fun <T> addReceiver(notifier: Notifier<T>, action: (arg: T) -> Unit) {
        if (view.getTag(App.idReceivers) == null) {
            view.setTag(App.idReceivers, receivers)
        }
        receivers.add(Receiver(notifier, action))
    }

    fun getColorFromID(id: Int): Int {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            view.resources.getColor(id, null)
        } else {
            return 0
        }
    }

    fun getContext(): Context {
        return view.context
    }

    fun getCornerRadii(size: Float): FloatArray {
        return floatArrayOf(size, size, size, size, size, size, size, size)
    }

    fun getPixelFromDP(value: Int): Int {
        val density = view.context.resources.displayMetrics.density
        return (value * density).toInt()
    }

    fun getScreenHeightPX(): Int {
        return view.resources.displayMetrics.heightPixels
    }

    fun getScreenWithDP(): Int {
        return (getScreenWithPX() / view.getResources().getDisplayMetrics().density).toInt()
    }

    fun getScreenWithPX(): Int {
        return view.resources.displayMetrics.widthPixels
    }

    fun setBackgroundColor(value: Int) {
        view.setBackgroundColor(value)
    }

    fun setBackgroundColorId(value: Int) {
        setBackgroundColor(ContextCompat.getColor(view.context, value))
    }

    fun setGradientBackground(action: GradientDrawable.() -> Unit) {
        val gradient = GradientDrawable()
        action.invoke(gradient)
        view.background = gradient
    }

    fun setGradientBackgroundVertical(id1: Int, id2: Int) {
        view.setBackgroundDrawable(
            GradientDrawable(
                GradientDrawable.Orientation.TOP_BOTTOM, intArrayOf(
                    getColorFromID(id1),
                    getColorFromID(id2)
                )
            )
        )
    }

    fun setHeightDP(value: Int) {
        setHeightPX(getPixelFromDP(value))
    }

    fun setHeightMatchParent() {
        view.getLayoutParams().height = ViewGroup.LayoutParams.MATCH_PARENT
    }

    fun setHeightPX(value: Int) {
        view.getLayoutParams().height = value
    }

    fun setHeightRE(value: Float) {
        setHeightPX((value * getScreenWithPX()).toInt())
    }

    fun setHeightWrapContent() {
        view.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT
    }

    fun setLayoutWeight(value: Float) {
        if (view.layoutParams is LinearLayout.LayoutParams) {
            val params = view.layoutParams as LinearLayout.LayoutParams
            params.weight = value
        }
    }

    fun setMarginsDP(left: Int, top: Int, right: Int, bottom: Int) {
        val leftPX = getPixelFromDP(left)
        val topPX = getPixelFromDP(top)
        val rightPX = getPixelFromDP(right)
        val bottomPX = getPixelFromDP(bottom)

        setMarginsPX(leftPX, topPX, rightPX, bottomPX)
    }

    fun setMarginsPX(left: Int, top: Int, right: Int, bottom: Int) {
        if (view.layoutParams is ViewGroup.MarginLayoutParams) {
            val params = view.layoutParams as ViewGroup.MarginLayoutParams
            params.setMargins(left, top, right, bottom)
        }
    }

    fun setOnClickListener(action: () -> Unit) {
        view.setOnClickListener {
            action.invoke()
        }
    }

    fun setPaddingsDP(h: Int, v: Int) {
        val hpx = getPixelFromDP(h)
        val vpx = getPixelFromDP(v)
        view.setPadding(hpx, vpx, hpx, vpx)
    }

    fun setPaddingsDP(l: Int, t: Int, r: Int, b: Int) {
        val lpx = getPixelFromDP(l)
        val tpx = getPixelFromDP(t)
        val rpx = getPixelFromDP(r)
        val bpx = getPixelFromDP(b)
        view.setPadding(lpx, tpx, rpx, bpx)
    }

    fun setSizeDP(value: Int) {
        setHeightDP(value)
        setWidthDP(value)
    }

    fun setSizeMatchParent() {
        setHeightMatchParent()
        setWidthMatchParent()
    }

    fun setSizeWrapContent() {
        setHeightWrapContent()
        setWidthWrapContent()
    }

    fun setBackgroundGradient(action: GradientDrawableBuilder.() -> Unit) {
        val result = GradientDrawable()
        GradientDrawableBuilder(this, result).action()
        view.background = result
    }

    fun setBackgroundStateList(action: StateListDrawableBuilder.() -> Unit) {
        val result = StateListDrawable()
        StateListDrawableBuilder(this, result).action()
        view.background = result
    }


    fun setVisibleGone() {
        if (view.visibility != View.GONE) {
            view.visibility = View.GONE
        }
    }

    fun setVisibleTrue() {
        if (view.visibility != View.VISIBLE) {
            view.visibility = View.VISIBLE
        }
    }

    fun setVisibleFalse() {
        if (view.visibility != View.INVISIBLE) {
            view.visibility = View.INVISIBLE
        }
    }

    fun setWidthDP(value: Int) {
        setWidthPX(getPixelFromDP(value))
    }

    fun setWidthMatchParent() {
        view.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT
    }

    fun setWidthPX(value: Int) {
        view.getLayoutParams().width = value
    }

    fun setWidthRE(value: Float) {
        setWidthPX((value * getScreenWithPX()).toInt())
    }

    fun setWidthWrapContent() {
        view.getLayoutParams().width = ViewGroup.LayoutParams.WRAP_CONTENT
    }

    fun <T> use(target: ViewEditor<T>) {
        target.edit(this as T)
    }


}