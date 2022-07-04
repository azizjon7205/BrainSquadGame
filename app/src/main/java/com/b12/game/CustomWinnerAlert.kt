package com.b12.game

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.LinearLayout
import android.widget.TextView

class CustomWinnerAlert(context: Context): Dialog(context){

    private var layoutHome: LinearLayout? = null
    private var layoutReplay: LinearLayout? = null
    private var textResultTime: TextView? = null
    private  var textResultSteps: TextView? = null
    private var textRecordTime: TextView? = null
    private var textRecordSteps: TextView? = null
    private var layoutResult: LinearLayout? = null

    private var msg: String? = null
    private var stp: Int = 0
    private var bool = false

    private var keyTimer: String? = null
    private var keyStepsCount: String? = null

    constructor(context: Context, keyTimer: String, keyStepsCount: String): this(context){
        this.keyTimer = keyTimer
        this.keyStepsCount = keyStepsCount
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.custom_winner_alert)
        layoutHome = findViewById(R.id.layoutHome)
        layoutReplay = findViewById(R.id.layoutReplay)
        textResultTime = findViewById(R.id.textResultTime)
        textResultSteps = findViewById(R.id.textResultSteps)
        textRecordTime = findViewById(R.id.textRecordTime)
        textRecordSteps = findViewById(R.id.textRecordSteps)
        layoutResult = findViewById(R.id.layoutResult)

        if (bool){
            layoutResult?.visibility = View.GONE
            textRecordTime?.text = Base.getInstance()?.getFinishedTime(keyTimer!!)
            textRecordSteps?.text = Base.getInstance()?.getStepCount(keyStepsCount!!).toString()
            findViewById<TextView>(R.id.textRecord).text = "New Record"
        } else{
            layoutResult?.visibility = View.VISIBLE
            textRecordTime?.text = Base.getInstance()?.getFinishedTime(keyTimer!!)
            textRecordSteps?.text = Base.getInstance()?.getStepCount(keyStepsCount!!).toString()
            textResultTime?.text = msg
            textResultSteps?.text = stp.toString()
            findViewById<TextView>(R.id.textRecord).text = "Record"
        }

    }

    fun setTimer(msg: String){
        this.msg = msg
    }
    fun setSteps(stp: Int){
        this.stp = stp
    }
    fun setIsRecord(bool: Boolean){
        this.bool = bool
    }

    fun getHome() = layoutHome
    fun getReplay() = layoutReplay

}