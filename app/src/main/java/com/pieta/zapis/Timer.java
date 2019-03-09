package com.pieta.zapis;

public class Timer {
    private long startTime;
    private long endTime;
    private boolean running=false;
    private long totalTime;

    public void start(){
        running=true;
        startTime=System.currentTimeMillis();
    }

    void updateTime(){
        if(running){
            pause();
            totalTime+=endTime-startTime;
            start();
        }

    }

    public void pause(){
        running = false;
        endTime=System.currentTimeMillis();
    }

    public void stop(){
        running = false;
        endTime=System.currentTimeMillis();
        totalTime=0;
    }

    public long getTime(){
        return totalTime;
    }

    public boolean isRunning(){
        return running;
    }
}
