package com.lida.repairbike.datasource;

import android.content.Context;

import com.lida.repairbike.bean.ActivityRepairListBean;
import com.lida.repairbike.bean.ActivityRepairQueryBean;
import com.lida.repairbike.util.AppUtil;
import com.midian.base.base.BaseListDataSource;
import com.midian.base.bean.NetResult;

import java.util.ArrayList;


/**
 * 维修查询
 * Created by Administrator on 2017/3/6.
 */

public class ActivityRepairListDataSource extends BaseListDataSource {

    public ActivityRepairListDataSource(Context context) {
        super(context);
    }

    @Override
    protected ArrayList load(int page) throws Exception {
        this.page=page;
        ArrayList<NetResult> models=new ArrayList<>();
//        ActivityRepairListBean bean = AppUtil.getBikeApiClient(ac).getRepairALL("1");
        ActivityRepairQueryBean bean = AppUtil.getBikeApiClient(ac).getReAll();
        if(bean.isOK()){
            for(ActivityRepairQueryBean.DataBean item : bean.getData()){
                models.add(item);
            }
            hasMore = false;
        }
        return models;
    }
}
