package com.bask0xff.yagedemo

import android.content.Context
import android.content.pm.ActivityInfo
import android.os.*
import android.os.StrictMode.ThreadPolicy
import android.util.Log
import android.view.KeyEvent
import android.view.SurfaceView
import android.view.WindowManager
import android.widget.Button
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import com.bask0xff.yageclib.BaseScreen
import com.bask0xff.yageclib.GameLogic
import com.bask0xff.yageclib.GameLogic.IOnSurfaceCreatedListener
import com.bask0xff.yageclib.IScreen
import com.bask0xff.yageclib.MainGameView
import com.bask0xff.yagedemo.ui.MenuScreen
import com.bask0xff.yagedemo.ui.WordleScreen

class MainActivity : AppCompatActivity() {

    private val marginHorizontal = 20f
    private val marginBottom = 16f
    private val buttonHeight = 150f

    private val TAG = "MainActivity"

    var keyBackCounter = 0;
    private val SCREEN_NAME_WORDLE = "Wordle"
    private val SCREEN_NAME_MENU = "Menu"

    private var mainGameView //all game logic on one canvas: menu, game, settings, game over, etc
            : SurfaceView? = null

    private var gameLogic: GameLogic? = null

    private var context: Context? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (Build.VERSION.SDK_INT > 9) {
            val policy = ThreadPolicy.Builder().permitAll().build()
            StrictMode.setThreadPolicy(policy)
        }

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
//        requestWindowFeature(Window.FEATURE_NO_TITLE)

        if (true) window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        ) else window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN,
            WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN
        )

        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN, WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        context = this
        gameLogic = GameLogic(context as MainActivity, resources)

        addNewScreens(gameLogic!!)

        val layout = RelativeLayout(context)
        layout.addView(mainGameView)

        // Demonstration possibility to apply any Views over mainGameView. For example, for AdMob View, Buttons, e.t.c.
        val button = Button(this)

        gameLogic!!.SetOnSurfaceCreatedListener(listener = object : IOnSurfaceCreatedListener {
            override fun SurfaceCreated(w: Int, h: Int) {
                Log.d(TAG, "SurfaceCreated!!!!!: ${w} x ${h}")
                button.layoutParams = LinearLayout.LayoutParams( (w - 2 *marginHorizontal).toInt(), buttonHeight.toInt())
                button.x = marginHorizontal
                button.y = h - buttonHeight - marginBottom
                button.text = "Hit me!"

                button.setOnClickListener {
                    if( (gameLogic!!.ActiveScreen() as BaseScreen).Name() == SCREEN_NAME_WORDLE)
                        gameLogic!!.SetActiveScreen(SCREEN_NAME_MENU)
                    else
                        gameLogic!!.SetActiveScreen(SCREEN_NAME_WORDLE)
                }

                layout?.addView(button)
            }
        })

        //show canvas game screen
        setContentView(layout)

    }

    private fun addNewScreens(gameLogic: GameLogic) {
        val wordleScreen: IScreen = WordleScreen(SCREEN_NAME_WORDLE, gameLogic!!)
        gameLogic!!.AddScreen((wordleScreen as WordleScreen).Name(), wordleScreen)

        val menuScreen: IScreen = MenuScreen(SCREEN_NAME_MENU, gameLogic!!)
        gameLogic!!.AddScreen((menuScreen as MenuScreen).Name(), menuScreen)

        /*val loadingScreen: IScreen = LoadingScreen(SCREEN_NAME_LOADING, gameLogic)
        gameLogic.AddScreen((loadingScreen as LoadingScreen).Name(), loadingScreen)

        val newGame: IScreen = NewGameScreen(SCREEN_NAME_NEW_GAME, gameLogic)
        gameLogic.AddScreen((newGame as NewGameScreen).Name(), newGame)

        val settingsScreen: IScreen = SettingsScreen(SCREEN_NAME_SETTINGS, gameLogic)
        gameLogic.AddScreen((settingsScreen as SettingsScreen).Name(), settingsScreen)
       */

        mainGameView = MainGameView(this, gameLogic!!, wordleScreen)
        //mainGameView = (MainGameView) findViewById(R.id -or- layout.game);
    }


    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //saving game state
            Log.w(TAG, "KEY BACK PRESSED!")
            keyBackCounter++;
            if (
                keyBackCounter < 2 &&
                //gameLogic!!.ActiveScreen() is GameScreen ||
                gameLogic!!.ActiveScreen() is WordleScreen
            //|| gameLogic!!.ActiveScreen() is SettingsScreen
            ) {
                gameLogic?.SetActiveScreen(SCREEN_NAME_WORDLE)
                return super.onKeyDown(0, event)
            }
        }
        if (keyCode == KeyEvent.KEYCODE_HOME) {
            Log.w(TAG, "KEY HOME PRESSED!")
        }
        //gameLogic.SaveGames()
        return super.onKeyDown(keyCode, event)
    }

}