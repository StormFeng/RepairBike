package com.lida.repairbike.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lida.repairbike.R;
import com.lida.repairbike.activity.Activity_MyData;
import com.lida.repairbike.activity.Activity_MyRepairHistory;
import com.lida.repairbike.activity.Activity_Part;
import com.lida.repairbike.activity.Activity_PartManageCopy;
import com.lida.repairbike.activity.Activity_Scanner;
import com.lida.repairbike.app.Constant;
import com.makeramen.roundedimageview.RoundedImageView;
import com.midian.base.base.BaseFragment;
import com.midian.base.util.UIHelper;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 个人中心
 * Created by Administrator on 2017/3/16.
 */

public class FragmentPersonal extends BaseFragment {
    @BindView(R.id.iv_head)
    RoundedImageView ivHead;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_history)
    TextView tvHistory;
    @BindView(R.id.tv_part_manage)
    TextView tvPartManage;
    @BindView(R.id.tv_repair)
    TextView tvRepair;
    @BindView(R.id.tv_tool)
    TextView tvTool;
    @BindView(R.id.tv_account)
    TextView tvAccount;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_personal, null);
        ButterKnife.bind(this, v);
        tvName.setText(ac.name);
        tvAccount.setText(ac.account);
        ac.setImage(ivHead, Constant.IMGBASEURL + ac.head_img);
        return v;
    }

    @OnClick({R.id.tv_part_manage, R.id.tv_repair, R.id.tv_tool, R.id.tv_history, R.id.iv_head})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_history:
                UIHelper.jump(_activity, Activity_MyRepairHistory.class);
                break;
            case R.id.tv_part_manage:
                UIHelper.jump(_activity, Activity_PartManageCopy.class);
                break;
            case R.id.tv_repair:
                Bundle bundle = new Bundle();
                bundle.putString("flag", "repair");
                UIHelper.jump(_activity, Activity_Scanner.class, bundle);
                break;
            case R.id.tv_tool:
                UIHelper.jump(_activity, Activity_Part.class);
                break;
            case R.id.iv_head:
                UIHelper.jump(_activity, Activity_MyData.class);
                break;
        }
    }
}
