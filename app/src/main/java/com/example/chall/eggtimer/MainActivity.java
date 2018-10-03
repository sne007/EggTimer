package com.example.chall.eggtimer;

import android.media.MediaPlayer;
import android.os.Build;
import android.os.CountDownTimer;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    SeekBar sb;
    TextView tv;
    Button b;
    CountDownTimer ct;
    boolean counterIsActive = false;

    public void update(int secondsLeft){
        int minutes = (int) secondsLeft/60;
        int seconds = secondsLeft - minutes*60;
        String secondsString = Integer.toString(seconds);

        if(seconds <= 9){
            secondsString = "0" + seconds;
        }
        Log.i("seconds : ", secondsString);
        String minutesString = Integer.toString(minutes);
//        tv.setText(secondsString);
        tv.setText(minutesString + " : " + secondsString);
    }
    public void resetTimer(){
        sb.setEnabled(true);
        sb.setProgress(30);
        tv.setText("0 : 30");
        counterIsActive = false;
        ct.cancel();
    }
    public void controlTimer(View view){

        if(counterIsActive == false){
            counterIsActive = true;
            sb.setEnabled(false);

            ct = new CountDownTimer(sb.getProgress()*1000 + 100,1000){

                @Override
                public void onTick(long millisUntilFinished) {
                    update((int)millisUntilFinished/1000);
                }

                @Override
                public void onFinish() {
                    tv.setText("0 : 00");
                    MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.horn);
                    mediaPlayer.start();
                }
            }.start();
        }
        else{
            resetTimer();
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        b = (Button) findViewById(R.id.button);
        sb = (SeekBar) findViewById(R.id.seekBar);
        tv = (TextView) findViewById(R.id.textView);

        sb.setMax(600);
        sb.setMin(30);
        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                update(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Log.i("go:" , b.getText());
                if(b.getText().toString().equals("Go"))
                    b.setText("Stop");
                else
                    b.setText("Go");

                controlTimer(v);
            }
        });
    }
}
