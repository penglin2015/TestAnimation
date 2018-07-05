package com.shouxindao.testanimation;

import android.graphics.PointF;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorListenerAdapter;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.animation.ValueAnimator;
import com.shouxindao.testanimation.views.BezierEvaluator;
import com.xuyao.prancelib.util.ScreenUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BzAnimationActivity extends AppCompatActivity {

    @BindView(R.id.heartGroup)
    RelativeLayout heartGroup;

    int width = 0;
    int height = 0;
    @BindView(R.id.send)
    ImageView send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bz_animation);
        ButterKnife.bind(this);

        init();
    }

    private void init() {
        width = ScreenUtils.getScreenWidth(this);
        height = ScreenUtils.getScreenHeight(this);
    }


    @OnClick(R.id.send)
    public void onViewClicked(View v) {
//        start();
        bzStart();
    }

    private void bzStart() {
        animaionBut();
        final ImageView heartIv = new ImageView(BzAnimationActivity.this);
        heartGroup.addView(heartIv);
        float def_centerW = width / 2 - (height-send.getTop()) / 2;
        PointF p1 = new PointF();
        p1.x = def_centerW;
        p1.y = height - (height-send.getTop());

        PointF p2 = new PointF();
        p2.x = (float) (Math.random()*176+((width+send.getWidth())/2-88));
        p2.y = -(height-send.getTop()) - 300;
        ValueAnimator valueAnimator = ValueAnimator.ofObject(new BezierEvaluator(getPointF(1), getPointF(1)),
                p1,
                p2);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                PointF p = (PointF) animation.getAnimatedValue();
                heartIv.setImageResource(R.mipmap.icon_heart);
                heartIv.setX(p.x);
                heartIv.setY(p.y);
            }
        });
        ObjectAnimator alphaAnimation = ObjectAnimator.ofFloat(heartIv, "alpha", 1f, 0f);
        ObjectAnimator rotationAnimation = ObjectAnimator.ofFloat(heartIv, "rotation", -20, 20, -20, 20, -20, 20, -20, 20, -20, 20, -20, 20);
        ObjectAnimator scaleAnimationX = ObjectAnimator.ofFloat(heartIv, "scaleX", 0.2f, 1.0f, 0.2f);
        ObjectAnimator scaleAnimationY = ObjectAnimator.ofFloat(heartIv, "scaleY", 0.2f, 1.0f, 0.2f);
        AnimatorSet animatorSet=new AnimatorSet();
        animatorSet.play(valueAnimator).with(alphaAnimation).with(rotationAnimation).with(scaleAnimationX).with(scaleAnimationY);
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                heartGroup.removeView(heartIv);
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
            }
        });
        animatorSet.setTarget(heartIv);
        animatorSet.setDuration(12000);
        animatorSet.setInterpolator(new DecelerateInterpolator());
        animatorSet.start();
    }

    /**
     * 获取中间的两个点
     */
    private PointF getPointF(int scale) {

        Random random = new Random();
        PointF pointF = new PointF();
        pointF.x = (float) (Math.random()*500+(width/2-200/2-250));//减去50 是为了控制 x轴活动范围,看效果 随意~~
        //再Y轴上 为了确保第二个点 在第一个点之上,我把Y分成了上下两半 这样动画效果好一些  也可以用其他方法
        pointF.y = random.nextInt((50)) / scale;
        return pointF;
    }

    private void start() {
        animaionBut();
        final ImageView heartIv = new ImageView(this);
        heartIv.setImageResource(R.mipmap.icon_heart);
        heartGroup.addView(heartIv);
        List<PointF> points = aliveRadomPoint();
        float xChanges[] = new float[points.size()];
        float yChanges[] = new float[points.size()];
        for (PointF p : points) {
            int index = points.indexOf(p);
            xChanges[index] = p.x;
            yChanges[index] = p.y;
        }
        ObjectAnimator translationXAni = ObjectAnimator.ofFloat(heartIv, "translationX", xChanges);
        ObjectAnimator translationYAni = ObjectAnimator.ofFloat(heartIv, "translationY", 0, -1800);
        ObjectAnimator alphaAnimation = ObjectAnimator.ofFloat(heartIv, "alpha", 1f, 0f);
        ObjectAnimator rotationAnimation = ObjectAnimator.ofFloat(heartIv, "rotation", -20, 20, -20, 20, -20, 20, -20, 20, -20, 20, -20, 20);
        ObjectAnimator scaleAnimationX = ObjectAnimator.ofFloat(heartIv, "scaleX", 0.2f, 1.0f, 0.2f);
        ObjectAnimator scaleAnimationY = ObjectAnimator.ofFloat(heartIv, "scaleY", 0.2f, 1.0f, 0.2f);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                heartIv.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                heartIv.setVisibility(View.GONE);
                heartGroup.removeView(heartIv);
            }
        });
        animatorSet.play(translationXAni)
                .with(translationYAni)
                .with(alphaAnimation)
                .with(rotationAnimation)
                .with(scaleAnimationX)
                .with(scaleAnimationY);
        animatorSet.setDuration(12000);
        animatorSet.setInterpolator(new DecelerateInterpolator());
        animatorSet.setTarget(heartIv);
        animatorSet.start();
    }

    /**
     * 按钮动画
     */
    private void animaionBut() {
        send.setPivotX(send.getWidth() / 2);
        send.setPivotY(send.getHeight());
        ObjectAnimator btnAnimationX = ObjectAnimator.ofFloat(send, "scaleX", 1.0f, 0.8f, 1.0f);
        ObjectAnimator btnAnimationY = ObjectAnimator.ofFloat(send, "scaleY", 1.0f, 0.6f, 1.0f);
        AnimatorSet btnAnimationSet = new AnimatorSet();
        btnAnimationSet.setInterpolator(new DecelerateInterpolator());
        btnAnimationSet.setDuration(100);
        btnAnimationSet.play(btnAnimationX).with(btnAnimationY);
        btnAnimationSet.setTarget(send);
        btnAnimationSet.start();
    }

    private List<PointF> aliveRadomPoint() {
        int maxWidth = width / 5;
        int heightRotio = (int) (Math.random() * 400) + 200;
        int lrStatus = (int) (Math.random() * 55);
        List<PointF> pointFS = new ArrayList<>();
        pointFS.add(new PointF(0, 0));
        float y = 0;
        for (int i = 0; i < height / heightRotio; i++) {
            float x = (float) (Math.random() * maxWidth + 20);
            x = i % 2 == 0 && lrStatus % 2 == 0 ? x : -x;
            y += heightRotio;
            PointF pointF = new PointF(x, -y);
            pointFS.add(pointF);
        }
        return pointFS;
    }


}
