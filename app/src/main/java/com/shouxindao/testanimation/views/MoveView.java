package com.shouxindao.testanimation.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.xuyao.prancelib.util.ColorUtil;
import com.xuyao.prancelib.util.ScreenUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 仿动效动画
 */
public class MoveView extends View {
    public MoveView(Context context) {
        super(context);
        init();
    }

    public MoveView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MoveView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    Paint p,kPaint;
    int scw = 0, sch = 0;
    int paintColor = Color.RED;
    List<BallBean> ballBeans = new ArrayList<>();
    private void init() {
        p = new Paint();
        p.setColor(paintColor);
        p.setStyle(Paint.Style.FILL_AND_STROKE);
        p.setStrokeWidth(paintWidth);
        p.setDither(true);
        p.setAntiAlias(true);
        kPaint=new Paint(p);
        kPaint.setStyle(Paint.Style.STROKE);
        sch = ScreenUtils.getScreenHeight(getContext());
        scw = ScreenUtils.getScreenWidth(getContext());

        for (int i = 0; i < 8; i++) {
            ballBeans.add(new BallBean(){
                @Override
                public void changeColor(int color) {
                    kPaint.setColor(color);
                }
            }.withIndex(i));
        }
    }


    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawBall(canvas);
        postInvalidateDelayed(5);
    }

    int paintWidth = 20;//画笔的宽度

    private void drawBall(Canvas canvas) {
        for (BallBean ballBean : ballBeans) {
            ballBean.run(canvas);
        }
        drawK(canvas,kPaint);
    }

    /**
     * 画框
     *
     * @param canvas
     */
    Path path = new Path();

    private void drawK(Canvas canvas, Paint p) {
        path.reset();
        path.moveTo(0, 0);
        path.lineTo(scw, 0);
        path.lineTo(scw, sch - 200);
        path.lineTo(0, sch - 200);
        path.close();
        canvas.drawPath(path, p);
    }

    /**
     * 球对象
     */
    class BallBean {
        public int x = 0, y = 0;//球运动位置
        public int cx = 5;//更新的速度x
        public int cy = 5;//更新的速度y
        public int circleR = 200;//球半径
        int speed=5;//球速度
        public Paint ballPaint;
        public int ballPaintColor = 0;
        public int paintWidth = 20;
        public int index = 0;

        public BallBean withIndex(int i) {
            this.index = i;
            return this;
        }

        public BallBean() {
            x = (int) (Math.random() * (scw - circleR));
            y = (int) (Math.random() * (sch - circleR));
            circleR = (int) (Math.random() * 100 + 50);
            ballPaint = new Paint();
            ballPaint.setColor(Color.RED);
            ballPaint.setStyle(Paint.Style.FILL_AND_STROKE);
            ballPaint.setStrokeWidth(paintWidth);
            ballPaint.setDither(true);
            ballPaint.setAntiAlias(true);

            speed= (int) (Math.random()*2+1);

        }

        public void changeColor(int color){


        }

        public void run(Canvas canvas) {
            x += cx;
            y += cy;
            if (x <= 0 + circleR) {
                cx = speed;
                ballPaintColor = randomColor();
                changeColor(ballPaintColor);
            }
            if (y <= 0 + circleR) {
                cy = speed;
                ballPaintColor = randomColor();
                changeColor(ballPaintColor);
            }

            if (y >= sch - 200 - circleR) {
                cy = -speed;
                ballPaintColor = randomColor();
                changeColor(ballPaintColor);
            }
            if (x >= scw - circleR) {
                cx = -speed;
                ballPaintColor = randomColor();
                changeColor(ballPaintColor);
            }
            ballPaint.setColor(ballPaintColor);
            canvas.drawCircle(x, y, circleR - paintWidth, ballPaint);
        }
    }


    private int randomColor() {
        return ColorUtil.randomColor();
    }
}
