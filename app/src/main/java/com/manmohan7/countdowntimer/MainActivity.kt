package com.manmohan7.countdowntimer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.widget.Button
import android.widget.Chronometer

class MainActivity : AppCompatActivity() {

    lateinit var timer: Chronometer
    var running = false
    var offset: Long = 0

    val OFFSET_KEY = "offset"
    val RUNNING_KEY = "running"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        timer = findViewById<Chronometer>(R.id.timer)

        // restore saved instance from bundle
        if(savedInstanceState != null) {
            offset = savedInstanceState.getLong(OFFSET_KEY)
            running = savedInstanceState.getBoolean(RUNNING_KEY)

            setTimer(0)
            if(running) {
                timer.start()
            }
        }

        val add1Sec = findViewById<Button>(R.id.add_one)
        add1Sec.setOnClickListener {
            Log.v("btn click", "adding 1 sec")
            setTimer(1000)
        }

        val add5Sec = findViewById<Button>(R.id.add_five)
        add5Sec.setOnClickListener {
            Log.v("btn click", "adding 5 sec")
            setTimer(5000)
        }

        val add10Sec = findViewById<Button>(R.id.add_ten)
        add10Sec.setOnClickListener {
            Log.v("btn click", "adding 10 sec")
            setTimer(10000)
        }

        val startBtn = findViewById<Button>(R.id.start_button)
        startBtn.setOnClickListener {
            println("Start button")

            if(!running) {
                setTimer(0)
                timer.start()
                running = true
            }
        }

        val pauseBtn = findViewById<Button>(R.id.pause_button)
        pauseBtn.setOnClickListener {
            println("Pause button")

            if(running) {
                saveOffset()
                timer.stop()
                running = false
            }
        }

        val resetBtn = findViewById<Button>(R.id.reset_button)
        resetBtn.setOnClickListener {
            println("Reset Button")

            resetTimer()
            running = false
        }
    }

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        if(running) {
            saveOffset()
        }
        savedInstanceState.putLong(OFFSET_KEY, offset)
        savedInstanceState.putBoolean(RUNNING_KEY, running)

        super.onSaveInstanceState(savedInstanceState)
    }

    override fun onStop() {
        super.onStop()

        if(running) {
            saveOffset()
        }
    }

    private fun setTimer(value: Long) {
        offset += value
        timer.base = SystemClock.elapsedRealtime() + offset
    }

    private fun resetTimer() {
        timer.base = SystemClock.elapsedRealtime()
        offset = 0
    }

    private fun saveOffset() {
        offset = timer.base - SystemClock.elapsedRealtime()
    }

}