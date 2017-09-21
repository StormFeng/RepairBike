package com.midian.base.base;

import java.util.ArrayList;

//import midian.baselib.app.AppContext;
//import midian.baselib.bean.NetResult;
//import midian.baselib.shizhefei.view.listviewhelper.IDataSource;
import android.content.Context;

import com.midian.base.app.AppContext;
import com.midian.base.bean.NetResult;
import com.midian.base.widget.pulltorefresh.listviewhelper.IDataSource;

public abstract class BaseListDataSource<Model extends NetResult> implements
		IDataSource<ArrayList<Model>> {

	protected AppContext ac;
	protected Context context;

	protected int page = 1;
	protected boolean hasMore = true;
	protected boolean closeTip = false;
	protected ArrayList<Model> data = new ArrayList<Model>();

	public BaseListDataSource(Context context) {
		this.context = context;
		this.ac = (AppContext) context.getApplicationContext();
	}

	@Override
	public ArrayList<Model> refresh() throws Exception {
		return load(1);
	}

	@Override
	public ArrayList<Model> loadMore() throws Exception {
		return load(page + 1);
	}

	@Override
	public boolean hasMore() {
		return hasMore;
	}

	public boolean closeTip(){return closeTip;}

	public void onLoading() {

	}

	public void onError() {

	}

	public void onEmpty() {

	}

	@Override
	public ArrayList<Model> getResultList() {
		return data;
	}

	protected abstract ArrayList<Model> load(int page) throws Exception;

}
