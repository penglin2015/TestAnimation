package com.xuyao.prancelib.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.xuyao.prancelib.R;
import com.xuyao.prancelib.util.DensityUtil;
import com.xuyao.prancelib.util.IntentUtil;
import com.xuyao.prancelib.util.StatusBarUtil;
import com.xuyao.prancelib.util.UmPlusUtil;

import butterknife.ButterKnife;

public abstract class BaseWithAtTitleActivity extends AppCompatActivity {


    protected abstract void init();

    AQuery mAQuery;
    protected TitleBuildConfig titleBuildConfig;
    protected AppCompatActivity mActivity;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_base_prance_at_title);
        mActivity = this;
        mAQuery = new AQuery(this);
        titleBuildConfig = new TitleBuildConfig(mAQuery.id(R.id.titleGroup).getView());
        IntentUtil.addActivities(mActivity);
    }

    public AQuery getmAQuery() {
        return mAQuery;
    }

    public TitleBuildConfig getTitleBuildConfig() {
        return titleBuildConfig;
    }

    ViewGroup baseContentViewGroup;

    @Override
    public void setContentView(int layoutResID) {
        baseContentViewGroup = findViewById(R.id.baseContent);
        LayoutInflater.from(this).inflate(layoutResID, baseContentViewGroup, true);
        ButterKnife.bind(this);
        setRootBackgroundColor(R.color.white);
        setContentBackground(R.drawable.bac_draw_res);
        StatusBarUtil.initLightStatusBarTextColor(this);
        init();
    }


    public void setRootBackground(int drawableRes) {
        getWindow().getDecorView().setBackgroundResource(drawableRes);
    }


    public void setRootBackgroundColor(int colorRes) {
        getWindow().getDecorView().setBackgroundColor(getResources().getColor(colorRes));
    }

    public void setContentBackground(int drawableRes) {
        baseContentViewGroup.setBackgroundResource(drawableRes);
    }


    public void setContentBackgroundColor(int colorRes) {
        baseContentViewGroup.setBackgroundColor(getResources().getColor(colorRes));
    }

    /**
     * 头部的一些参数
     */
    public class TitleBuildConfig {
        View titleView;
        ImageView back;
        TextView titleName;
        ImageView right_icon;
        TextView right_text;

        public TitleBuildConfig(View titleView) {
            this.titleView = titleView;
            back = titleView.findViewById(R.id.back);
            titleName = titleView.findViewById(R.id.titleName);
            right_icon = titleView.findViewById(R.id.right_icon);
            right_text = titleView.findViewById(R.id.right_text);
        }

        public void hideTitle(){
            titleView.setVisibility(View.GONE);
        }

        public void showTitle(){
            titleView.setVisibility(View.VISIBLE);
        }

        String title;
        int leftIvRes;
        int rightIvRes;
        String rightName;

        int titleTextColor;
        int rightTextColor;
        int titleTextSize;
        int rightTextSize;


        public String getTitle() {
            return title;
        }

        public TitleBuildConfig setTitle(String title) {
            this.title = title;
            build();
            return this;
        }

        public int getLeftIvRes() {
            return leftIvRes;
        }

        public TitleBuildConfig setLeftIvRes(int leftIvRes) {
            this.leftIvRes = leftIvRes;
            build();
            return this;
        }

        public int getRightIvRes() {
            return rightIvRes;
        }

        public TitleBuildConfig setRightIvRes(int rightIvRes) {
            this.rightIvRes = rightIvRes;
            build();
            return this;
        }

        public String getRightName() {
            return rightName;
        }

        public TitleBuildConfig setRightName(String rightName) {
            this.rightName = rightName;
            build();
            return this;
        }

        public int getTitleTextColor() {
            return titleTextColor;
        }

        public TitleBuildConfig setTitleTextColor(int titleTextColor) {
            this.titleTextColor = titleTextColor;
            build();
            return this;
        }

        public int getRightTextColor() {
            return rightTextColor;
        }

        public TitleBuildConfig setRightTextColor(int rightTextColor) {
            this.rightTextColor = rightTextColor;
            build();
            return this;
        }

        public int getTitleTextSize() {
            return titleTextSize;
        }

        public TitleBuildConfig setTitleTextSize(int titleTextSize) {
            this.titleTextSize = titleTextSize;
            build();
            return this;
        }

        public int getRightTextSize() {
            return rightTextSize;
        }

        public TitleBuildConfig setRightTextSize(int rightTextSize) {
            this.rightTextSize = rightTextSize;
            build();
            return this;
        }

        public void build() {
            back.setVisibility(getLeftIvRes() == 0 ? View.GONE : View.VISIBLE);
            back.setImageResource(getLeftIvRes());
            titleName.setText(getTitle() == null ? "" : getTitle());
            titleName.setVisibility(getTitle() == null || getTitle().equals("") ? View.GONE : View.VISIBLE);
            if (titleTextColor != 0) {
                titleName.setTextColor(titleTextColor);
            }

            titleName.setTextSize(TypedValue.COMPLEX_UNIT_PX, titleTextSize == 0 ? titleName.getTextSize() : titleTextSize);
            right_icon.setVisibility(getRightIvRes() == 0 ? View.GONE : View.VISIBLE);
            right_icon.setImageResource(getRightIvRes());
            right_text.setVisibility(getRightName() == null || getRightName().equals("") ? View.GONE : View.VISIBLE);
            right_text.setText(getRightName() == null ? "" : getRightName());
            right_text.setTextSize(TypedValue.COMPLEX_UNIT_PX, rightTextSize == 0 ? right_text.getTextSize() : rightTextSize);
            if (rightTextColor != 0) {
                right_text.setTextColor(rightTextColor);
            }
            back.setOnClickListener(titleClickListener);
            titleName.setOnClickListener(titleClickListener);
            right_icon.setOnClickListener(titleClickListener);
            right_text.setOnClickListener(titleClickListener);
        }


        View.OnClickListener titleClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = view.getId();
                titleClickModeCallBack(id == R.id.back ? TitleMode.leftIv : id == R.id.titleName ? TitleMode.centerTv : id == R.id.right_icon ? TitleMode.rightIv : id == R.id.right_text ? TitleMode.rightTv : TitleMode.default_);
            }
        };
    }


    public abstract void titleClickModeCallBack(TitleMode titleMode);

    public enum TitleMode {
        leftIv, centerTv, rightIv, rightTv, default_
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

    protected float getScreenWidth(){
        return DensityUtil.getDisPlayWidth(this);
    }

    protected float getScreenHeight(){
        return DensityUtil.getDisPlayHeight(this);
    }
}
