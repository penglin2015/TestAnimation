package com.shouxindao.testanimation.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorListenerAdapter;
import com.nineoldandroids.animation.ValueAnimator;
import com.shouxindao.testanimation.R;
import com.xuyao.prancelib.util.RandomUti;
import com.xuyao.prancelib.util.ScreenUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class WeatherView extends View {
    public WeatherView(Context context) {
        super(context);
        init();
    }

    public WeatherView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public WeatherView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    Paint p;
    int w, h;

    CityBean cityBean;

    private void init() {
        p = new Paint();
        p.setColor(Color.RED);
        p.setAntiAlias(true);
        p.setDither(true);

        p.setStyle(Paint.Style.FILL_AND_STROKE);
        p.setStrokeWidth(5);
        w = ScreenUtils.getScreenWidth(getContext());
        h = ScreenUtils.getScreenHeight(getContext());
        cityBean = new CityBean();

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawCity(canvas);
        drawRain(canvas);
        drawLightning(canvas);
        drawCloud(canvas);
        postInvalidate();
    }

    /**
     * 绘制城市
     *
     * @param canvas
     */
    private void drawCity(Canvas canvas) {
        cityBean.draw(canvas);
    }

    int maxCloudCount = 8;//最大云数
    List<CloudBean> cloudBeans = new ArrayList<>();//云集合

    /**
     * 绘制云
     *
     * @param canvas
     */
    private void drawCloud(final Canvas canvas) {
        if (cloudBeans.size() == 0) {
            for (int i = 0; i < maxCloudCount; i++) {
                final int finalI = i;
                CloudBean cloudBean = new CloudBean(canvas) {
                    @Override
                    public float resetX(Bitmap cloudBitmap) {
                        return -cloudBitmap.getScaledWidth(canvas) + (float) w / (float) maxCloudCount * (float) finalI;
                    }
                };
                cloudBeans.add(cloudBean);
            }
        } else if (cloudBeans.size() < maxCloudCount) {
            for (int i = 0; i < maxCloudCount - cloudBeans.size(); i++) {
                cloudBeans.add(new CloudBean(canvas));
            }
        }

        List<CloudBean> isMove = new ArrayList<>();
        for (CloudBean cloudBean : cloudBeans) {
            if (cloudBean.isOver) {
                isMove.add(cloudBean);
            } else {
                cloudBean.draw(canvas);
            }
        }
        cloudBeans.removeAll(isMove);
    }

    int maxRainCont = 200;//保持多少雨数
    List<RainBean> rainBeans = new ArrayList<>();//雨集合

    /**
     * 绘制雨
     *
     * @param canvas
     */
    private void drawRain(Canvas canvas) {
        if (rainBeans.size() < maxRainCont) {
            for (int i = 0; i < maxRainCont - rainBeans.size(); i++) {
                rainBeans.add(new RainBean());
            }
        }


        List<RainBean> isMove = new ArrayList<>();
        for (RainBean rainBean : rainBeans) {
            if (rainBean.isOver) {
                isMove.add(rainBean);
            } else {
                rainBean.draw(canvas);
            }
        }
        rainBeans.removeAll(isMove);
    }


    List<LightningBean> lightningBeans = new ArrayList<>();//闪电集合
    int maxLightingCount = 1;//最大闪电次数

    /**
     * 绘制闪电
     *
     * @param canvas
     */
    private void drawLightning(Canvas canvas) {
        if (lightningBeans.size() < maxLightingCount) {
            for (int i = 0; i < maxLightingCount - lightningBeans.size(); i++) {
                lightningBeans.add(new LightningBean());
            }
        }
        List<LightningBean> isMove = new ArrayList<>();
        for (LightningBean lightningBean : lightningBeans) {
            if (lightningBean.isOver) {
                isMove.add(lightningBean);
            } else {
                lightningBean.draw(canvas);
            }
        }
        lightningBeans.removeAll(isMove);
    }

    /**
     * 闪电模型
     */
    class LightningBean implements Serializable {
        Paint lightningPaint;
        boolean isOver = false;
        int sec = 0;


        public LightningBean() {
            lightningPaint = new Paint(p);
            lightningPaint.setColor(Color.WHITE);
            sec = (int) (RandomUti.getInstance().getRandom().nextDouble() * 3 + 4);
            start();
        }


        public void draw(Canvas canvas) {
            lightningPaint.setAlpha((int) (changeAlpha * 255f));
            canvas.drawPaint(lightningPaint);
        }

        float changeAlpha = 0;

        private void start() {
            float[]lightningChangeValue=getLightningValue();
            ValueAnimator valueAnimator = ValueAnimator.ofFloat(lightningChangeValue);
            valueAnimator.setInterpolator(new DecelerateInterpolator());
            valueAnimator.setDuration(sec * 200);
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float alpha = (float) animation.getAnimatedValue();
                    changeAlpha = alpha;
                }
            });
            valueAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    handler.postDelayed(runnable, 6000);
                }
            });
            valueAnimator.start();
        }

        /**
         * 生成闪电变化效果
         * @return
         */
        private float[] getLightningValue() {
//            0f 0.8f 0.0f 0.5f 0.0f 0.9f 0.0f
            int changeNum= (int) (Math.random()*5+2);
            if(changeNum%2==0){
                changeNum+=1;
            }
            float values[]=new float[changeNum];
            for(int i=0;i<changeNum;i++){
                if(i%2!=0){
                    values[i]= (float) (Math.random()*6f+4f)/10f;
                }else{
                    values[i]=0f;
                }
            }
            return values;
        }

        Handler handler = new Handler(new android.os.Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {

                return false;
            }
        });
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                isOver = true;
            }
        };
    }

    /**
     * 雨模型
     */
    class RainBean implements Serializable {
        Paint rainPaint;
        float sx, sy, ex, ey;
        int sec = 0;
        float rainDistance = 0;

        public RainBean() {
            rainPaint = new Paint(p);
            rainPaint.setColor(Color.WHITE);
            rainPaint.setAlpha((int) (255f * 0.3f));
            rainPaint.setStrokeWidth(RandomUti.getInstance().getRandom().nextFloat() * 4f + 0.5f);
            sx = RandomUti.getInstance().getRandom().nextFloat() * (2 * w);//初始值
            sy = -200;//初始值
            sec = RandomUti.getInstance().getRandom().nextInt(3) + 2;
            rainDistance = RandomUti.getInstance().getRandom().nextFloat() * 50f;

            start();
        }

        public void draw(Canvas canvas) {
            ex = sx - 4;
            ey = sy + rainDistance;
            canvas.drawLine(sx, sy, ex, ey, rainPaint);
        }

        boolean isOver = false;

        private void start() {
            ValueAnimator xchange = ValueAnimator.ofFloat(sx, -200);
            xchange.setDuration(sec * 1000);
            xchange.setInterpolator(new LinearInterpolator());
            xchange.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float changex = (float) animation.getAnimatedValue();
                    sx = changex;
                }
            });
            xchange.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    isOver = true;
                }
            });
            xchange.start();

            ValueAnimator ychange = ValueAnimator.ofFloat(sy, h + 200);
            ychange.setDuration(sec * 500);
            ychange.setInterpolator(new LinearInterpolator());
            ychange.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float changey = (float) animation.getAnimatedValue();
                    sy = changey;
                }
            });
            ychange.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    isOver = true;
                }
            });
            ychange.start();

        }
    }

    /**
     * 云模型
     */
    class CloudBean implements Serializable {
        Paint cloudPaint;
        Bitmap cloudBitmap;
        Matrix matrix;
        float x = 0;
        float y = 0;
        int sec = 0;
        float scx;
        float scy;

        public CloudBean(Canvas canvasa) {
            cloudPaint = new Paint(p);
            cloudBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.cloud2);
            cloudPaint.setAlpha((int) (255f * (Math.random() + 0.1f)));
            matrix = new Matrix();
            scx = (float) RandomUti.getInstance().getRandom().nextDouble() + 0.5f;
            scy = (float) (RandomUti.getInstance().getRandom().nextDouble());
            matrix.preScale(scx, scy);
            x = -cloudBitmap.getScaledWidth(canvasa);
            x = resetX(cloudBitmap) == 0 ? x : resetX(cloudBitmap);
            y = RandomUti.getInstance().getRandom().nextFloat() * 150 - 100;
            sec = RandomUti.getInstance().getRandom().nextInt(100) + 100;
            start();
        }

        public float resetX(Bitmap cloudBitmap) {
            return 0;
        }


        public void draw(Canvas canvas) {
            if (cloudBitmap.isRecycled() || cloudBitmap == null) {
                return;
            }
            matrix.setTranslate(x, y);
            canvas.drawBitmap(cloudBitmap, matrix, cloudPaint);
        }

        boolean isOver = false;

        private void start() {
            ValueAnimator xchange = ValueAnimator.ofFloat(x, w);
            xchange.setInterpolator(new LinearInterpolator());
            xchange.setDuration(sec * 1000);
            xchange.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    isOver = true;
                    if (!cloudBitmap.isRecycled()) {
                        cloudBitmap.recycle();
                        cloudBitmap = null;
                    }
                }
            });
            xchange.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float changex = (float) animation.getAnimatedValue();
                    x = changex;
                }
            });
            xchange.start();
        }
    }

    /*
    城市的模型
     */
    class CityBean implements Serializable {
        Paint cityPaint;
        Bitmap cityBitmap;
        Matrix matrix;

        public CityBean() {
            cityPaint = new Paint(p);
            cityPaint.setAlpha((int) (255f * 0.3f));
            cityBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.city);
            matrix = new Matrix();
            float bw = cityBitmap.getWidth();
            float sc = w / bw;
            matrix.preScale(sc, sc);
            cityBitmap = Bitmap.createBitmap(cityBitmap, 0, 0, cityBitmap.getWidth(), cityBitmap.getHeight(), matrix, false);
            matrix.setTranslate(0, h - cityBitmap.getHeight());

        }

        public void draw(Canvas canvas) {
            canvas.drawBitmap(cityBitmap, matrix, cityPaint);
        }
    }
}
