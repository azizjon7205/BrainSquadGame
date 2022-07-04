package com.b12.game.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.b12.game.Base
import com.b12.game.R
import com.google.android.material.bottomsheet.BottomSheetDialog

class SecondGameActivity : AppCompatActivity() {

    val games = arrayOf("3 x 3", "4 x 4", "5 x 5")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second_game)

        val splashActivity = SplashActivity()
        splashActivity.changeStatusBarColor(this)

        findViewById<CardView>(R.id.game3x3).setOnClickListener(View.OnClickListener {
            val intent = Intent(this, SecondGame3x3Activity::class.java)
            startActivity(intent)
        })
        findViewById<CardView>(R.id.game4x4).setOnClickListener(View.OnClickListener {
            val intent = Intent(this, SecondGame4x4Activity::class.java)
            startActivity(intent)
        })
        findViewById<CardView>(R.id.game5x5).setOnClickListener(View.OnClickListener {
            val intent = Intent(this, SecondGame5x5Activity::class.java)
            startActivity(intent)
        })

        findViewById<CardView>(R.id.records_squid_puzzle).setOnClickListener {
            showBottomSheetDialog()
        }

    }

    @SuppressLint("ResourceType")
    private fun showBottomSheetDialog(){
        val bottomSheetDialog = BottomSheetDialog(this)
        bottomSheetDialog.setContentView(R.layout.bottom_sheet_for_records)

        val spinner = bottomSheetDialog.findViewById<Spinner>(R.id.spinner_records)
        val stepRecords = bottomSheetDialog.findViewById<TextView>(R.id.stepCountRecords)
        val timeRecords = bottomSheetDialog.findViewById<TextView>(R.id.recordTime)

        bottomSheetDialog.show()

        val arrayAdapter = ArrayAdapter(applicationContext,android.R.layout.simple_spinner_item, games)
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner?.adapter = arrayAdapter
        spinner?.gravity = Gravity.CENTER

        spinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

                (p0?.getChildAt(0) as TextView).textSize = 22f
                (p0.getChildAt(0) as TextView).setTextColor(Color.WHITE)
                (p0.getChildAt(0) as TextView).gravity = Gravity.CENTER

                 val keyTimer = "Timer${games[p2]}"
                 val keyStepsCount = "Steps${games[p2]}"
                stepRecords?.text = Base.getInstance().getStepCount(keyStepsCount).toString()
                timeRecords?.text = Base.getInstance().getFinishedTime(keyTimer)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

        }
    }
}