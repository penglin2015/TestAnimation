package com.shouxindao.testanimation.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.shouxindao.testanimation.R;
import com.xuyao.prancelib.util.LogUtils;
import com.xuyao.prancelib.util.ScreenUtils;

import java.util.ArrayList;
import java.util.List;

import okhttp3.internal.platform.Platform;

public class AnimationView extends View {
    public AnimationView(Context context) {
        super(context);
        init();
    }

    public AnimationView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AnimationView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    /**
     * 初始化相关数据
     * 画笔
     * 获取屏幕的宽度高度
     */
    Paint paint;
    int width;
    int height;

    int def_pointx=0;//默认的绘制起点
    List<PlacePoint> placePoints=new ArrayList<>();//初始化3块画布图一遍衔接绘制完整
    void init(){
        width= ScreenUtils.getScreenWidth(getContext());
        height=ScreenUtils.getScreenHeight(getContext());
        if(width==0||height==0)
            return;
        paint=new Paint();
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setStyle(Paint.Style.FILL);

        for(int i=0;i<2;i++){
            PlacePoint placePoint=new PlacePoint();
            int bw=placePoint.getBitmap().getWidth();
            def_pointx=-bw;
            placePoint.setMoveX(-bw+i*bw);
            placePoints.add(placePoint);
        }
    }



    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawSc(canvas);
       postInvalidateDelayed(5);//刷新
    }

    /**
     * 绘制和记录相关的点
     * @param canvas
     */
    private void drawSc(Canvas canvas) {
        if(placePoints.size()==0)
            return;
       for(PlacePoint placePoint:placePoints){
           canvas.drawBitmap(placePoint.getBitmap(),placePoint.getMoveX(),0,paint);//绘制背景图
           placePoint.setMoveX(placePoint.getMoveX()+1);//记录点位自增加1
           if(placePoint.getMoveX()>=width){//判断每块画布的记录点是否超过了屏幕宽度
               placePoint.setMoveX(width-Math.abs(2*def_pointx));//超过就初始化原始点-1图宽
           }
       }
    }

    /**
     * 移动的位置背景
     */
    class PlacePoint{

        int moveX=0;//记录移动的点
        Bitmap bitmap;//所需要绘制的背景
        public PlacePoint(){
            bitmap=BitmapFactory.decodeResource(getResources(), R.mipmap.scroll_bac);//获取绘制图
            Matrix matrix=new Matrix();
            float rotio=height/bitmap.getHeight();
            matrix.setScale(rotio,rotio);
            bitmap=Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),bitmap.getHeight(),matrix,true);
        }

        public int getMoveX() {
            return moveX;
        }

        public void setMoveX(int moveX) {
            this.moveX = moveX;
        }

        public Bitmap getBitmap() {
            return bitmap;
        }

        public void setBitmap(Bitmap bitmap) {
            this.bitmap = bitmap;
        }
    }

}
