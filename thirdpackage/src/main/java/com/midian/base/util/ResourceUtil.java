package com.midian.base.util;

import android.content.Context;
import android.content.res.Resources;

public class ResourceUtil {
	private Context mContext;
	private Resources mResources;

	public ResourceUtil(Context context) {
		mContext = context;
		mResources = mContext.getResources();
	}

	/**
	 * 根据资源的名字获取它的ID
	 * 
	 * @param name
	 *            要获取的资源的名字
	 * @param defType
	 *            资源的类型，如drawable, string 。。。
	 * @return 资源的id
	 */
	public int getResId(String name, String defType) {
		String packageName = mContext.getApplicationInfo().packageName;
		return mResources.getIdentifier(name, defType, packageName);
	}

	public String getString(String name) {
		int id = getResId(name, "string");
		if (id > 0)// 是否找到资源文件定义
			return mContext.getString(id);
		return name;
	}

}
