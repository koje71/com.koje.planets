package com.koje.planets.view

import android.view.WindowManager
import com.koje.framework.events.IntNotifier
import com.koje.framework.events.StringNotifier
import com.koje.framework.view.BaseActivity
import com.koje.framework.view.FrameLayoutBuilder
import com.koje.framework.view.LinearLayoutBuilder
import com.koje.framework.view.TextViewBuilder
import com.koje.planets.R
import com.koje.planets.core.Playground


class Activity : BaseActivity() {

    override fun createLayout(target: FrameLayoutBuilder) {
        with(window) {
            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            // statusBarColor = target.getColorFromID(R.color.white)
            // navigationBarColor = target.getColorFromID(R.color.black)
        }
        actionBar?.hide()

        with(target) {
            addLinearLayout {
                setOrientationVertical()
                createHeader(this)
                createContent(this)
                createFooter(this)
            }
        }
    }

    private fun createHeader(target: LinearLayoutBuilder) {
        with(target) {
            addLinearLayout {
                setOrientationHorizontal()
                setBackgroundColorId(R.color.white)
                setPaddingsDP(10, 0)
                addTextView {
                    setTextColorID(R.color.black)
                    setTextSizeSP(30)
                    setFontId(R.font.nunito_bold)
                    setText("Star Conflict")
                }
                addFiller()
                addImageView {
                    setMarginsDP(0, 10, 0, 0)
                    setDrawableId(R.drawable.menu)
                    setWidthDP(60)
                    setHeightDP(40)

                    setOnClickListener {
                        BoardSelection.visible.switch()
                    }
                }
            }
        }
    }

    private fun createContent(target: LinearLayoutBuilder) {
        with(target) {
            addFrameLayout {
                setLayoutWeight(1f)
                setWidthMatchParent()
                addSurfaceView {
                    setSurface(Playground)
                    setSizeMatchParent()
                }
                addLinearLayout {
                    setOrientationVertical()
                    addLinearLayout {
                        setOrientationHorizontal()
                        setMarginsDP(5, 0, 5, 0)
                        addTextView {
                            formatLabel(this)
                            addReceiver(board) {
                                setText("$it Sector")
                            }
                        }
                        addFiller()
                        addTextView {
                            formatLabel(this)
                            setGravityRight()
                            addReceiver(Playground.reloadControls) {
                                setText("Tech Level ${Playground.board.selection.team.level}")
                            }
                        }
                    }
                    addLinearLayout {
                        setOrientationHorizontal()
                        setMarginsDP(5, -10, 5, 0)
                        addTextView {
                            formatLabel(this)
                            setGravityRight()
                            setTextColorID(R.color.white)
                            addReceiver(year) {
                                setText("Year $it")
                            }
                        }
                    }
                    addFiller()
                    addFrameLayout {
                        setHeightDP(60)
                        setPaddingsDP(5, 0)
                        setWidthMatchParent()

                        addRelativeLayout {
                            setSizeMatchParent()
                            setGravityCenterLeft()
                            addTextView {
                                formatLabel(this)
                                addReceiver(Playground.reloadControls) {
                                    val selection = Playground.board.selection
                                    with(StringBuilder()) {
                                        append(selection.population.civilianCount)
                                        append("/")
                                        append(selection.blueStrategy.civilianCount)
                                        setText(toString())
                                    }
                                }
                            }
                        }

                        addRelativeLayout {
                            setSizeMatchParent()
                            setGravityCenterRight()
                            addTextView {
                                formatLabel(this)
                                addReceiver(Playground.reloadControls) {
                                    val selection = Playground.board.selection
                                    with(StringBuilder()) {
                                        append(selection.population.militaryCount)
                                        append("/")
                                        append(selection.blueStrategy.militaryCount)
                                        setText(toString())
                                    }
                                }
                            }
                        }
                    }

                }
                addRelativeLayout {
                    setSizeMatchParent()
                    setGravityCenter()
                    add(BoardSelection())
                }
            }
        }
    }

    fun formatLabel(target: TextViewBuilder) {
        with(target) {
            setTextColorID(R.color.white)
            setFontId(R.font.nunito_bold)
            setTextSizeSP(20)
        }

    }

    private fun createFooter(target: LinearLayoutBuilder) {
    }


    companion object {
        val year = IntNotifier(0)
        val board = StringNotifier("")
    }
}


