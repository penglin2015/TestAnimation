package com.shouxindao.testanimation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.shouxindao.testanimation.util.SoundUtil;
import com.shouxindao.testanimation.views.FlyBirdSurfaceView;
import com.xuyao.prancelib.util.ScreenUtils;
import com.xuyao.prancelib.view.NormalDialog;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FlyBirdActivity extends AppCompatActivity {

    @BindView(R.id.flyBirdView)
    FlyBirdSurfaceView flyBirdView;
    @BindView(R.id.coinsTv)
    TextView coinsTv;
    long coinNum=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fly_bird);
        ScreenUtils.setScreenFull(this);
        ButterKnife.bind(this);
        SoundUtil.getInstance(this).playMe();

        flyBirdView.setGameCallBackListener(new FlyBirdSurfaceView.GameCallBackListener() {
            @Override
            public void coinChange(int coin) {
                coinNum+=coin;
                coinsTv.setText(""+coinNum);
                SoundUtil.getInstance(FlyBirdActivity.this).play(SoundUtil.getInstance(FlyBirdActivity.this).getSuccessId());
            }

            @Override
            public void gameOver() {
                //游戏结束
                SoundUtil.getInstance(FlyBirdActivity.this).play(SoundUtil.getInstance(FlyBirdActivity.this).getOverId());
                SoundUtil.getInstance(FlyBirdActivity.this).stopMediaPlayer();
                new NormalDialog(FlyBirdActivity.this){
                    @Override
                    public void buttonTouch(BUTT_TYPE butt_type) {
                        super.buttonTouch(butt_type);
                        switch (butt_type){
                            case PAIRING_LEFT:
                                finish();
                                break;
                            case PAIRING_RIGHT:
                                coinNum=0;
                                coinsTv.setText(""+coinNum);
                                SoundUtil.getInstance(FlyBirdActivity.this).playMe();
                                flyBirdView.restartGame();
                                break;
                        }
                    }
                }.setMsg("游戏结束，本次得分"+coinNum).setFirstBottonName("结束").setSecondBottonName("重新开始").show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SoundUtil.getInstance(this).stopMediaPlayer();
    }
}
