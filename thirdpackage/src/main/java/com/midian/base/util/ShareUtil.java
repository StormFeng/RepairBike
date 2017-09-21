package com.midian.base.util;

import com.umeng.socialize.PlatformConfig;

/**
 * Created by Administrator on 2016/11/3 0003.
 */

public class ShareUtil {

    public static String weiboAppId = "902141203";
    public static String weiboAppSecret = "347df4167afd79fa1c822860a15aae14";
    public static String qqAppId = "1105617978";
    public static String qqAppKEY = "U9N7NQne0tMIcaS0";
    public static String weixinAppId = "wx6ec4b95185411c3c";
    public static String weixinAppSecret = "e27867f76bca1a55204d97ef785237d1";

    public static void init(){
        PlatformConfig.setWeixin(weixinAppId, weixinAppSecret);
        PlatformConfig.setSinaWeibo(weiboAppId, weiboAppSecret);
        PlatformConfig.setQQZone(qqAppId,qqAppKEY);
    }
}
