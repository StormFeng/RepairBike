package com.lida.repairbike.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.lida.repairbike.R;
import com.lida.repairbike.fragment.FragmentPartNeed;
import com.lida.repairbike.fragment.FragmentPartStock;
import com.midian.base.base.BaseFragmentActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 配件
 * Created by Administrator on 2017/3/7.
 */

public class Activity_Part extends BaseFragmentActivity {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.btn_generation)
    Button btnGeneration;
    @BindView(R.id.btn_two_generation)
    Button btnTwoGeneration;
    @BindView(R.id.viewPager)
    ViewPager viewPager;

    private List<Fragment> fragments = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parts);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        fragments.add(new FragmentPartStock());
        fragments.add(new FragmentPartNeed());
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

    @OnClick({R.id.iv_back, R.id.btn_generation, R.id.btn_two_generation})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_generation:
                setButton(0);
                break;
            case R.id.btn_two_generation:
                setButton(1);
                break;
        }
    }
    private void setButton(int position) {
        if (position == 0) {
            viewPager.setCurrentItem(0);
            btnGeneration.setTextColor(Color.parseColor("#FCFBFB"));
            btnGeneration.setBackgroundColor(Color.parseColor("#5FCED8"));
            btnTwoGeneration.setTextColor(Color.parseColor("#5FCED8"));
            btnTwoGeneration.setBackgroundResource(R.drawable.rightbtn);
        } else {
            viewPager.setCurrentItem(1);
            btnGeneration.setTextColor(Color.parseColor("#5FCED8"));
            btnGeneration.setBackgroundResource(R.drawable.leftbtn);
            btnTwoGeneration.setTextColor(Color.parseColor("#FCFBFB"));
            btnTwoGeneration.setBackgroundColor(Color.parseColor("#5FCED8"));
        }
    }
}
