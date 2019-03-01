package xicheapp.app.mdb.android.xiche.fuwu;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapOptions;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;

import xicheapp.app.mdb.android.xiche.BaseActivity;
import xicheapp.app.mdb.android.xiche.MainActivity;
import xicheapp.app.mdb.android.xiche.R;
import xicheapp.app.mdb.android.xiche.Utils.ToastUtils;

import static com.amap.api.location.AMapLocationClientOption.AMapLocationMode.Hight_Accuracy;

public class ShowMapActivity extends BaseActivity {
    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //声明mLocationOption对象
    public AMapLocationClientOption mLocationOption = null;
    private double lat;
    private double lon;
    private MapView mapView;
    private AMap aMap;//地图控制器对象
    private UiSettings mUiSettings;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_map);
        //初始化定位
        mLocationClient = new AMapLocationClient(getApplicationContext());
        //设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener);//设置其为定位完成后的回调函数
        mapView = (MapView) findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        init();
    }

    /**
     * * 初始化AMap类对象 aMap 地图控制器
     */
    private void init() {
        if (aMap == null) {
            aMap = mapView.getMap();//地图控制器对象
            mUiSettings = aMap.getUiSettings();
        }
        //设置logo位置
        mUiSettings.setLogoPosition(AMapOptions.LOGO_POSITION_BOTTOM_CENTER);//高德地图标志的摆放位置
        mUiSettings.setZoomControlsEnabled(true);//地图缩放控件是否可见
        aMap.moveCamera(CameraUpdateFactory.zoomTo(12));
        mUiSettings.setZoomPosition(AMapOptions.ZOOM_POSITION_RIGHT_BUTTOM);//地图缩放控件的摆放位置
        //aMap  为地图控制器对象
        aMap.getUiSettings().setMyLocationButtonEnabled(true);//地图的定位标志是否可见
        aMap.setMyLocationEnabled(true);//地图定位标志是否可以点击

        //判断是否添加定位权限
        //这里以ACCESS_COARSE_LOCATION为例
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            //申请WRITE_EXTERNAL_STORAGE权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    1);//自定义的code
        }




    }

    /**
     * 授权提示回调
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //可在此继续其他操作。
        if (requestCode == 1) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                ToastUtils.shortToast("定位权限已申请");
                setUpMap();
            } else {
                ToastUtils.shortToast("定位权限已拒绝");
            }
        }

    }

    /**
     * 配置定位参数
     */
    private void setUpMap() {

        //初始化定位参数
        mLocationOption = new AMapLocationClientOption();

        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(Hight_Accuracy);

        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);

        //设置是否只定位一次,默认为false
        mLocationOption.setOnceLocation(true);


        //设置是否允许模拟位置,默认为false，不允许模拟位置
        mLocationOption.setMockEnable(false);


        //设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setInterval(2000);

        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);

        //启动定位
        mLocationClient.startLocation();
    }

    public AMapLocationListener mLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation amapLocation) {
            if (amapLocation != null) {

                if (amapLocation.getErrorCode() == 0) {

                    Log.v("getLocationType", ""+amapLocation.getLocationType() ) ;
                    lat = amapLocation.getLatitude();
                    lon = amapLocation.getLongitude();

                    Log.v("getAccuracy", ""+amapLocation.getAccuracy()+" 米");//获取精度信息
                    Log.v("joe", "lat :-- " + lat + " lon :--" + lon);
                    Log.v("joe", "Country : " + amapLocation.getCountry() + " province : " + amapLocation.getProvince() + " City : " + amapLocation.getCity() + " District : " + amapLocation.getDistrict());
                    //清空缓存位置
                    aMap.clear();


                    // 设置显示的焦点，即当前地图显示为当前位置
                    aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lon), 18));
                    //aMap.moveCamera(CameraUpdateFactory.zoomTo(18));
                    //aMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(lat, lon)));


                    MarkerOptions markerOptions = new MarkerOptions();
                    markerOptions.position(new LatLng(lat, lon));
                    markerOptions.title("我的位置");
                    markerOptions.visible(true);
                    BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.dingwei));
                    markerOptions.icon(bitmapDescriptor);
                    markerOptions.draggable(true);
                    Marker marker = aMap.addMarker(markerOptions);
                    marker.showInfoWindow();
                } else {
                    //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                    Log.e("joe", "location Error, ErrCode:"
                            + amapLocation.getErrorCode() + ", errInfo:"
                            + amapLocation.getErrorInfo());
                }
            }
        }
    };


    /**
     * 重新绘制加载地图
     */
    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    /**
     * 暂停地图的绘制
     */
    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }


    /**
     * 保存地图当前的状态方法必须重写
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    /**
     * 销毁地图
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
        if (mLocationClient != null) {
            mLocationClient.stopLocation();
            mLocationClient.onDestroy();
        }
        mLocationClient = null;
    }
}
