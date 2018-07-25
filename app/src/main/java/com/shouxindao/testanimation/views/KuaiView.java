package com.shouxindao.testanimation.views;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.xuyao.prancelib.util.ColorUtil;
import com.xuyao.prancelib.util.LogUtils;
import com.xuyao.prancelib.util.RandomUti;
import com.xuyao.prancelib.util.ScreenUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;

public class KuaiView extends View {
    public KuaiView(Context context) {
        super(context);
        init();
    }

    public KuaiView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public KuaiView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    Paint p;
    int w, h;
    float gamePanelHeight,//游戏滑块的总最大高度
            gw;//每个方块的大小
    boolean GAME_OVER = false;//游戏是否结束

    public void setGAME_OVER(boolean GAME_OVER) {
        this.GAME_OVER = GAME_OVER;
        kuaiBeanList.clear();
    }

    SparseArray<PointF> pointFSparseArray = new SparseArray<>();

    private void init() {
        p = new Paint();
        p.setColor(Color.RED);
        p.setAntiAlias(true);
        p.setDither(true);

        p.setStyle(Paint.Style.FILL_AND_STROKE);
        p.setStrokeWidth(5);
        w = ScreenUtils.getScreenWidth(getContext());
        h = ScreenUtils.getScreenHeight(getContext());
        gamePanelHeight = h - 500f;
        gw = w / 20f;
        geBean = new GeBean();
        gePointF();
    }

    public float getGamePanelHeight() {
        return gamePanelHeight;
    }

    public void setGamePanelHeight(float gamePanelHeight) {
        this.gamePanelHeight = gamePanelHeight;
    }

    /**
     * 生成点位整列
     */
    private void gePointF() {
        PointF k0 = new PointF(0, 0);
        PointF k1 = new PointF(0, -1);
        PointF k2 = new PointF(0, -2);
        PointF k3 = new PointF(0, -3);
        PointF k30 = new PointF(-3, 0);
        PointF k20 = new PointF(-2, 0);
        PointF k10 = new PointF(-1, 0);
        PointF k11 = new PointF(-1, -1);
        PointF k12 = new PointF(-1, -2);
        PointF k13 = new PointF(-1, -3);
        PointF k21 = new PointF(-2, -1);
        PointF k22 = new PointF(-2, -2);
        PointF k23 = new PointF(-2, -3);
        PointF k31 = new PointF(-3, -1);
        PointF k32 = new PointF(-3, -2);
        PointF k33 = new PointF(-3, -3);
        pointFSparseArray.put(0, k0);
        pointFSparseArray.put(1, k1);
        pointFSparseArray.put(2, k2);
        pointFSparseArray.put(3, k3);
        pointFSparseArray.put(30, k30);
        pointFSparseArray.put(20, k20);
        pointFSparseArray.put(10, k10);
        pointFSparseArray.put(11, k11);
        pointFSparseArray.put(12, k12);
        pointFSparseArray.put(13, k13);
        pointFSparseArray.put(21, k21);
        pointFSparseArray.put(22, k22);

        pointFSparseArray.put(23, k23);
        pointFSparseArray.put(31, k31);
        pointFSparseArray.put(32, k32);
        pointFSparseArray.put(33, k33);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (GAME_OVER) {
            return;
        }
        drawGe(canvas);
        drawKuai(canvas);
        postInvalidate();
    }

    GeBean geBean;

    /**
     * 绘制格子
     *
     * @param canvas
     */
    private void drawGe(Canvas canvas) {
        geBean.draw(canvas);
    }

    final List<KuaiBean> kuaiBeanList = new ArrayList<>();

    //-1 1  左右移动
    public void changeX(int status) {
        if (kuaiBeanList.size() == 0) {
            return;
        }
        float cx = status * gw;
        KuaiBean mkb = kuaiBeanList.get(kuaiBeanList.size() - 1);
        if (mkb == null || mkb.isBottom)
            return;
        mkb.Cx(cx);
    }

    /**
     * 变形
     */
    public void change() {
        if (kuaiBeanList.size() == 0) {
            return;
        }
        KuaiBean mkb = kuaiBeanList.get(kuaiBeanList.size() - 1);
        if (mkb != null && !mkb.isBottom) {
            synchronized (mkb.geItemBeanList) {
                mkb.change();
            }
        }
    }

    /**
     * 改变下降速度
     */
    public void changeSpeed() {
        if (kuaiBeanList.size() == 0) {
            return;
        }
        KuaiBean mkb = kuaiBeanList.get(kuaiBeanList.size() - 1);
        if (mkb != null && !mkb.isBottom) {
            synchronized (mkb.geItemBeanList) {
                if (!mkb.isChangeSpeed) {
                    mkb.isChangeSpeed = true;
                    mkb.valueAnimator.setDuration(200);
                }
            }
        }
    }

    /**
     * 绘制移动方快
     *
     * @param canvas
     */
    private void drawKuai(Canvas canvas) {
        if (kuaiBeanList.size() == 0) {
            kuaiBeanList.add(new KuaiBean());
        }
        synchronized (kuaiBeanList) {
            List<KuaiBean> moveList=new ArrayList<>();
            for (KuaiBean item : kuaiBeanList) {
                if(item.isMove){
                    moveList.add(item);
                    continue;
                }
                item.draw(canvas);
            }
            if(moveList.size()>0){
                kuaiBeanList.removeAll(moveList);
                for(KuaiBean item:moveList){
                    item=null;//指控
                }
            }
        }
    }

    class GeBean implements Serializable {
        Paint gePaint;

        GeBean() {
            gePaint = new Paint(p);
            gePaint.setStrokeWidth(1f);
            gePaint.setColor(ColorUtil.randomColor());
            gePaint.setAlpha((int) (255*0.5f));
        }

        void draw(Canvas canvas) {
            for (int i = 1; i <= w / gw; i++) {
                canvas.drawLine(i * gw, gamePanelHeight, i * gw, -5, gePaint);
            }

            for (int j = 0; j <= gamePanelHeight / gw; j++) {
                canvas.drawLine(0, gamePanelHeight - (j) * gw, w, gamePanelHeight - (j) * gw, gePaint);
            }
        }
    }


    public class MapCompare implements Comparator<Integer> {

        @Override
        public int compare(Integer o1, Integer o2) {
            return o1.compareTo(o2);
        }
    }

    TreeMap<Integer, List<GeItemBean>> xStatInYPointMap = new TreeMap<>(new MapCompare());//统计静态后的最低Y点位置
    final TreeMap<Integer, List<GeItemBean>> yStatMapInXPointMap = new TreeMap<>(new MapCompare());//统计在每个Y轴的X点

    /**
     * 组合块状
     */
    class KuaiBean implements Serializable {
        float x, y;
        final List<GeItemBean> geItemBeanList = new ArrayList<>();
        int type = 0;//当前为什么类型

        KuaiBean() {
            float c = w / gw;
            x = gw / 2f + gw * c / 2f;
            y = gamePanelHeight % (gw / 2f);

            int type = RandomUti.getInstance().getRandom().nextInt(allTypeArray.length);
            this.type = type;
            //0为l形 1为k形  2为i形  3为c形  4为 o形  5为 fz形  6为z形
            geItemBeanList.addAll(def(allTypeArray[type][0]));
            moveStep();
        }

        public void setY(float y) {
            this.y = y;
            changeItemY(y);
        }

        /**
         * 需要得出左右和上下的点的位置
         */
        GeItemBean[] ltrb = new GeItemBean[4];//包含了上下左右的节点
        GeItemBean left = null;
        GeItemBean right = null;
        GeItemBean top = null;
        GeItemBean bottom = null;

        private void sort() {
            synchronized (geItemBeanList) {
                float l = 10000, r = 0, t = 10000, b = 0;
                for (GeItemBean gb : geItemBeanList) {
                    if (gb.canvasReallyPointF.x > r) {
                        right = gb;
                        r = gb.canvasReallyPointF.x;
                    }
                    if (gb.canvasReallyPointF.x < l) {
                        l = gb.canvasReallyPointF.x;
                        left = gb;
                    }
                    if (gb.canvasReallyPointF.y > b) {
                        b = gb.canvasReallyPointF.y;
                        bottom = gb;
                    }
                    if (gb.canvasReallyPointF.y < t) {
                        t = gb.canvasReallyPointF.y;
                        top = gb;
                    }
                }
                ltrb[0] = left;
                ltrb[1] = top;
                ltrb[2] = right;
                ltrb[3] = bottom;
            }

            errorSect();
        }


        TreeMap<Integer, List<GeItemBean>> kXInYStatMap = new TreeMap<>(new MapCompare());//x轴上的Y点
        TreeMap<Integer, List<GeItemBean>> kYInXStatMap = new TreeMap<>(new MapCompare());//y轴上的X点

        private void kStat() {
            kXInYStatMap.clear();//动态的需要清除重新统计  防止变形或者左右移动的时候错位
            kYInXStatMap.clear();//动态的需要清除重新统计
            synchronized (geItemBeanList) {
                for (GeItemBean item : geItemBeanList) {
                    int bqx = item.getBqx();
                    int bqy = item.getBqy();
                    if (kXInYStatMap.containsKey(bqx)) {
                        List<GeItemBean> yList = kXInYStatMap.get(bqx);
                        if (!yList.contains(item)) {
                            yList.add(item);
                        }
                        Collections.sort(yList);
                    } else {
                        List<GeItemBean> yList = new ArrayList<>();
                        yList.add(item);
                        kXInYStatMap.put(bqx, yList);
                    }

                    if (kYInXStatMap.containsKey(bqy)) {
                        List<GeItemBean> xList = kYInXStatMap.get(bqy);
                        if (!xList.contains(item)) {
                            xList.add(item);
                        }
                        Collections.sort(xList);
                    } else {
                        List<GeItemBean> xList = new ArrayList<>();
                        xList.add(item);
                        kYInXStatMap.put(bqy, xList);
                    }
                }
            }
        }

        /**
         * 记录最高点
         */
        private float maxY() {
            if (isBottom) {
                return y;
            }

            kStat();//进行所有点统计并进行排序
            //进行所有点对比得出最理想的滑动距离
            float sety = 100000;//需要改变的Y的值
            float max = gamePanelHeight - gw / 2f;//最大的下滑距离
            float heightX = 10000;//
            GeItemBean fingg = null;//最终以那个点为目标计算出的位置
            Set<Integer> set = kXInYStatMap.keySet();
            for (Integer key : set) {
                List<GeItemBean> yList = kXInYStatMap.get(key);//得到在X点上的统计的点
                //动态块的最高点就是最后一个点
                GeItemBean geItemBean = yList.get(yList.size() - 1);
                int bqx = geItemBean.getBqx();
                float rh = geItemBean.getRh();//动态的格子的目前的高度
                List<GeItemBean> yGeList = xStatInYPointMap.get(bqx);//对应静态上面统计的Y点集合
                float jjrh = max;
                if (yGeList != null && yGeList.size() > 0) {
                    jjrh = yGeList.get(0).getRh();//静态格子的最低高度
                }
                float hx = jjrh - rh;//距离差值
                if (hx < heightX) {//两点距离差小于已知的时候赋值
                    heightX = hx;
                    sety = jjrh;
                    fingg = geItemBean;
                } else if ((int) hx == (int) heightX) {
                    if (jjrh > sety) {
                        sety = jjrh;
                        fingg = geItemBean;
                    }
                }
            }

            if (fingg != null && ltrb != null) {
                GeItemBean bgg = ltrb[3];
                if (bgg != null) {
                    float tx = bgg.getRh() - fingg.getRh();
                    sety = sety + tx;
                }
            }

            //改变下滑的时间距离
            if (sety <= 0) {
                setGAME_OVER(true);
            }
            return sety;
        }

        int typeIndex = 0;//指定当前变换顺序
        //所有形状组合
        int allTypeArray[][][] =
                {
                        {{0, 10, 11, 12}, {20, 21, 11, 1}, {12, 2, 1, 0}, {20, 10, 0, 1}},//l型
                        {{10, 0, 1, 2}, {21, 20, 10, 0}, {2, 12, 11, 10}, {0, 1, 11, 21}},//fl型
                        {{12, 11, 10, 1}, {21, 11, 1, 10}, {2, 1, 0, 11}, {20, 10, 0, 11}},//k型
                        {{0, 1, 2, 3}, {30, 20, 10, 0}},//i型
                        {{0, 10, 11, 12, 2}, {20, 21, 11, 1, 0}, {12, 2, 1, 0, 10}, {21, 20, 10, 0, 1}},//c型
                        {{10, 0, 1, 11}},//o型
                        {{12, 11, 1, 0}, {20, 10, 11, 1}},//fz型
                        {{10, 11, 1, 2}, {21, 11, 10, 0}},//z型
                        {{0}}//点型
                };

        //变形
        void change() {
            synchronized (geItemBeanList) {
                int[][] typeList = allTypeArray[type];//是那种的形状
                typeIndex += 1;
                int index = typeIndex % typeList.length;
                changeAlreadyGePointF(typeList[index]);//处于形状的那个状态
            }
        }

        /**
         * 改变每个点的位置  左右移动位置
         */
        void Cx(float cx) {
            synchronized (geItemBeanList) {
                GeItemBean ltrbGb[] = ltrb;//需要判断左右临界点
                if (ltrbGb == null) {//为空时直接返回，没有进行临界统计
                    return;
                }
                GeItemBean left = ltrbGb[0];//左边绘制点
                GeItemBean right = ltrbGb[2];//右边绘制点
                if (left.canvasReallyPointF.x + cx < gw / 2f) {
                    return;
                } else if (right.canvasReallyPointF.x + cx > w - gw / 2f) {
                    return;
                }
                //改变每个点的位置
                for (GeItemBean item : geItemBeanList) {
                    item.x = item.x + cx;
                }
                //改变组合的位置
                x += cx;
            }
        }


        /**
         * 错误纠正
         */
        private void errorSect() {
            synchronized (geItemBeanList) {
                if (ltrb[0].canvasReallyPointF.x < gw / 2f) {
                    for (GeItemBean geItemBean : geItemBeanList) {
                        geItemBean.x = geItemBean.x + gw;
                    }
                } else if (ltrb[2].canvasReallyPointF.x > w - gw / 2f) {
                    for (GeItemBean geItemBean : geItemBeanList) {
                        geItemBean.x = geItemBean.x - gw;
                    }
                }
            }
        }


        /**
         * 生成
         *
         * @param xy
         * @return
         */
        private List<GeItemBean> def(int... xy) {
            List<GeItemBean> arr = new ArrayList<>();
            for (int d : xy) {
                GeItemBean geItemBean = new GeItemBean(x, y, d);
                arr.add(geItemBean);

            }
            return arr;
        }

        /**
         * 改变已有点的绘制位置
         *
         * @param xy
         */
        private void changeAlreadyGePointF(int... xy) {
            if (geItemBeanList == null)
                return;
            if (geItemBeanList.size() != xy.length) {
                return;
            }
            synchronized (geItemBeanList) {
                for (GeItemBean cp : geItemBeanList) {
                    int index = geItemBeanList.indexOf(cp);
                    cp.setIndex(xy[index]);
                }
            }

        }


        boolean isMove=false;//是否可以移除标致
        /**
         * 绘制所有格子
         *
         * @param canvas
         */
        void draw(Canvas canvas) {
            synchronized (geItemBeanList) {
                if(!isMove&&geItemBeanList.size()==0){
                    isMove=true;
                    return;
                }
                List<GeItemBean> isMoveList = new ArrayList<>();//统计需要移除的item
                for (GeItemBean geItemBean : geItemBeanList) {
                    if (geItemBean.isRemove) {
                        isMoveList.add(geItemBean);//记录需要移除的点
                    }
                    geItemBean.draw(canvas);
                }
                if(isMoveList.size()!=0){
                    geItemBeanList.removeAll(isMoveList);
                    for(GeItemBean mm:isMoveList){
                        mm=null;
                    }
                }
            }
        }


        /**
         * 格子下降变化
         */
        ValueAnimator valueAnimator;
        boolean isBottom = false;
        boolean isChangeSpeed = false;

        private void moveStep() {
            valueAnimator = ValueAnimator.ofFloat(y, gamePanelHeight - gw / 2f);
            valueAnimator.setInterpolator(new LinearInterpolator());
            valueAnimator.setDuration(10000);
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    y = (float) animation.getAnimatedValue();
                    if (y >= maxY()) {
                        y = maxY();
                        isBottom=true;
                        sendStatMessage(y);
                    }
                    changeItemY(y);
                    sort();
                }
            });
            valueAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    if (!GAME_OVER) {
                        synchronized (kuaiBeanList) {
                            kuaiBeanList.add(new KuaiBean());
                        }
                    }
                    sendStatMessage(y);
                    recordXInY();
                    isBottom = true;
                }

                @Override
                public void onAnimationCancel(Animator animation) {
                    super.onAnimationCancel(animation);
                    isBottom=true;
                    sendStatMessage(y);
                }
            });
            valueAnimator.start();
        }

        /**
         * 发送统计信息
         *
         * @param y
         */
        private void sendStatMessage(float y) {
            Message message = new Message();
            message.what = 0;
            message.obj = y;
            handler.sendMessage(message);//发送结束
        }

        Handler handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                if (valueAnimator != null && valueAnimator.isRunning() && valueAnimator.isStarted()) {
                    valueAnimator.cancel();
                    valueAnimator = null;
                    handler = null;
                }
                if(!isBottom){
                    return true;
                }
                LogUtils.e("是否记录了y轴上对应的X点");
                synchronized (yStatMapInXPointMap) {
                    recordYInX();
                    clearGg();
                }
                return true;
            }
        });

        /**
         * 统计每个Y轴上的X点
         */
        private void recordYInX() {
            //统计每个Y轴的X点
            for (GeItemBean kgg : geItemBeanList) {
                int bqy = kgg.getBqy();
                if (yStatMapInXPointMap.containsKey(bqy)) {
                    List<GeItemBean> arr = yStatMapInXPointMap.get(bqy);
                    if (!arr.contains(kgg)) {
                        arr.add(kgg);
                    }
                    Collections.sort(arr);
                } else {
                    List<GeItemBean> arr = new ArrayList<>();
                    arr.add(kgg);
                    yStatMapInXPointMap.put(bqy, arr);
                }
            }
        }

        /**
         * 统计X轴上的Y点
         */
        private void recordXInY() {
            synchronized (geItemBeanList) {
                for (GeItemBean gg : geItemBeanList) {
                    int bqx = gg.getBqx();
                    if (xStatInYPointMap.containsKey(bqx)) {
                        List<GeItemBean> ygg = xStatInYPointMap.get(bqx);
                        if (!ygg.contains(gg)) {
                            ygg.add(gg);
                        }
                        Collections.sort(ygg);
                    } else {
                        List<GeItemBean> yggList = new ArrayList<>();
                        yggList.add(gg);
                        xStatInYPointMap.put(bqx, yggList);
                    }
                }
            }
        }

        /**
         * 改变格子的Y轴位置
         *
         * @param y
         */
        private void changeItemY(float y) {
            synchronized (kuaiBeanList) {
                for (GeItemBean geItemBean : geItemBeanList) {
                    geItemBean.setY(y);
                }
            }
        }
    }

    /**
     * 清除满格条件
     */
    private void clearGg() {
        int myCount = (int) (w / gw);
        List<Integer> mmyList = new ArrayList<>();//统计需要改变的Y轴上X点
        Set<Integer> set = yStatMapInXPointMap.keySet();
        for (Integer key : set) {
            List<GeItemBean> geItemBeanList = yStatMapInXPointMap.get(key);
            if (geItemBeanList.size() >= myCount) {
                //满员处理
                for (GeItemBean ism : geItemBeanList) {
                    ism.setRemove(true);//设置需要移除标记
                }
                geItemBeanList.clear();//清空y轴上的x统计
                mmyList.add(key);
            }
        }

        //统一做了下移操作//对需要处理的Y轴进行正序排序
        Collections.sort(mmyList, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1.compareTo(o2);
            }
        });

        int mmySize = mmyList.size();//需要处理的y轴行数
        //需要统一做下移操作
        if (mmySize == 1 || mmySize == 4) {
            //只消除了1行或者4行的时候
            int mmy = mmyList.get(0);
            Set<Integer> xyControlSet = yStatMapInXPointMap.keySet();
            for (Integer key : xyControlSet) {
                List<GeItemBean> geItemBeans = yStatMapInXPointMap.get(key);
                if (key < mmy) {
                    for (GeItemBean xl : geItemBeans) {
                        xl.setY(xl.getY() + gw * mmySize);
                    }
                }
            }
        } else if (mmySize == 2) {
            //消除了2行的情况
            int mmy1 = mmyList.get(0);//小
            int mmy2 = mmyList.get(1);//大
            Set<Integer> xyControlSet = yStatMapInXPointMap.keySet();
            for (Integer key : xyControlSet) {
                List<GeItemBean> geItemBeans = yStatMapInXPointMap.get(key);
                if (key > mmy1 && key < mmy2) {
                    for (GeItemBean xl : geItemBeans) {
                        xl.setY(xl.getY() + gw);
                    }
                } else if (key < mmy1) {
                    for (GeItemBean xl : geItemBeans) {
                        xl.setY(xl.getY() + mmySize * gw);
                    }
                }
            }
        } else if (mmySize == 3) {
            //消除3行的情况
            int mmy1 = mmyList.get(0);//最小
            int mmy2 = mmyList.get(1);//中间数
            int mmy3 = mmyList.get(2);//最大

            Set<Integer> xyControlSet = yStatMapInXPointMap.keySet();
            for (Integer key : xyControlSet) {
                List<GeItemBean> geItemBeans = yStatMapInXPointMap.get(key);
                if (key > mmy2 && key < mmy3) {
                    for (GeItemBean xl : geItemBeans) {
                        xl.setY(xl.getY() + gw);
                    }
                } else if (key < mmy2 && key > mmy1) {
                    for (GeItemBean xl : geItemBeans) {
                        xl.setY(xl.getY() + 2 * gw);
                    }
                } else if (key < mmy1) {
                    for (GeItemBean xl : geItemBeans) {
                        xl.setY(xl.getY() + mmySize * gw);
                    }
                }
            }
        }

        //下移后需要重新统计y轴上的X点
        TreeMap<Integer, List<GeItemBean>> resetMap = new TreeMap<>();
        Set<Integer> cpTjSet = yStatMapInXPointMap.keySet();
        for (Integer key : cpTjSet) {
            List<GeItemBean> cpTjList = yStatMapInXPointMap.get(key);
            if (cpTjList.size() > 0) {
                for(GeItemBean cg:cpTjList){
                    if(cg.isRemove)//需要移除的点不做处理
                        continue;
                    int reallyBqy=cg.getBqy();
                    if(resetMap.containsKey(reallyBqy)){
                        List<GeItemBean> geItemBeans=resetMap.get(reallyBqy);
                            geItemBeans.add(cg);
                    }else{
                        List<GeItemBean> geItemBeans=new ArrayList<>();
                        geItemBeans.add(cg);
                        resetMap.put(reallyBqy,geItemBeans);
                    }
                }
            }
        }

        yStatMapInXPointMap.clear();
        yStatMapInXPointMap.putAll(resetMap);

        //统一移除统计里面的x轴对应的y点
        Set<Integer> xinty = xStatInYPointMap.keySet();
        for (Integer key : xinty) {
            List<GeItemBean> xinyRemoveList = new ArrayList<>();
            List<GeItemBean> xinyList = xStatInYPointMap.get(key);
            for (GeItemBean xyItem : xinyList) {
                if (xyItem.isRemove) {
                    xinyRemoveList.add(xyItem);
                }
            }
            if (xinyRemoveList.size() == 0) {
                continue;
            }
            xinyList.removeAll(xinyRemoveList);
        }
    }

    /**
     * 格子坐标的点位
     */
    class GeItemBean implements Serializable, Comparable<GeItemBean> {
        float x, y;
        Paint geItemPaint;
        PointF artPointF;
        boolean isRemove = false;

        void setRemove(boolean remove) {
            isRemove = remove;
        }

        //针对下标进行点绘制，不同点算法不同 比如 0的时候就是x,y  1的时候根据算法所得 x,y0+1
        public int index = 0;

        GeItemBean(float x, float y, int index) {
            this.index = index;
            this.x = x;
            this.y = y;
            geItemPaint = new Paint(p);
            geItemPaint.setStrokeWidth(gw*0.9f);
            geItemPaint.setColor(ColorUtil.randomColor());
            artPointF = pointFSparseArray.get(index);

            float px = x + artPointF.x * gw;
            float py = y + artPointF.y * gw;
            canvasReallyPointF.x = px;
            canvasReallyPointF.y = py;
            rh = py - gw;
            bqx = (int) px;
            bqy = (int) py;
        }

        /**
         * 改变绘制点位置
         *
         * @param index
         */
        public void setIndex(int index) {
            this.index = index;
            artPointF = pointFSparseArray.get(index);
        }

        public void setY(float y) {
            this.y = y;
        }

        public float getY() {
            return y;
        }

        PointF canvasReallyPointF = new PointF();
        float rh = 0;
        int bqx = 0;
        int bqy = 0;

        public float getRh() {
            return rh;
        }

        public int getBqx() {
            return bqx;
        }

        public int getBqy() {
            return bqy;
        }

        public void draw(Canvas canvas) {
            float px = x + artPointF.x * gw;
            float py = y + artPointF.y * gw;
            canvasReallyPointF.x = px;
            canvasReallyPointF.y = py;
            rh = py - gw;
            bqx = (int) px;
            bqy = (int) py;
            canvas.drawPoint(px, py, geItemPaint);
        }

        @Override
        public int compareTo(@NonNull GeItemBean o) {
            int i = 0;
            int cy = this.bqy - o.getBqy();
            int cx = this.bqx - o.getBqx();
            if (cx == 0) {
                if (cy > 0) {
                    i = 1;
                } else if (cy < 0) {
                    i = -1;
                }
            } else {
                if (cx > 0) {
                    i = 1;
                } else {
                    i = -1;
                }
            }
            return i;
        }
    }
}
