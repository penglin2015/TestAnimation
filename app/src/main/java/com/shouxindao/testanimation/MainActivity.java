package com.shouxindao.testanimation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.shouxindao.testanimation.util.SoundUtil;
import com.xuyao.prancelib.util.IntentUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.gotoZhenTv)
    Button gotoZhenTv;
    @BindView(R.id.gotoBetweenAnimationTv)
    Button gotoBetweenAnimationTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        SoundUtil.getInstance(this).initMed(true);
    }


    @OnClick({R.id.gotoTestBasinAnimation,
            R.id.gotoTestUtilSlideMenu,
            R.id.gotoTestSlideMenu,
            R.id.gotoTestViewPager,
            R.id.gotoTestKotlin,
            R.id.gotoTestRecycler,
            R.id.gotoDynamicBz,
            R.id.gotoTestCircleButton,
            R.id.gotoTestViewFlipper,
            R.id.gotoFlyBird,
            R.id.gotoTestFoucus,
            R.id.gotoMoveAnimation,
            R.id.gotoZhenTv,
            R.id.gotoBetweenAnimationTv,
            R.id.gotoPropertyAnimatiorTv,
            R.id.gotoMix,
            R.id.gotoBz,
            R.id.gotoBzAnimation,
            R.id.gotoWeatherAnimation,
            R.id.gotoGreenEyes,
            R.id.gotoWaterChange,
            R.id.gotoPaoPao,
            R.id.gotoKuaiGame})
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.gotoZhenTv:
                startActivity(new Intent(this, ZhenAnimationActivity.class));
                break;
            case R.id.gotoBetweenAnimationTv:
                startActivity(new Intent(this, BetweenAnimationActivity.class));
                break;
            case R.id.gotoPropertyAnimatiorTv:
                startActivity(new Intent(this, PropertyAnimatiorActivity.class));
                break;
            case R.id.gotoMix:
                startActivity(new Intent(this, SnowFlakeActivity.class));
                break;
            case R.id.gotoBz:
                startActivity(new Intent(this, BezierActivity.class));
                break;
            case R.id.gotoBzAnimation:
                startActivity(new Intent(this, BzAnimationActivity.class));
                break;
            case R.id.gotoMoveAnimation:
                startActivity(new Intent(this, MoveAnimationActivity.class));
                break;

            case R.id.gotoTestFoucus:
                IntentUtil.startSiampleActivity(MainActivity.this, TestFoucusActivity.class);
                break;
            case R.id.gotoFlyBird:
                IntentUtil.startSiampleActivity(MainActivity.this, FlyBirdActivity.class);
                break;
            case R.id.gotoTestViewFlipper:
                IntentUtil.startSiampleActivity(MainActivity.this, TestViewFlipperActivity.class);
                break;
            case R.id.gotoTestCircleButton:
                IntentUtil.startSiampleActivity(MainActivity.this, TestCircleButtonActivity.class);
                break;
            case R.id.gotoDynamicBz:
                IntentUtil.startSiampleActivity(MainActivity.this, DynamicActivity.class);
                break;
            case R.id.gotoTestRecycler:

                IntentUtil.startSiampleActivity(MainActivity.this, TestRecyclerForPositionActivity.class);
                break;
            case R.id.gotoTestKotlin:

                IntentUtil.startSiampleActivity(MainActivity.this, TestKotlinActivity.class);
                break;

            case R.id.gotoTestViewPager:

                IntentUtil.startSiampleActivity(MainActivity.this, TestViewPagerActivity.class);
                break;

            case R.id.gotoTestSlideMenu:
                IntentUtil.startSiampleActivity(MainActivity.this, TestSlideMenuActivity.class);
                break;

            case R.id.gotoTestUtilSlideMenu:
                IntentUtil.startSiampleActivity(MainActivity.this, UtilSlideMenuActivity.class);
                break;

            case R.id.gotoTestBasinAnimation:
                IntentUtil.startSiampleActivity(MainActivity.this, BasinAnimationActivity.class);
                break;
            case R.id.gotoWeatherAnimation:
                IntentUtil.startSiampleActivity(MainActivity.this, WeatherAnimationActivity.class);
                break;

            case R.id.gotoGreenEyes:
                IntentUtil.startSiampleActivity(MainActivity.this, CircleScaleActivity.class);
                break;

            case R.id.gotoWaterChange:
                IntentUtil.startSiampleActivity(MainActivity.this, WaterChangeActivity.class);
                break;
            case R.id.gotoPaoPao:
                IntentUtil.startSiampleActivity(MainActivity.this, PaoPaoActivity.class);
                break;


            case R.id.gotoKuaiGame:
                IntentUtil.startSiampleActivity(MainActivity.this, KuaiActivity.class);
                break;

        }
    }

}
