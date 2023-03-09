package com.bask0xff.yageclib

import android.graphics.Canvas

interface IScreen {
    fun OnCreate()
    fun OnShow()
    fun OnHide()
    fun Render(frames: Int, canvas: Canvas?)
    fun Update()
    fun Destroy()
    fun OnTouch(x: Float, y: Float)
    fun SwitchScreen(screen: IScreen)
    fun SwitchScreen(name: String)
    fun OnDestroy()
}
