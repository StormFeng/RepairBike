package com.lida.repairbike.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.apkfuns.logutils.LogUtils;
import com.lida.repairbike.R;
import com.lida.repairbike.adapter.AdapterGridViewReason;
import com.lida.repairbike.bean.StorageBean;
import com.lida.repairbike.bean.TroubleBean;
import com.lida.repairbike.util.AppUtil;
import com.lida.repairbike.widget.AddDialog;
import com.lida.repairbike.widget.InnerGridView;
import com.lida.repairbike.widget.popupwindow.BasePopupWindow;
import com.lida.repairbike.widget.popupwindow.OnItemClickListener;
import com.midian.base.api.ApiCallback;
import com.midian.base.base.BaseActivity;
import com.midian.base.bean.NetResult;
import com.midian.base.util.UIHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 故障检修
 * Created by Administrator on 2017/3/6 0006.
 */

public class Activity_CheckPoint extends BaseActivity {
    @BindView(R.id.tv_software)
    TextView tvSoftware;
    @BindView(R.id.tv_hardware)
    TextView tvHardware;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.gv_reason_software)
    InnerGridView gvReasonSoftware;
    @BindView(R.id.gv_reason_hardware)
    InnerGridView gvReasonHardware;
    @BindView(R.id.btn_complate)
    Button btnComplate;
    @BindView(R.id.tv_bikeId)
    TextView tvBikeId;
    @BindView(R.id.et_key)
    EditText etKey;
    @BindView(R.id.btn_factory)
    Button btnFactory;
    @BindView(R.id.btn_warehouse)
    Button btnWarehouse;
    @BindView(R.id.btn_scene)
    TextView btnScene;
    @BindView(R.id.iv1)
    ImageView iv1;
    @BindView(R.id.iv2)
    ImageView iv2;

    private List<String> troubleListSoftWare = new ArrayList<>();//软件故障
    private Map<Integer, Boolean> troubleStateSoftWare = new HashMap<>();//软件故障选中状态
    private List<String> softWareIds = new ArrayList<>();
    private AdapterGridViewReason adapterTroubleSoftWare;//软件故障
    private List<String> troubleListHardWare = new ArrayList<>();//硬件故障
    private List<String> hardWareIds = new ArrayList<>();
    private Map<Integer, Boolean> troubleStateHardWare = new HashMap<>();//硬件故障选中状态
    private AdapterGridViewReason adapterTroubleHardWare;//硬件故障

    private String type = "1";
    private String storage = "";
    private String factory = "";

    private ArrayList<Integer> softIds = null;//向下一个页面传递的故障id
    private ArrayList<Integer> hardIds = null;

    private String mid;//接收上个页面的单车id;

    private BasePopupWindow popFactory;
    private List<String> factoryNames = new ArrayList<>();
    private List<String> warehouseNames = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkpoint);
        ButterKnife.bind(this);
        mid = mBundle.getString("mid");
        tvBikeId.setText(mid);
        etKey.setHint(mid);
        initView();
    }

    private void initView() {
        adapterTroubleSoftWare = new AdapterGridViewReason(_activity, troubleListSoftWare, troubleStateSoftWare);
        adapterTroubleHardWare = new AdapterGridViewReason(_activity, troubleListHardWare, troubleStateHardWare);

        AppUtil.getBikeApiClient(ac).getTroubleSoftWare("1", this);//软件故障
        AppUtil.getBikeApiClient(ac).getTroubleHardWare("2", this);//硬件故障
        AppUtil.getBikeApiClient(ac).getFactory(this);
        AppUtil.getBikeApiClient(ac).getStorage(this);
        gvReasonSoftware.setAdapter(adapterTroubleSoftWare);
        gvReasonHardware.setAdapter(adapterTroubleHardWare);
        initPopWindow();
    }

    private void initPopWindow() {
        View view = LayoutInflater.from(_activity).inflate(R.layout.layout_spinner, null);
        popFactory = new BasePopupWindow(this, view, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popFactory.setOutsideTouchable(false);

//        factoryNames.add("返厂维修");
//        factoryNames.add("1号厂");
//        factoryNames.add("2号厂");
//        factoryNames.add("3号厂");
//
//        warehouseNames.add("送仓维修");
//        warehouseNames.add("1号仓库");
//        warehouseNames.add("2号仓库");
//        warehouseNames.add("3号仓库");
    }

    OnItemClickListener onFactoryItemClickListener = new OnItemClickListener() {
        @Override
        public void doNext(int positon, String text) {
            popFactory.dismiss();
            btnFactory.setText(text);
            iv1.setImageDrawable(getResources().getDrawable(R.drawable.icon_spinner_arrow));
            if (positon == 0) {
                factory = "";
            } else {
                factory = text;
            }
        }
    };

    OnItemClickListener onWarehouseItemClickListener = new OnItemClickListener() {
        @Override
        public void doNext(int positon, String text) {
            popFactory.dismiss();
            btnWarehouse.setText(text);
            iv2.setImageDrawable(getResources().getDrawable(R.drawable.icon_spinner_arrow));
            if (positon == 0) {
                storage = "";
            } else {
                storage = text;
            }
        }
    };

    @Override
    public void onApiStart(String tag) {
        super.onApiStart(tag);
        showLoadingDlg();
    }

    @Override
    public void onApiSuccess(NetResult res, String tag) {
        super.onApiSuccess(res, tag);
        hideLoadingDlg();
        if (res.isOK()) {
            if ("getTroubleSoftWare".equals(tag)) {
                TroubleBean bean = (TroubleBean) res;
                for (int i = 0; i < bean.getData().size(); i++) {
                    troubleListSoftWare.add(bean.getData().get(i).getName());
                    troubleStateSoftWare.put(i, false);
                    softWareIds.add(bean.getData().get(i).getId());
                }
                adapterTroubleSoftWare.notifyDataSetChanged();
            }
            if ("getTroubleHardWare".equals(tag)) {
                TroubleBean bean = (TroubleBean) res;
                for (int i = 0; i < bean.getData().size(); i++) {
                    troubleListHardWare.add(bean.getData().get(i).getName());
                    troubleStateHardWare.put(i, false);
                    hardWareIds.add(bean.getData().get(i).getId());
                }
                adapterTroubleHardWare.notifyDataSetChanged();
            }
            if ("repair".equals(tag)) {
                UIHelper.t(_activity, "提交成功");
                finish();
            }
            if ("getFactory".equals(tag)) {
                StorageBean bean = (StorageBean) res;
                for (int i = 0; i < bean.getData().size(); i++) {
                    factoryNames.add(bean.getData().get(i).getName());
                }
            }
            if ("getStorage".equals(tag)) {
                StorageBean bean = (StorageBean) res;
                for (int i = 0; i < bean.getData().size(); i++) {
                    warehouseNames.add(bean.getData().get(i).getName());
                }
            }
        }
    }

    @OnClick({R.id.tv_software, R.id.tv_hardware, R.id.iv_back, R.id.btn_complate,
            R.id.btn_scene, R.id.btn_factory, R.id.btn_warehouse})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_software:
                gvReasonSoftware.setVisibility(View.VISIBLE);
                gvReasonHardware.setVisibility(View.GONE);
                setButton(0);
                break;
            case R.id.tv_hardware:
                gvReasonSoftware.setVisibility(View.GONE);
                gvReasonHardware.setVisibility(View.VISIBLE);
                setButton(1);
                break;
            case R.id.btn_complate://提交
                String eid = ac.id;//员工id
                StringBuilder sb = new StringBuilder();
                softIds = new ArrayList<>();
                hardIds = new ArrayList<>();
                sb.append("{");
                for (int i = 0; i < troubleStateSoftWare.size(); i++) {
                    if (troubleStateSoftWare.get(i)) {
                        softIds.add(i);
                        sb.append(softWareIds.get(i) + ":" + troubleListSoftWare.get(i) + ";");
                    }
                }
                for (int i = 0; i < troubleStateHardWare.size(); i++) {
                    if (troubleStateHardWare.get(i)) {
                        hardIds.add(i);
                        sb.append(hardWareIds.get(i) + ":" + troubleListHardWare.get(i) + ";");
                    }
                }
                sb.append("}");
                LogUtils.e(softIds);
//                String parts = "{3:太阳能板}";
                String key = etKey.getText().toString().trim();
                LogUtils.e("key:" + key);
                if ("".equals(key)) {
                    key = etKey.getHint().toString().trim();
                }
                AppUtil.getBikeApiClient(ac).repair(eid, mid, key, type + "", sb.toString(), ac.getProperty("position"), factory, storage, this);
                break;
            case R.id.btn_scene:
                setRepairType(0);
                if(popFactory.isShowing()){
                    popFactory.dismiss();
                }
                break;
            case R.id.btn_factory:
                setRepairType(1);
                popFactory.setData(factoryNames);
                popFactory.setOnItemClickListener(onFactoryItemClickListener);
                if (!popFactory.isShowing()) {
                    popFactory.showAsDropDown(btnFactory);
                } else {
                    popFactory.dismiss();
                    iv1.setImageDrawable(getResources().getDrawable(R.drawable.icon_spinner_arrow));
                }
                break;
            case R.id.btn_warehouse:
                setRepairType(2);
                popFactory.setData(warehouseNames);
                popFactory.setOnItemClickListener(onWarehouseItemClickListener);
                LogUtils.e(popFactory.isShowing());
                if (!popFactory.isShowing()) {
                    popFactory.showAsDropDown(btnWarehouse);
                } else {
                    popFactory.dismiss();
                    iv2.setImageDrawable(getResources().getDrawable(R.drawable.icon_spinner_arrow));
                }
                break;
        }
    }

    private void setRepairType(int position) {
        if (position == 0) {
            type = "1";
            iv1.setImageDrawable(getResources().getDrawable(R.drawable.icon_spinner_arrow));
            iv2.setImageDrawable(getResources().getDrawable(R.drawable.icon_spinner_arrow));
            btnWarehouse.setText("送仓维修");
            btnFactory.setText("返厂维修");
            btnScene.setBackgroundResource(R.drawable.bg_5radiu_blue);
            btnFactory.setBackgroundResource(R.drawable.bg_3radiu_gray);
            btnWarehouse.setBackgroundResource(R.drawable.bg_3radiu_gray);
            btnScene.setTextColor(Color.parseColor("#FFFFFF"));
            btnFactory.setTextColor(Color.parseColor("#1D1D1D"));
            btnWarehouse.setTextColor(Color.parseColor("#1D1D1D"));
        }
        if (position == 1) {
            type = "2";
            iv1.setImageDrawable(getResources().getDrawable(R.drawable.icon_spinner_arrow_up));
            iv2.setImageDrawable(getResources().getDrawable(R.drawable.icon_spinner_arrow));
            btnWarehouse.setText("送仓维修");
            btnScene.setBackgroundResource(R.drawable.bg_3radiu_gray);
            btnFactory.setBackgroundResource(R.drawable.bg_5radiu_blue);
            btnWarehouse.setBackgroundResource(R.drawable.bg_3radiu_gray);
            btnScene.setTextColor(Color.parseColor("#1D1D1D"));
            btnFactory.setTextColor(Color.parseColor("#FFFFFF"));
            btnWarehouse.setTextColor(Color.parseColor("#1D1D1D"));
        }
        if (position == 2) {
            type = "3";
            iv1.setImageDrawable(getResources().getDrawable(R.drawable.icon_spinner_arrow));
            iv2.setImageDrawable(getResources().getDrawable(R.drawable.icon_spinner_arrow_up));
            btnFactory.setText("返厂维修");
            btnScene.setBackgroundResource(R.drawable.bg_3radiu_gray);
            btnFactory.setBackgroundResource(R.drawable.bg_3radiu_gray);
            btnWarehouse.setBackgroundResource(R.drawable.bg_5radiu_blue);
            btnScene.setTextColor(Color.parseColor("#1D1D1D"));
            btnFactory.setTextColor(Color.parseColor("#1D1D1D"));
            btnWarehouse.setTextColor(Color.parseColor("#FFFFFF"));
        }
    }

    private void setButton(int position) {
        if (position == 0) {
            tvSoftware.setTextColor(Color.parseColor("#FFFFFF"));
            tvSoftware.setBackgroundResource(R.drawable.bg_16radiu_blue);
            tvSoftware.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(R.drawable.icon_up), null);
            tvHardware.setTextColor(Color.parseColor("#ABABAA"));
            tvHardware.setBackgroundResource(R.drawable.bg_16stroke_blue);
            tvHardware.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(R.drawable.icon_down), null);
        } else {
            tvSoftware.setTextColor(Color.parseColor("#ABABAA"));
            tvSoftware.setBackgroundResource(R.drawable.bg_16stroke_blue);
            tvSoftware.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(R.drawable.icon_down), null);
            tvHardware.setTextColor(Color.parseColor("#FFFFFF"));
            tvHardware.setBackgroundResource(R.drawable.bg_16radiu_blue);
            tvHardware.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(R.drawable.icon_up), null);
        }
    }
}
