package com.shouxindao.testanimation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.BounceInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BetweenAnimationActivity extends AppCompatActivity {

    @BindView(R.id.betweenAnimationIv)
    ImageView betweenAnimationIv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_between_animation);
        ButterKnife.bind(this);

        initAnimation();
//        playRes();

//        playTran();

//        playRota();


//        playAlpha();


//        playScale();
//        mixAnimation();


        betweenAnimationIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(BetweenAnimationActivity.this,"点击了",Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 初始化各种动画
     */
    private void initAnimation() {
        //放大缩小的动画实例
        scaleAnimation = new ScaleAnimation(0.2f, 1f, 0.2f, 1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setDuration(1000);
        scaleAnimation.setRepeatMode(Animation.REVERSE);
        scaleAnimation.setRepeatCount(Animation.INFINITE);

        //透明度的动画实例
        alphaAnimation = new AlphaAnimation(1f, 0.2f);
        alphaAnimation.setDuration(1000);
        alphaAnimation.setInterpolator(new LinearInterpolator());
        alphaAnimation.setRepeatCount(Animation.INFINITE);
        alphaAnimation.setRepeatMode(Animation.REVERSE);

//移动的动画实例
        translateAnimation = new TranslateAnimation(0, 0, 0, 600);
        translateAnimation.setDuration(1000);
        translateAnimation.setInterpolator(new BounceInterpolator());
        translateAnimation.setRepeatCount(Animation.INFINITE);
        translateAnimation.setRepeatMode(Animation.REVERSE);

//选中的动画实例
        rotateAnimation = new RotateAnimation(0, 359, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setDuration(1000);
        rotateAnimation.setRepeatCount(Animation.INFINITE);
        rotateAnimation.setRepeatMode(Animation.RESTART);
        rotateAnimation.setInterpolator(new LinearInterpolator());


        animationSet = new AnimationSet(false);
        animationSet.setDuration(1000);
        animationSet.addAnimation(scaleAnimation);
        animationSet.addAnimation(translateAnimation);
        animationSet.addAnimation(rotateAnimation);
        animationSet.addAnimation(alphaAnimation);
        animationSet.setRepeatMode(Animation.RESTART);
        animationSet.setRepeatCount(Animation.INFINITE);
    }

    ScaleAnimation scaleAnimation;

    private void playScale() {
        scaleAnimation.reset();
        betweenAnimationIv.startAnimation(scaleAnimation);
    }

    /**
     * 播放透明度转补间动画
     */
    AlphaAnimation alphaAnimation;

    private void playAlpha() {
        alphaAnimation.reset();
        betweenAnimationIv.startAnimation(alphaAnimation);
    }


    /**
     * 通过实例化实现旋转补间动画
     */
    RotateAnimation rotateAnimation;

    private void playRota() {
        rotateAnimation.reset();
        betweenAnimationIv.startAnimation(rotateAnimation);
    }


    /**
     * 通过实例化对象实现移动补间动画
     */
    TranslateAnimation translateAnimation;

    private void playTran() {
        translateAnimation.reset();
        betweenAnimationIv.startAnimation(translateAnimation);
    }


    /**
     * 从资源文件中加载补间动画
     */
    private void playRes() {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.ani);
        //++++++++++++++资源文件这样设置无效
//        animation.setRepeatCount(Animation.INFINITE);//设置重复次数  -1永久重复
//        animation.setRepeatMode(Animation.RESTART);//设置重复方式
        animation.setInterpolator(new LinearInterpolator());//设置插值器  不设置无法实现循环动画

        //设置对应的监听事件  可以根据不同的状态去实现对应的逻辑
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        betweenAnimationIv.startAnimation(animation);
    }


    /**
     * 混合动画实现
     */
    AnimationSet animationSet;

    private void mixAnimation() {
        betweenAnimationIv.startAnimation(animationSet);
    }

    @OnClick({R.id.rotationChange, R.id.alphaChange, R.id.translateChange, R.id.scaleChange, R.id.mixAnimation})
    public void onViewClicked(View view) {
        betweenAnimationIv.clearAnimation();
        switch (view.getId()) {
            case R.id.rotationChange:
                playRota();
                break;
            case R.id.alphaChange:
                playAlpha();
                break;
            case R.id.translateChange:
                playTran();
                break;
            case R.id.scaleChange:
                playScale();
                break;
            case R.id.mixAnimation:
                mixAnimation();
                break;
        }
    }

}
