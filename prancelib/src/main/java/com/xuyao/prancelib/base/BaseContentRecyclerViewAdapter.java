package com.xuyao.prancelib.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xuyao.prancelib.util.LogUtils;

import java.util.ArrayList;
import java.util.List;
import butterknife.ButterKnife;


public abstract class BaseContentRecyclerViewAdapter<T,V extends BaseContentRecyclerViewAdapter.BaseViewHolder> extends RecyclerView.Adapter<V> {


    List<T> datas = new ArrayList<>();//设置的数据
    Context context;

    public abstract void setDataToItemUI(V holder, int position);

    public abstract int getLayoutItemId(int viewType,boolean isItem);

    public abstract V getViewHolder(View v,int viewType,boolean isItem);

    public abstract void itemClick(V holder, int position);

    public abstract void setNotDataToUI(V holder,int viewType);


    int notDataSize=0;//不是数据的数量
    int itemCount=0;
    public int getNotDataSize() {
        return notDataSize;
    }

    public void setNotDataSize(int notDataSize) {
        this.notDataSize = notDataSize;
    }

    public BaseContentRecyclerViewAdapter(Context context) {
        this.context = context;
    }


    @Override
    public void onBindViewHolder( final V holder, final int position) {
        final int dataPosition=position-getNotDataSize();//除开顶部的数据以外的item下标
        if(dataPosition>=0){
            setDataToItemUI(holder, dataPosition);//设置数据到itemUI显示
            holder.itemView.setOnClickListener(new View.OnClickListener() {//item点击事件
                @Override
                public void onClick(View v) {
                    itemClick(holder,dataPosition);
                }
            });
        }else{
            setNotDataToUI(holder,position);//不是item数据的设置数据方法
        }
    }


    @Override
    public V onCreateViewHolder( ViewGroup parent, int viewType) {
        int dataPosition=viewType-getNotDataSize();
        return getViewHolder(LayoutInflater.from(context).inflate(getLayoutItemId(viewType,dataPosition>=0?true:false), null),dataPosition<0?viewType:dataPosition,dataPosition>=0?true:false);
    }


    @Override
    public int getItemCount() {
        return itemCount;
    }


    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public  static abstract class BaseViewHolder extends ViewHolder {
        Context context;
        View itemView;

        public BaseViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            context = itemView.getContext();
            ButterKnife.bind(this,itemView);
        }
    }


    public void setDatas(List<T> datas) {
        this.datas = datas;
        itemCount=getNotDataSize()+datas.size();
        notifyDataSetChanged();
    }

    public List<T> getDatas() {
        return datas;
    }

    public Context getContext() {
        return context;
    }
}
