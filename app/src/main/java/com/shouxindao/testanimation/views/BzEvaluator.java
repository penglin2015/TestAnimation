package com.shouxindao.testanimation.views;

import android.graphics.PointF;

import com.nineoldandroids.animation.TypeEvaluator;


public class BzEvaluator implements TypeEvaluator<PointF> {


    PointF[] pointFS = null;
    public BzEvaluator(PointF... pointFS) {
        this.pointFS = pointFS;
    }

    public void setPointFS(PointF[] pointFS) {
        this.pointFS = pointFS;
    }

    @Override
    public PointF evaluate(float t, PointF startValue, PointF endValue) {
        if (pointFS == null || pointFS.length == 0) {
            return new PointF(0, 0);
        }
        int pointCount = pointFS.length;
        float tLeft = 1 - t;
        float jieCount = pointCount - 1;
        PointF pointF = new PointF();
        //得到统计点
        float x = 0;//定义运行x点
        float y = 0;//定义的运行y点
        for (int k = 0; k < pointCount; k++) {
            //循环累加计算
            if (k == 0) {
                x += Math.pow(tLeft, jieCount) * pointFS[k].x;
                y += Math.pow(tLeft, jieCount) * pointFS[k].y;
                continue;
            } else if (k == pointCount - 1) {
                x += Math.pow(t, jieCount) * pointFS[k].x;
                y += Math.pow(t, jieCount) * pointFS[k].y;
                continue;
            }
            x += jieCount * t * Math.pow(tLeft, jieCount - k) * pointFS[k].x;
            y += jieCount * t * Math.pow(tLeft, jieCount - k) * pointFS[k].y;
        }
        pointF.x = x;
        pointF.y = y;
        return pointF;
    }


}
