package com.midian.base.afinal.bitmap.display;

import android.graphics.Bitmap;
import android.view.View;

import com.midian.base.afinal.bitmap.core.BitmapDisplayConfig;

public interface Displayer {

	/**
	 * 图片加载完成 回调的函数
	 * @param imageView
	 * @param bitmap
	 * @param config
	 */
	public void loadCompletedisplay(View imageView, Bitmap bitmap, BitmapDisplayConfig config);
	
	/**
	 * 图片加载失败回调的函数
	 * @param imageView
	 * @param bitmap
	 */
	public void loadFailDisplay(View imageView, Bitmap bitmap);
	
}
