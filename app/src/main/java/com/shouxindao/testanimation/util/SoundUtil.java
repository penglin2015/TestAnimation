package com.shouxindao.testanimation.util;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Build;

import com.shouxindao.testanimation.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class SoundUtil {

    static SoundUtil instance = null;
    Context context;

    public static SoundUtil getInstance(Context context) {
        if (instance == null) {
            instance = new SoundUtil(context);
        }
        return instance;
    }

    SoundPool soundPool;
    int bacMusicId = -1;
    int successId = 0;
    int touchId = 0;
    int overId=0;
    int firRiseId=0;
    int fireOpenId=0;
    MediaPlayer mediaPlayer;


    private SoundUtil(Context context) {
        this.context = context;
        soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
        successId = soundPool.load(context, R.raw.su, 1);
        touchId = soundPool.load(context, R.raw.touch, 2);
        overId = soundPool.load(context, R.raw.over1, 3);
        firRiseId = soundPool.load(context, R.raw.fir_rise_voice1, 4);
        fireOpenId = soundPool.load(context, R.raw.fire_open_voice5, 5);
    }

    boolean isLoad = false;
    boolean isRate = false;

    public void initMed(boolean isRate) {
        this.isRate = isRate;
        if (mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();
        }
        try {

            AssetFileDescriptor assetFileDescriptor = context.getAssets().openFd("bbb_music.mp3");
            mediaPlayer.setDataSource(assetFileDescriptor.getFileDescriptor(), assetFileDescriptor.getStartOffset(), assetFileDescriptor.getLength());
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.prepareAsync();
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    isLoad = true;
                }
            });
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    if (SoundUtil.this.isRate)
                        mp.start();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stop(int stream_id) {
        soundPool.stop(stream_id);
    }

    public int getSuccessId() {
        return successId;
    }

    public int getTouchId() {
        return touchId;
    }

    public int getOverId() {
        return overId;
    }

    public int getFirRiseId() {
        return firRiseId;
    }

    public int getFireOpenId() {
        return fireOpenId;
    }

    public void play(int stream_id) {
        soundPool.play(stream_id, 0.8f, 0.8f, 4, 0, 0.5f);
    }

    public void stopMediaPlayer() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    public void playMe() {
        if (mediaPlayer == null) {
            initMed(true);
        }
        mediaPlayer.start();
    }
}
