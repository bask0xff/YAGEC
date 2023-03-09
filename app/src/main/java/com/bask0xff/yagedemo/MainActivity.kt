package com.bask0xff.yagedemo


import android.content.Context
import android.content.pm.ActivityInfo
import android.graphics.Bitmap
import android.graphics.Canvas
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
import com.bask0xff.yageclib.GameLogic
import com.bask0xff.yageclib.IScreen
import com.bask0xff.yageclib.MainGameView
import com.bask0xff.yagedemo.ui.WordleScreen
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"
    private val mt: MyTask? = null
    private val myExecutor = Executors.newSingleThreadExecutor()
    private val myHandler = Handler(Looper.getMainLooper())

    private var mainGameView //all game logic on one canvas: menu, game, settings, game over, etc
            : SurfaceView? = null
    var bitmap: Bitmap? = null
    var canvas: Canvas? = null
    private var gameLogic: GameLogic? = null
    private val wordsDictionary: ArrayList<String>? = null
    private var context: Context? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val myInput = 100
        MyTask("Tasks").execute(myInput)

        doMyTask(myInput)

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

        val wordleScreen: IScreen = WordleScreen("WordleScreen", gameLogic!!)
        gameLogic!!.AddScreen((wordleScreen as WordleScreen).Name(), wordleScreen)

        mainGameView = MainGameView(this, gameLogic!!, wordleScreen)
        //mainGameView = (MainGameView) findViewById(R.id -or- layout.game);

        //mainGameView = (MainGameView) findViewById(R.id -or- layout.game);
        val layout = RelativeLayout(context)
        layout.addView(mainGameView)

        val button = Button(this)
        // setting height and width of imageview
        button.layoutParams = LinearLayout.LayoutParams(400, 150)
        button.x = 20F //setting margin from left
        button.y = 555F //setting margin from top
        button.text = "Press me!"
        //imageView.setBackgroundColor(Color.RED)


        layout?.addView(button)

        if (true) {
            //show canvas game screen
            setContentView(layout)
        }
    }

    var keyBackCounter = 0;

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
                gameLogic?.SetActiveScreen("Wordle"/*SCREEN_NAME_WORDLE*/)
                return super.onKeyDown(0, event)
            }
        }
        if (keyCode == KeyEvent.KEYCODE_HOME) {
            Log.w(TAG, "KEY HOME PRESSED!")
        }
        //gameLogic.SaveGames()
        return super.onKeyDown(keyCode, event)
    }


    private fun doMyTask(input: Int){
        myExecutor.execute {
            val result = input.toString()
            myHandler.post {
                //textView.text = result
                Log.d(TAG, "doMyTask: " + result)
            }
        }
    }

    inner class MyTask(var text: String): AsyncTask<Int, Void, String>(){

        override fun doInBackground(vararg params: Int?): String {
            // Convert the input Params:Int
            // to String and return the Result:String
            return params[0].toString()
        }

        // Result:String is set as text in the passed TextView
        override fun onPostExecute(result: String?) {
            //textView.text = result
            Log.d(TAG, "MyTask: " + result)
        }
    }

}