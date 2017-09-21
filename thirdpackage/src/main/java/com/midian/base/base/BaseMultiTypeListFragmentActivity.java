package com.midian.base.base;

import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;


import com.bishilai.thirdpackage.R;
import com.midian.base.bean.NetResult;
import com.midian.base.widget.pulltorefresh.PullToRefreshListView;
import com.midian.base.widget.pulltorefresh.listviewhelper.IDataAdapter;
import com.midian.base.widget.pulltorefresh.listviewhelper.IDataSource;
import com.midian.base.widget.pulltorefresh.listviewhelper.ListViewHelper;
import com.midian.base.widget.pulltorefresh.listviewhelper.OnStateChangeListener;

import java.util.ArrayList;


/**
 * 列表activity基类 Created by XuYang on 15/4/15.
 */
public abstract class BaseMultiTypeListFragmentActivity<Model extends NetResult>
		extends BaseFragmentActivity implements OnItemClickListener,
		OnStateChangeListener<ArrayList<Model>> {

	protected ListViewHelper<ArrayList<Model>> listViewHelper;

	protected PullToRefreshListView refreshListView;

	protected ListView listView;

	protected IDataSource<ArrayList<Model>> dataSource;

	protected ArrayList<Model> resultList;
    protected BaseListAdapter<Model> adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getLayoutId() == -1) {
			setContentView(R.layout.c_list_activity);
		} else {
			setContentView(getLayoutId());
		}
		refreshListView = findView(R.id.pullToRefreshListView);
		listViewHelper = new ListViewHelper<ArrayList<Model>>(refreshListView);
		// 设置数据源
		dataSource = getDataSource();
		listViewHelper.setDataSource(this.dataSource);
		listView = refreshListView.getRefreshableView();
		listView.setOnItemClickListener(this);
		resultList = dataSource.getResultList();
		// 设置适配器
        adapter = new BaseMultiTypeListAdapter(listView, this, resultList, getTemplateClasses(), listViewHelper);
        listViewHelper.setOnStateChangeListener(this);
        listViewHelper.setAdapter(adapter);
        listViewHelper.refresh();
//		listViewHelper.setAdapter(new BaseMultiTypeListAdapter(listView, this,resultList, getTemplateClasses(), listViewHelper));
	}


	/**
	 * 默认
	 * 
	 * @return
	 */
	protected final int getDefaultLayoutId() {
		return -1;
	}

	protected int getLayoutId() {
		return getDefaultLayoutId();
	}

	/**
	 * 获取数据源
	 * 
	 * @return
	 */
	protected abstract IDataSource<ArrayList<Model>> getDataSource();

	/**
	 * 获取条目模板
	 * 
	 * @return
	 */
	protected abstract ArrayList<Class> getTemplateClasses();

	@Override
	public void onDestroy() {
		super.onDestroy();
		// 释放资源
		listViewHelper.destory();
	}

	public void refresh() {
		listViewHelper.doPullRefreshing(true, 0);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {

	}

	@Override
	public void onStartRefresh(IDataAdapter<ArrayList<Model>> adapter) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onEndRefresh(IDataAdapter<ArrayList<Model>> adapter,
			ArrayList<Model> result) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStartLoadMore(IDataAdapter<ArrayList<Model>> adapter) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onEndLoadMore(IDataAdapter<ArrayList<Model>> adapter,
			ArrayList<Model> result) {
		// TODO Auto-generated method stub

	}
}
