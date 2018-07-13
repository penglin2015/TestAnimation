package com.shouxindao.testanimation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.shouxindao.testanimation.views.GreenEyeView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CircleScaleActivity extends AppCompatActivity {

    @BindView(R.id.drawTypeChoseSp)
    Spinner drawTypeChoseSp;
    @BindView(R.id.ge)
    GreenEyeView ge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle_scale);
        ButterKnife.bind(this);
        GreenEyeView.DRAW_TYPE[] array = GreenEyeView.DRAW_TYPE.values();
        String values[] = new String[array.length];
        for (int i = 0; i < array.length; i++) {
            values[i] = array[i].name();
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, values);
        drawTypeChoseSp.setAdapter(arrayAdapter);
        drawTypeChoseSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ge.setPositionType(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
