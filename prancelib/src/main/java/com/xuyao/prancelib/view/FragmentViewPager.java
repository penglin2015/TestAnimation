package com.xuyao.prancelib.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;

import java.util.ArrayList;
import java.util.List;

public class FragmentViewPager extends ViewPager{


    public FragmentViewPager(@NonNull Context context) {
        super(context);
    }

    public FragmentViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }


    public void setFragmentCards(AppCompatActivity activity, List<Fragment> fragmentCards){
        FvpAdapter fvpAdapter=new FvpAdapter(activity.getSupportFragmentManager(),fragmentCards);
        setAdapter(fvpAdapter);
        setOffscreenPageLimit(fragmentCards.size());
    }


    class FvpAdapter extends FragmentPagerAdapter{

        List<Fragment> fragments=new ArrayList<>();
        public FvpAdapter(FragmentManager fm, List<Fragment> fragments) {
            super(fm);
            this.fragments=fragments;
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public int getItemPosition(@NonNull Object object) {
            return POSITION_NONE;
        }
    }


    public interface OnPageChangeSelectedListener{
        void onPageSelected(int position);
    }

    OnPageChangeSelectedListener onPageChangeSelectedListener;
    public void setOnPageChangeSelectedListener(final OnPageChangeSelectedListener onPageChangeSelectedListener) {
        this.onPageChangeSelectedListener = onPageChangeSelectedListener;
        addOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(onPageChangeSelectedListener==null)
                    return;
                onPageChangeSelectedListener.onPageSelected(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

}
