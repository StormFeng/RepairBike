package com.midian.base.api;

import com.midian.base.bean.NetResult;

/**
 * 网络请求回调接口
 * 
 * @author moshouguan
 * 
 */
public interface ApiCallback {

	void onApiStart(String tag);

	void onApiLoading(long count, long current, String tag);

	void onApiSuccess(NetResult res, String tag);

	void onApiFailure(Throwable t, int errorNo, String strMsg, String tag);

	void onParseError(String tag);

}
