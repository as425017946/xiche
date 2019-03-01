package xicheapp.app.mdb.android.xiche.fuwu;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import xicheapp.app.mdb.android.xiche.Utils.ToastUtils;
import xicheapp.app.mdb.android.xiche.bean.TongyongBean;

public class LiuYanActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liu_yan);
        ButterKnife.bind(this);
        setTtitle();
        fanhui();
        setinof();
    }

    @BindView(R.id.liuyan_edt)
    EditText edt_info;
    @BindView(R.id.liulan_tijiao)
    Button btn_tijiao;
    private void setinof() {
        btn_tijiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edt_info.getText().toString().equals("")){
                    ToastUtils.shortToast("请输入要留言的信息");
                }else {
                    OkGo.post(Api.liuyan)
                            .tag(this)
                            .params("orderUUID",getIntent().getStringExtra("orderid"))
                            .params("serviceManWord",edt_info.getText().toString())
                            .execute(new StringCallback() {
                                @Override
                                public void onSuccess(String s, Call call, Response response) {
                                    Gson gson = new Gson();
                                    TongyongBean tongyongBean = gson.fromJson(s,TongyongBean.class);
                                    if (tongyongBean.getState()==1){
                                        ToastUtils.shortToast("留言成功，我们将尽快为您处理");
                                        Handler handler = new Handler();
                                        handler.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                LiuYanActivity.this.finish();
                                            }
                                        },1000);
                                    }else {
                                        ToastUtils.shortToast(tongyongBean.getMessage());
                                    }
                                }
                            });
                }
            }
        });

    }

    @BindView(R.id.xiche_fanhui)
    LinearLayout lfanhui;
    @BindView(R.id.xiche_imgfanhui)
    ImageView img;
    private void fanhui(){
        lfanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LiuYanActivity.this.finish();
            }
        });
    }

    @BindView(R.id.xiche_title)
    TextView ttitle;
    private void setTtitle() {
        ttitle.setText("联系客服留言");
    }
}
