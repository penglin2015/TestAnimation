package com.shouxindao.testanimation.views;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorListenerAdapter;
import com.nineoldandroids.animation.ValueAnimator;
import com.shouxindao.testanimation.R;
import com.shouxindao.testanimation.util.BzUtil;
import com.shouxindao.testanimation.util.SoundUtil;
import com.xuyao.prancelib.util.ColorUtil;
import com.xuyao.prancelib.util.RandomUti;
import com.xuyao.prancelib.util.ScreenUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class BzLineView extends SurfaceView implements SurfaceHolder.Callback, Runnable {
    public BzLineView(Context context) {
        super(context);
        init();
    }

    public BzLineView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BzLineView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    Paint masterPaint;
    int width = 0, height = 0;
    Bitmap paoBitmap;
    BlBean blBean;
    Canvas canvas;
    SurfaceHolder surfaceHolder;
    boolean isDraw = false;
    Activity activity;
    Bitmap bacBitmap;

    void init() {
        activity = (Activity) getContext();
        paoBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.icon_pao);
        masterPaint = new Paint();
        masterPaint.setColor(ColorUtil.randomColor());
        masterPaint.setDither(true);
        masterPaint.setAntiAlias(true);
        masterPaint.setStyle(Paint.Style.STROKE);
        masterPaint.setStrokeWidth(20);
        width = ScreenUtils.getScreenWidth(getContext());
        height = ScreenUtils.getScreenHeight(getContext());

        Bitmap bacTempBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.icon_bac);
        Matrix matrix = new Matrix();
        float sw = (float) width / (float) bacTempBitmap.getWidth();
        float sh=(float) height/(float)bacTempBitmap.getHeight();
        matrix.setScale(sw, sh, bacTempBitmap.getWidth() / 2, bacTempBitmap.getHeight() / 2);
        bacBitmap = Bitmap.createBitmap(bacTempBitmap, 0, 0, bacTempBitmap.getWidth(), bacTempBitmap.getHeight(), matrix, false);
        if (bacTempBitmap.isRecycled()) {
            bacTempBitmap.recycle();
            bacTempBitmap = null;
        }

        blBean = new BlBean(new float[]{1, 15, 18}, new float[]{0, 50, 80});
        liveData();

        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
        setFocusable(true);
        setFocusableInTouchMode(true);
        setKeepScreenOn(true);
    }

    int ratio = 80;
    int lHeight = 80;

    private List<PointF> liveData() {
        List<PointF> listPoints = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            int st = 2 * i;
            int en = st + 3;
            List<PointF> pointFList = new ArrayList<>();
            for (int j = st; j < en; j++) {
                if (i % 2 == 0) {
                    pointFList.add(new PointF(ratio * j, j % 2 == 0 ? height / 2 + lHeight : height / 2 - lHeight));
                } else {
                    pointFList.add(new PointF(ratio * j, j % 2 == 0 ? height / 2 - lHeight + lHeight * 2 : height / 2 + lHeight + lHeight * 2));
                }
            }
            List<PointF> bzPoints = BzUtil.moreStepsCurveListPointF(pointFList.toArray(new PointF[pointFList.size()]));
            listPoints.addAll(bzPoints);
        }
        return listPoints;
    }


    /**
     * 绘制模拟烟花
     *
     * @param canvas
     */
    private void drawHua(Canvas canvas) {
        if (yanHuaBeanList.size() == 0 || yanHuaBeanList.size() < 8) {
            for (int i = 0; i < 8 - yanHuaBeanList.size(); i++) {
                yanHuaBeanList.add(new FireBean());
            }
        }

        List<FireBean> moveYanList = new ArrayList<>();
        for (FireBean fireBean : yanHuaBeanList) {
            if (fireBean.liveYanItemBean != null && fireBean.liveYanItemBean.isPlayOver) {
                moveYanList.add(fireBean);
                continue;
            }
            if (!fireBean.isRiseOver) {
                canvas.drawBitmap(fireBean.fireBitmap,fireBean.x-fireBean.fireBitmap.getScaledWidth(canvas)/2f,
                        fireBean.y,fireBean.yanPaint);
//                canvas.drawCircle(fireBean.x, fireBean.y, 4, fireBean.yanPaint);
//                canvas.drawLine(fireBean.x, fireBean.y, fireBean.x, fireBean.y + fireBean.distancex, fireBean.yanPaint);
            }
            if (fireBean.isRiseOver) {
                FireBean.FireOpenBean itemBean = fireBean.liveYanItemBean;
                if (itemBean.isPlayOver) {
                    continue;
                }
                itemBean.matrix.reset();
                fireBean.yanPaint.setAlpha((int) (itemBean.alpha * 255f));
                itemBean.matrix.setTranslate(itemBean.x - itemBean.yanb.getWidth() / 2, itemBean.y - itemBean.yanb.getHeight() / 2);
                itemBean.matrix.preScale(itemBean.scale, itemBean.scale, itemBean.yanb.getWidth() / 2, itemBean.yanb.getHeight() / 2);
                canvas.drawBitmap(itemBean.yanb, itemBean.matrix, fireBean.yanPaint);
            }
        }
        yanHuaBeanList.removeAll(moveYanList);
    }

    List<FireBean> yanHuaBeanList = new ArrayList<>();

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        isDraw = true;
        new Thread(this).start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        isDraw = false;
    }

    @Override
    public void run() {
        while (isDraw) {
            draw();
        }
    }

    private void draw() {
        try {
            canvas = surfaceHolder.lockCanvas();
            drawBac(canvas);
            drawHua(canvas);
            Thread.sleep(1);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (canvas != null) {
                surfaceHolder.unlockCanvasAndPost(canvas);
            }
        }
    }

    /**
     * 画背景
     *
     * @param canvas
     */
    private void drawBac(Canvas canvas) {
        canvas.drawBitmap(bacBitmap, 0, 0, masterPaint);
    }

    class FireBean implements Serializable {
        float x;//运行点x
        float y;//运行点y
        float rise_distance;//上升的距离
        Paint yanPaint;
        long speed = 2;
        boolean isRiseOver = false;

        float distancex = 0;

        FireOpenBean liveYanItemBean;
        Bitmap fireBitmap;
        Matrix m;

        /**
         * 烟花上升
         */
        public FireBean() {
            yanPaint = new Paint(masterPaint);
            yanPaint.setColor(ColorUtil.randomColor());
            yanPaint.setStyle(Paint.Style.FILL);
            yanPaint.setStrokeWidth(4);
            distancex = RandomUti.getInstance().getRandom().nextInt(100) + 50;
            x = RandomUti.getInstance().getRandom().nextInt(width + 1);
            y = height;
            speed = (long) (RandomUti.getInstance().getRandom().nextDouble() * 6 + 2);
            rise_distance = RandomUti.getInstance().getRandom().nextInt((height / 2)) + 350;

            fireBitmap=BitmapFactory.decodeResource(getResources(),R.mipmap.icon_fire);
            m=new Matrix();
            float randomFireWidth=RandomUti.getInstance().getRandom().nextInt(8)+2;
            float fsc=randomFireWidth/fireBitmap.getWidth();
            m.setScale(fsc,1f,fireBitmap.getWidth()/2,fireBitmap.getHeight()/2);
            fireBitmap=Bitmap.createBitmap(fireBitmap,0,0,fireBitmap.getWidth(),fireBitmap.getHeight(),m,false);

            rise();
        }

        /**
         * 上升
         */
        public void rise() {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    SoundUtil.getInstance(getContext()).play(SoundUtil.getInstance(getContext()).getFirRiseId());
                    ValueAnimator valueAnimator = ValueAnimator.ofFloat(height, height - rise_distance - distancex);
                    valueAnimator.setDuration(speed * 1000);
                    valueAnimator.setInterpolator(new DecelerateInterpolator());
                    valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            y = (float) animation.getAnimatedValue();
                        }
                    });
                    valueAnimator.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            isRiseOver = true;
                            liveYanItemBean = new FireOpenBean(x, y);
                        }
                    });
                    valueAnimator.start();
                }
            });
        }

        /**
         * 烟花绽放
         */
        public class FireOpenBean implements Serializable {
            float x;
            float y;
            int time = 2;
            float scale = 0;
            float alpha = 1f;
            boolean isPlayOver = false;
            int res[] = {
                    R.mipmap.icon_yanghua2,
                    R.mipmap.icon_yanhua3,
                    R.mipmap.icon_yanhua4,
                    R.mipmap.icon_yanhua5,
                    R.mipmap.icon_yanhua6,
                    R.mipmap.icon_yanhua7,
                    R.mipmap.icon_yanhua8,
                    R.mipmap.icon_yanhua9,
                    R.mipmap.icon_yanhua10};
            Bitmap yanb;
            Matrix matrix;
            float toScale = 0;

            public FireOpenBean(float x, float y) {
                this.x = x;
                this.y = y;
                matrix = new Matrix();
                yanb = BitmapFactory.decodeResource(getResources(), res[RandomUti.getInstance().getRandom().nextInt(res.length)]);
                time = RandomUti.getInstance().getRandom().nextInt(3) + 1;
                toScale = (float) (Math.random() * 5) + 0.4f;
                play();
            }

            public void play() {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        SoundUtil.getInstance(getContext()).play(SoundUtil.getInstance(getContext()).getFireOpenId());
                        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0f, toScale);
                        valueAnimator.setDuration(time * 1000);
                        valueAnimator.setInterpolator(new DecelerateInterpolator());
                        valueAnimator.addListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                super.onAnimationEnd(animation);
                                alphaChange();
                            }
                        });

                        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                            @Override
                            public void onAnimationUpdate(ValueAnimator animation) {
                                scale = (float) animation.getAnimatedValue();
                            }
                        });
                        valueAnimator.start();
                    }
                });
            }

            private void alphaChange() {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ValueAnimator valueAnimator = ValueAnimator.ofFloat(1f, 0);
                        valueAnimator.setInterpolator(new DecelerateInterpolator());
                        valueAnimator.setDuration(time * 1000);
                        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                            @Override
                            public void onAnimationUpdate(ValueAnimator animation) {
                                alpha = (float) animation.getAnimatedValue();
                            }
                        });
                        valueAnimator.addListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                super.onAnimationEnd(animation);
                                isPlayOver = true;
                                if (yanb.isRecycled()) {
                                    yanb.recycle();
                                    yanb = null;
                                }

                                if(fireBitmap.isRecycled()){
                                    fireBitmap.recycle();
                                    fireBitmap=null;
                                }
                            }
                        });
                        valueAnimator.start();
                    }
                });
            }
        }
    }


    List<PaoBean> paoBeanList = new ArrayList<>();
    int paoCount = 20;

    private void drawPao(Canvas canvas) {
        if (paoBeanList.size() == 0) {
            for (int i = 0; i < paoCount; i++) {
                paoBeanList.add(new PaoBean());
            }
        } else if (paoBeanList.size() < paoCount) {
            for (int i = 0; i < paoCount - paoBeanList.size(); i++) {
                paoBeanList.add(new PaoBean());
            }
        }

        List<PaoBean> removeList = new ArrayList<>();
        for (PaoBean p : paoBeanList) {
            if (p.y == 0) {
                removeList.add(p);
                continue;
            }
            p.matrix.reset();
            p.matrix.setTranslate(p.x, p.y);
            p.matrix.preScale(p.scale, p.scale, p.pao.getWidth() / 2, p.pao.getHeight() / 2);
            canvas.drawBitmap(p.pao, p.matrix, p.paopaoPaint);
        }
        paoBeanList.removeAll(removeList);
    }

    class PaoBean implements Serializable {
        float x = 0;//圆的x点
        float y = 0;//圆的y点
        int speed = 60;
        Paint paopaoPaint;
        Bitmap pao;
        Matrix matrix;
        float scale = 0.1f;

        public PaoBean() {
            matrix = new Matrix();
            pao = paoBitmap;
            paopaoPaint = new Paint(masterPaint);
            paopaoPaint.setColor(ColorUtil.randomColor());
            paopaoPaint.setStyle(Paint.Style.FILL);
            speed = RandomUti.getInstance().getRandom().nextInt(5) + 2;
            x = RandomUti.getInstance().getRandom().nextInt(width + pao.getWidth() + 1);
            y = height + pao.getHeight();
            PointF p1 = new PointF(x, height + pao.getHeight());
            PointF p2 = new PointF(x - 50, height + pao.getHeight() - 10);
            PointF p3 = new PointF(x - 50, height + pao.getHeight() - 50);
            PointF p4 = new PointF(x + 50, height + pao.getHeight() - 100);
            PointF p5 = new PointF((float) (x + Math.random() * 400) - 200, height - pao.getHeight() - 200);
            startValueAnimator(p1, p2, p3, p4, p5);
        }

        /**
         * 值得变化
         *
         * @param pointFS
         */
        private void startValueAnimator(PointF... pointFS) {
            ValueAnimator valueAnimator = ValueAnimator.ofObject(new BzEvaluator(pointFS), pointFS[0], pointFS[pointFS.length - 1]);
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    PointF pointF = (PointF) animation.getAnimatedValue();
                    x = pointF.x;
                    y = pointF.y;
                    scale = (float) animation.getCurrentPlayTime() / (speed * 1000);
                }
            });
            valueAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    x = 0;
                    y = 0;
                }
            });
            valueAnimator.setInterpolator(new DecelerateInterpolator());
            valueAnimator.setDuration(speed * 1000);
            valueAnimator.start();
        }


    }

    /**
     * 画正弦曲线
     *
     * @param canvas
     */
    //    y=Asin(ωx+φ)+k
    private void drawBl(Canvas canvas) {

        for (int i = 0; i < width; i++) {
            for (PointF pointF : blBean.change(i)) {
                canvas.drawLine(pointF.x, height, pointF.x + 1, height - 200 - pointF.y, masterPaint);
            }
        }
    }

    //    y=Asin(ωx+φ)+k
    class BlBean implements Serializable {
        float x = 0;
        float y = 0;
        float a[] = null;
        float angle = 0;
        long anglet = 1000;
        float k[] = null;

        public BlBean(float a[], float k[]) {
            this.a = a;
            this.k = k;
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

        private void changeAngle() {
            ValueAnimator changeAngleAnimator = ValueAnimator.ofFloat(0, 360);
            changeAngleAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float angle = (float) animation.getAnimatedValue();
                    BlBean.this.angle = angle;
                }
            });
            changeAngleAnimator.setInterpolator(new LinearInterpolator());
            changeAngleAnimator.setDuration(anglet);
            changeAngleAnimator.setRepeatMode(ValueAnimator.RESTART);
            changeAngleAnimator.setRepeatCount(ValueAnimator.INFINITE);
            changeAngleAnimator.start();
        }
    }

    /**
     * 曲线
     *
     * @param canvas
     */
    private void drawQx(Canvas canvas) {
        for (PointF p : x2y()) {
            canvas.drawPoint(p.x, p.y, masterPaint);
        }
    }

    /**
     * 贝塞尔
     *
     * @param canvas
     */
    private void drawBz(Canvas canvas) {
        for (PointF p : liveData()) {
            canvas.drawPoint(p.x, p.y, masterPaint);
        }
    }

    /**
     * 曲线
     *
     * @return
     */
    private List<PointF> x2y() {
        float a = 2f;
        float b = 6;
        float c = 0;
        //公式  a x平方+c=y
        //x的变化范围
        List<PointF> points = new ArrayList<>();
        for (float i = 0f; i < width; i += 1) {
            float x = i;
            float y = (float) (a * Math.pow(x, 2) + b * x + c);
            PointF p = new PointF(x + 200, y + 200);
            points.add(p);
        }
        return points;
    }

}
