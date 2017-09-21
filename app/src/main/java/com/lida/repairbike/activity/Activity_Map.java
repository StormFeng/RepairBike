package com.lida.repairbike.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;

import com.apkfuns.logutils.LogUtils;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.lida.repairbike.R;
import com.midian.base.base.BaseActivity;
import com.midian.base.util.UIHelper;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/3/8.
 */

public class Activity_Map extends BaseActivity {
    @BindView(R.id.mapview)
    MapView mMapView;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.iv_scan)
    ImageView ivScan;

    private LocationClient mLocationClient = null;
    private BaiduMap mBaiduMap = null;
    private BDLocationListener myListener = new MyLocationListener();
    private boolean isFristLocation=true;
    private LatLng myLatLng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        ButterKnife.bind(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(_activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1001);
            } else {

            }
        }
        mBaiduMap = mMapView.getMap();
        mBaiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(new MapStatus.Builder().zoom(15).build()));
        mLocationClient = new LocationClient(getApplicationContext());
        mLocationClient.registerLocationListener(myListener);
        initLocation();
        findViewById(R.id.btn_location).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MapStatusUpdate u= MapStatusUpdateFactory.newLatLng(myLatLng);
                mBaiduMap.animateMapStatus(u);
            }
        });
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

    class MyLocationListener implements BDLocationListener{

        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            if(bdLocation==null || mMapView == null){
                return;
            }
            mBaiduMap.setMyLocationEnabled(true);
            MyLocationData locData=new MyLocationData.Builder()
                    .accuracy(bdLocation.getRadius())
                    .latitude(bdLocation.getLatitude())
                    .longitude(bdLocation.getLongitude())
                    .build();
            mBaiduMap.setMyLocationData(locData);
            BitmapDescriptor mMyPosition = BitmapDescriptorFactory.fromResource(R.drawable.icon_marker);
            MyLocationConfiguration configuration = new MyLocationConfiguration(MyLocationConfiguration.LocationMode.NORMAL,true,mMyPosition);
            mBaiduMap.setMyLocationConfigeration(configuration);
            myLatLng = new LatLng(bdLocation.getLatitude(), bdLocation.getLongitude());
            LogUtils.e(bdLocation.getLocationDescribe());
            ac.setProperty("position",bdLocation.getLocationDescribe());
            if(isFristLocation){
                isFristLocation=false;
                MapStatusUpdate u= MapStatusUpdateFactory.newLatLng(myLatLng);
                mBaiduMap.animateMapStatus(u);
            }
        }

        @Override
        public void onConnectHotSpotMessage(String s, int i) {
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @OnClick({R.id.iv_back, R.id.iv_scan})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_scan:
                Bundle bundle=new Bundle();
                bundle.putString("flag","repair");
                UIHelper.jump(_activity,Activity_Scanner.class,bundle);
                break;
        }
    }
}
