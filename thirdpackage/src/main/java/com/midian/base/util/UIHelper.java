package com.midian.base.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bishilai.thirdpackage.R;


public class UIHelper {

	public static String lastToast = "";
	public static long lastToastTime;

	public static void jump(Activity context, Class clazz) {
		Intent intent = new Intent(context, clazz);
		context.startActivity(intent);
	}

	public static void jump(Activity context, Class clazz, Bundle bundle) {
		Intent intent = new Intent(context, clazz);
		intent.putExtras(bundle);
		context.startActivity(intent);
	}

	public static void jumpForResult(Activity context, Class clazz,
			int requestCode) {
		Intent intent = new Intent(context, clazz);
		context.startActivityForResult(intent, requestCode);
	}

	public static void jumpForResult(Activity context, Class clazz,
			Bundle bundle, int requestCode) {
		Intent intent = new Intent(context, clazz);
		intent.putExtras(bundle);
		context.startActivityForResult(intent, requestCode);
	}

	public static void jumpForResult(Fragment fragment, Class clazz,
			Bundle bundle, int requestCode) {
		Intent intent = new Intent(fragment.getActivity(), clazz);
		intent.putExtras(bundle);
		fragment.startActivityForResult(intent, requestCode);
	}

	/**
	 * 点击返回监听事件
	 */
	public static View.OnClickListener finish(final Activity activity) {
		return new View.OnClickListener() {
			public void onClick(View v) {
				activity.finish();
			}
		};
	}

	/**
	 * 点击返回监听事件
	 */
	public static View.OnClickListener OnClickJump(final Activity context,
			final Class clazz) {
		return new View.OnClickListener() {
			public void onClick(View v) {
				jump(context, clazz);
			}
		};
	}

	public static void t(Context context, String msg) {
		if (msg != null && !msg.equalsIgnoreCase("")) {
			long time = System.currentTimeMillis();
			if (!msg.equalsIgnoreCase(lastToast)
					|| Math.abs(time - lastToastTime) > 2000) {
				View view = LayoutInflater.from(context).inflate(
						R.layout.view_toast, null);
				((TextView) view.findViewById(R.id.title_tv)).setText(msg);
				Toast toast = new Toast(context);
				toast.setView(view);
				toast.setDuration(Toast.LENGTH_SHORT);
				toast.show();
				lastToast = msg;
				lastToastTime = System.currentTimeMillis();
			}
		}
	}

	public static void t(Context context, int msg) {
		if (msg > 0) {
			t(context, context.getApplicationContext().getString(msg));
		}
	}
}
