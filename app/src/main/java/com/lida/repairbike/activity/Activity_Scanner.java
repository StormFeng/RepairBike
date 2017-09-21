package com.lida.repairbike.activity;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.apkfuns.logutils.LogUtils;
import com.fastaccess.permission.base.PermissionHelper;
import com.fastaccess.permission.base.callback.OnPermissionCallback;
import com.lida.repairbike.R;
import com.lida.repairbike.bean.BikeStateBean;
import com.lida.repairbike.util.AppUtil;
import com.midian.base.api.ApiCallback;
import com.midian.base.base.BaseFragmentActivity;
import com.midian.base.bean.NetResult;
import com.midian.base.util.Func;
import com.midian.base.util.UIHelper;
import com.uuzuche.lib_zxing.activity.CaptureFragment;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 扫码页
 * Created by Administrator on 2017/3/7.
 */

public class Activity_Scanner extends BaseFragmentActivity implements OnPermissionCallback{

    @BindView(R.id.tv_bikeNum)
    TextView tvBikeNum;
    @BindView(R.id.tv_open_light)
    TextView tvOpenLight;
    @BindView(R.id.ll_control)
    LinearLayout llControl;
    @BindView(R.id.iv_back)
    ImageView ivBack;

    private CaptureFragment captureFragment;
    public static boolean isOpen = false;
    private int REQUEST_CODE = 1001;
    private String flag;//判断上一个activity

    private PermissionHelper permissionHelper;
    private String mid="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);
        ButterKnife.bind(this);
        permissionHelper = PermissionHelper.getInstance(_activity);
        permissionHelper.requestAfterExplanation(Manifest.permission.CAMERA);
    }

    private void getAppDetailSettingIntent(){
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if(Build.VERSION.SDK_INT >= 9){
            intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            intent.setData(Uri.fromParts("package", getPackageName(), null));
        } else if(Build.VERSION.SDK_INT <= 8){
            intent.setAction(Intent.ACTION_VIEW);
            intent.setClassName("com.android.settings","com.android.settings.InstalledAppDetails");
            intent.putExtra("com.android.settings.ApplicationPkgName", getPackageName());
        }
        startActivity(intent);
    }


    View.OnClickListener listener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(R.id.btn_ok == v.getId()){
                String mid = captureFragment.etNumber.getText().toString().trim();
                if(10 > mid.length()){
                    UIHelper.t(_activity,"请输入正确的单车号");
                    return;
                }else{
                    mid = mid.substring(0,10);
                }
//                LogUtils.e(mid);

//                Bundle bundle=new Bundle();
//                bundle.putString("bikeId",mid);
//                UIHelper.jump(_activity,Activity_RepairPoint.class,bundle);
                if("check".equals(flag)){
                    Bundle bundle=new Bundle();
                    bundle.putString("mid",mid);
                    UIHelper.jump(_activity,Activity_CheckPoint.class,bundle);
                    finish();
                }else if("repair".equals(flag)){
                    AppUtil.getBikeApiClient(ac).getRepair(mid,apiCallback);
                }
            }
        }
    };

    ApiCallback apiCallback = new ApiCallback() {
        @Override
        public void onApiStart(String tag) {
            showLoadingDlg();
        }

        @Override
        public void onApiLoading(long count, long current, String tag) {

        }

        @Override
        public void onApiSuccess(NetResult res, String tag) {
            hideLoadingDlg();
            if(res.isOK()){
                BikeStateBean bean = (BikeStateBean) res;
                if("1".equals(bean.getData().getStaus())){
                    Bundle bundle = new Bundle();
                    bundle.putString("bikeId",bean.getData().getMid());
                    bundle.putString("key",bean.getData().getLockid());
                    bundle.putString("trouble",bean.getData().getTrouble());
                    bundle.putString("id",bean.getData().getId());
                    UIHelper.jump(_activity,Activity_RepairPoint.class,bundle);
                    finish();
                }else{
                    UIHelper.t(_activity,"该车辆已完成维修");
                }
                finish();
            }else if(res.noData()){
                Bundle bundle=new Bundle();
                if("check".equals(flag)){
                    bundle.putString("mid",mid);
                    UIHelper.jump(_activity,Activity_CheckPoint.class,bundle);
                    finish();
                }else if("repair".equals(flag)){
                    if("".equals(mid)){
                        mid=captureFragment.etNumber.getText().toString();
                    }
                    bundle.putString("bikeId",mid);
                    bundle.putString("key",mid);
                    bundle.putString("trouble","");
                    bundle.putString("id","");
                    UIHelper.jump(_activity,Activity_RepairPoint.class,bundle);
                    finish();
                }
            }else{
                ac.handleErrorCode(_activity,res.getStatus());
            }
        }

        @Override
        public void onApiFailure(Throwable t, int errorNo, String strMsg, String tag) {
            hideLoadingDlg();
        }

        @Override
        public void onParseError(String tag) {
            hideLoadingDlg();
        }
    };


    CodeUtils.AnalyzeCallback analyzeCallback = new CodeUtils.AnalyzeCallback() {
        @Override
        public void onAnalyzeSuccess(Bitmap mBitmap, String result) {
//            Intent resultIntent = new Intent();
//            Bundle bundle = new Bundle();
//            bundle.putInt(CodeUtils.RESULT_TYPE, CodeUtils.RESULT_SUCCESS);
//            bundle.putString(CodeUtils.RESULT_STRING, result);
//            resultIntent.putExtras(bundle);
//            LogUtils.e(result);
//            Toast.makeText(_activity,result,Toast.LENGTH_LONG).show();
            mid = Func.getNumberFromString(result);
            if(mid.length()>10){
                mid = mid.substring(0, 10);
            }
            LogUtils.e(mid);
            AppUtil.getBikeApiClient(ac).getRepair(mid,apiCallback);
        }

        @Override
        public void onAnalyzeFailed() {
            Intent resultIntent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putInt(CodeUtils.RESULT_TYPE, CodeUtils.RESULT_FAILED);
            bundle.putString(CodeUtils.RESULT_STRING, "");
            resultIntent.putExtras(bundle);
            UIHelper.t(_activity,"解析二维码失败");
//            SecondActivity.this.setResult(RESULT_OK, resultIntent);
//            SecondActivity.this.finish();
        }
    };

    @OnClick({R.id.iv_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
        }
    }

    View.OnClickListener buttonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(v.getId() == R.id.tv_bikeNum){
                captureFragment.btnOk.setOnClickListener(listener);
                captureFragment.etNumber.setPasswordLength(10);
                llControl.setVisibility(View.GONE);
                captureFragment.viewfinderView.setVisibility(View.GONE);
                captureFragment.tvNotice.setVisibility(View.GONE);
                captureFragment.rlNumber.setVisibility(View.VISIBLE);
            }else if(v.getId()==R.id.tv_open_light){
                if (!isOpen) {
                    CodeUtils.isLightEnable(true);
                    isOpen = true;
                } else {
                    CodeUtils.isLightEnable(false);
                    isOpen = false;
                }
            }
        }
    };


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        permissionHelper.onRequestPermissionsResult(requestCode,permissions,grantResults);
//        if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
//            flag = mBundle.getString("flag");
//            captureFragment = new CaptureFragment();
//            CodeUtils.setFragmentArgs(captureFragment, R.layout.my_camera);
//            captureFragment.setAnalyzeCallback(analyzeCallback);
//            getSupportFragmentManager().beginTransaction().replace(R.id.framelayout, captureFragment).commit();
//        }else{
//            UIHelper.t(_activity,"请允许"+getString(R.string.app_name)+"使用摄像头权限");
//            finish();
//            getAppDetailSettingIntent();
//        }
    }

    @Override
    public void onPermissionGranted(@NonNull String[] permissionName) {
        LogUtils.e("onPermissionGranted");
        flag = mBundle.getString("flag");
        captureFragment = new CaptureFragment();
        CodeUtils.setFragmentArgs(captureFragment, R.layout.my_camera);
        captureFragment.setAnalyzeCallback(analyzeCallback);
        getSupportFragmentManager().beginTransaction().replace(R.id.framelayout, captureFragment).commitAllowingStateLoss();
        tvBikeNum.setOnClickListener(buttonListener);
        tvOpenLight.setOnClickListener(buttonListener);
    }

    @Override
    public void onPermissionDeclined(@NonNull String[] permissionName) {
        LogUtils.e("onPermissionDeclined");
        UIHelper.t(_activity,"请在设置中为"+getString(R.string.app_name)+"开启摄像头权限");

    }

    @Override
    public void onPermissionPreGranted(@NonNull String permissionsName) {
        flag = mBundle.getString("flag");
        captureFragment = new CaptureFragment();
        CodeUtils.setFragmentArgs(captureFragment, R.layout.my_camera);
        captureFragment.setAnalyzeCallback(analyzeCallback);
        getSupportFragmentManager().beginTransaction().replace(R.id.framelayout, captureFragment).commit();
        tvBikeNum.setOnClickListener(buttonListener);
        tvOpenLight.setOnClickListener(buttonListener);
    }

    @Override
    public void onPermissionNeedExplanation(@NonNull String permissionName) {

    }

    @Override
    public void onPermissionReallyDeclined(@NonNull String permissionName) {
        UIHelper.t(_activity,"请在设置中为"+getString(R.string.app_name)+"开启摄像头权限");
    }

    @Override
    public void onNoPermissionNeeded() {

    }
}
