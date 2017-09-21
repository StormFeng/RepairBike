package com.midian.base.util;

import android.content.Context;

import com.umeng.analytics.MobclickAgent;


/**
 * 友盟工具类
 * 
 * @author MIDIAN
 * 
 */
public class UMengStatistticUtil {
	public static boolean isAdd = false;

	public static void openActivityDurationTrack(boolean isOpen) {
		if (isAdd)
			MobclickAgent.openActivityDurationTrack(isOpen);
	}

	public static void onResumeForActivity(Context context) {
		if (isAdd) {
			MobclickAgent.onPageStart(context.getClass().getName());
			MobclickAgent.onResume(context);
		}
	}

	public static void onPauseForActivity(Context context) {
		if (isAdd) {
			MobclickAgent.onPageEnd(context.getClass().getName());
			MobclickAgent.onPause(context);
		}
	}

	public static void onResumeForFragmentActivity(Context context) {
		if (isAdd) {
			MobclickAgent.onResume(context);
		}

	}

	public static void onPauseForFragmentActivity(Context context) {
		if (isAdd)
			MobclickAgent.onPause(context);
	}

	public static void onResumeForFragment(Context context) {
		if (isAdd)
			MobclickAgent.onPageStart(context.getClass().getName());
	}

	public static void onPauseForFragment(Context context) {
		if (isAdd)
			MobclickAgent.onPageEnd(context.getClass().getName());
	}

	public static void onEvent(Context context, String eventId) {
		if (isAdd)
			MobclickAgent.onEvent(context, eventId);
		// FDDebug.d("eventId::::::::::"+eventId);
		// System.out.println("eventId::::::::::"+eventId);
	}

}
