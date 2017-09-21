package com.lida.repairbike.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.apkfuns.logutils.LogUtils;
import com.lida.repairbike.R;
import com.lida.repairbike.adapter.AdapterGridViewPartNumber;
import com.lida.repairbike.util.AppUtil;
import com.lida.repairbike.widget.InnerGridView;
import com.midian.base.base.BaseActivity;
import com.midian.base.bean.NetResult;
import com.midian.base.util.UIHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 配件数量
 * Created by Administrator on 2017/3/11 0011.
 */

public class Activity_PartNumber extends BaseActivity {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.gridView)
    InnerGridView gridView;
    @BindView(R.id.btn_ok)
    Button btnOk;

    private String bikeId;
    private String trouble;
    private ArrayList<String> ids = new ArrayList<>();
    private ArrayList<String> names = new ArrayList<>();
    private Map<String, String> numbers = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partnumber);
        ButterKnife.bind(this);
        ids = mBundle.getStringArrayList("partIds");
        names = mBundle.getStringArrayList("partNames");
        trouble = mBundle.getString("trouble");
        bikeId = mBundle.getString("bikeId");
        initView();
    }

    private void initView() {
        for (int i = 0; i < ids.size(); i++) {
            numbers.put(ids.get(i),"1");
        }
        gridView.setAdapter(new AdapterGridViewPartNumber(_activity, ids, names, numbers));
    }

    @OnClick({R.id.iv_back, R.id.btn_ok})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_ok:
                String parts = numbers.toString().replaceAll("=", ":").replaceAll(",", ";").trim();
                LogUtils.e(parts);
//                AppUtil.getBikeApiClient(ac).submitRepair(ac.id,bikeId,trouble,parts,ac.getProperty("position"),this);
                break;
        }
    }

    @Override
    public void onApiSuccess(NetResult res, String tag) {
        super.onApiSuccess(res, tag);
        if(res.isOK()){
            if ("submitRepair".equals(tag)) {
                UIHelper.t(_activity, "维修完成");
                setResult(RESULT_OK);
                finish();
            }
        }
    }
}
