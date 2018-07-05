package com.shouxindao.testanimation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.xuyao.prancelib.util.ScreenUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 模仿下雪动画
 */
public class SnowFlakeActivity extends AppCompatActivity {

    @BindView(R.id.cat)
    ImageView cat;
    @BindView(R.id.ballIv)
    ImageView ballIv;
    @BindView(R.id.thingGroup)
    LinearLayout thingGroup;
    @BindView(R.id.sunIv)
    ImageView sunIv;
    @BindView(R.id.topBackground)
    View topBackground;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snow_flake);
        ButterKnife.bind(this);
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        Bitmap bacBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.scroll_bac);//获取绘制图
        Matrix matrix = new Matrix();
        float scaleRatio = ScreenUtils.getScreenHeight(this) / bacBitmap.getHeight();
        matrix.setScale(scaleRatio, scaleRatio);
        bacBitmap = Bitmap.createBitmap(bacBitmap, 0, 0, bacBitmap.getWidth(), bacBitmap.getHeight(), matrix, true);
        Bitmap catBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.cat1);
        FrameLayout.LayoutParams flp = (FrameLayout.LayoutParams) thingGroup.getLayoutParams();
        flp.topMargin = (int) (bacBitmap.getHeight() - catBitmap.getHeight() / 1.4f);
        thingGroup.setLayoutParams(flp);
        catRunFrameAnimation();
        catAndBallMovePropertyAnimation();
        ballRotationPropertyAnimation();
        sunRotationBetweenAnimation();
        alphaTopBackgroundBetweenAnimation();
        cat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SnowFlakeActivity.this, "点击了猫", Toast.LENGTH_SHORT).show();
            }
        });

        sunIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SnowFlakeActivity.this,"点击了太阳",Toast.LENGTH_SHORT).show();
            }
        });
    }


    /**
     * 猫跑动动作的动画
     */
    int catResList[]={
            R.mipmap.cat1,
            R.mipmap.cat2,
            R.mipmap.cat3,
            R.mipmap.cat4,
            R.mipmap.cat5,
            R.mipmap.cat6,
            R.mipmap.cat7,
            R.mipmap.cat8,
    };
    private void catRunFrameAnimation() {
        cat.clearAnimation();
        AnimationDrawable animationDrawable = new AnimationDrawable();
        for(int catRes:catResList){
            animationDrawable.addFrame(getResources().getDrawable(catRes), 100);
        }
        animationDrawable.setOneShot(false);
        cat.setBackground(animationDrawable);
        animationDrawable.start();
    }

    /**
     * 属性动画  猫跑动的动效
     */
    private void catAndBallMovePropertyAnimation() {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(thingGroup, "translationX", ScreenUtils.getScreenWidth(this), -ScreenUtils.getScreenWidth(this) / 2);
        objectAnimator.setDuration(10000);
        objectAnimator.setRepeatCount(ValueAnimator.INFINITE);
        objectAnimator.setRepeatMode(ValueAnimator.RESTART);
        objectAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                super.onAnimationRepeat(animation);
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
            }
        });
        objectAnimator.start();
    }

    /**
     * 球滚动属性动画
     */
    private void ballRotationPropertyAnimation() {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(ballIv, "rotation", ScreenUtils.getScreenWidth(this), -ScreenUtils.getScreenWidth(this) / 2);
        objectAnimator.setDuration(4000);
        objectAnimator.setRepeatCount(ValueAnimator.INFINITE);
        objectAnimator.setRepeatMode(ValueAnimator.RESTART);
        objectAnimator.setInterpolator(new LinearInterpolator());
        objectAnimator.start();
    }

    /**
     * 太阳的动效补间动画
     */
    int sunRes=R.mipmap.icon_sun;
    int moomRes=R.mipmap.icon_moon;
    int i=0;
    private void sunRotationBetweenAnimation() {
        RotateAnimation rotateAnimation = new RotateAnimation(-20,//从哪里开始旋转的角度
                20,//旋转到那个角度
                Animation.RELATIVE_TO_SELF,//中心点x的属性是否以自己为参考物
                0.5f,//距离x轴多少
                Animation.RELATIVE_TO_SELF,//中心点y的属性是否以自己为参考物
                0.5f);//距离y轴多少百分比0-1之间的值
        rotateAnimation.setRepeatMode(Animation.REVERSE);//循环类型
        rotateAnimation.setRepeatCount(Animation.INFINITE);//循环次数
        rotateAnimation.setDuration(2000);//动画时长
        TranslateAnimation translateAnimation=new TranslateAnimation(-250,
                ScreenUtils.getScreenWidth(this),
                40,40);
        translateAnimation.setDuration(40000);
        translateAnimation.setRepeatMode(Animation.RESTART);
        translateAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                if(i==0){
                    sunIv.setImageResource(moomRes);
                    i=1;
                }else if(i==1){
                    sunIv.setImageResource(sunRes);
                    i=0;
                }
            }
        });
        translateAnimation.setRepeatCount(Animation.INFINITE);
        AnimationSet animationSet=new AnimationSet(false);
        animationSet.addAnimation(rotateAnimation);
        animationSet.addAnimation(translateAnimation);
        sunIv.startAnimation(animationSet);//开始动画
    }


    /*
    黑夜和白天的变化的补间动画
     */
    private void alphaTopBackgroundBetweenAnimation(){
        AlphaAnimation alphaAnimation=new AlphaAnimation(0.0f,0.85f);
        alphaAnimation.setRepeatMode(Animation.REVERSE);//循环类型
        alphaAnimation.setRepeatCount(Animation.INFINITE);//循环次数
        alphaAnimation.setDuration(40000);//动画时长
        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
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
        topBackground.startAnimation(alphaAnimation);//开始动画
    }
}
