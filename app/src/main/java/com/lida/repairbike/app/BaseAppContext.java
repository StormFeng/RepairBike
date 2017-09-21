package com.lida.repairbike.app;

import com.baidu.mapapi.SDKInitializer;
import com.midian.base.app.AppContext;
import com.midian.base.util.ShareUtil;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;

/**
 * Created by Administrator on 2016/11/3 0003.
 */

public class BaseAppContext extends AppContext {

    @Override
    public void onCreate() {
        super.onCreate();
        ShareUtil.init();
        BikeApiClient.init(this);
        ZXingLibrary.initDisplayOpinion(this);
        SDKInitializer.initialize(getApplicationContext());
    }
}
