package com.koje.planets.core

import android.view.MotionEvent
import com.koje.animals.core.Background
import com.koje.framework.events.Notifier
import com.koje.framework.graphics.ComponentGroup
import com.koje.framework.graphics.Position
import com.koje.framework.graphics.Surface
import com.koje.planets.R
import com.koje.planets.view.Activity
import javax.microedition.khronos.opengles.GL10
import kotlin.random.Random

object Playground : Surface() {

    val random = Random(42)
    val picmap = createImageDrawer(R.drawable.picmap)
    var board: Board = Board02()
    val boardHolder = ComponentGroup(this)
    var civilianControlBar = BuildControlBar(Civilian)
    var militaryControlBar = BuildControlBar(Military)

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        super.onSurfaceChanged(gl, width, height)

    }

    init {
        with(components) {
            addComponent(Background())
            addComponent(boardHolder)
            addComponent(BuildProgressView())
            addComponent(PowerBalanceBar())
            addComponent(civilianControlBar)
            addComponent(militaryControlBar)

            addProcedure {
                Activity.year.set((board.totalTime / 4000).toInt())
            }
        }

        loadBoard(Board02(), false)
    }

    fun loadBoard(creation: Board, animated: Boolean = true) {
        Activity.board.set(creation.name)
        boardHolder.addComponent(creation)

        with(board) {
            if (animated) {
                addProcedure {
                    progress += 0.001f * loopTime
                    //move(progress,0f)
                    scale(1f - progress)
                    if (progress > 1f) {
                        death = true
                    }
                }
            } else {
                death = true
            }
        }

        board = creation

        with(board) {
            if (animated) {
                addProcedure {
                    progress += 0.001f * loopTime
                    scale(progress)
                }
            }
        }
    }


    override fun onTouch(position: Position, event: MotionEvent) {
        if (civilianControlBar.onTouch(position, event)) {
            return
        }

        if (militaryControlBar.onTouch(position, event)) {
            return
        }

        board.onTouch(position, event)
    }

    val reloadControls = Notifier(Any())

}