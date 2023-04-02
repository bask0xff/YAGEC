package com.bask0xff.yageclib

import android.graphics.Canvas
import android.util.Log
import android.view.MotionEvent

open class BaseScreen(private val name: String, private val gameLogic: GameLogic) : IScreen {

    private var swipeDirection: GameLogic.SwipeDirection = GameLogic.SwipeDirection.NONE
    private var swipeState = 0; // 0 -nothing, 1 - start, 2 - moving, 3 - end
    private var swipeDx = 0
    private var swipeDy = 0
    private var swipeGradient = 0
    private var touchStartX = 0
    private var touchStartY = 0
    private var touchEndX = 0
    private var touchEndY = 0

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
    override fun OnTouchDown(x: Float, y: Float) {
        swipeState = 1
        touchStartX = x.toInt()
        touchStartY = y.toInt()
    }
    override fun OnTouchUp(x: Float, y: Float) {
        swipeState = 2

        touchEndX = x.toInt()
        touchEndY = y.toInt()

        val dx = touchEndX - touchStartX
        var dy = touchEndY - touchStartY
        val dxa = Math.abs(dx)
        var dya = Math.abs(dy)

        if(dxa > 20 || dya > 20)
        {
            if(dxa > dya){
                if(dx > 0)
                    swipeDirection = GameLogic.SwipeDirection.SWIPE_RIGHT
                else
                    swipeDirection = GameLogic.SwipeDirection.SWIPE_LEFT
            }
            else{
                if(dy > 0)
                    swipeDirection = GameLogic.SwipeDirection.SWIPE_DOWN
                else
                    swipeDirection = GameLogic.SwipeDirection.SWIPE_UP
            }
            OnSwipe(swipeDirection)
        }

        swipeState = 0
    }
    override fun OnMove(event: MotionEvent) {
        swipeState = 2
    }

    override fun OnSwipe(swipeDirection: GameLogic.SwipeDirection) {

    }

    companion object {
        private val TAG = BaseScreen::class.java.simpleName
    }
}