package com.lida.repairbike.util;

import com.lida.repairbike.app.BaseAppContext;
import com.lida.repairbike.app.BikeApiClient;
import com.midian.base.app.AppContext;

/**
 * Created by Administrator on 2017/3/7.
 */

public class AppUtil {
    public static BaseAppContext getBaseAppContext(AppContext ac){
        return (BaseAppContext) ac;
    }

    public static BikeApiClient getBikeApiClient(AppContext ac){
        return ac.api.getApiClient(BikeApiClient.class);
    }
}
