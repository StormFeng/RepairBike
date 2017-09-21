package com.lida.repairbike.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.apkfuns.logutils.LogUtils;
import com.lida.repairbike.R;
import com.lida.repairbike.datasource.ActivityMyRepairHistoryDataSource;
import com.lida.repairbike.tpl.ActivityMyRepairHistoryTpl;
import com.lida.repairbike.widget.MainHandler;
import com.lida.repairbike.widget.UpdateMain;
import com.midian.base.base.BaseListActivity;
import com.midian.base.bean.NetResult;
import com.midian.base.util.UIHelper;
import com.midian.base.widget.pulltorefresh.listviewhelper.IDataSource;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import cn.qqtheme.framework.picker.DatePicker;

/**
 * 个人中心-维修历史
 * Created by Administrator on 2017/3/10.
 */

public class Activity_MyRepairHistory extends BaseListActivity implements UpdateMain{

    private EditText etEnd;
    private EditText etStart;
    private TextView tvNumber;
    ActivityMyRepairHistoryDataSource dataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        dataSource = new ActivityMyRepairHistoryDataSource(this);
        dataSource.setUpdateMain(this);
        super.onCreate(savedInstanceState);
        etEnd = (EditText) findViewById(R.id.et_end);
        etStart = (EditText) findViewById(R.id.et_start);
        tvNumber = (TextView) findViewById(R.id.tv_Number);
        findViewById(R.id.iv_back).setOnClickListener(this);
        findViewById(R.id.time_start).setOnClickListener(this);
        findViewById(R.id.time_end).setOnClickListener(this);
        findViewById(R.id.ll_number).setOnClickListener(this);

    }

    @Override
    public void onClick(View arg0) {
        super.onClick(arg0);
        if(R.id.iv_back == arg0.getId()){
            finish();
        }else if(R.id.time_start == arg0.getId()){
            onYearMonthDayPicker(etStart);
        }else if(R.id.time_end == arg0.getId()){
            onYearMonthDayPicker(etEnd);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_myrepairhistory;
    }

    @Override
    protected IDataSource<ArrayList> getDataSource() {
        return dataSource;
    }

    @Override
    protected Class getTemplateClass() {
        return ActivityMyRepairHistoryTpl.class;
    }

    @Override
    public void update(final String number) {
        MainHandler.getInstance().post(new Runnable() {
            @Override
            public void run() {
                tvNumber.setText(number);
            }
        });
    }

    private void onYearMonthDayPicker(final EditText et) {
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH)+1;
        int day = c.get(Calendar.DAY_OF_MONTH);
        LogUtils.e("year:"+year+" month:"+month+" day:"+day);
        final DatePicker picker = new DatePicker(this);
        picker.setTopPadding(2);
        picker.setRangeStart(2016, 1, 1);
        picker.setRangeEnd(year, month, day);
        picker.setSelectedItem(year, month, day);
        picker.setOnDatePickListener(new DatePicker.OnYearMonthDayPickListener() {
            @Override
            public void onDatePicked(String year, String month, String day) {
                et.setText(year + "-" + month + "-" + day);
                String start = etStart.getText().toString();
                String end = etEnd.getText().toString();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
                Date dStart = null;
                Date dEnd = null;
                try {
                    dStart = sdf.parse(start);
                    dEnd = sdf.parse(end);
                    LogUtils.e(dEnd);
                    LogUtils.e(dStart);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if("".equals(start) || "".equals(end)){

                }else{

                    if(dEnd.before(dStart)){
                        UIHelper.t(_activity,"时间区间不正确");
                        et.setText("");
                        return;
                    }
                }
//                UIHelper.t(_activity, year + "-" + month + "-" + day);
            }
        });
        picker.setOnWheelListener(new DatePicker.OnWheelListener() {
            @Override
            public void onYearWheeled(int index, String year) {
                picker.setTitleText(year + "-" + picker.getSelectedMonth() + "-" + picker.getSelectedDay());
            }

            @Override
            public void onMonthWheeled(int index, String month) {
                picker.setTitleText(picker.getSelectedYear() + "-" + month + "-" + picker.getSelectedDay());
            }

            @Override
            public void onDayWheeled(int index, String day) {
                picker.setTitleText(picker.getSelectedYear() + "-" + picker.getSelectedMonth() + "-" + day);
            }
        });
        picker.show();
    }
}
