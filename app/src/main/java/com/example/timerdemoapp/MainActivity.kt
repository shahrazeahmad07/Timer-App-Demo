package com.example.timerdemoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Toast
import com.example.timerdemoapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val totalTime: Long = 60000
    private var timePassed: Long = 0
    private var countDownTimer: CountDownTimer? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvTimer.text = (totalTime/1000).toString()
        binding.progressBar.max = (totalTime/1000).toInt()
        binding.progressBar.progress = 100

        binding.btnStart.setOnClickListener{
            startTimer()
        }
    }

    private fun startTimer() {
        if(countDownTimer == null) {
            binding.progressBar.progress = (timePassed).toInt()
            countDownTimer = object : CountDownTimer(totalTime - timePassed, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    timePassed++
                    binding.tvTimer.text = (millisUntilFinished/1000).toString()
                    binding.progressBar.progress = (millisUntilFinished/1000).toInt()
                }

                override fun onFinish() {
                    Toast.makeText(this@MainActivity, "Time Ended", Toast.LENGTH_SHORT).show()
                }
            }.start()
        }
    }
}