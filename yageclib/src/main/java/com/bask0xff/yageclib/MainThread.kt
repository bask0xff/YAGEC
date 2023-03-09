package com.bask0xff.yageclib


import android.graphics.Canvas
import android.util.Log
import android.view.SurfaceHolder


/**
 * @author impaler
 *
 * The Main thread which contains the game loop. The thread must have access to
 * the surface view and holder to trigger events every game tick.
 */
class MainThread(surfaceHolder: SurfaceHolder, gamePanel: MainGameView) : Thread() {
    // Surface holder that can access the physical surface
    private val surfaceHolder: SurfaceHolder

    // The actual view that handles inputs
    // and draws to the surface
    private val gamePanel: MainGameView

    // flag to hold game state
    private var running = false
    private var isHidden = false
    fun setRunning(running: Boolean) {
        this.running = running
        isHidden = false
    }

    override fun run() {
        var canvas: Canvas?
        Log.d(TAG, "Starting game loop")
        var beginTime: Long // the time when the cycle begun
        var timeDiff: Long // the time it took for the cycle to execute
        var sleepTime: Int // ms to sleep (<0 if we're behind)
        var framesSkipped: Int // number of frames being skipped
        sleepTime = 0
        var frames = 0;

        while (running && !isHidden) {
            canvas = null
            // try locking the canvas for exclusive pixel editing
            // in the surface
            //Log.e(TAG, "run():" + isHidden);
            try {
                canvas = surfaceHolder.lockCanvas()
                synchronized(surfaceHolder) {
                    beginTime = System.currentTimeMillis()
                    framesSkipped = 0 // resetting the frames skipped
                    // update game state
                    gamePanel.update()
                    // render state to the screen
                    // draws the canvas on the panel
                    frames++
                    gamePanel.render(frames, canvas)
                    // calculate how long did the cycle take
                    timeDiff = System.currentTimeMillis() - beginTime
                    // calculate sleep time
                    sleepTime = (FRAME_PERIOD - timeDiff).toInt()
                    if (sleepTime > 0) {
                        // if sleepTime > 0 we're OK
                        try {
                            // send the thread to sleep for a short period
                            // very useful for battery saving
                            sleep(sleepTime.toLong())
                        } catch (e: InterruptedException) {
                            Log.i(
                                TAG,
                                "exception in sleep : " + e.message
                            )
                        }
                    }
                    while (sleepTime < 0 && framesSkipped < MAX_FRAME_SKIPS) {
                        // we need to catch up
                        gamePanel.update() // update without rendering
                        sleepTime += FRAME_PERIOD // add frame period to check if in next frame
                        framesSkipped++
                    }
                }
            } finally {
                // in case of an exception the surface is not left in
                // an inconsistent state
                if (canvas != null) {
                    surfaceHolder.unlockCanvasAndPost(canvas)
                }
            } // end finally
        }
    }

    fun onSurfaceDestroyed() {
        //isHidden = true;
    }

    fun setNotHidden() {
        isHidden = false
    }

    companion object {
        private val TAG = MainThread::class.java.simpleName

        // desired fps
        private const val MAX_FPS = 150

        // maximum number of frames to be skipped
        private const val MAX_FRAME_SKIPS = 5

        // the frame period
        private const val FRAME_PERIOD = 1000 / MAX_FPS
    }

    init {
        Log.i(TAG, "MainThread...")
        this.surfaceHolder = surfaceHolder
        this.gamePanel = gamePanel
    }
}
