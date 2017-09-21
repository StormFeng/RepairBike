package com.lida.repairbike.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.lida.repairbike.R;
import com.lida.repairbike.adapter.AdapterActivityPart;
import com.lida.repairbike.adapter.AdapterGridViewPartUsed;
import com.lida.repairbike.adapter.AdapterGridViewReason;
import com.lida.repairbike.bean.PartBean;
import com.lida.repairbike.bean.PartsBean;
import com.lida.repairbike.util.AppUtil;
import com.midian.base.api.ApiCallback;
import com.midian.base.base.BaseFragment;
import com.midian.base.bean.NetResult;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 库存
 * Created by Administrator on 2017/3/23.
 */

public class FragmentPartStock extends BaseFragment implements ApiCallback {

    @BindView(R.id.gridView)
    GridView gridView;

    private List<String> partsList = new ArrayList<>();//配件
    private List<String> numbers = new ArrayList<>();//配件
    private AdapterGridViewPartUsed adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_part, null);
        ButterKnife.bind(this, v);
        init();
        return v;
    }

    private void init() {
        adapter = new AdapterGridViewPartUsed(_activity,partsList,numbers);
        gridView.setAdapter(adapter);
        AppUtil.getBikeApiClient(ac).getPart(this);
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
            if ("getPart".equals(tag)) {
                PartsBean bean = (PartsBean) res;
                for (int i = 0; i < bean.getData().size(); i++) {
                    partsList.add(bean.getData().get(i).getName());
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
