package com.bask0xff.yageclib


import android.content.Context
import android.graphics.Canvas
import android.util.Log
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import java.lang.Thread.State


/**
 * Simple Arkanoid
 * @author impaler
 * This is the main surface that handles the ontouch events and draws
 * the image to the screen.
 */
class MainGameView(context: Context?, gameLogic: GameLogic, startScreen: IScreen) :
    SurfaceView(context), SurfaceHolder.Callback {

    private val thread: MainThread

    //private Game game;//no! we can have many games
    private val gameLogic //GameLogic + MainGameView ?
            : GameLogic

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
        Log.i(TAG, "surfaceChanged")
        gameLogic.SurfaceChanged(width, height)
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        // at this point the surface is created and
        // we can safely start the game loop
        Log.i(TAG, "surfaceCreated: $width x $height")
        gameLogic.SurfaceChanged(width, height)

        //solve problem:
        ////java.lang.IllegalThreadStateException: Thread already started.
        val state = Thread.currentThread().state
        Log.i("Thread.State", "" + state)
        try {
            if (state != State.NEW) {
                //thread.join();
            }
            //else
            run {
                thread.setRunning(true)
                thread.start()
            }
        } catch (e: Exception) {
            Log.i(TAG, "surfaceCreated, thread error:" + e.message)
        }
        gameLogic.ActiveScreen()?.OnCreate()
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        Log.d(TAG, "Surface is being destroyed")
        // tell the thread to shut down and wait for it to finish
        // this is a clean shutdown
        thread.onSurfaceDestroyed()
        if (false) {
            thread.setRunning(false)
            var retry = true
            while (retry) {
                try {
                    Log.d(TAG, "surfaceDestroyed(): joining...")
                    thread.join()
                    Log.d(TAG, "surfaceDestroyed(): joined")
                    retry = false
                } catch (e: InterruptedException) {
                    Log.d(
                        TAG,
                        "surfaceDestroyed(): try again shutting down the thread, error:" + e.message
                    )
                }
            }
            Log.d(TAG, "Thread was shut down cleanly")
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            //check if in the left upper part of the screen we exit
            /*if (event.getY() < game.CardSize() && event.getX() < game.CardSize()) {
				thread.setRunning(false);
				((Activity)getContext()).finish();
			}*/
            val activeScreen: IScreen = gameLogic.ActiveScreen()!!
            activeScreen.OnTouchDown(event.x, event.y)
        }
        if (event.action == MotionEvent.ACTION_MOVE) {
            // the gestures
            val activeScreen: IScreen = gameLogic.ActiveScreen()!!
            activeScreen.OnMove(event)
        }
        if (event.action == MotionEvent.ACTION_UP) {
            val activeScreen: IScreen = gameLogic.ActiveScreen()!!
            activeScreen.OnTouchUp(event.x, event.y)
        }
        return true
    }

    fun render(frames: Int, canvas: Canvas?) {
        if (canvas == null) return

        //Log.i("Render", ((BaseScreen)gameLogic.ActiveScreen()).Name());
        //Log.i(TAG, "render()");
        gameLogic.ActiveScreen()?.Render(frames, canvas)
    }

    /**
     * This is the game update method. It iterates through all the objects
     * and calls their update method if they have one or calls specific
     * engine's update method.
     */
    fun update() {
        gameLogic.ActiveScreen()?.Update()
    }

    companion object {
        private val TAG = MainGameView::class.java.simpleName
    }

    init {
        holder.addCallback(this)
        this.gameLogic = gameLogic
        // create the game loop thread
        thread = MainThread(holder, this)

        gameLogic.SetActiveScreen(startScreen)

        isFocusable = true
    }
}

