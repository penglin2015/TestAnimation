package com.xuyao.prancelib.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidquery.AQuery;
import com.xuyao.prancelib.interfaces.BaseUtilRecyclerOnItemClickListener;
import com.xuyao.prancelib.util.ScreenUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeMap;

/**
 * Created by admin on 2017/6/13.
 * 自定义父类RecyclerView的适配器
 */

public class BaseUtilLiveRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    Context context;//上下文
    LayoutInflater inflater;//视图适配器

    public Context getContext() {
        return context;
    }

    public LayoutInflater getInflater() {
        return inflater;
    }
    AQuery imgQuery;
    public AQuery getImgQuery() {
        return imgQuery;
    }

    public BaseUtilLiveRecyclerViewAdapter(Context context){
        this.context=context;
        inflater=LayoutInflater.from(context);
        imgQuery=new AQuery(context);
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return setRecyclerViewHolder(viewType);
    }

    /**
     * 自定义创建ViewHolder
     * @param viewType
     * @return
     */
    protected RecyclerView.ViewHolder setRecyclerViewHolder(int viewType){
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        setDataToView(holder,position);
        UtilRecyclerViewHolder utilRecyclerViewHolder= (UtilRecyclerViewHolder) holder;
        setConvertView(utilRecyclerViewHolder.getConvertView(),position);
    }

    /**
     * 绑定数据到视图
     * @param holder
     * @param position
     */
    protected void setDataToView(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemViewType(int position) {
        return getViewType(position);
    }

    /**
     * 自定义获取item类型
     * @param position
     * @return
     */
    protected int getViewType(int position){
        return position;
    }

    @Override
    public int getItemCount() {
        return getItemSize();
    }

    /**
     * 自定义获得子项数
     * @return
     */
    protected int getItemSize(){
        return getDatas().size();
    }

    /**
     * 设置数据为list集合
     */
    List<?> datas=new ArrayList<>();
    public void setDatas(List<?> datas){
        this.datas=datas;
        notifyDataSetChanged();
    }

    /**
     * 设置数据为顺序map
     */
    TreeMap<Integer,?> mapDatas=new TreeMap<>();
    public void setDatas(TreeMap<Integer,Object> mapDatas){
        this.mapDatas=mapDatas;
        notifyDataSetChanged();
    }

    /**
     * 设置数据
     * @param datas
     */
    public void setDatas(Object ...datas){
        this.datas= Arrays.asList(datas);
        notifyDataSetChanged();
    }


    public List<?> getDatas() {
        return datas;
    }

    public TreeMap<Integer, ?> getMapDatas() {
        return mapDatas;
    }

    public void setMapDatas(TreeMap<Integer, ?> mapDatas) {
        this.mapDatas = mapDatas;
        notifyDataSetChanged();
    }

    private void setConvertView(final View convertView, final int position){
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onItemClickListener==null)
                    return;
                onItemClickListener.onItemClick(convertView,position);
            }
        });

    }

    public void setOnItemClickListener(final BaseUtilRecyclerOnItemClickListener onItemClickListener){
        this.onItemClickListener=onItemClickListener;
    }

    BaseUtilRecyclerOnItemClickListener onItemClickListener;

    /**
     * 自定义ViewHolder
     */
    public class UtilRecyclerViewHolder extends RecyclerView.ViewHolder{

        View convertView;
        public View getConvertView() {
            return convertView;
        }

        public UtilRecyclerViewHolder(View itemView) {
            super(itemView);
            this.convertView=itemView;
        }

        /**
         * 寻找控件
         * @param id
         * @return
         */
        public <T extends View> T fv(int id){
            return convertView.findViewById(id);
        }
    }


    protected  int getw(){
        return ScreenUtils.getScreenWidth(context);
    }

    protected int geth(){
        return ScreenUtils.getScreenHeight(context);
    }
}
