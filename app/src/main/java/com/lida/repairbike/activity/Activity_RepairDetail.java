package com.lida.repairbike.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.apkfuns.logutils.LogUtils;
import com.lida.repairbike.R;
import com.lida.repairbike.fragment.FragmentCurrent;
import com.lida.repairbike.fragment.FragmentHistory;
import com.midian.base.base.BaseFragmentActivity;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 维修详情
 * Created by Administrator on 2017/3/6.
 */

public class Activity_RepairDetail extends BaseFragmentActivity implements View.OnClickListener {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.btn_current)
    Button btnCurrent;
    @BindView(R.id.btn_history)
    Button btnHistory;
    @BindView(R.id.viewPager)
    ViewPager viewPager;

    private List<Fragment> fragments = new ArrayList<>();
    private ArrayList<String> params = new ArrayList<>();
    private String mid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repairhistory);
        ButterKnife.bind(this);
        params = mBundle.getStringArrayList("params");
        mid = params.get(0);
        LogUtils.e(params);
        initView();
    }

    private void initView(){
        fragments.add(new FragmentCurrent(params));
        fragments.add(new FragmentHistory(mid));
        viewPager.setAdapter(new FragmentPagerAdapter(fm) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }
        });
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setButton(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @OnClick({R.id.iv_back, R.id.btn_current, R.id.btn_history})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_current:
                setButton(0);
                viewPager.setCurrentItem(0);
                break;
            case R.id.btn_history:
                setButton(1);
                viewPager.setCurrentItem(1);
                break;
        }
    }

    private void setButton(int position) {
        if (position == 0) {
            btnCurrent.setTextColor(Color.parseColor("#FCFBFB"));
            btnCurrent.setBackgroundColor(Color.parseColor("#5FCED8"));
            btnHistory.setTextColor(Color.parseColor("#5FCED8"));
            btnHistory.setBackgroundResource(R.drawable.rightbtn);
        } else {
            btnCurrent.setTextColor(Color.parseColor("#5FCED8"));
            btnCurrent.setBackgroundResource(R.drawable.leftbtn);
            btnHistory.setTextColor(Color.parseColor("#FCFBFB"));
            btnHistory.setBackgroundColor(Color.parseColor("#5FCED8"));
        }
    }
}
