# Timer-App-Demo

Its a basic demo timer application, built by using built-in CountDownTimer class.

## Features/Controls:
- Circular Timer -> Sets the timer in seconds
- Start Button -> Starts the timer and changes Start button to Pause button
- Pause Button -> Pause the timer and changes the Pause Button to Resume Button
- Resume Button -> Resumes the Time and Changes Resume Button to Pause again
- Stop and Reset Button -> Stops and reset timer and all UI controls to defaults
- When Timer Ends -> Rings a Tick Sound and reset all UI controls to defaults

## Details
I have build this timer by using CountDownTimer class of Android.
When we make an object of CountDownTimer, we have to pas 2 arguments, 1st be the total time in milliseconds, and the second be the tick duration say 1 second (1000 milliseconds).
then there are two methods to override to implement it proper
onTick() method, that is executed after every tick,
onFinish() method, this is run when the timer is ended!

Also, to play the buzzer sound, I used MediaPlayer class of android, which is quite a big class with lots of implementations.
for more info checkout the official documentation of these classes.
CountDownTimer: https://developer.android.com/reference/android/os/CountDownTimer
MediaPlayer: https://developer.android.com/reference/android/media/MediaPlayer

## Video Demo
https://github.com/shahrazeahmad07/Timer-App-Demo/assets/68849516/b089d16a-108d-40e3-9d0f-753aae467fc4

