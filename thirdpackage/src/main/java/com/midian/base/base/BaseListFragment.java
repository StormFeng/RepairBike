package com.midian.base.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

//import com.midian.baselib.R;

import com.bishilai.thirdpackage.R;
import com.midian.base.bean.NetResult;
import com.midian.base.widget.ListViewForScrollView;
import com.midian.base.widget.pulltorefresh.PullToRefreshListView;
import com.midian.base.widget.pulltorefresh.listviewhelper.IDataAdapter;
import com.midian.base.widget.pulltorefresh.listviewhelper.IDataSource;
import com.midian.base.widget.pulltorefresh.listviewhelper.ListViewHelper;
import com.midian.base.widget.pulltorefresh.listviewhelper.OnStateChangeListener;

import java.util.ArrayList;

//import midian.baselib.bean.NetResult;
//import midian.baselib.shizhefei.view.listviewhelper.IDataAdapter;
//import midian.baselib.shizhefei.view.listviewhelper.IDataSource;
//import midian.baselib.shizhefei.view.listviewhelper.ListViewHelper;
//import midian.baselib.shizhefei.view.listviewhelper.OnStateChangeListener;
//import midian.baselib.widget.ListViewForScrollView;
//import midian.baselib.widget.pulltorefresh.PullToRefreshListView;
//import midian.baselib.widget.pulltorefresh.PullToRefreshScrollView;

/**
 * 列表fragment基类 Created by XuYang on 15/4/15.
 */
public abstract class BaseListFragment<Model extends NetResult> extends BaseFragment implements OnItemClickListener, OnStateChangeListener<ArrayList<Model>> {

    protected ListViewHelper<ArrayList<Model>> listViewHelper;

    protected PullToRefreshListView refreshListView;
//    protected PullToRefreshScrollView

    protected ListView listView;
    protected ListViewForScrollView listViewForScrollView;

    protected IDataSource<ArrayList<Model>> dataSource;

    protected ArrayList<Model> resultList;
    protected BaseListAdapter<Model> adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = null;

        if (getLayoutId() == -1) {
            refreshListView = new PullToRefreshListView(_activity);
            root = refreshListView;
        } else {
            root = inflater.inflate(getLayoutId(), null);
            refreshListView = (PullToRefreshListView) root.findViewById(R.id.pullToRefreshListView);
        }
        if (listViewHelper == null) {
            listViewHelper = new ListViewHelper<ArrayList<Model>>(refreshListView);
        }
        // 设置数据源
        if (dataSource == null) {
            dataSource = getDataSource();
        }
        listViewHelper.setDataSource(this.dataSource);
        listView = refreshListView.getRefreshableView();
        listView.setDivider(getResources().getDrawable(R.drawable.divider_line));
        listView.setOnItemClickListener(this);
        listView.setVerticalScrollBarEnabled(false);
        if (resultList == null) {
            resultList = dataSource.getResultList();
        }
        // 设置适配器
        if (listViewHelper.getAdapter() == null) {
            adapter = new BaseListAdapter(listView, _activity, resultList, getTemplateClass(), listViewHelper);
            listViewHelper.setOnStateChangeListener(this);
            listViewHelper.setAdapter(adapter);
        }
        if (resultList.size() == 0) {
            listViewHelper.refresh();
        }

        return root;
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
    public void onDestroyView() {
        super.onDestroyView();
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
    public void onDestroy() {

        // 释放资源
        super.onDestroy();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onStartRefresh(IDataAdapter<ArrayList<Model>> adapter) {

    }

    @Override
    public void onEndRefresh(IDataAdapter<ArrayList<Model>> adapter, ArrayList<Model> result) {

    }

    @Override
    public void onStartLoadMore(IDataAdapter<ArrayList<Model>> adapter) {

    }

    @Override
    public void onEndLoadMore(IDataAdapter<ArrayList<Model>> adapter, ArrayList<Model> result) {

    }
}
