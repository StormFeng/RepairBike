package com.midian.base.app;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * 配置管理类
 */
public class AppConfig {

	/**
	 * 默认配置文件名称
	 */
	public final static String APP_CONFIG = "config";

	private Context mContext;
	private static AppConfig appConfig;

	/**
	 * 获得AppConfig实例
	 * 
	 * @param context
	 * @return
	 */
	public static AppConfig getAppConfig(Context context) {
		if (appConfig == null) {
			appConfig = new AppConfig();
			appConfig.mContext = context;
		}
		return appConfig;
	}

	/**
	 * 获取Preference设置
	 */
	public static SharedPreferences getSharedPreferences(Context context) {
		return context.getSharedPreferences(APP_CONFIG, Context.MODE_PRIVATE);
	}

	/**
	 * 获得key对应配置信息
	 * 
	 * @param key
	 * @return
	 */
	public String get(String key) {
		return getSharedPreferences(mContext).getString(key, "");
	}

	/**
	 * 获得key对应配置信息
	 * 
	 * @param key
	 * @return
	 */
	public boolean getBoolean(String key) {
		return getSharedPreferences(mContext).getBoolean(key, false);
	}

	/**
	 * 获得key对应配置信息
	 * 
	 * @param key
	 * @return
	 */
	public int getInteger(String key) {
		return getSharedPreferences(mContext).getInt(key, -1);
	}

	/**
	 * 设置配置信息
	 * 
	 * @param key
	 * @param value
	 */
	public void set(String key, String value) {
		Editor editor = getSharedPreferences(mContext).edit();
		editor.putString(key, value);
		editor.commit();
	}

	/**
	 * 设置配置信息
	 * 
	 * @param key
	 * @param value
	 */
	public void set(String key, boolean value) {
		Editor editor = getSharedPreferences(mContext).edit();
		editor.putBoolean(key, value);
		editor.commit();
	}

	/**
	 * 设置配置信息
	 * 
	 * @param key
	 * @param value
	 */
	public void set(String key, int value) {
		Editor editor = getSharedPreferences(mContext).edit();
		editor.putInt(key, value);
		editor.commit();
	}

	/**
	 * 移除多个配置信息
	 * 
	 * @param keys
	 */
	public void remove(String... keys) {
		Editor editor = getSharedPreferences(mContext).edit();
		for (String key : keys) {
			editor.remove(key);
		}
		editor.commit();
	}
}
