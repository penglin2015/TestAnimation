package com.shouxindao.testanimation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.BounceInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.animation.ValueAnimator;
import com.xuyao.prancelib.util.ColorUtil;
import com.xuyao.prancelib.util.LogUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PropertyAnimatiorActivity extends AppCompatActivity {

    @BindView(R.id.animatiorIv)
    ImageView animatiorIv;
    @BindView(R.id.bfl)
    FrameLayout bfl;
    @BindView(R.id.testTouchV)
    View testTouchV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_animatior);
        ButterKnife.bind(this);
//        changeValueParams();
//        objectAnimnatorControl("alpha",new float[]{0.2f,1f}).start();
        mixAnimator();
        changeBackgroupColor();

        animatiorIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PropertyAnimatiorActivity.this,"点击了ImgageView",Toast.LENGTH_SHORT).show();
            }
        });

        testTouchV.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Toast.makeText(PropertyAnimatiorActivity.this,"点击了最顶层",Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }

    /**
     * 常见的插值器
     */
//    AccelerateDecelerateInterpolator@android:anim/accelerate_decelerate_interpolator	其变化开始和结束速率较慢，中间加速
//    AccelerateInterpolator@android:anim/accelerate_interpolator	其变化开始速率较慢，后面加速
//    DecelerateInterpolator@android:anim/decelerate_interpolator	其变化开始速率较快，后面减速
//    LinearInterpolator	@android:anim/linear_interpolator	其变化速率恒定
//    AnticipateInterpolator	@android:anim/anticipate_interpolator	其变化开始向后甩，然后向前
//    AnticipateOvershootInterpolator@android:anim/anticipate_overshoot_interpolator	其变化开始向后甩，然后向前甩，过冲到目标值，最后又回到了终值
//    OvershootInterpolator@android:anim/overshoot_interpolator	其变化开始向前甩，过冲到目标值，最后又回到了终值
//    BounceInterpolator@android:anim/bounce_interpolator	其变化在结束时反弹
//    CycleInterpolator@android:anim/cycle_interpolator	循环播放，其速率为正弦曲线

    /**
     * 利用值去改变控件的属性
     */
    private void changeValueParams() {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(50, 500);
        valueAnimator.setDuration(1000);//设置动画时长
        valueAnimator.setRepeatCount(Animation.INFINITE);//设置动画循环属性
        valueAnimator.setRepeatMode(Animation.REVERSE);//设置循环类型
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                //根据改变后的值去改变控件的属性
                int value = (int) animation.getAnimatedValue();
                ViewGroup.LayoutParams vlp = animatiorIv.getLayoutParams();
                vlp.width = value;
                animatiorIv.setLayoutParams(vlp);
            }
        });
        valueAnimator.start();


        ValueAnimator valueAnimator1 = ValueAnimator.ofInt(250, 500);
        valueAnimator1.setDuration(1000);
        valueAnimator1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                ViewGroup.LayoutParams vlp = animatiorIv.getLayoutParams();
                vlp.height = value;
                animatiorIv.setLayoutParams(vlp);
            }
        });
        valueAnimator1.setRepeatCount(Animation.INFINITE);
        valueAnimator1.setRepeatMode(Animation.REVERSE);
        valueAnimator1.setInterpolator(new LinearInterpolator());
        valueAnimator1.start();
    }

    /**
     * 根据属性值去改变
     *
     * @param propertyName scaleX  scaleY  rotation  translation alpha
     */
    private ObjectAnimator objectAnimnatorControl(String propertyName, float[] propertyValue) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(animatiorIv, propertyName, propertyValue);
        objectAnimator.setDuration(1000);
        objectAnimator.setRepeatCount(Animation.INFINITE);
        objectAnimator.setRepeatMode(Animation.REVERSE);
        objectAnimator.setInterpolator(new LinearInterpolator());
        return objectAnimator;
    }

    /**
     * 混合属性动画
     */
    private void mixAnimator() {
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(1000);
        animatorSet.play(objectAnimnatorControl("tanslation", new float[]{20, 250}))
                .with(objectAnimnatorControl("scaleX", new float[]{0.2f, 1f}))
                .with(objectAnimnatorControl("alpha", new float[]{0.3f, 1f}))
                .with(objectAnimnatorControl("rotation", new float[]{0, 60, 0, -50, 0}))
                .with(objectAnimnatorControl("scaleY", new float[]{0.2f, 0.9f}));
        animatorSet.setTarget(animatiorIv);
        animatorSet.setInterpolator(new BounceInterpolator());
        animatorSet.start();
    }


    /**
     * 通过属性动画去设置控件的背景图
     */
    private void changeBackgroupColor() {
        int minColor = ColorUtil.randomColor();
        int maxColor = ColorUtil.randomColor();
        ObjectAnimator objectAnimator = ObjectAnimator.ofInt(bfl, "backgroundColor", minColor, -maxColor);
        objectAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                LogUtils.e("变化的值" + animation.getAnimatedValue());
            }
        });
        objectAnimator.setDuration(500000000);
        objectAnimator.setRepeatCount(Animation.INFINITE);
        objectAnimator.setRepeatMode(Animation.REVERSE);
        objectAnimator.setInterpolator(new LinearInterpolator());
        objectAnimator.start();
    }

}
