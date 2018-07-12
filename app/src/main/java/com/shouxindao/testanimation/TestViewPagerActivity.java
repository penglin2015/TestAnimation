package com.shouxindao.testanimation;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.gigamole.infinitecycleviewpager.HorizontalInfiniteCycleViewPager;
import com.xuyao.prancelib.util.ImageUtil;
import com.xuyao.prancelib.view.BothSideSmallerMiddleBigViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TestViewPagerActivity extends AppCompatActivity {

    @BindView(R.id.testVp)
    BothSideSmallerMiddleBigViewPager testVp;
    @BindView(R.id.hicvp)
    HorizontalInfiniteCycleViewPager hicvp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_view_pager2);
        ButterKnife.bind(this);
        testVp.setAdapter(new MyPagerAdapter(this));
        testVp.setCurrentItem(100);

        hicvp.setAdapter(new MyPagerAdapter(this));
        hicvp.setCurrentItem(100);
    }


    class MyPagerAdapter extends PagerAdapter {

        Context context;
        List<String> imgUrls = new ArrayList<>();

        public MyPagerAdapter(Context context) {
            this.context = context;
            imgUrls.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1530617458953&di=10d58010f6fb82e4bec10ac49478a8d6&imgtype=0&src=http%3A%2F%2Fimgsrc.baidu.com%2Fimage%2Fc0%253Dshijue1%252C0%252C0%252C294%252C40%2Fsign%3D407b2e95de3f8794c7f2406dba726481%2Fa5c27d1ed21b0ef4c9eb5573d7c451da81cb3e98.jpg");
            imgUrls.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1530617459126&di=51c8df53339ab072c6a30924cd51751b&imgtype=0&src=http%3A%2F%2Fimgsrc.baidu.com%2Fimage%2Fc0%253Dpixel_huitu%252C0%252C0%252C294%252C40%2Fsign%3D11cd7df45966d0166a149668fe53b16b%2Fb219ebc4b74543a9aa3004f515178a82b90114db.jpg");
            imgUrls.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1530617459124&di=9a3539c9ae35d2a4c3c153752b977ec1&imgtype=0&src=http%3A%2F%2Fpic23.photophoto.cn%2F20120406%2F0034034524909519_b.jpg");
            imgUrls.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1530617459124&di=2dd342332fbb278079115101fc57da41&imgtype=0&src=http%3A%2F%2Fimg02.tooopen.com%2Fimages%2F20150608%2Ftooopen_sy_129103188937.jpg");
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            ImageView imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            ImageUtil.glideSetImgToImgView(imageView, imgUrls.get(position % imgUrls.size()), R.drawable.icon_over);
            container.addView(imageView);
            return imageView;
        }

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {

            return view == object;
        }


        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }


    }
}
