package xicheapp.app.mdb.android.xiche.login;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.tu.loadingdialog.LoadingDailog;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import java.lang.reflect.Method;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jpush.android.api.JPushInterface;
import okhttp3.Call;
import okhttp3.Response;
import xicheapp.app.mdb.android.xiche.BaseActivity;
import xicheapp.app.mdb.android.xiche.MainActivity;
import xicheapp.app.mdb.android.xiche.R;
import xicheapp.app.mdb.android.xiche.Utils.Api;
import xicheapp.app.mdb.android.xiche.Utils.SharedPreferencesHelper;
import xicheapp.app.mdb.android.xiche.Utils.ToastUtils;
import xicheapp.app.mdb.android.xiche.Utils.UiUtils;
import xicheapp.app.mdb.android.xiche.Utils.Utils2;
import xicheapp.app.mdb.android.xiche.bean.LoginBean;

import static xicheapp.app.mdb.android.xiche.MyApplication.registrationID;

/**
 * 登录页面
 */
public class LoginActivity extends BaseActivity {
    SharedPreferencesHelper sharehelper;
    public static int dizhi=0,shouji =0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        JPushInterface.init(getApplicationContext());
//        Log.e("测试id", JPushInterface.getRegistrationID(getApplicationContext())+"++");
        ButterKnife.bind(this);
        sharehelper = new SharedPreferencesHelper(LoginActivity.this,"xicheqishou");
        setTtitle();
        setinfo();
        //判断是否添加定位权限
        //这里以ACCESS_COARSE_LOCATION为例
        if (ContextCompat.checkSelfPermission(LoginActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            //申请WRITE_EXTERNAL_STORAGE权限
            ActivityCompat.requestPermissions((Activity) LoginActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    1);//自定义的code
        }{
            dizhi=1;
//            ToastUtils.shortToast("已有权限");
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(LoginActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions((Activity) LoginActivity.this,
                        new String[]{Manifest.permission.CALL_PHONE},
                        2);
            }else {
                shouji = 1;
//                showToast("权限已申请");
            }
        }

        if (TextUtils.isEmpty(sharehelper.getSharedPreference("zhanghao","").toString())){

        }else {
            checkBox.setChecked(true);
            edt_phone.setText(sharehelper.getSharedPreference("zhanghao","").toString());
            edt_phone.setSelection(edt_phone.getText().length());
            edt_password.setText(sharehelper.getSharedPreference("mima","").toString());
            edt_password.setSelection(edt_password.getText().length());
        }
        //查看是否有网络
        isNetworkConnected(LoginActivity.this);
        isWifiConnected(LoginActivity.this);
    }
    protected void hideBottomUIMenu() {
        //隐藏虚拟按键，并且全屏
        if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) { // lower api
            ToastUtils.longToast("1");
            View v = this.getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
        } else if (Build.VERSION.SDK_INT >= 19) {
            ToastUtils.longToast("3");
            //for new api versions.
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }


    /**
     * 判断是否存在虚拟按键
     * @return
     */
    public boolean checkDeviceHasNavigationBar() {
        boolean hasNavigationBar = false;
        Resources rs = getResources();
        int id = rs.getIdentifier("config_showNavigationBar", "bool", "android");
        if (id > 0) {
            hasNavigationBar = rs.getBoolean(id);
        }
        try {
            Class<?> systemPropertiesClass = Class.forName("android.os.SystemProperties");
            Method m = systemPropertiesClass.getMethod("get", String.class);
            String navBarOverride = (String) m.invoke(systemPropertiesClass, "qemu.hw.mainkeys");
            Log.e("测试虚拟键",navBarOverride);
            if ("1".equals(navBarOverride)) {
                hasNavigationBar = false;
            } else if ("0".equals(navBarOverride)) {
                hasNavigationBar = true;
            }
        } catch (Exception e) {

        }
        return hasNavigationBar;
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode==1){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                ToastUtils.shortToast("定位权限已申请");
                dizhi=1;
            } else {
                dizhi=0;
                ToastUtils.shortToast("定位权限已拒绝");
            }
        }else if (requestCode==2){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                ToastUtils.shortToast("定位权限已申请");
                shouji=1;
            } else {
                shouji=0;
                ToastUtils.shortToast("拨打电话权限已拒绝");
            }
        }
    }

    /**
     * 写入title名字
     */
    @BindView(R.id.xiche_title)
    TextView ttitle;
    @BindView(R.id.xiche_quxiao_title)
    TextView ttitle2;
    private void setTtitle(){
        ttitle.setText("登录");
        ttitle2.setText("注册");
        ttitle2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
                LoginActivity.this.finish();
            }
        });
    }
    /**
     * 返回
     */
    @BindView(R.id.xiche_fanhui)
    LinearLayout lfanhui;
    private void fanhui(){
        lfanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginActivity.this.finish();
            }
        });
    }
    /**
     * 忘记密码
     * 短信登录
     * 登录
     */
    @BindView(R.id.login_forgetpassword)
    TextView tv_forgetpassword;
    @BindView(R.id.login_duanxin)
    TextView tv_duanxin;
    @BindView(R.id.login)
    Button btn_login;
    @BindView(R.id.login_phone)
    EditText edt_phone;
    @BindView(R.id.login_password)
    EditText edt_password;
    @BindView(R.id.login_baocuninfo)
    CheckBox checkBox;
    private void setinfo(){
        tv_forgetpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,ChongzhiPasswordActivity.class);
                startActivity(intent);
                LoginActivity.this.finish();
            }
        });
        tv_duanxin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,DuanxiLoginActivity.class);
                startActivity(intent);
                LoginActivity.this.finish();
            }
        });
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Utils2.isFastClick()==true){
                    if (TextUtils.isEmpty(edt_phone.getText().toString())){
                        ToastUtils.shortToast("请输入手机号");
                    }else{
                        if (TextUtils.isEmpty(edt_password.getText().toString())){
                            ToastUtils.shortToast("请输入密码");
                        }else{
                            if (UiUtils.isCellphone(edt_phone.getText().toString())==false){
                                ToastUtils.shortToast("输入的手机号不正确");
                            }else {
                                OkGo.post(Api.login)
                                        .tag(this)
                                        .params("mobile",edt_phone.getText().toString())
                                        .params("password",edt_password.getText().toString())
                                        .params("registrationId",registrationID)
                                        .execute(new StringCallback() {
                                            @Override
                                            public void onSuccess(String s, Call call, Response response) {
                                                Log.e("账号密码登录",s);
                                                Gson gson = new Gson();
                                                final LoginBean loginBean = gson.fromJson(s,LoginBean.class);
                                                if (loginBean.getState()==1){
                                                    if (checkBox.isChecked()==true){
                                                        sharehelper.put("zhanghao",edt_phone.getText().toString());
                                                        sharehelper.put("mima",edt_password.getText().toString());
                                                    }else {
                                                        sharehelper.remove("zhanghao");
                                                        sharehelper.remove("mima");
                                                    }
                                                    if (loginBean.getData()!=null){
                                                        sharehelper.put("phone",loginBean.getData().getMobile());
                                                        sharehelper.put("id",loginBean.getData().getId()+"");
                                                        sharehelper.put("status",loginBean.getData().getStatus());
                                                        if (loginBean.getData().getReason()==null){
                                                            sharehelper.put("reason","");
                                                        }else {
                                                            sharehelper.put("reason",loginBean.getData().getReason());
                                                        }
                                                        if (loginBean.getData().getIs_receive()==null){
                                                            sharehelper.put("isreceive","");
                                                        }else {
                                                            sharehelper.put("isreceive",loginBean.getData().getIs_receive());
                                                        }
                                                    }
                                                    LoadingDailog.Builder loadBuilder=new LoadingDailog.Builder(LoginActivity.this)
                                                            .setMessage("登录成功...")
                                                            .setCancelable(false)
                                                            .setCancelOutside(false);
                                                    final LoadingDailog dialog=loadBuilder.create();
                                                    dialog.show();
                                                    Handler handler = new Handler();
                                                    handler.postDelayed(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            dialog.dismiss();
                                                            if (loginBean.getData().getStatus().equals("4")){
                                                                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                                                                startActivity(intent);
                                                                LoginActivity.this.finish();
                                                            }else if (loginBean.getData().getStatus().equals("1")){
                                                                if (loginBean.getData().getCard_no()==null || loginBean.getData().getName()==null){
                                                                    Intent intent = new Intent(LoginActivity.this,ShenfenyanzhengActivity.class);
                                                                    intent.putExtra("status","1");
                                                                    startActivity(intent);
                                                                    LoginActivity.this.finish();
                                                                }else {
                                                                    Intent intent = new Intent(LoginActivity.this,YanzhengShenheActivity.class);
                                                                    intent.putExtra("status",loginBean.getData().getStatus());
                                                                    startActivity(intent);
                                                                    LoginActivity.this.finish();
                                                                }

                                                            }else {
                                                                Intent intent = new Intent(LoginActivity.this,YanzhengShenheActivity.class);
                                                                intent.putExtra("status",loginBean.getData().getStatus());
                                                                startActivity(intent);
                                                                LoginActivity.this.finish();
                                                            }

                                                        }
                                                    }, 2000);

                                                }else {
                                                    ToastUtils.shortToast(loginBean.getMessage());
                                                }

                                            }
                                        });
                            }
                        }
                    }
                }
            }
        });
    }

    //判断是否有网络连接
    public boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }else{
                Toast.makeText(LoginActivity.this, "当前无可用的网络服务",Toast.LENGTH_LONG).show();
            }
        }
        return false;
    }
    //判断WIFI网络是否可用
    public boolean isWifiConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mWiFiNetworkInfo = mConnectivityManager
                    .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if (mWiFiNetworkInfo != null) {
                return mWiFiNetworkInfo.isAvailable();
            }else{
                Toast.makeText(LoginActivity.this, "当前WIFI网络不可用",Toast.LENGTH_LONG).show();
            }
        }
        return false;
    }
}
