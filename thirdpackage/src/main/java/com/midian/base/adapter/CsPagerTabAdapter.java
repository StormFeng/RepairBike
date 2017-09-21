package com.midian.base.adapter;

import java.util.ArrayList;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by XuYang on 15/4/21.
 */
public class CsPagerTabAdapter extends PagerAdapter {
	private ArrayList<View> views;
	private ArrayList<String> titles;

	public CsPagerTabAdapter(ArrayList<View> views, ArrayList<String> titles) {
		this.views = views;
		this.titles = titles;
	}

	@Override
	public int getCount() {
		if (views == null || views.size() == 0)
			return 0;
		if (views.size() == 1)
			return 1;
		return Integer.MAX_VALUE;
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		if (views == null || views.size() == 0)
			return;
		if (views.size() > 2)
			container.removeView(views.get(position % views.size()));
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		View view = null;
		if (views == null || views.size() == 0)
			return null;
		if (views.size() > 1) {
			view = views.get(position % views.size());
			if (view.getParent() != null) {
				ViewGroup group = (ViewGroup) view.getParent();
				group.removeView(view);
			}
			// container.removeView(view);
			container.addView(view);
		} else if (views.size() == 1) {
			container.removeView(views.get(0));
			container.addView(views.get(0));
			return views.get(0);
		}
		return view;
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return titles.get(position % titles.size());
	}
}
