package com.lida.repairbike.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.lida.repairbike.R;
import com.midian.base.base.BaseActivity;
import com.midian.base.util.UIHelper;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 未检修页面
 * Created by Administrator on 2017/3/10.
 */

public class Activity_GoCheck extends BaseActivity {
    @BindView(R.id.btn_goCheck)
    Button btnGoCheck;

    String mid="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gocheck);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_goCheck)
    public void onClick() {
        Bundle bundle = new Bundle();
        bundle.putString("mid",mBundle.getString("mid"));
        UIHelper.jump(_activity,Activity_CheckPoint.class,bundle);
        finish();
    }
}
