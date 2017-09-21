package com.lida.repairbike.tpl;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lida.repairbike.R;
import com.lida.repairbike.activity.Activity_RepairDetail;
import com.lida.repairbike.bean.ActivityRepairQueryBean;
import com.midian.base.util.UIHelper;
import com.midian.base.view.BaseTpl;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 维修查询
 * Created by Administrator on 2017/3/6.
 */

public class ActivityRepairListTpl extends BaseTpl<ActivityRepairQueryBean.DataBean> {

    @BindView(R.id.tv_bikeId)
    TextView tvBikeId;
    @BindView(R.id.tv_bikePass)
    TextView tvBikePass;
    @BindView(R.id.tv_reason)
    TextView tvReason;
    @BindView(R.id.tv_position)
    TextView tvPosition;
    @BindView(R.id.ll_root)
    LinearLayout llRoot;
    @BindView(R.id.tvStatu)
    TextView tvStatu;
    @BindView(R.id.tv_name)
    TextView tvName;

    public ActivityRepairListTpl(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ActivityRepairListTpl(Context context) {
        super(context);
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.item_activityrepairlist;
    }

    @Override
    public void setBean(final ActivityRepairQueryBean.DataBean bean, int position) {
        if (bean != null) {
            tvBikeId.setText(bean.getMid());
            tvBikePass.setText(bean.getLockid());
            tvPosition.setText(bean.getAddress());
            Pattern pattern = Pattern.compile("\\d");
            Matcher matcher = pattern.matcher(bean.getTrouble());
            final String replace = matcher.replaceAll("").trim().replace("{", "").replace("}", "").replace(":", "");
            tvReason.setText("故障原因：" + replace);
            tvName.setText(bean.getName());
            if ("1".equals(bean.getStaus())) {
                tvStatu.setText("等待您的维修");
                tvStatu.setTextColor(Color.parseColor("#FC5D0F"));
            } else if ("2".equals(bean.getStaus())) {
                tvStatu.setText("已完成维修");
                tvStatu.setTextColor(Color.parseColor("#04D33F"));
            }
            llRoot.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    ArrayList<String> list = new ArrayList<>();
                    list.add(bean.getMid());
                    list.add(bean.getLockid());
                    list.add(replace);
                    list.add(bean.getAddress());
                    list.add(bean.getRtime());
                    list.add(bean.getName());
                    bundle.putStringArrayList("params", list);
                    UIHelper.jump(_activity, Activity_RepairDetail.class, bundle);
                }
            });
        }
    }
}
