package com.xuyao.prancelib.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.androidquery.AQuery;
import com.xuyao.prancelib.util.ScreenUtils;

import java.util.List;

/**
 * Created by PL_Moster on 2017/2/24.
 */

public class BaseUtilAdapter extends BaseAdapter{

    Context context;
    List<?> datas;
    LayoutInflater inflater;

    public LayoutInflater getInflater() {
        return inflater;
    }

    public void setInflater(LayoutInflater inflater) {
        this.inflater = inflater;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public List<?> getDatas() {
        return datas;
    }

    AQuery imgQuery;

    public AQuery getImgQuery() {
        return imgQuery;
    }

    public void setImgQuery(AQuery imgQuery) {
        this.imgQuery = imgQuery;
    }

    public BaseUtilAdapter(List<?> datas, Context context){
        this.context=context;
        this.datas=datas;
        inflater=LayoutInflater.from(context);
        imgQuery=new AQuery(context);
    }

    public void setDatas(List<?> datas){
        this.datas=datas;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Object holder=null;
        if(convertView==null){
            convertView=setConvertView();
            holder= setHolder();
            initChildView(convertView,holder);
            convertView.setTag(holder);
        }else{
            holder=convertView.getTag();
        }
        setDataToUI(position,convertView,parent,holder);
        return convertView;
    }

    public void setDataToUI(int position, View convertView, ViewGroup parent, Object holder) {

    }

    public void initChildView(View convertView, Object holder) {

    }

    public Object setHolder(){
        return null;
    }

    public View setConvertView(){
        return null;
    }

    protected View inflaterView(int lay_id){
        return getInflater().inflate(lay_id,null);
    }

    protected  int getw(){
        return ScreenUtils.getScreenWidth(context);
    }

    protected int geth(){
        return ScreenUtils.getScreenHeight(context);
    }
}
