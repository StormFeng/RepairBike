package com.midian.base.adapter;

import java.util.ArrayList;

import com.midian.base.util.ViewUtil;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by XuYang on 15/4/21.
 */
public class CPagerTabAdapter extends PagerAdapter {
	private ArrayList<View> views;
	private ArrayList<String> titles;

	public CPagerTabAdapter(ArrayList<View> views, ArrayList<String> titles) {
		this.views = views;
		this.titles = titles;
	}

	@Override
	public int getCount() {
		if (views == null)
			return 0;

		return views.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		if (views == null || views.size() < 3)
			return;
		System.out.println("destroyItem::::::" + position);
		container.removeView((View) object);
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		if (views == null || views.size() == 0)
			return null;
		View view = views.get(position % views.size());
		if (view.getParent() != null) {
			ViewGroup group = (ViewGroup) view.getParent();
			group.removeView(view);
		}
		// container.removeView(view);
		System.out.println("position::::::" + position);
		container.addView(view);
		return view;
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return titles.get(position % titles.size());
	}
}
