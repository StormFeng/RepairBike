package com.midian.base.adapter;

import java.util.ArrayList;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

public class PagerTabAdapter extends FragmentPagerAdapter {

	private ArrayList<Fragment> fragments;
	private ArrayList<String>  titles;

	public PagerTabAdapter(FragmentManager fm, ArrayList<String> titles, ArrayList<Fragment> fragments) {
		super(fm);
		this.fragments = fragments;
		this.titles = titles;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
	}

	@Override
	public Fragment getItem(int position) {
		return fragments.get(position);
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return titles.get(position % titles.size());
	}

	@Override
	public int getCount() {
		return titles.size();
	}
}
