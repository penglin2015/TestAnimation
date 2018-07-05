package com.xuyao.prancelib.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;

/**
 * Created by yw on 2016/8/13.
 */
    public class CircleImageView extends MaskedImage {
        public CircleImageView(Context paramContext) {
            super(paramContext);
        }

        public CircleImageView(Context paramContext, AttributeSet paramAttributeSet) {
            super(paramContext, paramAttributeSet);
        }

        public CircleImageView(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
            super(paramContext, paramAttributeSet, paramInt);
        }


    Paint localPaint = null;
    public Bitmap createMask() {
            int i = getWidth();
            int j = getHeight();
            Bitmap.Config localConfig = Bitmap.Config.ARGB_8888;
            Bitmap localBitmap = Bitmap.createBitmap(i, j, localConfig);
            Canvas localCanvas = new Canvas(localBitmap);
            localPaint  = new Paint(1);
            localPaint.setColor(Color.parseColor("#ffffff"));
            float f1 = getWidth()-float_pading;
            float f2 = getHeight()-float_pading;
            RectF localRectF = new RectF(float_pading, float_pading, f1, f2);
            localCanvas.drawOval(localRectF, localPaint);
            return localBitmap;
    }

    float float_pading=0;//内嵌padding
    public float getPading() {
        return float_pading;
    }

    public void setPading(float float_pading) {
        this.float_pading = float_pading;
    }
}
