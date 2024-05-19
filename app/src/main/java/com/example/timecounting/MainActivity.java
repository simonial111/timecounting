package com.example.timecounting;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.lang.Runnable;

public class MainActivity extends AppCompatActivity {

    private Button startButton;
    private Button stopButton;
    private Button resetButton;
    private TextView timeTv;
    private int timeHour;
    private int timeMin;
    private int timeSec;
    private boolean isWorking;
    private Thread countingThread;
    private boolean isPause;
    private boolean isCounting;
    private boolean isThreadRunning;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startButton = (Button)findViewById(R.id.startbutton);
        stopButton = (Button)findViewById(R.id.stopbutton);
        resetButton = (Button)findViewById(R.id.resetbutton);
        timeTv = (TextView)findViewById(R.id.textView);

        timeHour = 0;
        timeMin = 0;
        timeSec = 0;
        isWorking = true;
        isPause = true;
        isCounting = false;
        isThreadRunning = false;
        if(savedInstanceState != null){
            timeHour = savedInstanceState.getInt("timeHour");
            timeMin = savedInstanceState.getInt("timeMin");
            timeSec = savedInstanceState.getInt("timeSec");
            isPause = savedInstanceState.getBoolean("isPause");
            isCounting = savedInstanceState.getBoolean("isCounting");

        }
        if(isPause)
            stopButton.setText(R.string.stop_butoon);
        else
            stopButton.setText(R.string.restart_button);

        timeTv.setText(String.format("%02d : %02d : %02d", timeHour, timeMin, timeSec));

        //method 1
        /*
        countingThread = new Thread(new Runnable(){
            @Override
            public void run(){

                while(isWorking ) {
                    try {
                        Thread.currentThread().sleep(1000);
                        if(isPause && isCounting) {
                            timeSec++;
                            if (timeSec >= 60) {
                                timeMin++;
                                timeSec = 0;
                            }
                            if (timeMin >= 60) {
                                timeHour++;
                                timeMin = 0;
                            }

                            timeTv.setText(String.format("%02d : %02d : %02d", timeHour, timeMin, timeSec));
                        }
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
            }


        });
        */
        //method 2
        runTimer();


        startButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v){
                timeHour = 0;
                timeMin = 0;
                timeSec = 0;
                timeTv.setText("00 : 00 : 00");
                isPause=true;
                isCounting = true;
                stopButton.setText(R.string.stop_butoon);
                //method 1
                /*if(!isThreadRunning) {
                    countingThread.start();
                    isThreadRunning = true;
                }*/
                //method 2

            }

        });

        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isCounting)
                    return;
                if(isPause) {
                    isPause = false;
                    stopButton.setText(R.string.restart_button);

                }else{
                    isPause = true;
                    stopButton.setText(R.string.stop_butoon);

                }
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
               if(!isPause) {
                   timeTv.setText("00 : 00 : 00");
                   timeHour = 0;
                   timeMin = 0;
                   timeSec = 0;
                   isWorking = true;
                   isPause = true;
                   isCounting = false;
                   stopButton.setText(R.string.stop_butoon);
               }
            }
        });
    }

    private void runTimer(){
        final TextView tv = (TextView) findViewById(R.id.textView);
        final Handler handler = new Handler();

        handler.post(new Runnable() {
            @Override
            public void run() {
                if(isPause && isCounting) {
                    timeSec++;
                    if (timeSec >= 60) {
                        timeMin++;
                        timeSec = 0;
                    }
                    if (timeMin >= 60) {
                        timeHour++;
                        timeMin = 0;
                    }

                    timeTv.setText(String.format("%02d : %02d : %02d", timeHour, timeMin, timeSec));

                }
                handler.postDelayed(this,1000);
            }
        });

    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt("timeHour", timeHour);
        savedInstanceState.putInt("timeMin", timeMin);
        savedInstanceState.putInt("timeSec", timeSec);
        savedInstanceState.putBoolean("isPause", isPause);
        savedInstanceState.putBoolean("isCounting", isCounting);



    }
}
