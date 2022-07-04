package com.b12.game.activities

import android.graphics.drawable.Drawable
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.SystemClock
import android.util.Log
import android.view.View
import android.widget.*
import com.b12.game.Base
import com.b12.game.CustomWinnerAlert
import com.b12.game.R
import java.util.*

class SecondGame5x5Activity : AppCompatActivity() {
    private val keyTimer: String = "Timer5 x 5"
    private val keyStepsCount: String = "Steps5 x 5"
    private var gridLayout: GridLayout? = null
    private var clickSound: MediaPlayer? = null
    private var btn: Button? = null
    private var invisiblebtn: Button? = null
    private var chronometer: Chronometer? = null
    private var x = 4
    private var y = 4
    private var btnCount: Int? = 0
    private var checkWin = false
    private var isStarted = false
    private var stepCount = 0
    private var newRecord = false
    private var numbers = listOf<String>("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24")
    private var nextNumbers: MutableList<String> =
        mutableListOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24")

    private val map = TreeMap<String, Drawable>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second_game5x5)

        val splashActivity = SplashActivity()
        splashActivity.changeStatusBarColor(this)

        gridLayout = findViewById(R.id.grid_5x5)

        invisiblebtn = findViewById(R.id.btn5x5_25)
        btnCount = gridLayout?.childCount
        btn = gridLayout?.getChildAt(btnCount!!-1) as Button?
        btn?.visibility = View.INVISIBLE
        btn?.text = ""
        chronometer = findViewById(R.id.chronometer5x5)
        chronometer?.base = SystemClock.elapsedRealtime()
        loadMap()
        loadNumbers(numbers)

        findViewById<ImageView>(R.id.help_for_5x5).setOnClickListener {
            Toast.makeText(applicationContext,"Press three seconds!", Toast.LENGTH_LONG).show()
        }

        findViewById<ImageView>(R.id.help_for_5x5).setOnLongClickListener(View.OnLongClickListener {
            Log.d("Click", "Clicked")
            val countDownTimer = object: CountDownTimer(2000, 1000){
                override fun onTick(p0: Long) {
                    findViewById<ImageView>(R.id.real_pic_5x5).visibility = View.VISIBLE
                }

                override fun onFinish() {
                    findViewById<ImageView>(R.id.real_pic_5x5).visibility = View.GONE
                }
            }
            countDownTimer.start()
            return@OnLongClickListener true
        })

    }

    fun onClick5x5(view: View) {
        if (isStarted) {
            var btn1 = view as Button?
            val button: Button?
            val tx = btn1?.tag.toString()[1].code - '0'.code
            val ty = btn1?.tag.toString()[0].code - '0'.code

            if (Math.abs(x - tx) == 1 && Math.abs(y - ty) == 0 || Math.abs(x - tx) == 0 && Math.abs(y - ty) == 1) {
                x = tx
                y = ty

                if (btn?.text?.isEmpty() == true) {
                    btn?.text = btn1?.text
                    btn1?.text = ""
                    btn?.visibility = View.VISIBLE
                    btn1?.visibility = View.INVISIBLE
                    button = btn1
                    btn1 = btn
                    btn = button

                    var drawable = btn?.background
                    btn?.background = btn1?.background
                    btn1?.background = drawable
                }
                stepCount++
                findViewById<TextView>(R.id.stepCount5x5).text = stepCount.toString()
                getNumbers()
                if (isCheckWin()){
                    checkRecord(chronometer?.text.toString(), stepCount)

                    chronometer?.stop()
                    val customWinnerAlert = CustomWinnerAlert(this, keyTimer, keyStepsCount)
                    customWinnerAlert.setTimer(chronometer?.text.toString())
                    customWinnerAlert.setSteps(stepCount)
                    customWinnerAlert.setIsRecord(newRecord)

                    customWinnerAlert.show()
                    customWinnerAlert.getHome()?.setOnClickListener {
                        finish()
                    }
                    customWinnerAlert.getReplay()?.setOnClickListener {
                        restart()
                        customWinnerAlert.dismiss()
                    }
                    customWinnerAlert.setOnCancelListener { restart() }
                }
            }
        }
    }

    private fun loadMap(){
        for (i in 0 until btnCount!! - 1) {
            map[numbers[i]] = (gridLayout?.getChildAt(i) as Button).background

        }
    }

    private fun loadNumbers(numbers1: List<String>) {
        for (i in 0 until btnCount!! - 1) {
            (gridLayout?.getChildAt(i) as Button).text = numbers1[i]
            (gridLayout?.getChildAt(i) as Button).background = map[numbers1[i]]
        }
    }

    private fun getNumbers() {
        for (i in 0 until btnCount!! - 1) {
            nextNumbers[i] = (gridLayout?.getChildAt(i) as Button).text.toString()
        }
    }

    private fun isCheckWin(): Boolean {
        var temp = 0
        for (i in 0 until btnCount!! - 1) {
            if (nextNumbers[i] == numbers[i]) {
                temp++
            }
        }
        checkWin = temp == btnCount!! - 1
        return checkWin
    }

    fun startGame5x5(view: View){
        if (!isStarted || checkWin){
            isStarted = true
            loadNumbers(numbers.shuffled())
            findViewById<TextView>(R.id.start5x5text).text = "RESTART"
            findViewById<ImageView>(R.id.start5x5img).setImageResource(R.drawable.ic_baseline_refresh_black_24)
            chronometer?.base = SystemClock.elapsedRealtime()
            chronometer?.start()
        } else {
            isStarted = true
            restart()
            findViewById<TextView>(R.id.start5x5text).text = "RESTART"
            findViewById<ImageView>(R.id.start5x5img).setImageResource(R.drawable.ic_baseline_refresh_black_24)
            chronometer?.start()
        }
    }

    private fun restart(){
        btn?.visibility = View.VISIBLE
        btn = invisiblebtn
        invisiblebtn?.visibility = View.INVISIBLE
        invisiblebtn?.text = ""
        loadNumbers(numbers.shuffled())
        x=4
        y=4
        chronometer?.base = SystemClock.elapsedRealtime()
        stepCount = 0
        chronometer?.start()
        findViewById<TextView>(R.id.stepCount5x5).text = stepCount.toString()
    }

    private fun checkRecord(msg: String, steps: Int){
        val msgMinut = (msg[0].toString()+msg[1].toString()).toInt()
        val msgSecund = (msg[3].toString()+msg[4].toString()).toInt()
        val recordTime = Base.getInstance()?.getFinishedTime(keyTimer)
        val recordSteps = Base.getInstance()?.getStepCount(keyStepsCount)
        val recordMinut = (recordTime?.get(0).toString()+recordTime?.get(1).toString()).toInt()
        val recordSecund = (recordTime?.get(3).toString()+recordTime?.get(4).toString()).toInt()

        if (msg < recordTime!! || msg == recordTime && steps == recordSteps || recordTime == "00:00"){
            Base.getInstance()?.setFinishedTime(keyTimer, msg)
            Base.getInstance()?.setStepCount(keyStepsCount, steps)
            newRecord = true
        } else newRecord = false

    }
}