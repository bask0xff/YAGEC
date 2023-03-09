package com.bask0xff.yagedemo.ui

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.Log
import com.bask0xff.yageclib.BaseScreen
import com.bask0xff.yageclib.GameLogic
import com.bask0xff.yageclib.IScreen

class MenuScreen(name: String, private val gameLogic: GameLogic) :
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

    override fun OnTouch(x: Float, y: Float) {
        Strike()
        //SwitchScreen(BUTTON_NAME_MENU);
    }

    private fun Strike() {}

    override fun OnDestroy() {
        Log.d(TAG, "OnDestroy: ")
    }

    override fun SwitchScreen(screen: IScreen) {}

    override fun Render(frames: Int, canvas: Canvas?) {
        canvas?.drawColor(Color.argb(255, 111, 111, 111))
        val paint = Paint()
        paint.color = Color.YELLOW
        paint.isAntiAlias = true
        paint.textSize = 42f//(Math.random()*100).toFloat()

        canvas?.drawText("MENU: $frames", ((50f + Math.random()*0f-10f).toFloat()), ((150f + Math.random()*0f-10f).toFloat()), paint);

        paint.isAntiAlias = true
        for(i in 0..10){
            paint.setColor(Color.argb(255, (255f * ((i)/10f)).toInt(), frames % 255, 0));
            canvas?.drawCircle(555f, 777f, 150f-i, paint);
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

