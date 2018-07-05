package com.shouxindao.testanimation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ViewFlipper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TestViewFlipperActivity extends AppCompatActivity {

    @BindView(R.id.vf)
    ViewFlipper vf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_view_flipper);
        ButterKnife.bind(this);

        vf.setAutoStart(true);
        vf.setFlipInterval(2000);
        vf.setInAnimation(this,R.anim.test1);
        vf.setOutAnimation(this,R.anim.test);
        for(TextView tv:getTv()){
            vf.addView(tv);
        }
        vf.startFlipping();
    }


    private List<TextView> getTv(){
        List<TextView> textViews=new ArrayList<>();
        for(int i=0;i<8;i++){
            TextView textView=new TextView(this);
            textView.setText("dfafa"+i);
            textView.setTextSize(56);
            ViewGroup.LayoutParams vlp=new ViewGroup.LayoutParams(-1,-1);
            textView.setGravity(Gravity.CENTER);
            textView.setLayoutParams(vlp);
            textViews.add(textView);
        }
        return textViews;
    }
}
