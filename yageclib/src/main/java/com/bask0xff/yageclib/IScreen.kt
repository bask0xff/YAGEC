package com.bask0xff.yageclib

import android.graphics.Canvas
import android.view.MotionEvent

interface IScreen {
    fun OnCreate()
    fun OnShow()
    fun OnHide()
    fun Render(frames: Int, canvas: Canvas?)
    fun Update()
    fun Destroy()
    fun OnTouchDown(x: Float, y: Float)
    fun OnTouchUp(x: Float, y: Float)
    fun OnTouch(x: Float, y: Float)
    fun OnMove(event: MotionEvent)
    fun SwitchScreen(screen: IScreen)
    fun SwitchScreen(name: String)
    fun OnDestroy()

    fun OnSwipe(swipeDirection: GameLogic.SwipeDirection)
}
