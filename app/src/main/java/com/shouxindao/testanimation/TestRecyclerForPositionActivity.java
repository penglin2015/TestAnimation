package com.shouxindao.testanimation;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.xuyao.prancelib.base.BaseUtilRecyclerViewAdapter;
import com.xuyao.prancelib.util.LogUtils;
import com.xuyao.prancelib.util.ScreenUtils;
import com.xuyao.prancelib.view.BaseRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TestRecyclerForPositionActivity extends AppCompatActivity {


    @BindView(R.id.rl)
    BaseRecyclerView rl;
    TestDataAdapter testDataAdapter;
    @BindView(R.id.showPositionName)
    TextView showPositionName;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_recycler_for_position);
        ButterKnife.bind(this);
        rl.setRecyclerViewStyle(1, LinearLayoutManager.VERTICAL, 5);
        testDataAdapter = new TestDataAdapter(this);
        List<String> data = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            data.add("");
        }
        testDataAdapter.setDatas(data);
        rl.setUtilAdapter(testDataAdapter);


        rl.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager lm = (LinearLayoutManager) recyclerView.getLayoutManager();
                int showPosition = lm.findFirstVisibleItemPosition();
                View itemView = lm.findViewByPosition(showPosition);
                int itemTop = itemView.getTop();
                int itemHeight = itemView.getHeight();
                int showPositionTvHeight=showPositionName.getHeight();
                int dliverHieght=5;
                LogUtils.e("dx="+dx+"dy="+dy);
                if(itemTop<=-(itemHeight-showPositionTvHeight+dliverHieght)){
                    //显示对应下一个即将出现的下标
                    showPositionName.setText("显示对应下标"+(showPosition+1)+"这个是需要加1的");
                }else{
                    showPositionName.setText(
                            "下标"+showPosition
                            + "item高度"+itemHeight+"_信息高度"+showPositionTvHeight
                            +"离顶部高度"+itemTop);
                }
            }
        });
    }


    class TestDataAdapter extends BaseUtilRecyclerViewAdapter {


        public TestDataAdapter(Context context) {
            super(context);
        }

        @Override
        protected RecyclerView.ViewHolder setRecyclerViewHolder(int viewType) {
            return new Holder(inflate(R.layout.item_test_recyclerview_position));
        }

        @Override
        protected void setDataToView(RecyclerView.ViewHolder holder, int position) {
            Holder h = (Holder) holder;
            h.dataItem.setText("测试数据" + position);
        }

        class Holder extends UtilRecyclerViewHolder {
            @BindView(R.id.dataItem)
            TextView dataItem;

            public Holder(View itemView) {
                super(itemView);
            }
        }

    }
}
