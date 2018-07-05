package com.shouxindao.testanimation.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.animation.ValueAnimator;
import com.nineoldandroids.view.ViewHelper;
import com.xuyao.prancelib.util.LogUtils;
import com.xuyao.prancelib.util.ToastUtil;

public class CircleButtonLayout extends FrameLayout implements View.OnTouchListener {
    public CircleButtonLayout(@NonNull Context context) {
        super(context);
    }

    public CircleButtonLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CircleButtonLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    //子项必须为center_horizontal  然后设置旋转角度
    Paint paint;
    float ih = 0;
    float ratioAngle = 0;

    public void init() {
//        setWillNotDraw(false);//ViewGroup下需要设置此项才能绘制
        setOnTouchListener(this);
        paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setDither(true);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(20);

        int count = getChildCount();
        if (count == 0)
            return;
        float angleVal = 360f / (float) (count);//平均的角度
        ratioAngle = angleVal;
        for (int i = 0; i < count; i++) {
            View item = getChildAt(i);
            float iw = item.getMeasuredWidth();
            float ih = item.getMeasuredHeight();
            this.ih = ih;
            item.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    TextView textView = (TextView) v;
                    ToastUtil.toast(getContext(), "点击了" + textView.getText());

                }
            });
            item.setPivotX(iw / 2);
            item.setPivotY(ph / 2-item.getTop());
            ViewHelper.setRotation(item, i * angleVal);
        }

        rotate();
    }


    /**
     * 获取屏幕中的真实位置
     *
     * @param v
     * @return
     */
    Point point;

    private Point loc(View v) {
        int location[] = new int[2];
        v.getLocationOnScreen(location);//获取当前窗口的位置
//        v.getLocationOnScreen(location);  //获取屏幕中位置
        return new Point(location[0], location[1]);
    }

    ObjectAnimator objectAnimator;

    private void rotate() {
        objectAnimator = ObjectAnimator.ofFloat(this, "rotation", 0, 360);
        objectAnimator.setDuration(50000);
        objectAnimator.setInterpolator(new LinearInterpolator());
        objectAnimator.setRepeatMode(ValueAnimator.RESTART);
        objectAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
            }
        });
        objectAnimator.setRepeatCount(ValueAnimator.INFINITE);
    }

    float pw = 0;
    float ph = 0;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        pw = MeasureSpec.getSize(widthMeasureSpec);
        ph = MeasureSpec.getSize(heightMeasureSpec);
    }

    long currentTime = 0;

    /**
     * 重新开始动画
     */
    public void restartAnimation() {
        objectAnimator.start();
        objectAnimator.setCurrentPlayTime(currentTime);
    }

    /**
     * 暂停动画
     */
    public void parseAnimation() {
        if (objectAnimator.isRunning() && objectAnimator.isStarted()) {
            currentTime = objectAnimator.getCurrentPlayTime();
            objectAnimator.cancel();
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        point = loc(this);
        init();
        restartAnimation();
    }

    float downx=0;
    float downy=0;
    float movex=0;
    float movey=0;
    float upx=0;
    float upy=0;
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        float centerx = point.x + getMeasuredWidth() / 2;//整个控件的中心点x
        float centery = point.y + getMeasuredHeight() / 2;//整个中心点的y

        //中心点
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downx=event.getRawX();
                downy=event.getRawY();
                parseAnimation();
                if (itemPositionListener != null)//进行回调
                    itemPositionListener.down(downx,downy);
                break;
            case MotionEvent.ACTION_MOVE:
                //移动的
                movex=event.getRawX()-downx;
                movey=event.getRawY()-downy;

                if (itemPositionListener != null)//进行回调
                    itemPositionListener.changeXY(movex,movey);
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                upx=event.getRawX()-downx;
                upy=event.getRawY()-downy;
                restartAnimation();
                float touchx = event.getRawX();
                float touchy = event.getRawY();
                //抬起后获取的x，y

                float yvalue = touchy - centery;//y的差值
                float xvalue = touchx - centerx;//x差值
                float r = (float) Math.sqrt(Math.pow(Math.abs(xvalue), 2) + Math.pow(Math.abs(yvalue), 2));//勾股定理
                int position = -1;
                //求得的半径
                if (r <= getHeight() / 2f && r >= getHeight() / 2f - ih) {
                    //在圆弧范围内
                    // 进行逻辑判断
                    double viewAngle = getRotation();//控件的旋转角度
                    if (touchx >= centerx && touchy <= centery) {//判断在那个象限
                        //第一象限
                        double y_r = Math.abs(yvalue) / Math.abs(xvalue);
                        double tanValue = Math.atan(y_r);//反切算法
                        double angleValue = 90 - Math.toDegrees(tanValue);//点击时获取的角度
                        LogUtils.e("第一象限角度=" + angleValue);
                        float firstAngle = (float) (viewAngle - ratioAngle / 2f);
                        float totalAngle = (float) (360 - firstAngle + angleValue);//得到的总角度数
                        position = (int) ((totalAngle / ratioAngle) % getChildCount());//求得下标位置


                    } else if (touchx <= centerx && touchy <= centery) {
                        //第二象限
                        double y_r = Math.abs(yvalue) / Math.abs(xvalue);
                        double tanValue = Math.atan(y_r);
                        double angleValue = 270 + Math.toDegrees(tanValue);
                        float firstAngle = (float) (viewAngle - ratioAngle / 2f);
                        float totalAngle = (float) (360 - firstAngle + angleValue);//得到的总角度数
                        position = (int) ((totalAngle / ratioAngle) % getChildCount());
                        LogUtils.e("第二象限角度=" + angleValue);
                    } else if (touchx <= centerx && touchy >= centery) {
                        //第三象限
                        double y_r = Math.abs(xvalue) / Math.abs(yvalue);
                        double tanValue = Math.atan(y_r);
                        double angleValue = 180 + Math.toDegrees(tanValue);
                        float firstAngle = (float) (viewAngle - ratioAngle / 2f);
                        float totalAngle = (float) (360 - firstAngle + angleValue);//得到的总角度数
                        position = (int) ((totalAngle / ratioAngle) % getChildCount());
                        LogUtils.e("第三象限角度=" + angleValue);
                    } else if (touchx >= centerx && touchy >= centery) {
                        //第四象限
                        double y_r = Math.abs(yvalue) / Math.abs(xvalue);
                        double tanValue = Math.atan(y_r);
                        double angleValue = 90 + Math.toDegrees(tanValue);
                        float firstAngle = (float) (viewAngle - ratioAngle / 2f);
                        float totalAngle = (float) (360 - firstAngle + angleValue);//得到的总角度数
                        position = (int) ((totalAngle / ratioAngle) % getChildCount());
                        LogUtils.e("第四象限角度=" + angleValue);
                    }
                }
                if (itemPositionListener != null)//进行回调
                    itemPositionListener.itemTouch(position,upx,upy);
                break;
        }
        return true;
    }


    public interface ItemPositionListener {
        void itemTouch(int position,float upx,float upy);
        void changeXY(float x,float y);
        void down(float x,float y);
    }

    ItemPositionListener itemPositionListener;

    public void setItemPositionListener(ItemPositionListener itemPositionListener) {
        this.itemPositionListener = itemPositionListener;
    }
}
