package com.shouxindao.testanimation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.shouxindao.testanimation.views.KuaiView;
import com.xuyao.prancelib.util.ScreenUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class KuaiActivity extends AppCompatActivity {

    @BindView(R.id.kv)
    KuaiView kv;
    @BindView(R.id.controlGroup)
    LinearLayout controlGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kuai);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.up, R.id.left, R.id.center, R.id.right, R.id.down, R.id.change})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.up:
                break;
            case R.id.left:
                kv.changeX(-1);
                break;
            case R.id.center:
                kv.change();
                break;
            case R.id.right:
                kv.changeX(1);
                break;
            case R.id.down:
                kv.changeSpeed();
                break;
            case R.id.change:
                kv.change();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        kv.setGAME_OVER(true);
    }
}
