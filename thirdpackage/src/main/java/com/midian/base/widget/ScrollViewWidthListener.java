package com.midian.base.widget;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

/**
 * 可以监听子view是否进入屏幕的ScrollView
 * 
 * @author XuYang
 * 
 */
public class ScrollViewWidthListener extends ScrollView {
	Set<Integer> _shownViewsIndices = new HashSet<Integer>();
	onChildViewVisibilityChangedListener _onChildViewVisibilityChangedListener;
	private boolean isInit = true;
	ScrollChangeListener scrollChangeListener;

	public interface onChildViewVisibilityChangedListener {
		public void onChildViewVisibilityChanged(int index, View v,
												 boolean becameVisible);
	}

	public ScrollViewWidthListener(final Context context) {
		super(context);
	}

	public ScrollViewWidthListener(final Context context,
			final AttributeSet attrs) {
		super(context, attrs);
	}

	public ScrollViewWidthListener(final Context context,
			final AttributeSet attrs, final int defStyle) {
		super(context, attrs, defStyle);
	}

	public void setOnChildViewVisibilityChangedListener(
			final onChildViewVisibilityChangedListener onChildViewVisibilityChangedListener) {
		_onChildViewVisibilityChangedListener = onChildViewVisibilityChangedListener;
	}

	@Override
	protected void onLayout(final boolean changed, final int l, final int t,
			final int r, final int b) {
		super.onLayout(changed, l, t, r, b);
		if (isInit) {
			checkViewsVisibility(l, t);
			isInit = false;
		}
	}

	private void checkViewsVisibility(final int l, final int t) {
		final ViewGroup viewGroup = (ViewGroup) getChildAt(0);
		final int childCount = viewGroup.getChildCount();
		if (childCount == 0)
			return;
		final int parentBottom = t + getHeight();
		// prepare to use binary search to find a view that is inside the bounds
		int min = 0, max = childCount - 1, piv = -1;
		int childTop, childBottom;
		View v;
		// check previously shown views
		for (final Iterator<Integer> iterator = _shownViewsIndices.iterator(); iterator
				.hasNext();) {
			final Integer cur = iterator.next();
			v = viewGroup.getChildAt(cur);
			childTop = v.getTop();
			childBottom = v.getBottom();
			if (childTop <= parentBottom && childBottom >= t) {
				if (piv == -1)
					piv = cur;
			} else {
				if (_onChildViewVisibilityChangedListener != null)
					_onChildViewVisibilityChangedListener.onChildViewVisibilityChanged(cur, v, false);
				iterator.remove();
			}
		}
		if (piv == -1) {
			// check first view
			v = viewGroup.getChildAt(min);
			childTop = v.getTop();
			childBottom = v.getBottom();
			if (childTop <= parentBottom && childBottom >= t)
				piv = min;
			else {
				// check last view
				v = viewGroup.getChildAt(max);
				childTop = v.getTop();
				childBottom = v.getBottom();
				if (childTop <= parentBottom && childBottom >= t)
					piv = min;
			}
			if (piv == -1)
				while (true) {
					piv = (min + max) / 2;
					v = viewGroup.getChildAt(piv);
					childTop = v.getTop();
					childBottom = v.getBottom();
					if (childTop <= parentBottom && childBottom >= t)
						break;
					if (max - min == 1)
						return;
					if (childBottom < t)
						// view above bounds
						min = piv;
					else
						max = piv;
				}
		}
		//
		for (int i = piv; i < childCount; ++i) {
			v = viewGroup.getChildAt(i);
			childTop = v.getTop();
			childBottom = v.getBottom();
			// _shownViewsIndices.
			if (childTop <= parentBottom && childBottom >= t&& !_shownViewsIndices.contains(i)) {
				_shownViewsIndices.add(i);
				if (_onChildViewVisibilityChangedListener != null)
					_onChildViewVisibilityChangedListener.onChildViewVisibilityChanged(i, v, true);
			}
		}
		for (int i = piv - 1; i >= 0; --i) {
			v = viewGroup.getChildAt(i);
			childTop = v.getTop();
			childBottom = v.getBottom();
			if (childTop <= parentBottom && childBottom >= t
					&& !_shownViewsIndices.contains(i)) {_shownViewsIndices.add(i);
				if (_onChildViewVisibilityChangedListener != null)
					_onChildViewVisibilityChangedListener.onChildViewVisibilityChanged(i, v, true);
			}
		}
	}

	public void setChangeListener(ScrollChangeListener listener){
		this.scrollChangeListener=listener;
	}

	@Override
	protected void onScrollChanged(final int l, final int t, final int oldl,
			final int oldt) {
		super.onScrollChanged(l, t, oldl, oldt);
		checkViewsVisibility(l, t);
		if (scrollChangeListener != null) {
			scrollChangeListener.onScrollChanged(this, l, t, oldl, oldt);
		}
	}

}
