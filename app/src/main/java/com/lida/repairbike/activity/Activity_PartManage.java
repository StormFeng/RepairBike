package com.lida.repairbike.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.lida.repairbike.R;
import com.lida.repairbike.adapter.AdapterGridViewPartUsed;
import com.lida.repairbike.bean.PartsBean;
import com.lida.repairbike.util.AppUtil;
import com.lida.repairbike.widget.InnerGridView;
import com.midian.base.base.BaseActivity;
import com.midian.base.bean.NetResult;
import com.midian.base.util.UIHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 配件管理
 * Created by Administrator on 2017/3/10.
 */

public class Activity_PartManage extends BaseActivity {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.gridView)
    GridView gridView;
    @BindView(R.id.ll_gridView)
    LinearLayout llGridView;
    @BindView(R.id.btn_receive)
    Button btnReceive;
    @BindView(R.id.btn_return)
    Button btnReturn;
    @BindView(R.id.btn_ok)
    Button btnOk;

    private List<String> names = new ArrayList<>();
    private List<String> numbers = new ArrayList<>();
    private AdapterGridViewPartUsed adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partused);
        ButterKnife.bind(this);
        initView();
        AppUtil.getBikeApiClient(ac).getPart(this);
    }

    private void initView() {
//        names.add("挡泥板");
//        names.add("挡泥板");
//        names.add("前（后）刹 总承");
//        names.add("前（后）刹 总承");
//        numbers.add("10");
//        numbers.add("18");
//        numbers.add("7");
//        numbers.add("3");
        adapter = new AdapterGridViewPartUsed(_activity, names, numbers);
        gridView.setAdapter(adapter);
    }

    @Override
    public void onApiSuccess(NetResult res, String tag) {
        if(res.isOK()){
            if ("getPart".equals(tag)) {
                PartsBean bean = (PartsBean) res;
                for (int i = 0; i < bean.getData().size(); i++) {
                    names.add(bean.getData().get(i).getName());
                    numbers.add(bean.getData().get(i).getNumber());
                }
                adapter.notifyDataSetChanged();
            }
        }
    }

    @OnClick({R.id.iv_back, R.id.btn_receive, R.id.btn_return, R.id.btn_ok})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_receive:
                setButton(0);
                break;
            case R.id.btn_return:
                setButton(1);
                break;
            case R.id.btn_ok:
                UIHelper.jump(_activity,Activity_PartReceive.class);
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
