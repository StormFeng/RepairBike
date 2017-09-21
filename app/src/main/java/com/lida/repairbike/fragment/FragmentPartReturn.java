package com.lida.repairbike.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.apkfuns.logutils.LogUtils;
import com.lida.repairbike.R;
import com.lida.repairbike.activity.Activity_PartReceive;
import com.lida.repairbike.adapter.AdapterGridViewPartUsed;
import com.lida.repairbike.bean.PartsBean;
import com.lida.repairbike.util.AppUtil;
import com.lida.repairbike.widget.InnerGridView;
import com.midian.base.api.ApiCallback;
import com.midian.base.base.BaseFragment;
import com.midian.base.bean.NetResult;
import com.midian.base.util.UIHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 退回的配件
 * Created by Administrator on 2017/3/24.
 */

public class FragmentPartReturn extends BaseFragment implements ApiCallback {

    @BindView(R.id.gridView)
    InnerGridView gridView;
    @BindView(R.id.btn_ok)
    Button btnOk;

    private List<String> names = new ArrayList<>();
    private List<String> numbers = new ArrayList<>();
    private AdapterGridViewPartUsed adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_partreturn, null);
        ButterKnife.bind(this, v);
        adapter = new AdapterGridViewPartUsed(_activity, names, numbers);
        gridView.setAdapter(adapter);
        AppUtil.getBikeApiClient(ac).getReturnPartByEid(ac.id,this);
        return v;
    }

    @OnClick(R.id.btn_ok)
    public void onClick() {
//        Bundle bundle = new Bundle();
//        bundle.putString("flag","FragmentPartReturn");
        Intent intent = new Intent(_activity, Activity_PartReceive.class);
        intent.putExtra("flag","FragmentPartReturn");
        this.startActivityForResult(intent,1001);
//        UIHelper.jump(_activity,Activity_PartReceive.class,bundle);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK){
            LogUtils.e("RESULT_OK");
            AppUtil.getBikeApiClient(ac).getReturnPartByEid(ac.id,this);
        }
    }

    @Override
    public void onApiStart(String tag) {
        _activity.showLoadingDlg();
    }

    @Override
    public void onApiLoading(long count, long current, String tag) {

    }

    @Override
    public void onApiSuccess(NetResult res, String tag) {
        _activity.hideLoadingDlg();
        if(res.isOK()){
            if ("getReturnPartByEid".equals(tag)) {
                PartsBean bean = (PartsBean) res;
                names.clear();
                numbers.clear();
                for (int i = 0; i < bean.getData().size(); i++) {
                    names.add(bean.getData().get(i).getName());
                    numbers.add(bean.getData().get(i).getNumber());
                }
                adapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void onApiFailure(Throwable t, int errorNo, String strMsg, String tag) {

    }

    @Override
    public void onParseError(String tag) {

    }
}
