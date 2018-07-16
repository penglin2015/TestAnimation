package com.shouxindao.testanimation.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorListenerAdapter;
import com.nineoldandroids.animation.ValueAnimator;
import com.xuyao.prancelib.util.ColorUtil;
import com.xuyao.prancelib.util.RandomUti;
import com.xuyao.prancelib.util.ScreenUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class WaterRunView extends View {
    public WaterRunView(Context context) {
        super(context);
        init();
    }

    public WaterRunView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public WaterRunView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    static Paint p;
    static int w, h;

    private void init() {
        p = new Paint();
        p.setColor(Color.RED);
        p.setAntiAlias(true);
        p.setDither(true);

        p.setStyle(Paint.Style.FILL_AND_STROKE);
        p.setStrokeWidth(5);
        w = ScreenUtils.getScreenWidth(getContext());
        h = ScreenUtils.getScreenHeight(getContext());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawBl(canvas);
        postInvalidate();
    }

    /**
     * 画正弦曲线
     *
     * @param canvas
     */
    //    y=Asin(ωx+φ)+k
    private void drawBl(Canvas canvas) {
        if (blBeanList.size() == 0) {
            blBeanList.add(BlBean.newInstance());
        }

        for (BlBean blBean : blBeanList) {
            blBean.draw(canvas);
        }
    }

    //    y=Asin(ωx+φ)+k
    static List<BlBean> blBeanList = new ArrayList<>();

    static class BlBean implements Serializable {


        public static BlBean newInstance() {
            return new BlBean(new float[]{1, 15, 18}, new float[]{0, 50, 80});
        }

        float x = 0;
        float y = 0;
        float a[] = null;
        float angle = 0;
        long sec = 5;
        float k[] = null;
        Paint blPaint;

        public BlBean(float a[], float k[]) {
            this.a = a;
            this.k = k;
            blPaint=new Paint(p);
            blPaint.setColor(ColorUtil.randomColor());
            sec= (long) (RandomUti.getInstance().getRandom().nextDouble()*5+1);
            changeAngle();
        }

        List<PointF> pointFList = new ArrayList<>();

        private List<PointF> change(float x) {
            BlBean.this.x = x;
            pointFList.clear();
            for (int i = 0; i < a.length; i++) {
                float avalue = a[i];
                float kvalue = k[i];
                float y = (float) (avalue * Math.sin((x + angle) * Math.PI / 180) + kvalue);
                PointF pointF = new PointF(x, y);
                pointFList.add(pointF);
            }
            return pointFList;
        }


        public void draw(Canvas canvas) {
            for (int i = 0; i < w; i++) {
                for (PointF pointF : this.change(i)) {
                    canvas.drawLine(pointF.x, h, pointF.x + 1, h - 200 - pointF.y, blPaint);
                }
            }
        }


        boolean isCreateWater = false;

        private void changeAngle() {
            ValueAnimator changeAngleAnimator = ValueAnimator.ofFloat(0, 360);
            changeAngleAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float angle = (float) animation.getAnimatedValue();
                    BlBean.this.angle = angle;
                    if (angle > 65 && !isCreateWater&&blBeanList.size()<3) {
                        blBeanList.add(BlBean.newInstance());
                        isCreateWater = true;
                    }
                }
            });
            changeAngleAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                }
            });
            changeAngleAnimator.setInterpolator(new LinearInterpolator());
            changeAngleAnimator.setDuration(sec*1000);
            changeAngleAnimator.setRepeatCount(ValueAnimator.INFINITE);
            changeAngleAnimator.setRepeatMode(ValueAnimator.RESTART);
            changeAngleAnimator.start();
        }
    }
}
