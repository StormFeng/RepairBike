package com.lida.repairbike.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lida.repairbike.R;
import com.lida.repairbike.datasource.ActivityRepairListDataSource;
import com.lida.repairbike.tpl.ActivityRepairListTpl;
import com.midian.base.base.BaseListActivity;
import com.midian.base.util.UIHelper;
import com.midian.base.widget.pulltorefresh.listviewhelper.IDataSource;

import java.util.ArrayList;

/**
 * 维修查询
 * Created by Administrator on 2017/3/6.
 */

public class Activity_RepairList extends BaseListActivity {

    private ImageView ivBack;
    private TextView tvScanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ivBack = (ImageView) findViewById(R.id.iv_back);
        ivBack.setOnClickListener(this);
        findViewById(R.id.tv_scanner).setOnClickListener(this);
    }

    @Override
    public void onClick(View arg0) {
        super.onClick(arg0);
        if(arg0.getId()==R.id.iv_back){
            finish();
        }else if(arg0.getId()==R.id.tv_scanner){
            Bundle bundle=new Bundle();
            bundle.putString("flag","repair");
            UIHelper.jump(_activity,Activity_Scanner.class,bundle);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_repairlist;
    }

    @Override
    protected IDataSource<ArrayList> getDataSource() {
        return new ActivityRepairListDataSource(_activity);
    }

    @Override
    protected Class getTemplateClass() {
        return ActivityRepairListTpl.class;
    }
}
