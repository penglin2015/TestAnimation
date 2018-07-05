package com.shouxindao.testanimation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.xuyao.prancelib.util.LogUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TestFoucusActivity extends AppCompatActivity {

    @BindView(R.id.ed_1)
    EditText ed1;
    @BindView(R.id.ed_2)
    EditText ed2;
    @BindView(R.id.ed_3)
    EditText ed3;
    @BindView(R.id.clickClearFoucusButt)
    Button clickClearFoucusButt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_foucus);
        ButterKnife.bind(this);


        ed1.setOnFocusChangeListener(onFocusChangeListener);
        ed2.setOnFocusChangeListener(onFocusChangeListener);
        ed3.setOnFocusChangeListener(onFocusChangeListener);

        clickClearFoucusButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ed1.clearFocus();
                ed2.clearFocus();
                ed3.clearFocus();
            }
        });
    }


    View.OnFocusChangeListener onFocusChangeListener=new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            EditText editText= (EditText) v;
            LogUtils.e(""+editText.getHint()+"_"+hasFocus);
        }
    };
}
