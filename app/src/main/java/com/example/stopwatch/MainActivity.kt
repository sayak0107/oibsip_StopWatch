package com.example.stopwatch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.google.android.material.button.MaterialButton
import android.os.Looper
import android.os.Handler
import java.util.Locale


class MainActivity : AppCompatActivity() {
//Change 1
    private lateinit var textView: TextView
    private lateinit var buttonStart: MaterialButton
    private lateinit var buttonStop: MaterialButton
    private lateinit var buttonReset: MaterialButton
    private var handler = Handler(Looper.getMainLooper())
    private var startTime = 0L
    private var elapsedTime = 0L
    private var running = false
    private var seconds = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textView=findViewById(R.id.textView)
        buttonStart=findViewById(R.id.start)
        buttonStop=findViewById(R.id.stop)
        buttonReset=findViewById(R.id.reset)

        buttonStart.setOnClickListener {
            startStopwatch()
        }

        buttonStop.setOnClickListener {
            stopStopwatch()
        }

        buttonReset.setOnClickListener {
            resetStopwatch()
        }

        updateStopwatch()
    }

    private fun startStopwatch() {
        if (!running) {
            startTime = System.currentTimeMillis() - elapsedTime
            handler.post(runnable)
            running = true
            buttonStart.isEnabled = false
            buttonStop.isEnabled = true
        }
    }

    private fun stopStopwatch() {
        if (running) {
            elapsedTime = System.currentTimeMillis() - startTime
            handler.removeCallbacks(runnable)
            running = false
            buttonStart.isEnabled = true
            buttonStop.isEnabled = false
        }
    }

    private fun resetStopwatch() {
        stopStopwatch()
        elapsedTime = 0L
        updateStopwatch()
    }

    private val runnable = object : Runnable {
        override fun run() {
            elapsedTime = System.currentTimeMillis() - startTime
            updateStopwatch()
            handler.postDelayed(this, 1000)
        }
    }

    private fun updateStopwatch() {
        val hours = (elapsedTime / 3600000).toInt()
        val minutes = ((elapsedTime % 3600000) / 60000).toInt()
        val secs = ((elapsedTime % 60000) / 1000).toInt()

        val time = String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes, secs)
        textView.text = time
    }
}
