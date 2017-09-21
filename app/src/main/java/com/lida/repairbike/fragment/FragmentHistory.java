package com.lida.repairbike.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lida.repairbike.R;
import com.lida.repairbike.datasource.FragmentHistoryDataSource;
import com.lida.repairbike.tpl.FragmentHistoryTpl;
import com.midian.base.base.BaseListFragment;
import com.midian.base.widget.pulltorefresh.listviewhelper.IDataSource;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/3/6.
 */

public class FragmentHistory extends BaseListFragment {

    private String mid;
    private TextView tvMid;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        tvMid = (TextView) view.findViewById(R.id.tv_mid);
        tvMid.setText("单车号："+mid);
        return view;
    }

    public FragmentHistory() {
    }

    @SuppressLint("ValidFragment")
    public FragmentHistory(String mid) {
        this.mid = mid;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_history;
    }

    @Override
    protected IDataSource<ArrayList> getDataSource() {
        return new FragmentHistoryDataSource(_activity,mid);
    }

    @Override
    protected Class getTemplateClass() {
        return FragmentHistoryTpl.class;
    }
}
