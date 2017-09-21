package com.lida.repairbike.fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.lida.repairbike.R;
import com.lida.repairbike.activity.Activity_Map;
import com.lida.repairbike.activity.Activity_Part;
import com.lida.repairbike.activity.Activity_RepairList;
import com.lida.repairbike.activity.Activity_Scanner;
import com.midian.base.base.BaseFragment;
import com.midian.base.util.UIHelper;
import com.midian.base.widget.Banner;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/3/16.
 */

public class FragmentHome extends BaseFragment {
    @BindView(R.id.tv_position)
    TextView tvPosition;
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.iv_search)
    ImageView ivSearch;
    @BindView(R.id.iv_new)
    ImageView ivNew;
    @BindView(R.id.bannerView)
    Banner bannerView;
    @BindView(R.id.tv_tab1)
    TextView tvTab1;
    @BindView(R.id.tv_tab2)
    TextView tvTab2;
    @BindView(R.id.tv_tab3)
    TextView tvTab3;
    @BindView(R.id.tv_tab4)
    TextView tvTab4;
    @BindView(R.id.tv_tab5)
    TextView tvTab5;
    @BindView(R.id.tv_tab7)
    TextView tvTab7;
    @BindView(R.id.ll_notice)
    LinearLayout llNotice;

    private List<Integer> images = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, null);
        ButterKnife.bind(this, v);
        initView();
        return v;
    }

    private void initView() {
        bannerView.setBannerStyle(Banner.CIRCLE_INDICATOR);//设置圆形指示器
        bannerView.setIndicatorGravity(Banner.CENTER);
        bannerView.isAutoPlay(true);
        bannerView.setDelayTime(5000);//设置轮播间隔时间
//        bannerView.setOnBannerClickListener(this);
        for (int i = 0; i < 3; i++) {
            images.add(R.drawable.icon_banner);
        }
        bannerView.setImages(images.toArray());
    }

    @OnClick({R.id.tv_position, R.id.iv_search, R.id.iv_new, R.id.tv_tab1, R.id.tv_tab2, R.id.tv_tab3, R.id.tv_tab4, R.id.tv_tab5, R.id.ll_notice})
    public void onClick(View view) {
        Bundle bundle ;
        switch (view.getId()) {
            case R.id.tv_position:
                break;
            case R.id.iv_search:
                break;
            case R.id.iv_new:
                break;
            case R.id.tv_tab1:
                UIHelper.jump(_activity, Activity_Part.class);//配件
                break;
            case R.id.tv_tab2:
                UIHelper.jump(_activity, Activity_RepairList.class);//维修查询
                break;
            case R.id.tv_tab3:
                bundle = new Bundle();
                bundle.putString("flag", "check");
                UIHelper.jump(_activity, Activity_Scanner.class, bundle);//扫码页
                break;
            case R.id.tv_tab4:
                UIHelper.jump(_activity, Activity_Map.class);//地图页
                break;
            case R.id.tv_tab5:
                bundle = new Bundle();
                bundle.putString("flag", "repair");
                UIHelper.jump(_activity, Activity_Scanner.class, bundle);//扫码页
                break;
            case R.id.ll_notice:
                break;
        }
    }
}
