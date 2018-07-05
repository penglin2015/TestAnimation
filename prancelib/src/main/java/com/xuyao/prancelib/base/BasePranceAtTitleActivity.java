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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xuyao.prancelib.R;
import com.xuyao.prancelib.interfaces.TitleInterface;
import com.xuyao.prancelib.util.IntentUtil;
import com.xuyao.prancelib.util.StatusBarUtil;
import com.xuyao.prancelib.util.UmPlusUtil;

import butterknife.ButterKnife;

/**
 * Created by T470 on 2018/3/26.
 */

public abstract class BasePranceAtTitleActivity extends AppCompatActivity {


    protected Activity mActivity;
    LinearLayout baseContent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_prance_at_title);
        baseContent=fv(R.id.baseContent);
        LayoutInflater.from(this).inflate(getLayoutId(),baseContent,true);
        StatusBarUtil.initLightStatusBarTextColor(this);
        setRootBackgroundColor(R.color.white);
        setContentBackground(R.drawable.bac_draw_res);
        mActivity=this;
        ButterKnife.bind(this);
        init(savedInstanceState);
        initTitleView();
        IntentUtil.addActivities(mActivity);
    }


    public void setRootBackground(int drawableRes){
        getWindow().getDecorView().setBackgroundResource(drawableRes);
    }


    public void setRootBackgroundColor(int colorRes){
        getWindow().getDecorView().setBackgroundColor(getResources().getColor(colorRes));
    }


    public void setContentBackground(int drawableRes){
        findViewById(R.id.baseContent).setBackgroundResource(drawableRes);
    }


    public void setContentBackgroundColor(int colorRes){
        findViewById(R.id.baseContent).setBackgroundColor(getResources().getColor(colorRes));
    }

    View titleView;
    TextView centerTitle;

    ImageView leftImgView;

    TextView rightName;
    ImageView rightImgView;

    private void initTitleView() {
        titleView=fv(R.id.titleGroup);
        centerTitle=fv(R.id.titleName);

        leftImgView=fv(R.id.back);

        rightName=fv(R.id.right_text);
        rightImgView=fv(R.id.right_icon);

        if(titleInterface==null){
            return;
        }

        String title=titleInterface.setTitle();
        int leftres=titleInterface.setLeftImgRes();
        int rightres=titleInterface.setRightImgRes();
        String rightname=titleInterface.rightName();


        centerTitle.setText(title!=null?title:"");
        centerTitle.setVisibility(title!=null&&!title.equals("")?View.VISIBLE:View.GONE);

        leftImgView.setImageResource(leftres);
        leftImgView.setVisibility(leftres!=0?View.VISIBLE:View.GONE);
        rightImgView.setImageResource(rightres);
        rightImgView.setVisibility(rightres!=0?View.VISIBLE:View.GONE);
        rightName.setText(rightname!=null?rightname:"");
        rightName.setVisibility(rightname!=null&&!rightname.equals("")?View.VISIBLE:View.GONE);

        View.OnClickListener titleClickListener=new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id=view.getId();
                titleInterface.onClickListener(view,id==R.id.back? TitleInterface.TitleMode.leftimg:id==R.id.right_icon? TitleInterface.TitleMode.rightimg:id==R.id.right_text? TitleInterface.TitleMode.rightnme: TitleInterface.TitleMode.default_);
            }
        };

        leftImgView.setOnClickListener(titleClickListener);
        rightImgView.setOnClickListener(titleClickListener);
        rightName.setOnClickListener(titleClickListener);
    }

    /**
     * 设置头部的回调参数
     */
    TitleInterface titleInterface;
    public BasePranceAtTitleActivity setTitleInterface(TitleInterface titleInterface) {
        this.titleInterface = titleInterface;
        return this;
    }

    protected abstract int getLayoutId();//获得设置的布局id

    protected abstract void init(Bundle savedInstanceState);//加载


    protected abstract void realTimeRefresh();//切换界面堆栈中实时刷新




    protected   <T extends View> T fv(int id){
        return findViewById(id);
    }

    @Override
    protected void onResume() {
        super.onResume();
        realTimeRefresh();
        new UmPlusUtil(mActivity).onR();
    }

    @Override
    protected void onPause() {
        super.onPause();
        new UmPlusUtil(mActivity).onP();
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

}
