package xicheapp.app.mdb.android.xiche.wode;

import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;
import xicheapp.app.mdb.android.xiche.BaseActivity;
import xicheapp.app.mdb.android.xiche.R;
import xicheapp.app.mdb.android.xiche.Utils.Api;
import xicheapp.app.mdb.android.xiche.Utils.SharedPreferencesHelper;
import xicheapp.app.mdb.android.xiche.Utils.ToastUtils;
import xicheapp.app.mdb.android.xiche.Utils.UiUtils;
import xicheapp.app.mdb.android.xiche.bean.GerenInfoBean;
import xicheapp.app.mdb.android.xiche.bean.TongyongBean;

/**
 * 修改联系手机号
 */
public class NewPhoneBingActivity extends BaseActivity {
    SharedPreferencesHelper sharehelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_phone_bing);
        ButterKnife.bind(this);
        sharehelper = new SharedPreferencesHelper(NewPhoneBingActivity.this,"xicheqishou");
        showolderphone();
        setTtitle();
        fanhui();
        setinfo();
    }

    @BindView(R.id.jiebang_phoneyuan)
    TextView tv_yuanphone;
    private void showolderphone() {
        OkGo.post(Api.selectshopinfo)
                .tag(this)
                .params("serviceManId",sharehelper.getSharedPreference("id","").toString())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Gson gson = new Gson();
                        GerenInfoBean gerenInfoBean = gson.fromJson(s,GerenInfoBean.class);
                        if (gerenInfoBean.getState()==1){
                            if (gerenInfoBean.getData()!=null){
                                tv_yuanphone.setText(gerenInfoBean.getData().getContact_mobile());
                            }
                        }
                    }
                });

    }

    /**
     * 提交信息
     */
    @BindView(R.id.bangding_tijiao)
    Button btn_tijiao;
    @BindView(R.id.bangding_huoqusms)
    Button btn_huoqu;
    @BindView(R.id.bangding_phone)
    EditText edt_phone;
    @BindView(R.id.bangding_sms)
    EditText edt_sms;
    private void setinfo() {
        //获取验证码
        btn_huoqu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(edt_phone.getText().toString())){
                    ToastUtils.shortToast("手机号不能为空！");
                }else{
                    if (UiUtils.isCellphone(edt_phone.getText().toString())==false){
                        ToastUtils.shortToast("手机号输入不正确！");
                    }else{
                        timer.start();
                        getsms(edt_phone.getText().toString());
                    }
                }
            }
        });
        btn_tijiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //判断手机号和验证码是否为空
                if (TextUtils.isEmpty(edt_phone.getText().toString())){
                    ToastUtils.shortToast("请输入新手机号");
                }else{
                    if (UiUtils.isCellphone(edt_phone.getText().toString())==false){
                        ToastUtils.shortToast("手机号输入的不正确");
                    }else {
                        if (TextUtils.isEmpty(edt_phone.getText().toString())){
                            ToastUtils.shortToast("请输入验证码");
                        }else {
                            OkGo.post(Api.smsOrlogin)
                                    .tag(this)
                                    .params("mobile",edt_phone.getText().toString())
                                    .params("smsCode",edt_sms.getText().toString())
                                    .execute(new StringCallback() {
                                        @Override
                                        public void onSuccess(String s, Call call, Response response) {
//                                        Log.e("验证1",edt_phone.getText().toString()+"*"+edt_sms.getText().toString());
//                                        Log.e("验证验证码",s);
                                            Gson gson = new Gson();
                                            TongyongBean tongyongBean = gson.fromJson(s,TongyongBean.class);
                                            if (tongyongBean.getState()==1){
                                                setupphone();
                                            }else{
                                                ToastUtils.shortToast("输入的验证码不正确");
                                            }
                                        }
                                    });

                        }
                    }

                }
            }
        });
    }

    /**
     * 更换手机号
     */
    private void setupphone() {
        OkGo.post(Api.xiugailianxiphone)
                .tag(this)
                .params("contactMobile",edt_phone.getText().toString())
                .params("serviceManId",sharehelper.getSharedPreference("id","").toString())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Gson gson = new Gson();
                        TongyongBean tongyongBean = gson.fromJson(s,TongyongBean.class);
                        if (tongyongBean.getState()==1){
                            ToastUtils.shortToast("修改联系手机号成功");
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    NewPhoneBingActivity.this.finish();
                                }
                            },1000);
                        }else{
                            ToastUtils.shortToast("修改联系手机号失败");
                        }
                    }
                });
    }

    /**
     * 获取验证码
     */
    private void getsms(String phone){
        OkGo.post(Api.sms)
                .tag(this)
                .params("mobile",phone)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
//                        Log.e("测试短信验证码",s);
                    }
                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                    }
                });
    }



    /**
     * 线程读秒
     */
    private CountDownTimer timer = new CountDownTimer(120000, 1000) {

        @Override
        public void onTick(long millisUntilFinished) {
            btn_huoqu.setText("倒计时("+(millisUntilFinished / 1000)+")");
            btn_huoqu.setEnabled(false);
        }

        @Override
        public void onFinish() {
            btn_huoqu.setEnabled(true);
            btn_huoqu.setText("再次获取");
        }
    };

    /**
     * 写入title名字
     */
    @BindView(R.id.xiche_title)
    TextView ttitle;
    @BindView(R.id.xiche_quxiao_title)
    TextView ttitle2;
    private void setTtitle(){
        ttitle.setText("修改联系电话");
    }
    /**
     * 返回
     */
    @BindView(R.id.xiche_fanhui)
    LinearLayout lfanhui;
    @BindView(R.id.xiche_imgfanhui)
    ImageView img;
    private void fanhui(){
        img.setVisibility(View.VISIBLE);
        lfanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NewPhoneBingActivity.this.finish();
            }
        });
    }

}
