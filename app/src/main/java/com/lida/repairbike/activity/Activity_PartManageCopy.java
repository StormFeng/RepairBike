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
import com.lida.repairbike.fragment.FragmentPartReceive;
import com.lida.repairbike.fragment.FragmentPartReturn;
import com.midian.base.base.BaseFragmentActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 配件管理
 * Created by Administrator on 2017/3/10.
 */

public class Activity_PartManageCopy extends BaseFragmentActivity {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.btn_receive)
    Button btnReceive;
    @BindView(R.id.btn_return)
    Button btnReturn;
    @BindView(R.id.viewPager)
    ViewPager viewPager;

    private List<Fragment> fragments = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partmanage);
        ButterKnife.bind(this);
        fragments.add(new FragmentPartReceive());
        fragments.add(new FragmentPartReturn());
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


    @OnClick({R.id.iv_back, R.id.btn_receive, R.id.btn_return})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_receive:
                setButton(0);
                viewPager.setCurrentItem(0);
                break;
            case R.id.btn_return:
                setButton(1);
                viewPager.setCurrentItem(1);
                break;
        }
    }


    private void setButton(int position) {
        if (position == 0) {
            btnReceive.setTextColor(Color.parseColor("#0566BB"));
            btnReceive.setBackgroundColor(Color.parseColor("#CAE3F9"));
            btnReturn.setTextColor(Color.parseColor("#000000"));
            btnReturn.setBackgroundColor(Color.parseColor("#FFFFFF"));
        } else {
            btnReceive.setTextColor(Color.parseColor("#000000"));
            btnReceive.setBackgroundColor(Color.parseColor("#FFFFFF"));
            btnReturn.setTextColor(Color.parseColor("#0566BB"));
            btnReturn.setBackgroundColor(Color.parseColor("#CAE3F9"));
        }
    }
}
