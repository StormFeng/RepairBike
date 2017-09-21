package com.lida.repairbike.datasource;

import android.content.Context;

import com.apkfuns.logutils.LogUtils;
import com.lida.repairbike.activity.Activity_MyRepairHistory;
import com.lida.repairbike.bean.ActivityMyRepairHistoryBean;
import com.lida.repairbike.util.AppUtil;
import com.lida.repairbike.widget.UpdateMain;
import com.midian.base.base.BaseListDataSource;
import com.midian.base.bean.NetResult;

import java.util.ArrayList;


/**
 * Created by Administrator on 2017/3/6.
 */

public class ActivityMyRepairHistoryDataSource extends BaseListDataSource {

    private UpdateMain updateMain;

    public ActivityMyRepairHistoryDataSource(Context context) {
        super(context);
    }

    public void setUpdateMain(UpdateMain updateMain){
        this.updateMain = updateMain;
    }

    @Override
    protected ArrayList load(int page) throws Exception {
        this.page=page;
        ArrayList<NetResult> models=new ArrayList<>();
        ActivityMyRepairHistoryBean bean = AppUtil.getBikeApiClient(ac).getRepairHistory(ac.id);
        if(bean.isOK()){
            for(ActivityMyRepairHistoryBean.DataBean item : bean.getData()){
                models.add(item);
            }
            hasMore=false;
            updateMain.update(bean.getData().size()+"");
        }
//        for (int i = 0; i < 10; i++) {
//            NetResult netResult=new NetResult();
//            models.add(netResult);
//        }
//        hasMore=false;
        return models;
    }
}
