package com.shouxindao.testanimation;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TestSlideMenuActivity extends AppCompatActivity {

    @BindView(R.id.clickShowSlideMenu)
    Button clickShowSlideMenu;
    @BindView(R.id.nav)
    NavigationView nav;
    @BindView(R.id.dly)
    DrawerLayout dly;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_slide_menu);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.clickShowSlideMenu)
    public void onViewClicked() {
        if(dly.isDrawerOpen(nav)){
            dly.closeDrawer(nav);
            return;
        }
        dly.openDrawer(nav);
    }
}
