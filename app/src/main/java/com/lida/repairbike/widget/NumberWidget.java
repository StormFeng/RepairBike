package com.lida.repairbike.widget;

import android.content.Context;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lida.repairbike.R;

/**
 * Created by Administrator on 2017/3/11 0011.
 */

public class NumberWidget extends LinearLayout implements View.OnClickListener {

    private TextView tvPlus;
    private TextView tvReduce;
    private EditText etNumber;
    private LinearLayout llReduce;

    public NumberWidget(Context context) {
        super(context);
        init();
    }

    public NumberWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public NumberWidget(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        View view = View.inflate(getContext(), R.layout.numberwidget,this);
        etNumber = (EditText) view.findViewById(R.id.et_number);
        tvPlus = (TextView) view.findViewById(R.id.tv_plus);
        tvReduce = (TextView) view.findViewById(R.id.tv_reduce);
        llReduce = (LinearLayout) view.findViewById(R.id.ll_reduce);
        tvPlus.setOnClickListener(this);
        llReduce.setOnClickListener(this);
    }

    public void setOnNumberChangeListener(TextWatcher watcher){
        etNumber.addTextChangedListener(watcher);
    }

    public String getNumber(){
        return etNumber.getText().toString().trim();
    }

    @Override
    public void onClick(View v) {
        String number = etNumber.getText().toString().trim();
        int i = Integer.valueOf(number);
        if(R.id.tv_plus == v.getId()){
            i++;
        }else{
            if(i==0){
                return;
            }else{
                i--;
            }
        }
        etNumber.setText(i+"");
    }
}
