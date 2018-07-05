package com.shouxindao.testanimation;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * 帧动画测试页面
 */
public class ZhenAnimationActivity extends AppCompatActivity {

    @BindView(R.id.startTv)
    ImageView startTv;
    @BindView(R.id.catTv)
    ImageView catTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhen_animation);
        ButterKnife.bind(this);
//        play1();
        play2();
        play3();
    }
/**
 * 弊端无法监听动画过程，并且对资源消耗过大
 */
    /**
     * 通过实例化去设置每帧图片实现  帧动画
     */
    private void play2() {
        AnimationDrawable animationDrawable = new AnimationDrawable();
        animationDrawable.addFrame(getResources().getDrawable(R.mipmap.cat1), 100);
        animationDrawable.addFrame(getResources().getDrawable(R.mipmap.cat2), 100);
        animationDrawable.addFrame(getResources().getDrawable(R.mipmap.cat3), 100);
        animationDrawable.addFrame(getResources().getDrawable(R.mipmap.cat4), 100);
        animationDrawable.addFrame(getResources().getDrawable(R.mipmap.cat5), 100);
        animationDrawable.addFrame(getResources().getDrawable(R.mipmap.cat6), 100);
        animationDrawable.addFrame(getResources().getDrawable(R.mipmap.cat7), 100);
        animationDrawable.addFrame(getResources().getDrawable(R.mipmap.cat8), 100);
        animationDrawable.setOneShot(false);
        catTv.setBackground(animationDrawable);
        animationDrawable.start();
    }


    /**
     * 弊端无法监听动画过程，并且对资源消耗过大
     */
    /**
     * 通过实例化去设置每帧图片实现  帧动画
     */
    private void play3() {
        AnimationDrawable animationDrawable = new AnimationDrawable();
        animationDrawable.addFrame(getResources().getDrawable(R.mipmap.p1), 100);
        animationDrawable.addFrame(getResources().getDrawable(R.mipmap.p2), 100);
        animationDrawable.addFrame(getResources().getDrawable(R.mipmap.p3), 100);
        animationDrawable.addFrame(getResources().getDrawable(R.mipmap.p4), 100);
        animationDrawable.addFrame(getResources().getDrawable(R.mipmap.p5), 100);
        animationDrawable.addFrame(getResources().getDrawable(R.mipmap.p6), 100);
        animationDrawable.addFrame(getResources().getDrawable(R.mipmap.p7), 100);
        animationDrawable.setOneShot(false);
        startTv.setBackground(animationDrawable);
        animationDrawable.start();
    }
    /**
     * 第一种帧动画 通过设置drawable实现动画方式  直接获取background
     */
    private void play1() {

        AnimationDrawable animation = (AnimationDrawable) startTv.getBackground();
        animation.setOneShot(false);
        animation.start();
    }
}
