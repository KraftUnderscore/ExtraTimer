package com.pieta.zapis;


import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends Activity {
    final String FILENAME = "data.csv";

    final int MSG_START_TIMER = 0;
    final int MSG_STOP_TIMER = 1;
    final int MSG_UPDATE_TIMER = 2;
    final int MSG_PAUSE_TIMER = 3;
    final int REFRESH_RATE = 60;

    private Button activitySwap;
    private ImageButton bStart, bStop, bPause, bUnpause;

    private TextView editName, timeName;
    private TextView timeText;
    private Timer timer;

    Date today = new Date();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        timer = new Timer();

        timeText = findViewById(R.id.timeText);
        editName = findViewById(R.id.editText);
        timeName = findViewById(R.id.nameText);
        timeName.setVisibility(View.GONE);

        activitySwap = findViewById(R.id.activitySwap);
        bStart = findViewById(R.id.bStart);
        bStop = findViewById(R.id.bStop);
        bPause = findViewById(R.id.bPause);
        bUnpause = findViewById(R.id.bUnpause);

        bStop.setVisibility(View.GONE);
        bPause.setVisibility(View.GONE);
        bUnpause.setVisibility(View.GONE);
    }
    Handler mHandler = new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_START_TIMER:
                    timer.start(); //start timer
                    mHandler.sendEmptyMessage(MSG_UPDATE_TIMER);
                    break;

                case MSG_UPDATE_TIMER:
                    timer.updateTime();
                    timeText.setText(""+ parseTime(timer.getTime()));
                    mHandler.sendEmptyMessageDelayed(MSG_UPDATE_TIMER,REFRESH_RATE); //text view is updated every second,
                    break;//though the timer is still running

                case MSG_STOP_TIMER:
                    mHandler.removeMessages(MSG_UPDATE_TIMER); // no more updates.
                    String txt = parseTime(timer.getTime());
                    timeText.setText(txt);
                    today = Calendar.getInstance().getTime();
                    save(timer.getTime());
                    timer.stop();//stop timer
                    break;

                case MSG_PAUSE_TIMER:
                    mHandler.removeMessages(MSG_UPDATE_TIMER); // no more updates.
                    timer.pause();
                    timeText.setText(""+ parseTime(timer.getTime()));
                    break;

                default:
                    break;
            }
        }
    };

    public void nextActivity(View view){
        startActivity(new Intent(MainActivity.this, Wykresy.class));
    }


    public void start(View view){
        mHandler.sendEmptyMessage(MSG_START_TIMER);
        bStart.setVisibility(View.GONE);
        bStop.setVisibility(View.VISIBLE);
        bPause.setVisibility(View.VISIBLE);
        activitySwap.setVisibility(View.GONE);

        String tmp = editName.getText().toString();
        timeName.setText(tmp);
        editName.setVisibility(View.GONE);
        timeName.setVisibility(View.VISIBLE);
    }

    public void stop(View view){
        mHandler.sendEmptyMessage(MSG_STOP_TIMER);
        bStop.setVisibility(View.GONE);
        bPause.setVisibility(View.GONE);
        bUnpause.setVisibility(View.GONE);
        bStart.setVisibility(View.VISIBLE);
        activitySwap.setVisibility(View.VISIBLE);

        editName.setVisibility(View.VISIBLE);
        timeName.setVisibility(View.GONE);
    }

    private void save(long time){
        LocalDate localDate = today.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int day = localDate.getDayOfMonth();
        int month = localDate.getMonthValue();
        int year = localDate.getYear();
        String out = timeName.getText().toString()+","+time+","+day+","+month+","+year;
        FileHandler.writeToFile(out, FILENAME, this);
    }

    public void pause(View view){
        mHandler.sendEmptyMessage(MSG_PAUSE_TIMER);
        bPause.setVisibility(View.GONE);
        bUnpause.setVisibility(View.VISIBLE);
    }

    public void unpause(View view){

        mHandler.sendEmptyMessage(MSG_START_TIMER);
        bPause.setVisibility(View.VISIBLE);
        bUnpause.setVisibility(View.GONE);
    }

    private String parseTime(long time){
        int seconds = (int) (time / 1000);
        int minutes = seconds / 60;
        seconds     = seconds % 60;
        time        = (time % 1000)/10;


        return String.format("%d:%02d:%02d", minutes, seconds, time);
    }

}
