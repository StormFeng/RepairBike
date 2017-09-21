package com.midian.base.base;

import java.util.ArrayList;

import android.os.Bundle;
import android.view.View;
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


/**
 * 列表activity基类 Created by XuYang on 15/4/15.
 */
public abstract class BaseListActivity<Model extends NetResult> extends BaseActivity implements OnItemClickListener, OnStateChangeListener<ArrayList<Model>> {

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
		listView.setDivider(getResources().getDrawable(R.drawable.divider_line));
		listView.setOnItemClickListener(this);
		listView.setVerticalScrollBarEnabled(false);
		resultList = dataSource.getResultList();
		// 设置适配器
        adapter = new BaseListAdapter(listView, this, resultList, getTemplateClass(), listViewHelper);
        listViewHelper.setOnStateChangeListener(this);
        listViewHelper.setAdapter(adapter);
        if (resultList.size() == 0) {
            listViewHelper.refresh();
        }
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
	protected abstract Class getTemplateClass();

	@Override
	public void onDestroy() {
		super.onDestroy();
		// 释放资源
        try {
            if (listViewHelper != null)
                listViewHelper.destory();
            listViewHelper = null;
            adapter=null;
            refreshListView=null;
            dataSource=null;
            resultList=null;
        } catch (Exception e) {
            e.printStackTrace();
        }
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

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
