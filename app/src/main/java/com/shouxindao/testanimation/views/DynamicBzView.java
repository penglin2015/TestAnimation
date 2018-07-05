package com.shouxindao.testanimation.views;

import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.Xfermode;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;

import com.shouxindao.testanimation.util.BzUtil;
import com.xuyao.prancelib.util.ColorUtil;
import com.xuyao.prancelib.util.ScreenUtils;

import java.util.ArrayList;
import java.util.List;

public class DynamicBzView extends View implements View.OnTouchListener {
    public DynamicBzView(Context context) {
        super(context);
        init();
    }

    public DynamicBzView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DynamicBzView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    Paint paint;
    float w = 0, h = 0;

    private void init() {
        setOnTouchListener(this);
        paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStrokeWidth(5);
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setTextSize(20);

        w = ScreenUtils.getScreenWidth(getContext());
        h = ScreenUtils.getScreenHeight(getContext());
        mp.x = w / 2;
        mp.y = h / 2;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        List<PointF> pointFList = BzUtil.moreStepsCurveListPointF(new PointF(0, h / 2), new PointF(w / 4, h / 2), mp);
        List<PointF> pointFList1 = BzUtil.moreStepsCurveListPointF(new PointF(w, h / 2), new PointF(w / 4 * 3, h / 2), mp);
        List<PointF> all = new ArrayList<>();
        all.addAll(pointFList);
        all.addAll(pointFList1);
        for (PointF p : all) {
            canvas.drawPoint(p.x, p.y, paint);
        }
        canvas.drawCircle(mp.x, mp.y, Math.abs(moveDistancePointF.y), paint);

        postInvalidate();
    }


    private RadialGradient newRadialGradient(){
      RadialGradient  radialGradient = new RadialGradient(mp.x, mp.y,Math.abs(moveDistancePointF.y)==0?1:Math.abs(moveDistancePointF.y),
                new int[]{ColorUtil.randomColor(),ColorUtil.randomColor(),ColorUtil.randomColor(),ColorUtil.randomColor()},new float[]{0.3f,0.5f,0.7f,1f}, Shader.TileMode.CLAMP);
        return radialGradient;
    }


    float dx = 0, dy = 0;
    float mx = 0, my = 0;
    float ux = 0, uy = 0;
    PointF dp = new PointF();
    PointF mp = new PointF();
    PointF up = new PointF();
    PointF moveDistancePointF = new PointF();

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                dx = event.getRawX();
                dy = event.getRawY();
                dp.x = dx;
                dp.y = dy;
                break;
            case MotionEvent.ACTION_MOVE:
                mx = event.getRawX();
                my = event.getRawY();

                moveDistancePointF.x = mx - dx;
                moveDistancePointF.y = my - dy;

                mp.x = mx;
                mp.y = my;
                paint.setShader(newRadialGradient());
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                ux = event.getRawX();
                uy = event.getRawY();
                up.x = ux;
                up.y = uy;
                backDefault();
                break;
        }
        return true;
    }

    private void backDefault() {
        ValueAnimator valueAnimator = ValueAnimator.ofObject(new TypeEvaluator<PointF>() {
            @Override
            public PointF evaluate(float fraction, PointF startValue, PointF endValue) {
                float t = fraction;
                return new PointF(startValue.x - (startValue.x - endValue.x) * t, startValue.y - (startValue.y - endValue.y) * t);
            }
        }, up, new PointF(w / 2, h / 2));
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                paint.setShader(newRadialGradient());
                float t=animation.getAnimatedFraction();
                moveDistancePointF.y=moveDistancePointF.y*(1-t);
                PointF p = (PointF) animation.getAnimatedValue();
                mp = p;
            }
        });
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.setDuration(800);
        valueAnimator.start();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);

    }


    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }



    class CircleScaleBean{
        float x;//圆心点x
        float y;//圆心点y
        float radius;//变动的半径
        float maxRadius;
        long during=0;//动画时长

        Paint cp;
        public CircleScaleBean(float x,float y){
            this.x=x;
            this.y=y;
            maxRadius= (float) (Math.random()*w+2);
            cp=new Paint(paint);
        }

        private void scale(){
            ValueAnimator valueAnimator=ValueAnimator.ofFloat(2,maxRadius);

        }
    }
}
