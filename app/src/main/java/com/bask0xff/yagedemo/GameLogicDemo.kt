package com.bask0xff.yagedemo

import android.content.Context
import android.content.res.Resources
import com.bask0xff.yageclib.GameLogic
import com.bask0xff.yageclib.IScreen

class GameLogicDemo(context: Context, resources: Resources): GameLogic(context, resources) {

    private val TAG = GameLogicDemo::class.java.simpleName
    private var ticks = 0
    private var resources: Resources? = null
    private var context: Context? = null

    init {
        this.resources = resources
        this.context = context
    }

    override fun width(): Int {
        return (1080 * .3f).toInt() // Realme GT Master Edition
        //return 576//(super.width() * .5f).toInt()
    }

    override fun height(): Int {
        return (2400 * .3f).toInt() // Realme GT Master Edition
        //return 1280//(super.height() * 1.0f).toInt()
    }

    fun Resources(): Resources? {
        return this.resources
    }

    fun exitGame(): Boolean {
        return false
    }

    fun getContext(): Context {
        return context!!
    }

}
