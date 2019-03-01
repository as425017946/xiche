package xicheapp.app.mdb.android.xiche.wode;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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
import xicheapp.app.mdb.android.xiche.bean.TongyongBean;
import xicheapp.app.mdb.android.xiche.login.PasswordActivity;
import xicheapp.app.mdb.android.xiche.login.RegisterActivity;

/**
 * 解绑手机
 */
public class JieBangPhoneActivity extends BaseActivity {
    SharedPreferencesHelper sharehelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jie_bang_phone);
        ButterKnife.bind(this);
        sharehelper = new SharedPreferencesHelper(JieBangPhoneActivity.this,"xicheqishou");
        setTtitle();
        fanhui();
        setinfo();
    }
    /**
     * 写入title名字
     */
    @BindView(R.id.xiche_title)
    TextView ttitle;
    @BindView(R.id.xiche_quxiao_title)
    TextView ttitle2;
    private void setTtitle(){
        if (getIntent().getStringExtra("bank2zhi").equals("1")){
            ttitle.setText("更换银行卡");
        }else{
            ttitle.setText("更换账号");
        }

    }
    /**
     * 返回
     */
    @BindView(R.id.xiche_fanhui)
    LinearLayout lfanhui;
    @BindView(R.id.xiche_imgfanhui)
    ImageView img;
    private void fanhui(){
        lfanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(getIntent().getStringExtra("bank2zhi").equals("1")){
                    Log.e("111","11");
                    Intent intent = new Intent(JieBangPhoneActivity.this,WodeBankActivity.class);
                    startActivity(intent);
                    JieBangPhoneActivity.this.finish();
                }else{
                    JieBangPhoneActivity.this.finish();
                    Log.e("22","22");
                }

            }
        });
    }
    /**
     * 提交信息
     */
    @BindView(R.id.bangding_tijiao)
    Button btn_tijiao;
    @BindView(R.id.jiebang_phone)
    TextView tv_phone;
    @BindView(R.id.jiebang_huoqusms)
    Button btn_huoqusms;
    @BindView(R.id.jiebang_sms)
    EditText edt_sms;

    private void setinfo(){
        //展示手机号
        tv_phone.setText(sharehelper.getSharedPreference("phone","").toString());
        btn_huoqusms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timer.start();
                getsms(tv_phone.getText().toString());
            }
        });

        btn_tijiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(getIntent().getStringExtra("bank2zhi").equals("1")){
                    if (TextUtils.isEmpty(edt_sms.getText().toString())){
                        ToastUtils.shortToast("请输入验证码");
                    }else {
                        yanzhengsms2();
                    }

                }else{
                    if (TextUtils.isEmpty(edt_sms.getText().toString())){
                        ToastUtils.shortToast("请输入验证码");
                    }else {
                        yanzhengsms();
                    }

                }

            }
        });
    }

    /**
     * 验证验证码
     */
    private void yanzhengsms(){
        OkGo.post(Api.smsOrlogin)
                .tag(this)
                .params("mobile",tv_phone.getText().toString())
                .params("smsCode",edt_sms.getText().toString())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
//                                        Log.e("验证1",edt_phone.getText().toString()+"*"+edt_sms.getText().toString());
//                                        Log.e("验证验证码",s);
                        Gson gson = new Gson();
                        TongyongBean tongyongBean = gson.fromJson(s,TongyongBean.class);
                        if (tongyongBean.getState()==1){
                            Intent intent = new Intent(JieBangPhoneActivity.this,BangdingPhoneActivity.class);
                            startActivity(intent);
                            JieBangPhoneActivity.this.finish();
                        }else{
                            ToastUtils.shortToast("输入的验证码不正确");
                        }
                    }
                });
    }

    private void yanzhengsms2(){
        OkGo.post(Api.smsOrlogin)
                .tag(this)
                .params("mobile",tv_phone.getText().toString())
                .params("smsCode",edt_sms.getText().toString())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
//                                        Log.e("验证1",edt_phone.getText().toString()+"*"+edt_sms.getText().toString());
//                                        Log.e("验证验证码",s);
                        Gson gson = new Gson();
                        TongyongBean tongyongBean = gson.fromJson(s,TongyongBean.class);
                        if (tongyongBean.getState()==1){
                            Intent intent = new Intent(JieBangPhoneActivity.this,WodeBank2Activity.class);
                            startActivity(intent);
                            JieBangPhoneActivity.this.finish();
                        }else{
                            ToastUtils.shortToast("输入的验证码不正确");
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
            btn_huoqusms.setText("倒计时("+(millisUntilFinished / 1000)+")");
            btn_huoqusms.setEnabled(false);
        }

        @Override
        public void onFinish() {
            btn_huoqusms.setEnabled(true);
            btn_huoqusms.setText("再次获取");
        }
    };
}
