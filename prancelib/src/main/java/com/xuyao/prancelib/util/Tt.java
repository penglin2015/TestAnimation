package com.xuyao.prancelib.util;

import android.app.Activity;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by PL_Moster on 2017/4/26.
 */

public  class Tt {

    Activity activity;
    public Tt(Activity ...activity){
        if(activity!=null&&activity.length>0){
            this.activity=activity[0];
        }
    }
    Timer timer;
    TimerTask timerTask;
    public void newTask(){
        timerTask=new TimerTask() {
            @Override
            public void run() {
                totalSeconds--;
                if(totalSeconds <=0){
                    Tt.this.cancel();
                }
                period(totalSeconds);
            }
        };
    }

    long totalSeconds =0;
    public void start(long totalSeconds,long periodSecond){
        if(isLive()){
//            ToastBase.showBaseToastShortMsg("慢点来");
            return;
        }
        setLive(true);
        this.totalSeconds =totalSeconds;
        cancel();
        timer=new Timer();
        newTask();
        timer.schedule(timerTask,periodSecond*1000,periodSecond*1000);
    }

    public void cancel(){
        if(timer==null){
            return;
        }
        timer.cancel();
        timer=null;
        timerTask.cancel();
        timerTask=null;
        totalSeconds =0;
        setLive(false);
        period(totalSeconds);
    }

    public void period(long seconds){

    }


    boolean isLive=false;

    public void setLive(boolean live) {
        isLive = live;
    }

    public boolean isLive() {
        return isLive;
    }
}
