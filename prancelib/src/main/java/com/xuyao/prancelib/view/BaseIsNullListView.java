package com.xuyao.prancelib.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.xuyao.prancelib.R;

public class BaseIsNullListView extends FrameLayout implements Runnable{
    public BaseIsNullListView(@NonNull Context context) {
        super(context);
        init(context);
    }

    public BaseIsNullListView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public BaseIsNullListView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    BaseRecyclerView baseRecyclerView;
    PranceRefreshView refreshView;

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.view_null_list,this,true);
        baseRecyclerView=findViewById(R.id.list);
        refreshView=findViewById(R.id.refresh);

        findViewById(R.id.nullPenpal).setVisibility(GONE);
        findViewById(R.id.nullPenpal).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(refreshTouch==null)
                    return;
                refreshTouch.refresh();
            }
        });
        postDelayed(this,500);
    }

    /**
     * 检测是否显示状态
     */
    private void checkStatus(){
        RecyclerView.Adapter adapter=baseRecyclerView.getAdapter();
        if(adapter!=null){
            if(adapter.getItemCount()!=0){
                setNullViewStatus(false);
                return ;
            }
            setNullViewStatus(true);
        }
    }

    public BaseRecyclerView getBaseRecyclerView() {
        return baseRecyclerView;
    }

    public PranceRefreshView getRefreshView() {
        return refreshView;
    }

    /**
     * 设置状态
     *
     * @param isShow
     */
    public void setNullViewStatus(boolean isShow) {
        findViewById(R.id.nullPenpal).setVisibility(isShow ? VISIBLE : GONE);
        refreshView.setVisibility(isShow ? GONE : VISIBLE);
    }

    @Override
    public void run() {
        checkStatus();
        postDelayed(this,500);
    }


    public interface RefreshTouch{
        void refresh();
    }

    RefreshTouch refreshTouch;
    public void setRefreshTouch(RefreshTouch refreshTouch) {
        this.refreshTouch = refreshTouch;
    }
}
