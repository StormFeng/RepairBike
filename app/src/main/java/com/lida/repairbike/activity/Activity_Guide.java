package com.lida.repairbike.activity;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.lida.repairbike.R;
import com.midian.base.base.BaseActivity;
import com.midian.base.util.UIHelper;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/3/7 0007.
 */

public class Activity_Guide extends BaseActivity {

    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.iv_start)
    ImageView ivStart;

    private int[] pics = {R.drawable.icon_start1, R.drawable.icon_start2, R.drawable.icon_start3};
    private List<View> views = new ArrayList<>();
    private ViewPagerAdapter viewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(ac.getBoolean("app")){
            UIHelper.jump(_activity,Activity_Login.class);
            onDestroy();
        }else{
            ac.setBoolean("app",true);
            setContentView(R.layout.activity_guide);
            ButterKnife.bind(this);
            initView();
        }
    }

    private void initView() {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        for (int i = 0; i < pics.length; i++) {
            final ImageView iv=new ImageView(this);
            iv.setScaleType(ImageView.ScaleType.FIT_XY);
            iv.setLayoutParams(layoutParams);
            iv.setImageResource(pics[i]);
            views.add(iv);
            viewPagerAdapter=new ViewPagerAdapter(views);
            viewpager.setAdapter(viewPagerAdapter);
        }
        viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position == pics.length-1){
                    ivStart.setVisibility(View.VISIBLE);
                }else{
                    ivStart.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }



    @OnClick(R.id.iv_start)
    public void onClick() {
        UIHelper.jump(_activity, Activity_Login.class);
        onDestroy();
    }

    class ViewPagerAdapter extends PagerAdapter{

        private List<View> views;

        public ViewPagerAdapter(List<View> views) {
            this.views = views;
        }

        @Override
        public int getCount() {
            if(views!=null){
                return views.size();
            }
            return 0;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return (view == object);
        }

        @Override
        public Object instantiateItem(View container, int position) {
            ((ViewPager) container).addView(views.get(position),0);
            return views.get(position);
        }

        @Override
        public void destroyItem(View container, int position, Object object) {
            ((ViewPager) container).removeView(views.get(position));
        }
    }
}
