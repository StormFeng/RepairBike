package com.lida.repairbike.tpl;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.lida.repairbike.R;
import com.lida.repairbike.bean.ActivityMyRepairHistoryBean;
import com.midian.base.util.Func;
import com.midian.base.view.BaseTpl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/3/6.
 */

public class ActivityMyRepairHistoryTpl extends BaseTpl<ActivityMyRepairHistoryBean.DataBean> {

    @BindView(R.id.tv_bikeId)
    TextView tvBikeId;
    @BindView(R.id.tv_bikePass)
    TextView tvBikePass;
    @BindView(R.id.tv_reason)
    TextView tvReason;
    @BindView(R.id.tv_position)
    TextView tvPosition;
    @BindView(R.id.tv_time)
    TextView tvTime;

    public ActivityMyRepairHistoryTpl(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ActivityMyRepairHistoryTpl(Context context) {
        super(context);
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
//        root.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                UIHelper.jump(_activity, Activity_RepairDetail.class);
//            }
//        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.item_activitymyrepairhistory;
    }

    @Override
    public void setBean(ActivityMyRepairHistoryBean.DataBean bean, int position) {
        if (bean != null) {
            tvBikeId.setText("单车号：" + bean.getMid());
            tvBikePass.setText("锁 号：" + bean.getLockid());
            tvPosition.setText(bean.getAddress());
            Pattern pattern = Pattern.compile("\\d");
            Matcher matcher = pattern.matcher(bean.getTrouble());
            String replace = matcher.replaceAll("").trim().replace("{", "").replace("}", "").replace(":", "");
            tvReason.setText("故障原因：" + replace);
            tvTime.setText(Func.stampToDate(bean.getRtime_complete()));
        }
    }
}
