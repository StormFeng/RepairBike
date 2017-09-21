package com.midian.base.base;

import java.util.ArrayList;

//import midian.baselib.bean.NetResult;
//import midian.baselib.shizhefei.view.listviewhelper.IDataAdapter;
//import midian.baselib.shizhefei.view.listviewhelper.ListViewHelper;
//import midian.baselib.view.BaseTpl;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;

import com.midian.base.bean.NetResult;
import com.midian.base.view.BaseTpl;
import com.midian.base.widget.pulltorefresh.listviewhelper.IDataAdapter;
import com.midian.base.widget.pulltorefresh.listviewhelper.ListViewHelper;

public class BaseListAdapter<Model extends NetResult> extends BaseAdapter implements IDataAdapter<ArrayList<Model>> {
	protected Context context;
	protected ArrayList<Model> data;
	protected Class itemViewClazz;
	protected AbsListView absListView;
	protected Runnable runnable;
	protected ListViewHelper<Model> listViewHelper;

	public <T> BaseListAdapter(AbsListView absListView, Context context, ArrayList<Model> arrayList,
			Class<T> itemViewClazz, ListViewHelper<Model> listViewHelper) {
		this.absListView = absListView;
		this.context = context;
		this.data = arrayList;
		this.itemViewClazz = itemViewClazz;
		this.listViewHelper = listViewHelper;
	}

	@Override
	public int getCount() {
		return data.size();
	}
	@Override
	public Model getItem(int position) {
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			try {
				convertView = (View) itemViewClazz.getConstructor(Context.class).newInstance(context);
				((BaseTpl) convertView).config(this, data, absListView, listViewHelper);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (!(convertView instanceof BaseTpl)) {
			if ("android.view.View".equals(convertView.getClass().getName())) {
			}
			return convertView;
		}
		BaseTpl view = (BaseTpl) convertView;
		view.setBean(getItem(position), position);
		return convertView;
	}

	@Override
	public void notifyDataSetChanged() {
		super.notifyDataSetChanged();
		if (runnable != null) {
			runnable.run();
		}
	}

	public Runnable getRunnable() {
		return runnable;
	}

	public void setRunnable(Runnable runnable) {
		this.runnable = runnable;
	}

	@Override
	public void setData(ArrayList<Model> res, boolean isRefresh) {
		if (isRefresh) {
			this.data.clear();
		}
		this.data.addAll(res);
	}

	@Override
	public ArrayList<Model> getData() {
		return data;
	}

}
