package xicheapp.app.mdb.android.xiche.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import xicheapp.app.mdb.android.xiche.Utils.ToastUtils;
import xicheapp.app.mdb.android.xiche.bean.TongyongBean;

/**
 * 重置密码
 */
public class ResetPasswordActivity extends BaseActivity {

    String phone,token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        ButterKnife.bind(this);
        setnext();
        setTtitle();
        fanhui();
        phone = getIntent().getStringExtra("phone");
    }
    /**
     * 写入title名字
     */
    @BindView(R.id.xiche_title)
    TextView ttitle;
    @BindView(R.id.xiche_quxiao_title)
    TextView ttitle2;
    private void setTtitle(){
        ttitle.setText("重置密码");
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
                Intent intent = new Intent(ResetPasswordActivity.this,LoginActivity.class);
                startActivity(intent);
                ResetPasswordActivity.this.finish();
            }
        });
    }
    /**
     * 下一步
     */
    @BindView(R.id.resetpassword_next)
    Button btn_next;
    @BindView(R.id.password_one)
    EditText edt_one;
    @BindView(R.id.password_two)
    EditText edt_two;
    private void setnext(){
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //判断密码和确认密码是否为空，是否一致
                if (TextUtils.isEmpty(edt_one.getText().toString())){
                    ToastUtils.shortToast("请输入密码");
                }else{
                    if (TextUtils.isEmpty(edt_two.getText().toString())){
                        ToastUtils.shortToast("请输入确认密码");
                    }else{
                        if (edt_one.getText().toString().length()<6 || edt_two.getText().toString().length()<6){
                            ToastUtils.shortToast("请输入密码长度过短");
                        }else {
                            if (!edt_one.getText().toString().equals(edt_two.getText().toString())){
                                ToastUtils.shortToast("两次输入的密码不一致");
                            }else{
                                OkGo.post(Api.chongzhipassword)
                                        .params("mobile",phone)
                                        .params("password",edt_one.getText().toString())
                                        .execute(new StringCallback() {
                                            @Override
                                            public void onSuccess(String s, Call call, Response response) {
                                                Gson gson = new Gson();
                                                TongyongBean tongyongBean = gson.fromJson(s,TongyongBean.class);
                                                if (tongyongBean.getState()==1){
                                                    Intent intent = new Intent(ResetPasswordActivity.this,ResetPasswordOkActivity.class);
                                                    startActivity(intent);
                                                    ResetPasswordActivity.this.finish();
                                                }else{
                                                    ToastUtils.shortToast("重置密码失败！");
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
}
