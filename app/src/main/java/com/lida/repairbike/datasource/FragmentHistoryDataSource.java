package com.lida.repairbike.datasource;

import android.content.Context;

import com.lida.repairbike.bean.FragmentHistoryBean;
import com.lida.repairbike.util.AppUtil;
import com.midian.base.base.BaseListDataSource;
import com.midian.base.bean.NetResult;

import java.util.ArrayList;


/**
 * Created by Administrator on 2017/3/6.
 */

public class FragmentHistoryDataSource extends BaseListDataSource {

    String mid="";
    String staus="2";

    public FragmentHistoryDataSource(Context context, String mid) {
        super(context);
        this.mid = mid;
    }

    @Override
    protected ArrayList load(int page) throws Exception {
        this.page=page;
        ArrayList<NetResult> models=new ArrayList<>();
        FragmentHistoryBean bean = AppUtil.getBikeApiClient(ac).getRepairByMid(mid, staus);
        if(bean.isOK()){
            for (FragmentHistoryBean.DataBean item : bean.getData()){
                models.add(item);
            }
            hasMore=false;
        }
        return models;
    }
}
