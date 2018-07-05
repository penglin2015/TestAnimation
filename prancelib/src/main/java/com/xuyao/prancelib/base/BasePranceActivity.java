package com.xuyao.prancelib.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xuyao.prancelib.R;
import com.xuyao.prancelib.interfaces.BaseCallBack;
import com.xuyao.prancelib.util.IntentUtil;
import com.xuyao.prancelib.util.ScreenUtils;
import com.xuyao.prancelib.util.StatusBarUtil;
import com.xuyao.prancelib.util.UmPlusUtil;

import butterknife.ButterKnife;

/**
 * Created by T470 on 2018/1/27.
 */

public abstract class BasePranceActivity extends AppCompatActivity implements BaseCallBack {

    protected Activity mActivity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity=this;
        init(savedInstanceState);

        IntentUtil.addActivities(mActivity);
    }


    ViewGroup contentLl=null;//内容容器
    @Override
    public void init(Bundle... bundle) {
        super.setContentView(R.layout.activity_base_prance);
        contentLl=fv(R.id.contentLl);
        if(layoutId()!=0){
            setContentView(layoutId());
        }
        setRootColor(R.color.white);
        setContentDrawable(R.drawable.bac_draw_res);
        initInData();
        initView();
        initEvent();
        initEndData();
    }
    @Override
    public void setContentView(int layoutResID) {
        LayoutInflater.from(this).inflate(layoutResID,contentLl,true);
        ButterKnife.bind(this);

        StatusBarUtil.initLightStatusBarTextColor(this);
    }

    @Override
    public void setContentView(View view) {
        contentLl.addView(view);
        ButterKnife.bind(this);
    }


    protected void setRootDrawable(int drawableRes){
        getWindow().getDecorView().setBackgroundResource(drawableRes);
    }


    protected void setRootColor(int colorRes){
        getWindow().getDecorView().setBackgroundColor(getResources().getColor(colorRes));
    }



    protected void setContentDrawable(int drawableRes){
        contentLl.setBackgroundResource(drawableRes);
    }


    protected void setContentColor(int colorRes){
        contentLl.setBackgroundColor(getResources().getColor(colorRes));
    }


    protected <T extends View> T fv(int id){
        return findViewById(id);
    }


    /**
     * 切换碎片方法
     *
     * @param fragment
     * @param fragmentTag
     */
    Fragment beforeFragment;

    protected void changeFragment(Fragment fragment, int parentLayId) {
        FragmentManager fm = getSupportFragmentManager();//管理器
        String tag = fragment.getClass().getSimpleName();
        FragmentTransaction ft = fm.beginTransaction();
        if (beforeFragment != null) {
            if (!beforeFragment.equals(fragment)) {
                ft.hide(beforeFragment);
            }
            if (fragment.isAdded()) {
                ft.show(fragment);
            } else {
                ft.add(parentLayId, fragment, tag);
            }
        } else {
            ft.add(parentLayId, fragment, tag);
        }
        ft.commit();
        beforeFragment = fragment;
    }


    protected  int getw(){
        return ScreenUtils.getScreenWidth(this);
    }

    protected int geth(){
        return ScreenUtils.getScreenHeight(this);
    }


    @Override
    protected void onResume() {
        super.onResume();
        new UmPlusUtil(mActivity).onR();
    }

    @Override
    protected void onPause() {
        super.onPause();
        new UmPlusUtil(mActivity).onP();
    }
}
