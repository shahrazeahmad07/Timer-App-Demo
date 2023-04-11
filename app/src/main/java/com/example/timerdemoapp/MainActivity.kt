package com.example.timerdemoapp

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.timerdemoapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var binding: ActivityMainBinding? = null
    private var totalTime: Long = 15000
    private var timePassed: Long = 0
    private var countDownTimer: CountDownTimer? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        binding?.tvTimer?.text = (totalTime/1000).toString()
        binding?.progressBar?.max = (totalTime/1000).toInt()
        binding?.progressBar?.progress = binding?.progressBar?.max!!

        binding?.btnStart?.setOnClickListener{
            startTimer()
        }

        binding?.btnPause?.setOnClickListener {
            pauseTimer()
        }

        binding?.btnStop?.setOnClickListener {
            resetTimer()
        }

        binding?.frameLayout?.setOnClickListener {
            showSetTimerDialog()
        }
    }

    private fun startTimer() {
        if(countDownTimer == null) {
            countDownTimer = object : CountDownTimer(totalTime - timePassed*1000, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    timePassed++
                    binding?.tvTimer?.text = (millisUntilFinished/1000).toString()
                    binding?.progressBar?.progress = (millisUntilFinished/1000).toInt()
                }

                override fun onFinish() {
                    Toast.makeText(this@MainActivity, "Time Ended", Toast.LENGTH_SHORT).show()
                    countDownTimer?.cancel()
                    countDownTimer = null
                    timePassed = 0
                }
            }.start()
        }
    }

    private fun pauseTimer() {
        if(countDownTimer != null) {
            countDownTimer?.cancel()
            countDownTimer = null
        }
    }

    private fun resetTimer() {
        countDownTimer?.cancel()
        countDownTimer = null
        binding?.tvTimer?.text = (totalTime/1000).toString()
        binding?.progressBar?.progress = binding?.progressBar?.max!!
        timePassed = 0
    }

    private fun showSetTimerDialog() {
        if (countDownTimer == null) {
            val dialog = Dialog(this)
            dialog.setContentView(R.layout.dialog_set_timer)
            val btnCancel = dialog.findViewById<Button>(R.id.btnCancel)
            btnCancel.setOnClickListener {
                dialog.dismiss()
            }
            val btnOkay = dialog.findViewById<Button>(R.id.btnOkay)
            btnOkay.setOnClickListener {
                val etSetTimer = dialog.findViewById<EditText>(R.id.etSetTimer)
                totalTime = (etSetTimer.text.toString().toLong()) * 1000
                binding?.tvTimer?.text = (totalTime / 1000).toString()
                binding?.progressBar?.max = (totalTime / 1000).toInt()
                binding?.progressBar?.progress = binding?.progressBar?.max!!
                dialog.dismiss()
            }
            dialog.show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (countDownTimer != null) {
            countDownTimer = null
        }
        if (binding != null) {
            binding = null
        }
    }
}