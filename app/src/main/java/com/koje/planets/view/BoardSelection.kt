package com.koje.planets.view

import com.koje.framework.events.BooleanNotifier
import com.koje.framework.view.LinearLayoutBuilder
import com.koje.framework.view.RelativeLayoutBuilder
import com.koje.planets.R
import com.koje.planets.core.Board
import com.koje.planets.core.Board01
import com.koje.planets.core.Board02
import com.koje.planets.core.Playground

class BoardSelection : RelativeLayoutBuilder.Editor {

    override fun edit(target: RelativeLayoutBuilder) {
        with(target) {
            addReceiver(visible) {
                when (it) {
                    true -> setVisibleTrue()
                    else -> setVisibleGone()
                }
            }

            setOnClickListener {
                visible.switch()
            }

            setBackgroundColorId(R.color.black70)
            addLinearLayout {
                setOrientationVertical()
                formatDialog(this)
                addTextView {
                    setText("Select Sector")
                    setWidthMatchParent()
                    setGravityCenter()
                    setTextColorID(R.color.black)
                    setFontId(R.font.nunito_bold)
                    setTextSizeSP(30)
                    setMarginsDP(5, 5, 5, 20)
                }

                addLinearLayout {
                    setOrientationHorizontal()
                    setWidthMatchParent()
                    createButton(this, "\u03B1", Board01::class.java) // alpha
                    createButton(this, "\u03B2", Board02::class.java)
                    createButton(this, "\u03B3", null)
                    createButton(this, "\u03B4", null)
                    createButton(this, "\u03B5", null)
                    createButton(this, "\u03B6", null)
                }
                addLinearLayout {
                    setOrientationHorizontal()
                    setWidthMatchParent()
                    createButton(this, "\u03B7", null) // \u03B3
                    createButton(this, "\u03B8", null)
                    createButton(this, "\u03B9", null)
                    createButton(this, "\u03BA", null)
                    createButton(this, "\u03BB", null)
                    createButton(this, "\u03BC", null)
                }
            }
        }
    }

    fun formatDialog(target: LinearLayoutBuilder) {
        with(target) {
            setWidthDP(300)
            setPaddingsDP(10, 10)
            setBackgroundColorId(R.color.white)

            setBackgroundGradient {
                setColorId(R.color.white)
                setCornerRadius(10)
                setStroke(2, R.color.black)
            }
        }
    }

    fun createButton(target: LinearLayoutBuilder, sector: String, board: Class<out Board>?) {
        with(target) {
            addTextView {
                setText(sector)
                setMarginsDP(4, 4, 4, 4)
                setPaddingsDP(2, 2, 2, 2)
                setTextSizeSP(35)
                setWidthDP(10)
                setLayoutWeight(1f)
                setGravityCenter()
                if (board != null) {
                    setTextColorID(R.color.black)
                    setOnClickListener {
                        Playground.loadBoard(board.getDeclaredConstructor().newInstance())
                        visible.switch()
                    }
                } else {
                    setTextColorID(R.color.black40)
                }
                setBackgroundGradient {
                    setColorId(R.color.white)
                    setCornerRadius(10)
                    setStroke(2, R.color.black)
                }
            }
        }
    }


    companion object {
        val visible = BooleanNotifier(false)
    }
}