package com.lida.repairbike.tpl;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.lida.repairbike.R;
import com.lida.repairbike.bean.FragmentHistoryBean;
import com.midian.base.bean.NetResult;
import com.midian.base.util.Func;
import com.midian.base.view.BaseTpl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/3/6.
 */

public class FragmentHistoryTpl extends BaseTpl<FragmentHistoryBean.DataBean> {

    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_trouble)
    TextView tvTrouble;
    @BindView(R.id.tv_part)
    TextView tvPart;
    @BindView(R.id.tv_position)
    TextView tvPosition;
    @BindView(R.id.tv_name)
    TextView tvName;

    public FragmentHistoryTpl(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FragmentHistoryTpl(Context context) {
        super(context);
    }

    @Override
    protected void initView() {
        root.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
//                UIHelper.jump(_activity, Activity_RepairDetail.class);
            }
        });
        ButterKnife.bind(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.item_fragmenthistory;
    }

    @Override
    public void setBean(FragmentHistoryBean.DataBean bean, int position) {
        if (bean != null) {
            Pattern pattern = Pattern.compile("\\d");
            Matcher matcher = pattern.matcher(bean.getTrouble());
            String replace = matcher.replaceAll("").trim().replace("{", "").replace("}", "").replace(":", "");
            tvTrouble.setText("故障原因："+replace);
            tvPart.setText("消耗配件："+bean.getParts_name());
            tvName.setText(bean.getName());
            tvPosition.setText(bean.getAddress());
            tvTime.setText(Func.stampToDate(bean.getRtime_complete()));
        }
    }
}
