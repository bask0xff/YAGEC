package com.bask0xff.yageclib


import android.content.Context
import android.content.res.Resources
import android.util.Log

open class GameLogic(context: Context, resources: Resources) {

    //default screen values, but they are would be replaced by new values of real screen size
    private var width = 0
    private var height  = 0

    private val TAG = GameLogic::class.java.simpleName
    private var screens: HashMap<String, IScreen?> = java.util.HashMap()
    private var activeScreen: IScreen? = null
    private var ticks = 0
    private var resources: Resources? = null
    private var context: Context? = null

    enum class SwipeDirection { NONE, SWIPE_LEFT, SWIPE_RIGHT, SWIPE_UP, SWIPE_DOWN, PINCH }

    init{
        this.resources = resources
        this.context = context
        screens = HashMap()
    }

    interface IOnSurfaceCreatedListener{
        fun SurfaceCreated(w: Int, h: Int)
    }

    lateinit var onSurfaceCreatedListener: IOnSurfaceCreatedListener

    fun SetOnSurfaceCreatedListener(listener: IOnSurfaceCreatedListener){
        this.onSurfaceCreatedListener = listener
    }

    fun SurfaceChanged(w: Int, h: Int) {
        width = w
        height = h
        Log.d(TAG, "CreateSurface: Window size: $width x $height")
        onSurfaceCreatedListener?.SurfaceCreated(width, height)
    }

    // if you want to test your app in different screen sizes, you can change it manually
    open fun width(): Int { return width}
    open fun height(): Int { return height}

    fun ActiveScreen(): IScreen? {
        return activeScreen
    }

    //RegisterScreen
    fun AddScreen(name: String, screen: IScreen?) {
        if (screens.get(name) == null) {
            screens.put(name, screen)
            Log.i(TAG, "new screen added: $name")
        }
    }

    fun RemoveScreen(name: String?) {
        screens.remove(name)
    }

    fun FindScreen(name: String?): IScreen? {
        val s: IScreen? = screens.get(name)
        Log.i(TAG, "screen found: $s")
        return s
    }

    fun SetActiveScreen(screen: IScreen) {
        Log.d(TAG, "SetActiveScreen: $screen")
        activeScreen = screen
        activeScreen?.OnCreate()
    }

    fun SetActiveScreen(name: String) {
        Log.i(TAG, "SetActiveScreen to $name")
        //current screen becomes old
        activeScreen!!.OnHide()

        //new screen
        activeScreen = FindScreen(name)
        Log.i(TAG, "ActiveScreen=$activeScreen")
        Log.i(TAG, "ActiveScreen is $name")
        activeScreen?.OnShow()
        Log.i(TAG, "ActiveScreen $name shown")
    }

    fun onSurfaceCreated(function: () -> Unit) {
        Log.d(TAG, "onSurfaceCreated: ")
    }

}

