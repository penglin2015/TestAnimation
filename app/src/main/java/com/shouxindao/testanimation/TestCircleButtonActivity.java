package com.shouxindao.testanimation;

import android.graphics.PointF;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.TextView;

import com.nineoldandroids.animation.TypeEvaluator;
import com.nineoldandroids.animation.ValueAnimator;
import com.nineoldandroids.view.ViewHelper;
import com.shouxindao.testanimation.views.CircleButtonLayout;
import com.xuyao.prancelib.util.LogUtils;
import com.xuyao.prancelib.util.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TestCircleButtonActivity extends AppCompatActivity {

    @BindView(R.id.w1)
    TextView w1;
    @BindView(R.id.w2)
    TextView w2;
    @BindView(R.id.w3)
    TextView w3;
    @BindView(R.id.w4)
    TextView w4;
    @BindView(R.id.testCbl)
    CircleButtonLayout testCbl;
    @BindView(R.id.w5)
    TextView w5;
    @BindView(R.id.w6)
    TextView w6;
    @BindView(R.id.w7)
    TextView w7;
    @BindView(R.id.centerV)
    View centerV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_circle_button);
        ButterKnife.bind(this);

        testCbl.setItemPositionListener(new CircleButtonLayout.ItemPositionListener() {
            @Override
            public void itemTouch(int position, float upx, float upy) {
                backDefault(upx, upy);
                if (position < 0) return;
                TextView tv = (TextView) testCbl.getChildAt(position);
                ToastUtil.toast(TestCircleButtonActivity.this, "滑动获取的内容下标" + position + "_获取的内容" + tv.getText());
            }

            @Override
            public void changeXY(float x, float y) {
                LogUtils.e("x=" + x + ",y=" + y);
                ViewHelper.setTranslationX(centerV, x);
                ViewHelper.setTranslationY(centerV, y);
            }

            @Override
            public void down(float x, float y) {
                //触摸下去
                alphaChage();
            }
        });
    }


    private void alphaChage() {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(1f, 0.2f);
        valueAnimator.setDuration(400);
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float alpha = (float) animation.getAnimatedValue();
                ViewHelper.setAlpha(centerV, alpha);
            }
        });
        valueAnimator.start();
    }

    /**
     * 回归原位
     *
     * @param x
     * @param y
     */
    private void backDefault(float x, float y) {
        ValueAnimator valueAnimator = ValueAnimator.ofObject(new TypeEvaluator<PointF>() {
            @Override
            public PointF evaluate(float fraction, PointF startValue, PointF endValue) {
                float t = 1 - fraction;
                return new PointF((startValue.x - endValue.x) * t, (startValue.y - endValue.y) * t);
            }
        }, new PointF(x, y), new PointF(0, 0));

        valueAnimator.setDuration(800);
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                PointF p = (PointF) animation.getAnimatedValue();
                ViewHelper.setTranslationX(centerV, p.x);
                ViewHelper.setTranslationY(centerV, p.y);
                float alpha = (float) animation.getCurrentPlayTime() / (float) (animation.getDuration());
                ViewHelper.setAlpha(centerV, alpha);
            }
        });
        valueAnimator.start();
    }


}
