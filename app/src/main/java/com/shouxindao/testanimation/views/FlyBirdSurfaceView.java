package com.shouxindao.testanimation.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import com.nineoldandroids.animation.ValueAnimator;
import com.shouxindao.testanimation.R;
import com.shouxindao.testanimation.util.SoundUtil;
import com.xuyao.prancelib.util.ColorUtil;
import com.xuyao.prancelib.util.RandomUti;
import com.xuyao.prancelib.util.ScreenUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class FlyBirdSurfaceView extends SurfaceView implements SurfaceHolder.Callback, Runnable, View.OnClickListener {

    public FlyBirdSurfaceView(Context context) {
        super(context);
        initData();
    }


    public FlyBirdSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initData();
    }

    public FlyBirdSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initData();
    }

    SurfaceHolder surfaceHolder;
    Canvas canvas;
    boolean isDraw = false;
    float w = 0, h = 0;
    Paint masterPaint;
    FlyBird flyBird;


    private void initData() {
        setOnClickListener(this);
        masterPaint = new Paint();
        masterPaint.setAntiAlias(true);
        masterPaint.setDither(true);
        masterPaint.setStrokeWidth(20);
        masterPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        masterPaint.setColor(ColorUtil.randomColor());
        w = ScreenUtils.getScreenWidth(getContext());
        h = ScreenUtils.getScreenHeight(getContext());
        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
        setFocusable(true);
        setFocusableInTouchMode(true);
        setKeepScreenOn(true);
    }

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
            clear(canvas);
            drawBird(canvas);
            drawGuanDao(canvas);
        } catch (Exception e) {

        } finally {
            if (canvas != null) {
                surfaceHolder.unlockCanvasAndPost(canvas);
            }
        }
    }

    /**
     * 绘制鸟
     *
     * @param canvas
     */
    private void drawBird(Canvas canvas) {
        if (flyBird == null) {
            flyBird = new FlyBird(canvas);
        }
        flyBird.draw();
    }


    /**
     * 绘制障碍物，管道
     *
     * @param canvas
     */
    int guandaoNum = 5;
    int turnSize = 0;
    List<Guandao> guandaos = new ArrayList<>();

    private void drawGuanDao(final Canvas canvas) {
        if (guandaos.size() == 0 || guandaos.size() < guandaoNum) {
            for (int i = 0; i < guandaoNum - guandaos.size(); i++) {
                final int turn = guandaos.size() + 1;
                float x = w / 2f * turn;
                guandaos.add(new Guandao(x, canvas));
                turnSize += 1;
                if (turnSize > 8) {
                    turnSize = 0;
                }
            }
        }

        List<Guandao> moveGuanList = new ArrayList<>();//统计被移除的管道
        for (Guandao guandao : guandaos) {
            if (guandao.isRemove()) {
                moveGuanList.add(guandao);//加入移除的管道
                continue;
            }
            guandao.draw();

            Guandao.BugBean bugBean = guandao.bugBean;
            if (bugBean != null) {
                bugBean.drawBug(canvas);
            }
            //进行游戏结束逻辑判断
            isOver(guandao);
        }
        guandaos.removeAll(moveGuanList);//统一进行移除

    }

    /**
     * 鸟和管道是否碰撞
     *
     * @param guandao
     */
    private void isOver(Guandao guandao) {
        float gx = guandao.x;
        float gy = guandao.y;
        float gw = guandao.getGuandaowidth();
        float gh = guandao.getGuandaoHeight();

        float nx = flyBird.x;
        float ny = flyBird.y;
        float nw = flyBird.bw;
        float nh = flyBird.bh;

        Guandao.BugBean bugBean = guandao.bugBean;
        if (bugBean != null) {
            float bx = bugBean.bx;
            float by = bugBean.by;
            float bw = bugBean.bw;
            float bh = bugBean.bh;

            if ((nx + nw >= bx && nx <= bx + bw) && (ny <= bh + by && ny + nh >= by)) {
                if (gameCallBackListener != null)
                    Message.obtain(handler, 1, bugBean.bugCoin).sendToTarget();
                guandao.bugBean = null;
            }
        }

        if ((nx + nw >= gx && nx <= gx + gw) && (ny <= gh + gy && ny + nh >= gy)) {
            //鸟的最大x轴大于管道的最小x位置  鸟的最小x轴小于管道的最大x位置视为碰撞
            isDraw = false;
            if (gameCallBackListener != null)
                Message.obtain(handler, 2, 2).sendToTarget();
        } else if (nx >= gx + gw) {
            if (!guandao.isSuccess) {
                guandao.isSuccess = true;
                //得分了
                if (gameCallBackListener != null)
                    Message.obtain(handler, 1, 1).sendToTarget();
            }
        }
    }

    /**
     * 重新开始游戏
     */
    public void restartGame() {
        flyBird = null;
        guandaos.clear();
        isDraw = true;
        new Thread(this).start();
    }

    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    gameCallBackListener.coinChange((Integer) msg.obj);
                    break;
                case 2:
                    gameCallBackListener.gameOver();
                    break;
            }
            return false;
        }
    });


    public interface GameCallBackListener {
        void coinChange(int coin);

        void gameOver();
    }

    GameCallBackListener gameCallBackListener;

    public void setGameCallBackListener(GameCallBackListener gameCallBackListener) {
        this.gameCallBackListener = gameCallBackListener;
    }

    /**
     * 点击之后让小鸟跳跃在空中
     *
     * @param v
     */
    ValueAnimator valueAnimator = null;

    @Override
    public void onClick(View v) {
        jump();
    }

    /**
     * 点击跳跃
     */
    private void jump() {
        SoundUtil.getInstance(getContext()).play(SoundUtil.getInstance(getContext()).getTouchId());
        if (valueAnimator == null) {
            valueAnimator = ValueAnimator.ofFloat(0, -8, -8, -8, -8, 9);
            valueAnimator.setInterpolator(new DecelerateInterpolator());
            valueAnimator.setDuration(600);
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float r = (float) animation.getAnimatedValue();
                    ratioy = r;
                }
            });
        }
        valueAnimator.start();
    }

    class Guandao implements Serializable {
        float x;
        float y;
        Bitmap guandaob;
        float bw;
        float bh;
        Paint p;
        Matrix m;
        float guandaoh;
        float guandaow;
        boolean isUpOrDown = false;
        Canvas canvas;
        boolean isSuccess = false;

        BugBean bugBean;

        public Guandao(float x, Canvas canvas) {
            this.x = x;
            this.canvas = canvas;
            guandaob = BitmapFactory.decodeResource(getResources(), R.mipmap.icon_guandao);
            bw = guandaob.getWidth();
            bh = guandaob.getHeight();
            p = new Paint(masterPaint);
            float nh = flyBird != null ? flyBird.bh : 0;
            guandaoh = RandomUti.getInstance().getRandom().nextInt((int) (h - nh - 50 - 20)) + 50;
            guandaow = w / 5f;
            m = new Matrix();
            m.postScale(guandaow / bw, guandaoh / bh, bw / 2, bh / 2);
            guandaob = Bitmap.createBitmap(guandaob, 0, 0, (int) bw, (int) bh, m, false);
            isUpOrDown = isUp();
            this.y = inity(isUpOrDown, guandaob.getScaledHeight(canvas));

            bugBean = new BugBean();
        }

        public float getGuandaowidth() {
            return guandaob.getScaledWidth(canvas);
        }

        public float getGuandaoHeight() {
            return guandaob.getScaledHeight(canvas);
        }

        /**
         * 可以被移除标致
         *
         * @return
         */
        public boolean isRemove() {
            boolean isRemove = x < -guandaob.getScaledWidth(canvas);
            if (isRemove) {
                if (guandaob.isRecycled()) {
                    guandaob.recycle();
                    guandaob = null;
                }
            }
            return isRemove;
        }

        /**
         * 绘制在下方还是在上方
         *
         * @return
         */
        private boolean isUp() {
            long num = RandomUti.getInstance().getRandom().nextInt(3001);
            return num % 2 == 0;
        }

        /**
         * 绘制
         */
        long guandaoMils = 0;//绘制时间间隔

        public void draw() {
            m.setTranslate(x, y);
            canvas.drawBitmap(guandaob, m, p);
            guandaoMils += 1;
            if (guandaoMils < 2) {
                return;
            }
            guandaoMils = 0;
            x -= 4;

            canvas.drawPoint(x, y, masterPaint);
            canvas.drawPoint(x + getGuandaowidth(), y, masterPaint);
            canvas.drawPoint(x, y + getGuandaoHeight(), masterPaint);
            canvas.drawPoint(x + getGuandaowidth(), y + getGuandaoHeight(), masterPaint);
        }

        /**
         * 初始化完毕回调
         */
        private float inity(boolean isUpOrDown, float bh) {
            return isUpOrDown ? -5 : h - bh + 5;
        }


        /**
         * 虫子对象
         */
        class BugBean implements Serializable {
            float bx;
            float by;

            float rtx;
            float rty;
            int bugRess[] = {//虫子资源
                    R.mipmap.icon_bug1,
                    R.mipmap.icon_bug2,
                    R.mipmap.icon_bug3,
                    R.mipmap.icon_bug4,
            };

            Bitmap bugb;
            float bw;
            float bh;

            Paint bugPaint;

            Matrix m;

            int bugCoin = 0;

            public BugBean() {
                bugPaint = new Paint(masterPaint);
                bugb = BitmapFactory.decodeResource(getResources(), bugRess[RandomUti.getInstance().getRandom().nextInt(bugRess.length)]);
                bw = bugb.getWidth();
                bh = bugb.getHeight();
                rtx = -(RandomUti.getInstance().getRandom().nextInt((int) (w / 2f - getGuandaowidth() - bw)) + 66f);
                rty = isUpOrDown ? RandomUti.getInstance().getRandom().nextInt((int) (h - 266)) + 200 :
                        RandomUti.getInstance().getRandom().nextInt((int) h) - y + 66;
                bx = x + rtx;
                by = y + rty;
                m = new Matrix();

                bugCoin = RandomUti.getInstance().getRandom().nextInt(10) + 2;//随机一个分值
            }

            public void drawBug(Canvas canvas) {
                m.setTranslate(bx, by);
                canvas.drawBitmap(bugb, m, bugPaint);
                bx = x + rtx;
                by = y + rty;
            }
        }
    }


    float ratioy = 0;//是减还是加

    /**
     * 飞翔的鸟的实例
     */
    class FlyBird implements Serializable {
        float x, y;
        Bitmap flyBirdb;
        Paint birdP;
        Canvas canvas;
        float bw, bh;
        Matrix m;

        public FlyBird(Canvas canvas) {
            x = w / 4;
            this.canvas = canvas;
            birdP = new Paint(masterPaint);
            Bitmap birdb = BitmapFactory.decodeResource(getResources(), R.mipmap.icon_fly_bird);
            float bw = birdb.getWidth();
            float bh = birdb.getHeight();
            float birdw = w / 20f;
            m = new Matrix();
            m.setTranslate(x, y);
            m.postScale(birdw / bw, birdw / bw, birdb.getWidth() / 2, birdb.getHeight() / 2);
            flyBirdb = Bitmap.createBitmap(birdb, 0, 0, (int) bw, (int) bh, m, false);
            if (birdb.isRecycled()) {
                birdb.recycle();
                birdb = null;
            }
            this.bw = flyBirdb.getScaledWidth(canvas);
            this.bh = flyBirdb.getScaledHeight(canvas);
            x = w / 4 - this.bw / 2;
            y = h / 2 - this.bh / 2;
        }


        /**
         * 绘制鸟
         */
        public void draw() {
            y += ratioy;
            m.setTranslate(x, y);
            canvas.drawBitmap(flyBirdb, m, birdP);
            if (y >= h - bh) {
                y = h - bh;
            } else if (y <= -5) {
                y = -5;
            }
            canvas.drawPoint(x, y, masterPaint);
            canvas.drawPoint(x + bw, y, masterPaint);
            canvas.drawPoint(x + bw, y + bh, masterPaint);
            canvas.drawPoint(x, y + bh, masterPaint);
        }
    }

    /**
     * 清屏操作
     *
     * @param canvas
     */
    private void clear(Canvas canvas) {
        canvas.drawColor(Color.parseColor("#202020"));

    }
}
