package xicheapp.app.mdb.android.xiche.fuwu;


import android.Manifest;
import android.app.ActionBar;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapOptions;
import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;
import xicheapp.app.mdb.android.xiche.MainActivity;
import xicheapp.app.mdb.android.xiche.R;
import xicheapp.app.mdb.android.xiche.Utils.Api;
import xicheapp.app.mdb.android.xiche.Utils.BottomSheetPop;
import xicheapp.app.mdb.android.xiche.Utils.ConvertUtil;
import xicheapp.app.mdb.android.xiche.Utils.IsGpsWork;
import xicheapp.app.mdb.android.xiche.Utils.MyDialog;
import xicheapp.app.mdb.android.xiche.Utils.MyDialog2;
import xicheapp.app.mdb.android.xiche.Utils.QuxiaoyuanyinDialog;
import xicheapp.app.mdb.android.xiche.Utils.SharedPreferencesHelper;
import xicheapp.app.mdb.android.xiche.Utils.ToastUtils;
import xicheapp.app.mdb.android.xiche.bean.CurrentOrderBean;
import xicheapp.app.mdb.android.xiche.bean.NextOrderBean;
import xicheapp.app.mdb.android.xiche.bean.PaiduiNumBean;
import xicheapp.app.mdb.android.xiche.bean.TongyongBean;
import xicheapp.app.mdb.android.xiche.login.ShenfenyanzhengActivity;

import static android.content.Context.NOTIFICATION_SERVICE;
import static com.amap.api.location.AMapLocationClientOption.AMapLocationMode.Hight_Accuracy;
import static xicheapp.app.mdb.android.xiche.login.LoginActivity.dizhi;

/**
 * 服务页面
 * A simple {@link Fragment} subclass.
 */
public class FuwuFragment extends Fragment implements View.OnClickListener {
    /**
     * 没有完成认证展示的 no
     * 认证成功展示 yes
     * 点击停止接单 ok
     */
    @BindView(R.id.fuwu_yes)
    ScrollView scrollView_yes;
    @BindView(R.id.fuwu_ok)
    LinearLayout ll_ok;
    public static int zhi=0,zhuangtaizhi=0,bodadianhua=0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_fuwu, container, false);
        ButterKnife.bind(this,view);
        return view;
    }

    Activity activity;

    Context context;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
    }

    SharedPreferencesHelper sharedPreferencesHelper;
    @BindView(R.id.weishenhe)
    TextView tv_weishenhe;
    @BindView(R.id.fuwu_ok_info)
    TextView tv_fuwuok_info;
    //暂无接单时候的提示信息
    @BindView(R.id.fuwuuse_1)
    LinearLayout linearLayout1;
    @BindView(R.id.fuwuuse_2)
    LinearLayout linearLayout2;
    @BindView(R.id.fuwuuse_3)
    LinearLayout linearLayout3;
    @BindView(R.id.fuwuuse_4)
    LinearLayout linearLayout4;
    @BindView(R.id.fuwuuse_5)
    LinearLayout linearLayout5;
    @BindView(R.id.fuwuuse_5_info)
    TextView tv_fuwuuse_5_info;
    @BindView(R.id.fuwus_daohang)
    ImageView img_daohang;


    protected Button mNavigationBtn;

    private BottomSheetPop mBottomSheetPop;

    private View mapSheetView;

    private Button mBaiduBtn;
    private Button mGaodeBtn;
    private Button mTencentBtn;
    private Button mCancel2Btn;


    private TextToSpeech textToSpeech = null;//创建自带语音对象

    @BindView(R.id.fuwu_guize)
    LinearLayout ll_guize;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initTTS();

        sharedPreferencesHelper = new SharedPreferencesHelper(activity,"xicheqishou");
        if (sharedPreferencesHelper.getSharedPreference("status","").toString().equals("1")){
            tv_weishenhe.setVisibility(View.VISIBLE);
        }else {
            tv_weishenhe.setVisibility(View.GONE);

            if (dizhi==1){
                //初始化定位
                mLocationClient = new AMapLocationClient(context.getApplicationContext());
                //设置定位回调监听
                mLocationClient.setLocationListener(mLocationListener);//设置其为定位完成后的回调函数
                mapView = (MapView) view.findViewById(R.id.fuwu_map);
                mapView.onCreate(savedInstanceState);
                init();
                getlonlat();
            }
            mapSheetView = LayoutInflater.from(context).inflate(R.layout.map_navagation_sheet, null);
            mBaiduBtn = mapSheetView.findViewById(R.id.baidu_btn);
            mGaodeBtn = mapSheetView.findViewById(R.id.gaode_btn);
            mTencentBtn = mapSheetView.findViewById(R.id.tencent_btn);
            mCancel2Btn = mapSheetView.findViewById(R.id.cancel_btn2);

            img_daohang.setOnClickListener(this);
            mBaiduBtn.setOnClickListener(this);
            mGaodeBtn.setOnClickListener(this);
            mTencentBtn.setOnClickListener(this);
            mCancel2Btn.setOnClickListener(this);

            //判断是否开启了接单 0未开启接单 1开启 接单
            if (sharedPreferencesHelper.getSharedPreference("isreceive","").toString().equals("0")){
                Log.e("未开启","0");
                tv_state.setText("开始接单");
                tv_fuwuuse_5_info.setText("暂未开启接单，请开启接单模式");
                linearLayout1.setVisibility(View.GONE);
                linearLayout2.setVisibility(View.GONE);
                linearLayout3.setVisibility(View.GONE);
                linearLayout4.setVisibility(View.GONE);
                linearLayout5.setVisibility(View.VISIBLE);
                ll_1.setVisibility(View.GONE);
                ll_2.setVisibility(View.GONE);
                ll_guize.setVisibility(View.VISIBLE);
            }else {
                Log.e("开启","1");
                tv_state.setText("停止接单");
                ll_guize.setVisibility(View.GONE);
//                linearLayout1.setVisibility(View.VISIBLE);
//                linearLayout2.setVisibility(View.VISIBLE);
//                linearLayout3.setVisibility(View.VISIBLE);
//                linearLayout4.setVisibility(View.VISIBLE);
//                linearLayout5.setVisibility(View.GONE);
//                ll_1.setVisibility(View.VISIBLE);
//                ll_2.setVisibility(View.VISIBLE);
                //显示传入坐标，然后在请求信息

            }
            setinfo();
            show2();


//            //导航
//            img_daohang.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                }
//            });

        }


    }

    private void initTTS() {
        //实例化自带语音对象
        textToSpeech = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == textToSpeech.SUCCESS) {
                    // Toast.makeText(MainActivity.this,"成功输出语音",
                    // Toast.LENGTH_SHORT).show();
                    // Locale loc1=new Locale("us");
                    // Locale loc2=new Locale("china");

                    textToSpeech.setPitch(1.0f);//方法用来控制音调
                    textToSpeech.setSpeechRate(1.0f);//用来控制语速

                    //判断是否支持下面两种语言
                    int result1 = textToSpeech.setLanguage(Locale.US);
                    int result2 = textToSpeech.setLanguage(Locale.
                            SIMPLIFIED_CHINESE);
                    boolean a = (result1 == TextToSpeech.LANG_MISSING_DATA || result1 == TextToSpeech.LANG_NOT_SUPPORTED);
                    boolean b = (result2 == TextToSpeech.LANG_MISSING_DATA || result2 == TextToSpeech.LANG_NOT_SUPPORTED);

                    Log.i("zhh_tts", "US支持否？--》" + a +
                            "\nzh-CN支持否》--》" + b);

                } else {
                    Toast.makeText(context, "数据丢失或不支持", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fuwus_daohang:  //导航
                mBottomSheetPop = new BottomSheetPop(context);
                mBottomSheetPop.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
                mBottomSheetPop.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
                mBottomSheetPop.setContentView(mapSheetView);
                mBottomSheetPop.setBackgroundDrawable(new ColorDrawable(0x00000000));
                mBottomSheetPop.setOutsideTouchable(true);
                mBottomSheetPop.setFocusable(true);
                mBottomSheetPop.showAtLocation(activity.getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
                break;
            case R.id.cancel_btn2:
                if (mBottomSheetPop != null) {
                    mBottomSheetPop.dismiss();
                }
                break;
            case R.id.baidu_btn:
                if (isAvilible(context, "com.baidu.BaiduMap")) {//传入指定应用包名
                    try {
                        Intent intent = Intent.getIntent("intent://map/direction?" +
                                "destination=latlng:" + lonlat[1] + "," + lonlat[0] + "|name:我的目的地" +        //终点
                                "&mode=driving&" +          //导航路线方式
                                "&src=appname#Intent;scheme=bdapp;package=com.baidu.BaiduMap;end");
                        startActivity(intent); //启动调用
                    } catch (URISyntaxException e) {
                        Log.e("intent", e.getMessage());
                    }
                } else {//未安装
                    //market为路径，id为包名
                    //显示手机上所有的market商店
                    Toast.makeText(context, "您尚未安装百度地图", Toast.LENGTH_LONG).show();
                    Uri uri = Uri.parse("market://details?id=com.baidu.BaiduMap");
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    if (intent.resolveActivity(activity.getPackageManager()) != null){
                        startActivity(intent);
                    }
                }
                mBottomSheetPop.dismiss();
                break;
            case R.id.gaode_btn:
                if (isAvilible(context, "com.autonavi.minimap")) {
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.addCategory(Intent.CATEGORY_DEFAULT);

                    //将功能Scheme以URI的方式传入data
                    Uri uri = Uri.parse("androidamap://navi?sourceApplication=appname&poiname=fangheng&lat=" + lonlat[1] +  "&lon=" + lonlat[0] + "&dev=1&style=2");
                    intent.setData(uri);

                    //启动该页面即可
                    startActivity(intent);
                } else {
                    Toast.makeText(context, "您尚未安装高德地图", Toast.LENGTH_LONG).show();
                    Uri uri = Uri.parse("market://details?id=com.autonavi.minimap");
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    if (intent.resolveActivity(activity.getPackageManager()) != null){
                        startActivity(intent);
                    }
                }
                mBottomSheetPop.dismiss();
                break;
            case R.id.tencent_btn:
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_DEFAULT);

                //将功能Scheme以URI的方式传入data
                Uri uri = Uri.parse("qqmap://map/routeplan?type=drive&to=我的目的地&tocoord=" + lonlat[1] + "," + lonlat[0] );
                intent.setData(uri);
                if (intent.resolveActivity(activity.getPackageManager()) != null) {
                    //启动该页面即可
                    startActivity(intent);
                } else {
                    Toast.makeText(context, "您尚未安装腾讯地图", Toast.LENGTH_LONG).show();
                }
                mBottomSheetPop.dismiss();
                break;
        }
    }


    /**
     * 检查手机上是否安装了指定的软件
     *
     * @param context
     * @param packageName：应用包名
     * @return
     */
    public static boolean isAvilible(Context context, String packageName) {
        //获取packagemanager
        final PackageManager packageManager = context.getPackageManager();
        //获取所有已安装程序的包信息
        List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);
        //用于存储所有已安装程序的包名
        List<String> packageNames = new ArrayList<String>();
        //从pinfo中将包名字逐一取出，压入pName list中
        if (packageInfos != null) {
            for (int i = 0; i < packageInfos.size(); i++) {
                String packName = packageInfos.get(i).packageName;
                packageNames.add(packName);
            }
        }
        //判断packageNames中是否有目标程序的包名，有TRUE，没有FALSE
        return packageNames.contains(packageName);
    }


    /**
     * 获取经纬度
     */
    String[] lonlat;
    private void getlonlat() {
        OkGo.post(Api.selectdangqianorder)
                .tag(this)
                .params("serviceManId",sharedPreferencesHelper.getSharedPreference("id","").toString())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Gson gson = new Gson();
                        CurrentOrderBean currentOrderBean = gson.fromJson(s,CurrentOrderBean.class);
                        if (currentOrderBean.getState()==1){
                            if (currentOrderBean.getData()!=null){
                                if (currentOrderBean.getData().getLonLat().contains(",")){
                                    lonlat  = currentOrderBean.getData().getLonLat().split(",");
                                    btn_start.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            Intent intent = new Intent(context,CarwashActivity.class);
                                            intent.putExtra("orderid",orderid);
                                            intent.putExtra("lat",lonlat[1]);
                                            intent.putExtra("lon",lonlat[0]);
                                            startActivity(intent);
                                        }
                                    });
                                }
                            }
                        }
                    }
                });

    }

    private void upzuobiao(double a, double b, final String c) {
        Log.e("经纬度1",lat+"--"+lon);
            OkGo.post(Api.updatestatus)
                    .params("serviceManId",sharedPreferencesHelper.getSharedPreference("id","").toString())
                    .params("isReceive",c)
                    .params("manLng",b)
                    .params("manLat",a)
                    .execute(new StringCallback() {
                        @Override
                        public void onSuccess(String s, Call call, Response response) {
                            Log.e("请求修改状态接口",s);
                            Gson  gson = new Gson();
                            TongyongBean tongyongBean = gson.fromJson(s,TongyongBean.class);
                            if(tongyongBean.getState()==1){
                                Log.e("ccccccc",c);
                               if (c.equals("1")){
                                   showinfo();
                               }
                            }else {
                                ToastUtils.shortToast("服务器请求失败，稍后请重试");
                            }
                        }
                    });

    }

    /**
     * 地图开始
     */
    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //声明mLocationOption对象
    public AMapLocationClientOption mLocationOption = null;
    private double lat;
    private double lon;
    private MapView mapView;
    private AMap aMap;//地图控制器对象
    private UiSettings mUiSettings;
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
        mUiSettings.setZoomPosition(AMapOptions.ZOOM_POSITION_RIGHT_BUTTOM);//地图缩放控件的摆放位置
        //aMap  为地图控制器对象
        aMap.getUiSettings().setMyLocationButtonEnabled(true);//地图的定位标志是否可见
        aMap.setMyLocationEnabled(true);//地图定位标志是否可以点击

        //判断是否添加定位权限
        //这里以ACCESS_COARSE_LOCATION为例
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            //申请WRITE_EXTERNAL_STORAGE权限
            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    1);//自定义的code
        }{
//            ToastUtils.shortToast("已有权限");
            zhuangtaizhi = 1;
            setUpMap();
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions((Activity) context,
                        new String[]{Manifest.permission.CALL_PHONE},
                        2);
            }else {
                bodadianhua=2;
//                showToast("权限已申请");
            }
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
//                ToastUtils.shortToast("定位权限已申请");
                setUpMap();
                zhuangtaizhi = 1;
            } else {
                zhuangtaizhi = 0;
                ToastUtils.shortToast("定位权限已拒绝");
            }
        }else if (requestCode==2){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                ToastUtils.shortToast("定位权限已申请");
                bodadianhua = 2;
            } else {
                bodadianhua = 0;
                ToastUtils.shortToast("拨打电话权限已拒绝");
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
        mLocationOption.setInterval(30000);

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
//                    ToastUtils.longToast("经纬度"+lat+"**"+lon);
                    Log.e("getAccuracy", ""+amapLocation.getAccuracy()+" 米");//获取精度信息
                    Log.e("joe", "lat :-- " + lat + " lon :--" + lon);
                    Log.e("joe", "Country : " + amapLocation.getCountry() + " province : " + amapLocation.getProvince() + " City : " + amapLocation.getCity() + " District : " + amapLocation.getDistrict());
                  //获得经纬度后才可以执行点击事件
                   upzuobiao(lat,lon,sharedPreferencesHelper.getSharedPreference("isreceive","").toString());
                   ll_state.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (tv_state.getText().toString().equals("停止接单")){
                                tv_fuwuuse_5_info.setText("已经关闭接单");
                                tv_state.setText("开始接单");
                                upzuobiao(lat,lon,"0");
                                zhi = 3;
                            }else {
                                tv_state.setText("停止接单");
                                tv_fuwuuse_5_info.setText("当前暂无订单，等待平台派单");
//                                showinfo();
                                upzuobiao(lat,lon,"1");
                            }
                           }
                    });
                    //清空缓存位置
                    aMap.clear();


                    // 设置显示的焦点，即当前地图显示为当前位置
//                    aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lon), 15));
                    aMap.moveCamera(CameraUpdateFactory.zoomTo(15));
                    aMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(lat, lon)));

                    MarkerOptions markerOptions = new MarkerOptions();
                    markerOptions.position(new LatLng(lat, lon));
                    markerOptions.title("我的位置");
                    markerOptions.visible(false);
                    BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.dingwei));
                    markerOptions.icon(bitmapDescriptor);
                    markerOptions.draggable(false);
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
    /**
     * 暂停地图的绘制
     */
    @Override
    public void onPause() {
        super.onPause();
//        if (sharedPreferencesHelper.getSharedPreference("status","").toString().equals("1") || dizhi==0){
//
//        }else {
//            mapView.onPause();
//        }

    }


    /**
     * 保存地图当前的状态方法必须重写
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (sharedPreferencesHelper.getSharedPreference("status","").toString().equals("1") || dizhi==0){

        }else {
            mapView.onSaveInstanceState(outState);
        }

    }

    /**
     * 销毁地图
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (sharedPreferencesHelper.getSharedPreference("status","").toString().equals("1") || dizhi==0){

        }else {
            mapView.onDestroy();
        }
        if (mLocationClient != null) {
            mLocationClient.stopLocation();
            mLocationClient.onDestroy();
        }
        mLocationClient = null;
    }
    /**
     * 地图结束
     */


    /**
     * 显示信息
     */
    @BindView(R.id.fuwus_status)
    TextView tv_status;
    @BindView(R.id.fuwus_carimg)
    ImageView img_carimg;
    @BindView(R.id.fuwus_carinfo)
    TextView tv_carinfo;
    @BindView(R.id.fuwus_phone)
    ImageView img_phone;
    @BindView(R.id.fuwus_yudingwanchengtime)
    TextView tv_wangchengtime;
    @BindView(R.id.fuwus_xicheaddress)
    TextView tv_address;

    //展示当前订单
    @BindView(R.id.xiche_paidan)
    TextView tv_danshu;
    String orderid;
    private void showinfo() {

        Log.e("aaa",zhi+"***");
        if (zhi==2){
            zhi=0;
        }
        getlonlat();
        OkGo.post(Api.selectdangqianorder)
                .tag(this)
                .params("serviceManId",sharedPreferencesHelper.getSharedPreference("id","").toString())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Log.e("当前订单",s);
                        Gson gson = new Gson();
                        final CurrentOrderBean currentOrderBean = gson.fromJson(s,CurrentOrderBean.class);
                        if (currentOrderBean.getState()==1){
                            if (currentOrderBean.getData()==null){
//                                Log.e("aaaaa11","11");
                                Timer timer = new Timer();
                                timer.scheduleAtFixedRate(new TimerTask() {
                                    @Override
                                    public void run() {
                                        Log.e("TAGs","1每隔1分钟执行一次操作");
                                        showinfosss();
                                    }
                                },60000,60000);

                                tv_fuwuuse_5_info.setText("当前暂无订单，等待平台派单");
                                linearLayout1.setVisibility(View.GONE);
                                linearLayout2.setVisibility(View.GONE);
                                linearLayout3.setVisibility(View.GONE);
                                linearLayout4.setVisibility(View.GONE);
                                linearLayout5.setVisibility(View.VISIBLE);
                                ll_1.setVisibility(View.GONE);
                                ll_2.setVisibility(View.GONE);
                                ll_guize.setVisibility(View.VISIBLE);

                            }else {

                                ll_guize.setVisibility(View.GONE);
                                btn_start.setVisibility(View.VISIBLE);
                                btn_start.setText("开始洗车");
                                imageView.setImageResource(R.mipmap.xichezhong2);
                                tv_zhong.setTextColor(Color.parseColor("#ACACAC"));
                                imgwancheng.setImageResource(R.mipmap.wancheng2);
                                tv_wancheng.setTextColor(Color.parseColor("#ACACAC"));

                                tv_status.setText("停止接单");
                                linearLayout1.setVisibility(View.VISIBLE);
                                linearLayout2.setVisibility(View.VISIBLE);
                                linearLayout3.setVisibility(View.VISIBLE);
                                linearLayout4.setVisibility(View.VISIBLE);
                                linearLayout5.setVisibility(View.GONE);
                                ll_1.setVisibility(View.VISIBLE);
                                ll_2.setVisibility(View.VISIBLE);
                                ll_3.setVisibility(View.GONE);

                                orderid = currentOrderBean.getData().getUuid()+"";
                                tv_status.setText(currentOrderBean.getData().getService_name()+"  ("+currentOrderBean.getData().getType_name()+")");
                                tv_carinfo.setText(currentOrderBean.getData().getCarNo()+"  "+currentOrderBean.getData().getBrand()+"  "+currentOrderBean.getData().getCar_color());
                                tv_wangchengtime.setText(currentOrderBean.getData().getOrder_time());
                                img_phone.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        if (bodadianhua!=0){
                                            final android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(context);
                                            LayoutInflater inflater = LayoutInflater.from(context);
                                            final View DialogView = inflater .inflate ( R.layout.callphone4, null);//1、自定义布局
                                            TextView ok = (TextView) DialogView.findViewById(R.id.confirm);//自定义控件
                                            TextView paizhao = (TextView) DialogView.findViewById(R.id.cancel);//自定义控件
                                            TextView dianhua = (TextView) DialogView.findViewById(R.id.text4444);//自定义控件
                                            dianhua.setText("您确认拨打："+currentOrderBean.getData().getTel());
                                            final EditText edt_xinxi = (EditText) DialogView.findViewById(R.id.qitayuanyin);
                                            builder.setView(DialogView);
                                            final android.support.v7.app.AlertDialog dialog = builder.create();
                                            dialog.show();
                                            //拨打
                                            ok.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    dialog.dismiss();
                                                    if (bodadianhua!=0){
                                                        Intent intent = new Intent();

                                                        intent.setAction(Intent.ACTION_CALL);

                                                        intent.setData(Uri.parse("tel:"+ currentOrderBean.getData().getTel()));

                                                        startActivity(intent);
                                                    }else {
                                                        ToastUtils.shortToast("请在设置里面打开拨打电话的权限");
                                                    }

                                                }
                                            });
                                            //取消
                                            paizhao.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    dialog.dismiss();
                                                }
                                            });


                                        }else {
                                            ToastUtils.shortToast("请在设置里面打开拨打电话的权限");
                                        }

                                    }
                                });
                                tv_address.setText(currentOrderBean.getData().getCar_addr());
                                Glide.with(context).load(Api.ossUrl+currentOrderBean.getData().getCar_image()).into(img_carimg);

                            }


                        }else {
                            ToastUtils.shortToast(currentOrderBean.getMessage());
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        ToastUtils.shortToast(e+"");
                    }
                });
    }
    private void show2(){
        //查询当前的排队单数
        OkGo.post(Api.paiduinum)
                .tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Gson gson = new Gson();
                        PaiduiNumBean paiduiNumBean = gson.fromJson(s,PaiduiNumBean.class);
                        if (paiduiNumBean.getState()==1){
                            if (paiduiNumBean.getData()==null){
                                tv_danshu.setText("排队订单:0");
                            }else {
                                tv_danshu.setText("排队单数:"+paiduiNumBean.getData().getNum());
                            }
                        }else {
                            ToastUtils.shortToast(paiduiNumBean.getMessage());
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        ToastUtils.shortToast(e+"");
                    }
                });
    }

    /**
     * 信息
     */
    @BindView(R.id.fuwu_carstart)
    Button btn_start;
    @BindView(R.id.fuwu_carstart2)
    Button btn_start2;
    @BindView(R.id.fuwu_quxiaodingdan)
    TextView tv_dingdan;
    //点击停止接单
    @BindView(R.id.fuwu_xichestate)
    LinearLayout ll_state;
    @BindView(R.id.xiche_title)
    TextView tv_state;
    @BindView(R.id.fuwu_xiaoxi)
    ImageView img_messages;
    //联系客服
    @BindView(R.id.fuwu_kefu)
    TextView tv_kefu;

    String xiaoxi="";
    private void setinfo() {
        //点击消息
        img_messages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,MessagesBianjiActivity.class);
                startActivity(intent);
            }
        });
        //联系客服
        tv_kefu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(context);
                LayoutInflater inflater = LayoutInflater.from(context);
                final View DialogView = inflater .inflate ( R.layout.callphone, null);//1、自定义布局
                TextView ok = (TextView) DialogView.findViewById(R.id.confirm);//自定义控件
                TextView paizhao = (TextView) DialogView.findViewById(R.id.cancel);//自定义控件
                final EditText edt_xinxi = (EditText) DialogView.findViewById(R.id.qitayuanyin);
                builder.setView(DialogView);
                final android.support.v7.app.AlertDialog dialog = builder.create();
                dialog.show();
                //留言
                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
//                        ToastUtils.shortToast("留言");
                        Intent intent = new Intent(context,LiuYanActivity.class);
                        intent.putExtra("orderid",orderid);
                        context.startActivity(intent);
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                dialog.dismiss();
                            }
                        },500);
                    }
                });
                //拨打电话
                paizhao.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        final android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(context);
                        LayoutInflater inflater = LayoutInflater.from(context);
                        final View DialogView = inflater .inflate ( R.layout.callphone2, null);//1、自定义布局
                        TextView ok = (TextView) DialogView.findViewById(R.id.confirm);//自定义控件
                        TextView paizhao = (TextView) DialogView.findViewById(R.id.cancel);//自定义控件
                        final EditText edt_xinxi = (EditText) DialogView.findViewById(R.id.qitayuanyin);
                        builder.setView(DialogView);
                        final android.support.v7.app.AlertDialog dialog = builder.create();
                        dialog.show();
                        //拨打
                        ok.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.dismiss();
                                if (bodadianhua!=0){
                                    Intent intent = new Intent();

                                    intent.setAction(Intent.ACTION_CALL);

                                    intent.setData(Uri.parse("tel:"+ "022-25288258"));

                                    startActivity(intent);
                                }else {
                                    ToastUtils.shortToast("请在设置里面打开拨打电话的权限");
                                }

                            }
                        });
                        //取消
                        paizhao.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.dismiss();
                            }
                        });
                    }
                });
            }
        });

        btn_start2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ll_ok.setVisibility(View.VISIBLE);
                scrollView_yes.setVisibility(View.GONE);
            }
        });



        tv_dingdan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(context);
                LayoutInflater inflater = LayoutInflater.from(context);
                final View DialogView = inflater .inflate ( R.layout.dialog_view, null);//1、自定义布局
                TextView ok = (TextView) DialogView.findViewById(R.id.confirm);//自定义控件
                TextView paizhao = (TextView) DialogView.findViewById(R.id.cancel);//自定义控件
//                TextView tuku = (TextView) DialogView.findViewById(R.id.headimg_tuku);//自定义控件
                builder.setView(DialogView);
                final android.support.v7.app.AlertDialog dialog = builder.create();
                dialog.show();
                //确认
                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        //打开取消订单原因
                        final android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(context);
                        LayoutInflater inflater = LayoutInflater.from(context);
                        final View DialogView = inflater .inflate ( R.layout.quxiao_view, null);//1、自定义布局
                        TextView ok = (TextView) DialogView.findViewById(R.id.confirm);//自定义控件
                        TextView paizhao = (TextView) DialogView.findViewById(R.id.cancel);//自定义控件
                        final RadioGroup radioGroup = (RadioGroup)DialogView.findViewById(R.id.quxiao_radiogroup);
                        final RadioButton ra1 = (RadioButton) DialogView.findViewById(R.id.radio1);
                        final RadioButton ra2 = (RadioButton) DialogView.findViewById(R.id.radio1);
                        final RadioButton ra3 = (RadioButton) DialogView.findViewById(R.id.radio1);
                        final RadioButton ra4 = (RadioButton) DialogView.findViewById(R.id.radio1);
                        final EditText edt_xinxi = (EditText) DialogView.findViewById(R.id.qitayuanyin);
                        builder.setView(DialogView);
                        final android.support.v7.app.AlertDialog dialog = builder.create();
                        dialog.show();

                        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(RadioGroup group, int checkedId) {
                                RadioButton radioButton = (RadioButton)DialogView.findViewById(radioGroup.getCheckedRadioButtonId());
                                xiaoxi=radioButton.getText().toString();
//                                ToastUtils.shortToast(xiaoxi+"**");
                            }
                        });

                        //确认
                        ok.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {


                                if (xiaoxi.equals("")){
                                    ToastUtils.shortToast("请选择原因");
                                }else {
                                    dialog.dismiss();
                                    quxiaoorderreson(edt_xinxi.getText().toString());

                                }

                            }
                        });
                        //取消
                        paizhao.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.dismiss();
                            }
                        });
                    }
                });
                //取消
                paizhao.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

            }
        });


    }

    /***
     * 取消订单
     */
    private void quxiaoorderreson(String reason) {
        Log.e("取消信息",orderid+"--"+xiaoxi+reason+"**"+sharedPreferencesHelper.getSharedPreference("id","").toString());
        OkGo.post(Api.quxiaoorder)
                .tag(this)
                .params("orderUUID",orderid)
                .params("reason",xiaoxi+reason)
                .params("serviceManId",sharedPreferencesHelper.getSharedPreference("id","").toString())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Gson gson = new Gson();
                        TongyongBean tongyongBean = gson.fromJson(s,TongyongBean.class);
                        if (tongyongBean.getState()==1){
                            ToastUtils.shortToast("取消订单成功");
                            showinfo();
                        }else {
                            ToastUtils.shortToast(tongyongBean.getMessage());
                        }

                    }
                });
    }

    @BindView(R.id.fuwu_xichekaishi)
    ImageView image_kaishi;
    @BindView(R.id.fuwu_xichezhong)
    ImageView imageView;
    @BindView(R.id.fuwu_xichewancheng)
    ImageView imgwancheng;
    @BindView(R.id.fuwu_text_kaishi)
    TextView tv_kaishi;
    @BindView(R.id.fuwu_text_zhong)
    TextView tv_zhong;
    @BindView(R.id.fuwu_text_wancheng)
    TextView tv_wancheng;

    @BindView(R.id.fuwu_state1)
    LinearLayout ll_1;
    @BindView(R.id.fuwu_state2)
    LinearLayout ll_2;
    @BindView(R.id.fuwu_state3)
    LinearLayout ll_3;
    int orderstatus2 = 0;
    int orderflag = 0;
    @Override
    public void onResume() {
        super.onResume();
        Log.e("aaa","666");

        if (zhi==0){
            Log.e("aaa","0");
        }else if(zhi==1){
            Log.e("aaa","1");
            ll_1.setVisibility(View.VISIBLE);
            ll_2.setVisibility(View.GONE);
            btn_start.setText("完成洗车");
            imageView.setImageResource(R.mipmap.xichezhong1);
            tv_zhong.setTextColor(Color.parseColor("#0C63D7"));

            final Timer timer = new Timer();
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    OkGo.post(Api.nextorder)
                            .tag(this)
                            .params("personId",sharedPreferencesHelper.getSharedPreference("id","").toString())
                            .execute(new StringCallback() {
                                @Override
                                public void onSuccess(String s, Call call, Response response) {
                                    Log.e("下一订单",s);
                                    Gson gson = new Gson();
                                    NextOrderBean nextOrderBean = gson.fromJson(s,NextOrderBean.class);
                                    if (nextOrderBean.getState()==1){
                                        if (nextOrderBean.getData()!=null){
                                            if (nextOrderBean.getData().getFlag()==1){
                                                Log.e("下一订单信息",orderflag+"");
                                                if (orderflag==0){

                                                    // 设置音调，值越大声音越尖（女生），值越小则变成男声,1.0是常规
                                                    textToSpeech.setPitch(1.1f);
                                                    // 设置语速
                                                    textToSpeech.setSpeechRate(1.1f);
                                                    textToSpeech.speak("您有一条新的洗车订单",//输入中文，若不支持的设备则不会读出来
                                                            TextToSpeech.QUEUE_FLUSH, null);
                                                    orderflag = 1;
                                                    timer.cancel();
                                                }else {
                                                    orderflag = 1;
                                                }

                                            }else {
                                                orderflag = 0;
                                            }
                                        }
                                    }


                                }
                            });
                }
            },60000,60000);


        }
        if (zhi==2){
            orderflag = 0;
            btn_start.setVisibility(View.GONE);
            imgwancheng.setImageResource(R.mipmap.wancheng1);
            tv_wancheng.setTextColor(Color.parseColor("#0C63D7"));
            ll_1.setVisibility(View.GONE);
            ll_2.setVisibility(View.GONE);
            ll_3.setVisibility(View.VISIBLE);
            Timer timer = new Timer();
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    Log.e("TAGs","2每隔1分钟执行一次操作");
                    showinfosss();
                }
            },60000,60000);
//            btn_start.setText("开始洗车");
//            imageView.setImageResource(R.mipmap.xichezhong2);
//            tv_zhong.setTextColor(Color.parseColor("#0C63D7"));
//            showinfo();
        }

    }
    //一分钟查询一次，如果有就跳转到展示信息的页面
    private void showinfosss() {
        OkGo.post(Api.nextorder)
                .tag(this)
                .params("personId",sharedPreferencesHelper.getSharedPreference("id","").toString())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Gson gson = new Gson();
                        NextOrderBean nextOrderBean = gson.fromJson(s,NextOrderBean.class);
                        if (nextOrderBean.getState()==1) {
                            if (nextOrderBean.getData() != null) {
                                if (nextOrderBean.getData().getFlag() == 1) {
                                    showinfo();
                                }else {

                                }
                            }
                        }
                    }
                });
    }


}
