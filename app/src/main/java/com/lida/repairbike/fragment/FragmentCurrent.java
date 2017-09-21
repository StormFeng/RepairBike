package com.lida.repairbike.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lida.repairbike.R;
import com.midian.base.base.BaseFragment;
import com.midian.base.util.Func;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/3/6.
 */

public class FragmentCurrent extends BaseFragment {

    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_mid)
    TextView tvMid;
    @BindView(R.id.tv_lockid)
    TextView tvLockid;
    @BindView(R.id.tv_trouble)
    TextView tvTrouble;
    @BindView(R.id.tv_part)
    TextView tvPart;
    @BindView(R.id.tv_position)
    TextView tvPosition;
    @BindView(R.id.tv_name)
    TextView tvName;

    private ArrayList<String> params = new ArrayList<>();

    public FragmentCurrent() {
    }

    @SuppressLint("ValidFragment")
    public FragmentCurrent(ArrayList<String> params) {
        this.params = params;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_current, null);
        ButterKnife.bind(this, v);
        tvMid.setText("单车号："+params.get(0));
        tvLockid.setText("锁号："+params.get(1));
        tvTrouble.setText("故障原因："+params.get(2));
        tvPosition.setText(params.get(3));
        tvTime.setText(Func.stampToDate(params.get(4)));
        tvName.setText(params.get(5));
        return v;
    }
}
