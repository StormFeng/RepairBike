package com.lida.repairbike.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.widget.Toast;

import com.apkfuns.logutils.LogUtils;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.fastaccess.permission.base.PermissionHelper;
import com.fastaccess.permission.base.callback.OnPermissionCallback;
import com.lida.repairbike.R;
import com.lida.repairbike.fragment.FragmentHome;
import com.lida.repairbike.fragment.FragmentPersonal;
import com.lida.repairbike.widget.GradientTabStrip;
import com.lida.repairbike.widget.GradientTabStripAdapter;
import com.midian.base.base.BaseFragmentActivity;
import com.midian.base.util.UIHelper;

import java.util.ArrayList;
import java.util.List;

import am.widget.basetabstrip.BaseTabStrip;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/3/16.
 */

public class Activity_Main extends BaseFragmentActivity implements BaseTabStrip.OnItemClickListener ,OnPermissionCallback{

    @BindView(R.id.gts_vp_fragments)
    ViewPager vpFragments;
    @BindView(R.id.gradientTabStrip)
    GradientTabStrip tabStrip;

    private GradientTabStripAdapter adapter;
    private List<Fragment> fragments = new ArrayList<>();
    private PermissionHelper permissionHelper;

    private long waitTime = 2000;
    private long touchTime = 0;

    private LocationClient mLocationClient = null;
    private BDLocationListener myListener = new Activity_Main.MyLocationListener();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
        permissionHelper = PermissionHelper.getInstance(_activity);
        permissionHelper.requestAfterExplanation(Manifest.permission.ACCESS_COARSE_LOCATION);
    }

    private void initView() {
        vpFragments.setOffscreenPageLimit(2);
        fragments.add(new FragmentHome());
        fragments.add(new FragmentPersonal());
        adapter = new GradientTabStripAdapter(getSupportFragmentManager(),fragments);
        vpFragments.setAdapter(adapter);
        tabStrip.setAdapter(adapter);
        tabStrip.bindViewPager(vpFragments);
        tabStrip.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(int position) {

    }

    @Override
    public void onSelectedClick(int position) {

    }

    @Override
    public void onDoubleClick(int position) {

    }

    private void initLocation(){
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        //可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");
        //可选，默认gcj02，设置返回的定位结果坐标系
        int span=5000;
        option.setScanSpan(span);
        //可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);
        //可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);
        //可选，默认false,设置是否使用gps
        option.setIsNeedLocationDescribe(true);
        //可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        mLocationClient.setLocOption(option);
        mLocationClient.start();
    }

    class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            if(bdLocation==null){
                return;
            }
            LogUtils.e(bdLocation.getLocationDescribe());
            ac.setProperty("position",bdLocation.getLocationDescribe());
        }

        @Override
        public void onConnectHotSpotMessage(String s, int i) {
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            if (event.getAction() == KeyEvent.ACTION_DOWN && KeyEvent.KEYCODE_BACK == keyCode) {
                long currentTime = System.currentTimeMillis();
                if ((currentTime - touchTime) >= waitTime) {
                    Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
                    touchTime = currentTime;
                } else {
                    finish();
                }
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        permissionHelper.onRequestPermissionsResult(requestCode,permissions,grantResults);
    }

    @Override
    public void onPermissionGranted(@NonNull String[] permissionName) {
        mLocationClient = new LocationClient(getApplicationContext());
        mLocationClient.registerLocationListener(myListener);
        initLocation();
    }

    @Override
    public void onPermissionDeclined(@NonNull String[] permissionName) {
        UIHelper.t(_activity,"请在设置中为"+getString(R.string.app_name)+"开启定位权限");
    }

    @Override
    public void onPermissionPreGranted(@NonNull String permissionsName) {
        mLocationClient = new LocationClient(getApplicationContext());
        mLocationClient.registerLocationListener(myListener);
        initLocation();
    }

    @Override
    public void onPermissionNeedExplanation(@NonNull String permissionName) {

    }

    @Override
    public void onPermissionReallyDeclined(@NonNull String permissionName) {
    }

    @Override
    public void onNoPermissionNeeded() {

    }
}
