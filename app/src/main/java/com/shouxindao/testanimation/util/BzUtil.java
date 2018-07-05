package com.shouxindao.testanimation.util;

import android.graphics.Point;
import android.graphics.PointF;

import java.util.ArrayList;
import java.util.List;

public class BzUtil {

    /**
     * 生成对应的点  2阶曲线  抛物线  需要确定3个点
     *
     * @param p0
     * @param p1
     * @param p2
     * @return
     */
    public static List<PointF> twoStepsCurveListPoint(PointF p0, PointF p1, PointF p2) {
        List<PointF> pointList = new ArrayList<>();
        float t = 0;
        for (int i = 0; i < 200; i++) {
            t += 1f / 200f;
            float tleft=1-t;
            float bzx = (float) (Math.pow(tleft, 2) * p0.x + 2 * t * (tleft) * p1.x + Math.pow(t, 2) * p2.x);
            float bzy = (float) (Math.pow(tleft, 2) * p0.y + 2 * t * (tleft) * p1.y + Math.pow(t, 2) * p2.y);
            PointF bzPoint = new PointF();
            bzPoint.x = bzx;
            bzPoint.y = bzy;
            pointList.add(bzPoint);
        }
        return pointList;
    }

    /**
     * 3阶曲线
     *
     * @param p0
     * @param p1
     * @param p2
     * @param p3
     * @return
     */
    public static List<PointF> threeStepsCurveListPoint(PointF p0, PointF p1, PointF p2, PointF p3) {
        List<PointF> pointList = new ArrayList<>();
        float t = 0;
        for (int i = 0; i < 200; i++) {
            t += 1f / 200f;
            float tleft=1-t;

            float bzx = (float) (Math.pow(tleft, 3) * p0.x +
                    3 * t * Math.pow(tleft, 2) * p1.x +
                    3 * t * (tleft) * p2.x +
                    Math.pow(t, 3) * p3.x);


            float bzy = (float) (Math.pow(tleft, 3) * p0.y +
                    3 * t * Math.pow(tleft, 2) * p1.y +
                    3 * t * (tleft) * p2.y +
                    Math.pow(t, 3) * p3.y);
            PointF bzPoint = new PointF(bzx, bzy);
            pointList.add(bzPoint);
        }
        return pointList;
    }


    /**
     * 4阶曲线
     *
     * @param p0
     * @param p1
     * @param p2
     * @param p3
     * @param p4
     * @return
     */
    public static List<Point> fourStepsCurveListPoint(Point p0, Point p1, Point p2, Point p3, Point p4) {
        List<Point> pointList = new ArrayList<>();
        float t = 0;
        for (int i = 0; i < 200; i++) {
            t += 1f / 200f;
            int bzx = (int) (Math.pow(1 - t, 4) * p0.x +
                    4 * t * Math.pow(1 - t, 3) * p1.x +
                    4 * t * Math.pow(1 - t, 2) * p2.x +
                    4 * t * (1 - t) * p3.x +
                    Math.pow(t, 4) * p4.x);


            int bzy = (int) (Math.pow(1 - t, 4) * p0.y +
                    4 * t * Math.pow(1 - t, 3) * p1.y +
                    4 * t * Math.pow(1 - t, 2) * p2.y +
                    4 * t * (1 - t) * p3.y +
                    Math.pow(t, 4) * p4.y);
            Point bzPoint = new Point(bzx, bzy);
            pointList.add(bzPoint);
        }
        return pointList;
    }


    /**
     * 4阶曲线
     *
     * @param p0
     * @param p1
     * @param p2
     * @param p3
     * @param p4
     * @return
     */
    public static List<Point> fiveStepsCurveListPoint(Point p0, Point p1, Point p2, Point p3, Point p4, Point p5) {
        List<Point> pointList = new ArrayList<>();
        float t = 0;
        for (int i = 0; i < 200; i++) {
            t += 1f / 200f;
            int bzx = (int) (Math.pow(1 - t, 5) * p0.x +
                    5 * t * Math.pow(1 - t, 4) * p1.x +
                    5 * t * Math.pow(1 - t, 3) * p2.x +
                    5 * t * Math.pow(1 - t, 2) * p3.x +
                    5 * t * (1 - t) * p4.x +
                    Math.pow(t, 5) * p5.x);


            int bzy = (int) (Math.pow(1 - t, 5) * p0.y +
                    5 * t * Math.pow(1 - t, 4) * p1.y +
                    5 * t * Math.pow(1 - t, 3) * p2.y +
                    5 * t * Math.pow(1 - t, 2) * p3.y +
                    5 * t * (1 - t) * p4.y +
                    Math.pow(t, 5) * p5.y);
            Point bzPoint = new Point(bzx, bzy);
            pointList.add(bzPoint);
        }
        return pointList;
    }


    /**
     * 8阶曲线
     *
     * @return
     */
    public static List<PointF> eightStepsCurveListPoint(Point... points) {
        List<PointF> pointList = new ArrayList<>();
        float t = 0;
        for (int i = 0; i < 100; i++) {
            t += 1f / 100;
            float left = 1 - t;
            float bzx = (float) (Math.pow(left, 8) * points[0].x +
                    8 * t * Math.pow(left, 7) * points[1].x +
                    8 * t * Math.pow(left, 6) * points[2].x +
                    8 * t * Math.pow(left, 5) * points[3].x +
                    8 * t * Math.pow(left, 4) * points[4].x +
                    8 * t * Math.pow(left, 3) * points[5].x +
                    8 * t * Math.pow(left, 2) * points[6].x +
                    8 * t * left * points[7].x +
                    Math.pow(t, 8) * points[8].x);


            float bzy = (float) (Math.pow(left, 8) * points[0].y +
                    8 * t * Math.pow(left, 7) * points[1].y +
                    8 * t * Math.pow(left, 6) * points[2].y +
                    8 * t * Math.pow(left, 5) * points[3].y +
                    8 * t * Math.pow(left, 4) * points[4].y +
                    8 * t * Math.pow(left, 3) * points[5].y +
                    8 * t * Math.pow(left, 2) * points[6].y +
                    8 * t * Math.pow(left, 1) * points[7].y +
                    Math.pow(t, 8) * points[8].y);
            PointF bzPoint = new PointF(bzx, bzy);
            pointList.add(bzPoint);
        }
        return pointList;
    }

    /**
     * 万能的贝塞尔曲线
     *
     * @param pf
     * @return
     */
    public static List<PointF> moreStepsCurveListPointF(PointF... pf) {
        float t = 0;//t的变化值
        float ratioCount = 200;//变化次数
        float ratio = 1f / ratioCount;//变化频率
        int pointCount=pf.length;//点的数量
        int jieCount=pointCount-1;//阶次数
        List<PointF> pointFList=new ArrayList<>();
        for (int i = 0; i < ratioCount; i++) {
            t += ratio;//t的变化
            float tLeft = 1 - t;
            for (int j = 0; j < pointCount; j++) {
                //得到统计点
                float x=0;//定义运行x点
                float y=0;//定义的运行y点
               for(int k=0;k<pointCount;k++){
                   //循环累加计算
                   if(k==0){
                       x+=Math.pow(tLeft,jieCount)*pf[k].x;
                       y+=Math.pow(tLeft,jieCount)*pf[k].y;
                       continue;
                   }else if(k==pointCount-1){
                       x+=Math.pow(t,jieCount)*pf[k].x;
                       y+=Math.pow(t,jieCount)*pf[k].y;
                       continue;
                   }
                   x+=jieCount*t*Math.pow(tLeft,jieCount-k)*pf[k].x;
                   y+=jieCount*t*Math.pow(tLeft,jieCount-k)*pf[k].y;
               }
               PointF pointF=new PointF(x,y);
               pointFList.add(pointF);
            }
        }
        return pointFList;
    }
}
