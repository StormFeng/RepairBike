package com.lida.repairbike.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;

import com.apkfuns.logutils.LogUtils;
import com.lida.repairbike.R;
import com.lida.repairbike.adapter.AdapterActivityPartManage;
import com.lida.repairbike.bean.PartsBean;
import com.lida.repairbike.util.AppUtil;
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
 * Created by Administrator on 2017/3/23.
 */

public class Activity_PartReceive extends BaseActivity {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.gridView)
    GridView gridView;
    @BindView(R.id.btn_ok)
    Button btnOk;

    private List<String> names = new ArrayList<>();
    private List<String> numbers = new ArrayList<>();
    private AdapterActivityPartManage adapter;
    private String flag = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partreceive);
        ButterKnife.bind(this);
        flag = mBundle.getString("flag");
//        for (int i = 0; i < 10; i++) {
//            names.add("前后轴螺丝");
//            numbers.add("1");
//        }
        AppUtil.getBikeApiClient(ac).getPart(this);
        adapter = new AdapterActivityPartManage(_activity,names,numbers);
        gridView.setAdapter(adapter);
    }

    @OnClick({R.id.iv_back, R.id.btn_ok})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_ok:
                StringBuilder sb = new StringBuilder();
                sb.append("{");
                for (int i = 0; i < names.size(); i++) {
                    if(i==names.size()-1){
                        sb.append("\""+names.get(i) + "\":" + numbers.get(i));
                    }else{
                        sb.append("\""+names.get(i) + "\":" + numbers.get(i) + ",");
                    }

                }
                sb.append("}");
                String part = sb.toString();
                LogUtils.e(sb.toString());
                if("FragmentPartReturn".equals(flag)){
                    AppUtil.getBikeApiClient(ac).returnPartFromStock(ac.id, part,this);
                }else if("FragmentPartReceive".equals(flag)){
                    AppUtil.getBikeApiClient(ac).getPartFromStock(ac.id, part,this);
                }
                break;
        }
    }

    @Override
    public void onApiStart(String tag) {
        super.onApiStart(tag);
        showLoadingDlg();
    }

    @Override
    public void onApiSuccess(NetResult res, String tag) {
        hideLoadingDlg();
        if(res.isOK()){
            if("getPart".equals(tag)){
                PartsBean bean = (PartsBean) res;
                for (PartsBean.DataBean item : bean.getData()){
                    names.add(item.getName());
                    numbers.add(item.getNumber());
                }
                adapter.notifyDataSetChanged();
            }else{
                UIHelper.t(_activity,"提交成功");
                setResult(RESULT_OK);
                finish();
            }
        }
    }
}
