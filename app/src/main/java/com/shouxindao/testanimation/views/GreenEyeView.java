package com.shouxindao.testanimation.views;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;

import com.xuyao.prancelib.util.ColorUtil;
import com.xuyao.prancelib.util.ScreenUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GreenEyeView extends View implements View.OnTouchListener {
    public GreenEyeView(Context context) {
        super(context);
        init();
    }

    public GreenEyeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public GreenEyeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    Paint p;
    int w, h;

    private void init() {
        p = new Paint();
        p.setColor(Color.RED);
        p.setAntiAlias(true);
        p.setDither(true);

        p.setStyle(Paint.Style.FILL_AND_STROKE);
        p.setStrokeWidth(5);
        w = ScreenUtils.getScreenWidth(getContext());
        h = ScreenUtils.getScreenHeight(getContext());
        pointerBean=new PointerBean();
        setOnTouchListener(this);
    }


    public enum DRAW_TYPE {
        CIRCLE, RECT
    }

    DRAW_TYPE drawType = DRAW_TYPE.CIRCLE;

    public void setDrawType(DRAW_TYPE drawType) {
        this.drawType = drawType;
    }

    public void setPositionType(int position) {
        this.drawType = DRAW_TYPE.values()[position];
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        switch (drawType) {
            case RECT:
                drawRect(canvas);
                break;
            case CIRCLE:
                drawScale(canvas);
                break;
        }
        drawPointer(canvas);
        postInvalidate();
    }

    PointerBean pointerBean;
    private void drawPointer(Canvas canvas) {
        pointerBean.draw(canvas);
    }

    private void drawRect(Canvas canvas) {
        if (rectBeanList.size() == 0) {
            rectBeanList.add(new RectBean());
        }

        List<RectBean> isMove = new ArrayList<>();
        for (RectBean rectBean : rectBeanList) {
            if (rectBean.isOver) {
                isMove.add(rectBean);
            } else {
                rectBean.draw(canvas);
            }
        }
        rectBeanList.removeAll(isMove);
    }

    List<CircleBean> circleBeans = new ArrayList<>();

    private void drawScale(Canvas canvas) {
        if (circleBeans.size() == 0) {
            circleBeans.add(new CircleBean());
        }
        List<CircleBean> isMove = new ArrayList<>();
        for (CircleBean circleBean : circleBeans) {
            if (circleBean.isOver) {
                isMove.add(circleBean);
            } else {
                circleBean.draw(canvas);
            }
        }
        circleBeans.removeAll(isMove);
    }


    float dx, dy, mx, my, ux, uy;

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                dx = event.getRawX();
                dy = event.getRawY();
                pointerBean.setXY(dx,dy);
                break;
            case MotionEvent.ACTION_UP:

                break;
        }
        return true;
    }


    class CircleBean implements Serializable {
        float x;
        float y;
        float radus;

        Paint circlePaint;

        public CircleBean() {
            circlePaint = new Paint(p);
            circlePaint.setStyle(Paint.Style.STROKE);
            circlePaint.setStrokeWidth(2);
            circlePaint.setColor(ColorUtil.randomColor());
            x = dx;
            y = dy;
            radus = resetRadus() == 0 ? radus : resetRadus();
            start();
        }

        public float resetRadus() {
            return 0;
        }

        boolean isOver = false;
        long maxt = 4000;
        boolean isCreateCircleBean = false;

        private void start() {
            ValueAnimator valueAnimator = ValueAnimator.ofFloat(radus, h);
            valueAnimator.setDuration(maxt);
            valueAnimator.setInterpolator(new LinearInterpolator());
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float r = (float) animation.getAnimatedValue();
                    radus = r;
                    if (!isCreateCircleBean && r > 30) {
                        createNewCircleBean();
                        isCreateCircleBean = true;
                    }
                }
            });
            valueAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    isOver = true;
                }
            });
            valueAnimator.start();
        }

        public void createNewCircleBean() {
            circleBeans.add(new CircleBean());
        }

        public void draw(Canvas canvas) {
            canvas.drawCircle(x, y, radus, circlePaint);
        }
    }


    List<RectBean> rectBeanList = new ArrayList<>();//需要绘制的方形集合

    class RectBean implements Serializable {
        float left, right, buttom, top;
        float prox, proy;
        Paint rectPaint;

        public RectBean() {
            rectPaint = new Paint(p);
            rectPaint.setStyle(Paint.Style.STROKE);
            rectPaint.setStrokeWidth(4);
            rectPaint.setColor(ColorUtil.randomColor());
            prox = dx;
            proy = dy;
            start();
        }

        /**
         * 绘制方形
         *
         * @param canvas
         */
        public void draw(Canvas canvas) {
            canvas.drawRect(new RectF(left, top, right, buttom), rectPaint);
        }


        long maxt = 5;
        boolean isOver = false;
        boolean isCreateRect = false;

        private void start() {
            ValueAnimator valueChange = ValueAnimator.ofFloat(0, h);
            valueChange.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    isOver = true;
                }
            });

            valueChange.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float value = (float) animation.getAnimatedValue();
                    left = prox - value;
                    top = proy - value;
                    right = prox + value;
                    buttom = proy + value;
                    if (value > 50 && !isCreateRect) {
                        rectBeanList.add(new RectBean());
                        isCreateRect = true;
                    }
                }
            });
            valueChange.setDuration(maxt * 1000);
            valueChange.setInterpolator(new LinearInterpolator());
            valueChange.start();
        }
    }

    /**
     * 旋转指针
     */
    class PointerBean implements Serializable {
        float x, y, ex, ey, distance;
        Paint pointerPaint;

        public PointerBean() {
            x = dx;
            y = dy;
            distance = h;
            pointerPaint = new Paint(p);
            pointerPaint.setStrokeWidth(2);
            pointerPaint.setColor(ColorUtil.randomColor());
            start();
        }

        public void setXY(float x, float y) {
            this.x = x;
            this.y = y;
        }

        public void draw(Canvas canvas) {
            canvas.drawLine(x, y, ex, ey, pointerPaint);
        }

        long maxt = 1000;

        private void start() {
            ValueAnimator angeChange = ValueAnimator.ofFloat(360f, 0);
            angeChange.setDuration(maxt * 1000);
            angeChange.setInterpolator(new DecelerateInterpolator());
            angeChange.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float angle = (float) animation.getAnimatedValue();
                    ex= (float) (x+distance*Math.sin(angle));
                    ey= (float) (y+distance*Math.cos(angle));
                }
            });
            angeChange.setRepeatMode(ValueAnimator.RESTART);
            angeChange.setRepeatCount(ValueAnimator.INFINITE);
            angeChange.start();
        }

    }
}
