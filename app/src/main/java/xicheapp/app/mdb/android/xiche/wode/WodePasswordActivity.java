package xicheapp.app.mdb.android.xiche.wode;

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
import xicheapp.app.mdb.android.xiche.bean.TongyongBean;

public class WodePasswordActivity extends BaseActivity {
    SharedPreferencesHelper sharehelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wode_password);
        ButterKnife.bind(this);
        sharehelper = new SharedPreferencesHelper(WodePasswordActivity.this,"xicheqishou");
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
        ttitle.setText("修改密码");
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
                WodePasswordActivity.this.finish();
            }
        });
    }


    /**
     * 提交信息
     */
    @BindView(R.id.bangding_tijiao)
    Button btn_tijiao;
    @BindView(R.id.wodepassword_mima1)
    EditText edt_one;
    @BindView(R.id.wodepassword_mima2)
    EditText edt_two;
    private void setinfo(){
        btn_tijiao.setOnClickListener(new View.OnClickListener() {
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
                                        .tag(this)
                                        .params("password",edt_one.getText().toString())
                                        .params("mobile",sharehelper.getSharedPreference("phone","").toString())
                                        .execute(new StringCallback() {
                                            @Override
                                            public void onSuccess(String s, Call call, Response response) {
                                                Gson gson = new Gson();
                                                TongyongBean tongyongBean = gson.fromJson(s,TongyongBean.class);
                                                if (tongyongBean.getState()==1){
                                                    ToastUtils.shortToast("修改成功");
                                                    Handler handler = new Handler();
                                                    handler.postDelayed(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            WodePasswordActivity.this.finish();
                                                        }
                                                    },1000);

                                                }else{
                                                    ToastUtils.shortToast(tongyongBean.getMessage());
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
