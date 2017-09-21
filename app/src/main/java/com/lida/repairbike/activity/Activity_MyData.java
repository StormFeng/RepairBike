package com.lida.repairbike.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lida.repairbike.R;
import com.midian.base.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 我的资料
 * Created by Administrator on 2017/3/10.
 */

public class Activity_MyData extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.iv_setting)
    ImageView ivSetting;
    @BindView(R.id.tv_account)
    TextView tvAccount;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_birthday)
    TextView tvBirthday;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.tv_workYear)
    TextView tvWorkYear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mydata);
        ButterKnife.bind(this);
        tvAccount.setText(ac.account);
        tvName.setText(ac.name);
        tvPhone.setText(ac.phone);
        tvBirthday.setText(ac.birth);
        tvWorkYear.setText(ac.repair_life+"年");
    }

    @OnClick({R.id.iv_back, R.id.iv_setting})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_setting:
                break;
        }
    }
}
