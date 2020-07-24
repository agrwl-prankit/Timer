package com.prankit.timer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.prankit.timer.R.raw;

public class MainActivity extends AppCompatActivity {

    SeekBar seekBar;
    TextView timerTextView;
    Button controlButton;
    Boolean counterIsAtive = false;
    CountDownTimer countDownTimer;

    public void resetTimer(){
        timerTextView.setText("0 : 30");
        seekBar.setProgress(30);
        countDownTimer.cancel();
        controlButton.setText("Go !");
        seekBar.setEnabled(true);
        counterIsAtive = false;
    }

    public void updateTimer(int secondsLeft){
        // convert the time of seekbar slider in minutes and seconds
        int minutes = (int) secondsLeft / 60; // i is the value of seekBar slider and time in milisecond for this project
        int seconds = secondsLeft - minutes * 60;
        String secondString = Integer.toString(seconds);
        if (seconds <= 9) {                         // to convert 0 : 0 into
            secondString = "0" + secondString;      // 0 : 00 format if 0 is
        }                                           // less than 9 or single digit
        // set this time into timer Text view
        timerTextView.setText(Integer.toString(minutes) + ":" + secondString);
    }

    public void controlTimer(View view){
        if (counterIsAtive == false) {
            counterIsAtive = true;
            seekBar.setEnabled(false);
            controlButton.setText("Stop");
            countDownTimer = new CountDownTimer(seekBar.getProgress() * 1000, 1000) {
                @Override
                public void onTick(long l) {
                    updateTimer((int) l / 1000);
                }

                @Override
                public void onFinish() {
                    resetTimer();
                    MediaPlayer mplayer = MediaPlayer.create(getApplicationContext(), R.raw.cartoonbirds);
                    mplayer.start();
                }
            }.start();
        }
        else {
            resetTimer();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerTextView = (TextView) findViewById(R.id.timerTextView);
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        controlButton = (Button) findViewById(R.id.controlButton);

        seekBar.setMax(600); // time in seconds
        seekBar.setProgress(30); // time in seconds

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                updateTimer(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
        });
    }
}