package com.shouxindao.testanimation.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.shouxindao.testanimation.util.BzUtil;
import com.xuyao.prancelib.util.ScreenUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;

/**
 * 贝塞尔曲线绘图
 */
public class BzView extends View {
    public BzView(Context context) {
        super(context);
        init();
    }

    public BzView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BzView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    int width = 0;
    int height = 0;
    Paint paint;
    List<Point> points = new ArrayList<>();

    private void init() {
        width = ScreenUtils.getScreenWidth(getContext());
        height = ScreenUtils.getScreenHeight(getContext());
        if (width == 0 || height == 0) {
            return;
        }
        paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setDither(true);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(5);
        paint.setColor(Color.RED);
        paint.setTextSize(35);
        handler.postDelayed(runnable, 0);
        livePath();
    }

    /**
     * 生成随机
     */
    private void radomPoint() {
        int x0 = (int) (Math.random() * width / 2);
        int x1 = (int) (Math.random() * width / 2);
        int x2 = (int) (Math.random() * width / 2);
        int x3 = (int) (Math.random() * width / 2);
        int x4 = (int) (Math.random() * width / 2);
        int x5 = (int) (Math.random() * width / 2);

        int y0 = (int) (Math.random() * height / 2);
        int y1 = (int) (Math.random() * height / 2);
        int y2 = (int) (Math.random() * height / 2);
        int y3 = (int) (Math.random() * height / 2);
        int y4 = (int) (Math.random() * height / 2);
        int y5 = (int) (Math.random() * height / 2);
        points = BzUtil.fiveStepsCurveListPoint(
                new Point(x0, y0),
                new Point(x1, y1),
                new Point(x2, y2),
                new Point(x3, y3),
                new Point(x4, y4),
                new Point(x5, y5));
    }

    /**
     * 生成线
     *
     * @return
     */
    private Path livePath() {
        Path path = new Path();
        if (points.size() == 0) {
            return path;
        }
        for (Point p : points) {
            if (points.indexOf(p) == 0) {
                path.moveTo(p.x, p.y);
                continue;
            }
            path.lineTo(p.x, p.y);
        }
        return path;
    }

    android.os.Handler handler = new android.os.Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            radomPoint();
            handler.postDelayed(runnable, 2000);
        }
    };

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawBz(canvas);
        postInvalidateDelayed(2);
    }

    private void drawBz(Canvas canvas) {
        paint.setStrokeWidth(5);
        paint.setColor(Color.RED);
        canvas.drawPath(livePath(), paint);
    }
}
