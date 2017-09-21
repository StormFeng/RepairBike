package com.lida.repairbike.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.apkfuns.logutils.LogUtils;
import com.lida.repairbike.R;
import com.lida.repairbike.adapter.AdapterGridViewPart;
import com.lida.repairbike.adapter.AdapterGridViewReason;
import com.lida.repairbike.bean.PartsBean;
import com.lida.repairbike.bean.TroubleBean;
import com.lida.repairbike.util.AppUtil;
import com.lida.repairbike.widget.AddDialog;
import com.lida.repairbike.widget.InnerGridView;
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
 * 故障维修详情、原因
 * Created by Administrator on 2017/3/6 0006.
 */

public class Activity_RepairPoint extends BaseActivity {
    @BindView(R.id.tv_software)
    TextView tvSoftware;
    @BindView(R.id.tv_hardware)
    TextView tvHardware;
    @BindView(R.id.tv_generation)
    TextView tvGeneration;
    @BindView(R.id.tv_two_generation)
    TextView tvTwoGeneration;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.textView2)
    TextView textView2;
    @BindView(R.id.gv_reason_software)
    InnerGridView gvReasonSoftware;
    @BindView(R.id.gv_reason_hardware)
    InnerGridView gvReasonHardware;
    @BindView(R.id.gv_parts_one)
    InnerGridView gvPartsOne;
    @BindView(R.id.gv_parts_two)
    InnerGridView gvPartsTwo;
    @BindView(R.id.btn_complate)
    Button btnComplate;
    @BindView(R.id.tv_bikeId)
    TextView tvBikeId;
    @BindView(R.id.et_key)
    EditText etKey;
//    @BindView(R.id.iv_partnumber)
//    ImageView ivPartnumber;
//    @BindView(R.id.tv_Number)
//    TextView tvNumber;

    private List<String> troubleListSoftWare = new ArrayList<>();//软件故障
    private Map<Integer, Boolean> troubleStateSoftWare = new HashMap<>();//软件故障选中状态
    private AdapterGridViewReason adapterTroubleSoftWare;//软件故障
    private List<String> softWareIds = new ArrayList<>();

    private List<String> troubleListHardWare = new ArrayList<>();//硬件故障
    private Map<Integer, Boolean> troubleStateHardWare = new HashMap<>();//硬件故障选中状态
    private AdapterGridViewReason adapterTroubleHardWare;//硬件故障
    private List<String> hardWareIds = new ArrayList<>();

    private List<String> partsListOne = new ArrayList<>();//配件
    private AdapterGridViewPart adapterPartsOne;//配件
    private Map<Integer, Boolean> partsStateOne = new HashMap<>();//故障选中状态
    private List<String> partOneIds = new ArrayList<>();

//    private ArrayList<String> partIds = new ArrayList<>();//将选中的配件信息传递到下一个页面
//    private ArrayList<String> partNames = new ArrayList<>();

    private String bikeId;
    private String key;
    private String trouble = "";
    private String id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repairpoint);
        ButterKnife.bind(this);
        bikeId = mBundle.getString("bikeId");
        key = mBundle.getString("key");
        trouble = mBundle.getString("trouble");
        id = mBundle.getString("id");
        tvBikeId.setText(bikeId);
        etKey.setHint(key);
        initView();
//        softIds = mBundle.getIntegerArrayList("softIds");
//        hardIds = mBundle.getIntegerArrayList("hardIds");

    }

    private void initView() {
        adapterTroubleSoftWare = new AdapterGridViewReason(_activity, troubleListSoftWare, troubleStateSoftWare);
        adapterTroubleHardWare = new AdapterGridViewReason(_activity, troubleListHardWare, troubleStateHardWare);

        adapterPartsOne = new AdapterGridViewPart(_activity, partsListOne, partsStateOne);
//        adapterPartsTwo = new AdapterGridViewPart(_activity, partsListTwo, partsStateTwo, tvNumber);
        AppUtil.getBikeApiClient(ac).getTroubleSoftWare("1", this);//软件故障
        AppUtil.getBikeApiClient(ac).getTroubleHardWare("2", this);//硬件故障
        AppUtil.getBikeApiClient(ac).getPart(this);//一代配件
//        AppUtil.getBikeApiClient(ac).getPartTwo("2", this);//二代配件
        gvReasonSoftware.setAdapter(adapterTroubleSoftWare);
        gvReasonHardware.setAdapter(adapterTroubleHardWare);
        gvPartsOne.setAdapter(adapterPartsOne);
//        gvPartsTwo.setAdapter(adapterPartsTwo);

    }

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
                    softWareIds.add(bean.getData().get(i).getId());
                    if (trouble.contains(troubleListSoftWare.get(i))) {
                        troubleStateSoftWare.put(i, true);
                    } else {
                        troubleStateSoftWare.put(i, false);
                    }
                }
                adapterTroubleSoftWare.notifyDataSetChanged();
            }

            if ("getTroubleHardWare".equals(tag)) {
                TroubleBean bean = (TroubleBean) res;
                for (int i = 0; i < bean.getData().size(); i++) {
                    troubleListHardWare.add(bean.getData().get(i).getName());
                    hardWareIds.add(bean.getData().get(i).getId());
                    if (trouble.contains(troubleListHardWare.get(i))) {
                        troubleStateHardWare.put(i, true);
                    } else {
                        troubleStateHardWare.put(i, false);
                    }
                }
                adapterTroubleHardWare.notifyDataSetChanged();
            }

            if ("getPart".equals(tag)) {
                PartsBean bean = (PartsBean) res;
                for (int i = 0; i < bean.getData().size(); i++) {
                    partsListOne.add(bean.getData().get(i).getName());
                    partsStateOne.put(i, false);
                    partOneIds.add(bean.getData().get(i).getId());
                }
                adapterPartsOne.notifyDataSetChanged();
            }
            if("submitRepair".equals(tag)){
                UIHelper.t(_activity,"维修完成");
                finish();
            }
        }
    }

    @OnClick({R.id.tv_software, R.id.tv_hardware, R.id.iv_back, R.id.tv_generation,
            R.id.tv_two_generation,  R.id.btn_complate})
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
            case R.id.btn_complate:
                StringBuilder sbTrouble = new StringBuilder();
                sbTrouble.append("{");
                for (int i = 0; i < troubleStateSoftWare.size(); i++) {
                    if (troubleStateSoftWare.get(i)) {
                        sbTrouble.append(softWareIds.get(i) + ":" + troubleListSoftWare.get(i) + ";");
                    }
                }
                for (int i = 0; i < troubleStateHardWare.size(); i++) {
                    if (troubleStateHardWare.get(i)) {
                        sbTrouble.append(hardWareIds.get(i) + ":" + troubleListHardWare.get(i) + ";");
                    }
                }
//                partIds.clear();
//                partNames.clear();
                StringBuilder sbPart = new StringBuilder();
                sbPart.append("{");
                for (int i = 0; i < partsStateOne.size(); i++) {
                    if (partsStateOne.get(i)) {
                        sbPart.append("\""+partOneIds.get(i)+"\":1,");
//                        partIds.add(partOneIds.get(i));
//                        partNames.add(partsListOne.get(i));
                    }
                }
                sbTrouble.append("}");
                String substring = sbPart.substring(0, sbPart.length()-1);
                substring+="}";

//                Bundle bundle = new Bundle();
//                bundle.putString("bikeId", bikeId);
//                bundle.putString("trouble", sbTrouble.toString());
//                bundle.putStringArrayList("partIds", partIds);
//                bundle.putStringArrayList("partNames", partNames);
//                UIHelper.jumpForResult(_activity, Activity_PartNumber.class, bundle, 1001);
                LogUtils.e(sbPart.toString());
                LogUtils.e(sbTrouble.toString());
                AppUtil.getBikeApiClient(ac).submitRepair(ac.id,bikeId,sbTrouble.toString(),substring,ac.getProperty("position"),id,this);
                break;
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
