package com.midian.base.util;

import java.util.Timer;
import java.util.TimerTask;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.XmlResourceParser;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

@SuppressLint("NewApi")
public class ViewUtil {

	/**
	 * 弹出软键盘
	 * 
	 * @param v
	 */
	public static void showInputMethod(final EditText v) {
		v.setFocusable(true);
		v.setFocusableInTouchMode(true);
		v.requestFocus();
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			public void run() {
				InputMethodManager inputManager = (InputMethodManager) v
						.getContext().getSystemService(
								Context.INPUT_METHOD_SERVICE);
				inputManager.showSoftInput(v, 0);
			}
		}, 100);
	}

	/**
	 * 隐藏软键盘
	 * 
	 * @param v
	 */
	public static void hideInputMethod(final EditText v) {

		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				InputMethodManager m = (InputMethodManager) v.getContext()
						.getSystemService(Context.INPUT_METHOD_SERVICE);
				m.hideSoftInputFromWindow(v.getWindowToken(), 0);
			}
		}, 100);
	}

	/**
	 * 设置TextView颜色，selector用
	 * 
	 * @param context
	 * @param tv
	 * @param color
	 */
	public static void setTextViewColor(Context context, TextView tv, int color) {
		try {
			XmlResourceParser xrp = context.getResources().getXml(color);
			ColorStateList cl = ColorStateList.createFromXml(
					context.getResources(), xrp);
			if (cl != null) {
				tv.setTextColor(cl);
			}
		} catch (Exception ex) {
		}
	}
	
	/**
	 * WebView的一些配置
	 * @param web
	 */
	@SuppressLint("NewApi")
	public static void setWebView(WebView web){
		web.getSettings().setAppCacheEnabled(true);
		web.getSettings().setJavaScriptEnabled(true);
		web.getSettings().setDomStorageEnabled(true);
//		web.getSettings().setUseWideViewPort(true); 
		web.getSettings().setLoadWithOverviewMode(true);
		web.getSettings().setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
		web.getSettings().setBuiltInZoomControls(true);
//		web.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
//		web.setScrollbarFadingEnabled(false);
		
//		web.getSettings().setDefaultZoom(WebSettings.ZoomDensity.FAR);
		web.getSettings().setBuiltInZoomControls(false);
	}

	/**
	 * 获取View的位置
	 * @param context
	 * @param view
	 * @return
	 */
	public static int[] getLocationOnScreen(View view) {
		int[] location=new int[2];
		view.getLocationOnScreen(location);
		return location;
	}
	
	/**
	 * 动态设置ListView的高度
	 * @param listView
	 */
	public static void setListViewHeightBasedOnChildren(ListView listView) { 
	    if(listView == null) return;

	    ListAdapter listAdapter = listView.getAdapter(); 
	    if (listAdapter == null) { 
	        return; 
	    } 

	    int totalHeight = 0; 
	    for (int i = 0; i < listAdapter.getCount(); i++) { 
	        View listItem = listAdapter.getView(i, null, listView); 
	        listItem.measure(0, 0); 
	        totalHeight += listItem.getMeasuredHeight(); 
	    } 

	    ViewGroup.LayoutParams params = listView.getLayoutParams(); 
	    params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1)); 
	    listView.setLayoutParams(params); 
	}
	/**
	 * 获取View的位置
	 * @param context
	 * @param view
	 * @return
	 */
	public static int[] getLocationInWindow(View view) {
		int[] location=new int[2];
		view.getLocationInWindow(location);
		return location;
	}

}
