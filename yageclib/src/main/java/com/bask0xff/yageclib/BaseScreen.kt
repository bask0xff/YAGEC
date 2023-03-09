package com.bask0xff.yageclib


import android.graphics.Canvas
import android.util.Log
import android.view.MotionEvent

open class BaseScreen(private val name: String, private val gameLogic: GameLogic) : IScreen {
    fun Name(): String {
        return name
    }

    fun GetGameLogic(): GameLogic {
        return gameLogic
    }

    override fun SwitchScreen(screen: IScreen) {
        gameLogic.SetActiveScreen(screen)
    }

    override fun SwitchScreen(name: String) {
        Log.i(TAG, "SwitchScreen to $name")
        gameLogic.SetActiveScreen(name)
    }

    override fun OnDestroy() {}
    override fun OnShow() {
        Log.i(TAG, "Show:$name")
    }

    override fun OnHide() {
        Log.i(TAG, "Hide:$name")
    }

    override fun OnCreate() {}
    override fun Render(frames: Int, canvas: Canvas?) {}
    override fun Update() {}
    override fun Destroy() {}
    override fun OnTouch(x: Float, y: Float) {}
    override fun OnMove(event: MotionEvent) {}

    companion object {
        private val TAG = BaseScreen::class.java.simpleName
    }
}