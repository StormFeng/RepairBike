package com.midian.base.base;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.midian.base.app.AppContext;
import com.midian.base.util.UMengStatistticUtil;

/**
 * Fragment基类
 */
public class BaseFragment extends Fragment {
	protected AppContext ac;
	protected BaseFragmentActivity _activity;
	protected Fragment _fragment;
	protected FragmentManager fm;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		_activity = (BaseFragmentActivity) activity;
		ac = (AppContext) _activity.getApplication();
		fm = getChildFragmentManager();
		_fragment = this;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onResume() {
		super.onResume();
		UMengStatistticUtil.onResumeForFragment(this.getActivity());

	}

	@Override
	public void onPause() {
		super.onPause();
		UMengStatistticUtil.onPauseForFragment(this.getActivity());
	}

}
