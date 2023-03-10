package com.bask0xff.yagedemo.ui

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.Log
import android.view.MotionEvent
import com.bask0xff.yageclib.BaseScreen
import com.bask0xff.yageclib.GameLogic
import com.bask0xff.yageclib.IScreen

class WordleScreen(name: String, private val gameLogic: GameLogic) :
    BaseScreen(name, gameLogic) {

    private var ticks = 0
    private fun init() {
        for (j in 0..5) for (i in 0..4) {
        }
    }

    override fun OnCreate() {
        Log.d(TAG, "OnCreate ")
    }

    override fun OnShow() {
        Log.d(TAG, "OnShow: ")
        if (true) {
            init()
        }
    }

    override fun OnTouchUp(x: Float, y: Float) {
        //SwitchScreen(BUTTON_NAME_MENU);
        Log.d(TAG, "OnTouchUp: ${x.toInt()}, ${y.toInt()}")
    }

    override fun OnMove(event: MotionEvent) {
        Log.d(TAG, "OnMove ${event.action}: ${event.x.toInt()}, ${event.y.toInt()}")
    }

    override fun OnTouchDown(x: Float, y: Float) {
        Log.d(TAG, "OnTouchDown: ${x.toInt()}, ${y.toInt()}")
    }

    override fun OnDestroy() {
        Log.d(TAG, "OnDestroy: ")
    }

    override fun SwitchScreen(screen: IScreen) {}

    override fun Render(frames: Int, canvas: Canvas?) {
        canvas?.drawColor(Color.argb(255, 111, 111, 111))
        val paint = Paint()
        paint.color = Color.YELLOW
        paint.isAntiAlias = true
        paint.textSize = 36f

        canvas?.drawText("MAIN: ${gameLogic.width()} x ${gameLogic.height()} $ticks / $frames", 50f, 50f, paint);

        paint.isAntiAlias = true
        for(i in 0..10){
            paint.setColor(Color.argb(255, (255f * ((i)/10f)).toInt(), frames % 255, 0));
            canvas?.drawCircle(gameLogic.width()/2f, gameLogic.height()/4f, 50f-i, paint);
        }

        ticks++
    }

    override fun Destroy() {}

    override fun Update() {}

    companion object {
        private val TAG = WordleScreen::class.java.simpleName
    }

    init {
        Log.d(TAG, "WordleScreen: $name")
    }
}

