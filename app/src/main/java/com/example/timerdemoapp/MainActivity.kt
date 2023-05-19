package com.example.timerdemoapp

import android.app.Dialog
import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.timerdemoapp.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    enum class ButtonActions {
        PAUSE, START, RESUME
    }
    private var binding: ActivityMainBinding? = null
    private var totalTime: Long = 15000
    private var timePassed: Long = 0
    private var countDownTimer: CountDownTimer? = null

    private var timeEndMediaPlayer: MediaPlayer? = null

    private var currentButtonAction = ButtonActions.START


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        //! setting defaults
        if (currentButtonAction == ButtonActions.START) {
            binding?.btnMain?.text = getString(R.string.start)
        }
        binding?.tvTimer?.text = (totalTime/1000).toString()
        binding?.progressBar?.max = (totalTime).toInt()
        binding?.progressBar?.progress = binding?.progressBar?.max!!

        //! setting timer sound
        val soundURI = Uri.parse("android.resource://com.example.timerdemoapp/" + R.raw.timer)
        timeEndMediaPlayer = MediaPlayer.create(applicationContext, soundURI)
        timeEndMediaPlayer?.isLooping = false


        //! Main timer button
        binding?.btnMain?.setOnClickListener{
            mainButton(it)
        }

        //! stop timer button
        binding?.btnStop?.setOnClickListener {
            resetTimer()
        }

        //! set custom time, touch on frame layout showing time
        binding?.frameLayout?.setOnClickListener {
            showSetTimerDialog()
        }
    }

    private fun mainButton(view: View) {
        when(currentButtonAction) {
            ButtonActions.START -> {
                startTimer(view)
                currentButtonAction = ButtonActions.PAUSE
                binding?.btnMain?.text = getString(R.string.pause)
            }
            ButtonActions.PAUSE -> {
                pauseTimer()
                currentButtonAction = ButtonActions.RESUME
                binding?.btnMain?.text = getString(R.string.resume)
            }
            ButtonActions.RESUME -> {
                startTimer(view)
                currentButtonAction = ButtonActions.PAUSE
                binding?.btnMain?.text = getString(R.string.pause)
            }
            else -> {}
        }
    }

    //! method for start timer button
    private fun startTimer(view: View) {
        if(countDownTimer == null) {
            countDownTimer = object : CountDownTimer(totalTime - timePassed, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    timePassed += 1000
                    binding?.tvTimer?.text = (millisUntilFinished/1000).toString()
                    binding?.progressBar?.progress = (totalTime-timePassed).toInt()
                }

                override fun onFinish() {
                    try {
                        resetTimer()
                        timeEndMediaPlayer?.start()
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                    Snackbar.make(view, "Time Ended!!", Snackbar.LENGTH_SHORT).show()
//                    Toast.makeText(this@MainActivity, "Time Ended", Toast.LENGTH_SHORT).show()
                    countDownTimer?.cancel()
                    countDownTimer = null
                    timePassed = 0
                }
            }.start()
        }
    }

    //! method for pause timer button
    private fun pauseTimer() {
        if(countDownTimer != null) {
            countDownTimer?.cancel()
            countDownTimer = null
        }
    }

    //! method for reset timer button
    private fun resetTimer() {
        // UI portion
        currentButtonAction = ButtonActions.START
        binding?.btnMain?.text = getString(R.string.start)
        binding?.tvTimer?.text = (totalTime/1000).toString()
        binding?.progressBar?.max = totalTime.toInt()
        binding?.progressBar?.progress = binding?.progressBar?.max!!

        // Timer Portion
        countDownTimer?.cancel()
        countDownTimer = null
        timePassed = 0
    }

    //! dialog for setting custom time
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
                if (etSetTimer.text.isNotEmpty()) {
                    totalTime = (etSetTimer.text.toString().toLong()) * 1000
                    resetTimer()
                    dialog.dismiss()
                } else {
                    Toast.makeText(this@MainActivity, "Enter Time First", Toast.LENGTH_SHORT).show()
                }
            }
            dialog.show()
        }
    }

    //! destroying all memory leak variables
    override fun onDestroy() {
        super.onDestroy()
        if (countDownTimer != null) {
            countDownTimer = null
        }
        if (binding != null) {
            binding = null
        }
        if (timeEndMediaPlayer != null) {
            timeEndMediaPlayer?.stop()
            timeEndMediaPlayer = null
        }
    }
}