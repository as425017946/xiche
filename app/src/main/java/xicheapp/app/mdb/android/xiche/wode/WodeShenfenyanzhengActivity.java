package xicheapp.app.mdb.android.xiche.wode;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.tu.loadingdialog.LoadingDailog;
import com.bumptech.glide.Glide;
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
import xicheapp.app.mdb.android.xiche.bean.GerenInfoBean;
import xicheapp.app.mdb.android.xiche.login.BankActivity;
import xicheapp.app.mdb.android.xiche.login.LoginActivity;

/**
 * 身份验证起始页
 */
public class WodeShenfenyanzhengActivity extends BaseActivity {
    SharedPreferencesHelper sharedPreferencesHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wode_shenfenyanzheng_show);
        sharedPreferencesHelper = new SharedPreferencesHelper(WodeShenfenyanzhengActivity.this,"xicheqishou");
        ButterKnife.bind(this);
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
        ttitle.setText("身份验证");
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
                WodeShenfenyanzhengActivity.this.finish();
            }
        });
    }
    /**
     * 填写信息上传
     */
    @BindView(R.id.sfyz_next)
    Button btn_next;
    @BindView(R.id.sfyz_name)
    EditText edt_name;
    @BindView(R.id.sfyz_sfcard)
    EditText edt_card;
    @BindView(R.id.sfyz_phone)
    EditText edt_phone;
    @BindView(R.id.sfyz_weixinhao)
    TextView edt_email;
    @BindView(R.id.sfyz_zhengmian)
    ImageView img_zhengmian;
    @BindView(R.id.sfyz_fanmian)
    ImageView img_fanmian;
    private void setinfo(){
        OkGo.post(Api.selectshopinfo)
                .tag(this)
                .params("serviceManId",sharedPreferencesHelper.getSharedPreference("id","").toString())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
//                        Log.e("用户信息",s);
                        Gson gson = new Gson();
                        GerenInfoBean gerenInfoBean = gson.fromJson(s,GerenInfoBean.class);
                        if (gerenInfoBean.getState()==1){
                            if (gerenInfoBean.getData()!=null){
                                LoadingDailog.Builder loadBuilder=new LoadingDailog.Builder(WodeShenfenyanzhengActivity.this)
                                        .setMessage("加载中...")
                                        .setCancelable(false)
                                        .setCancelOutside(false);
                                final LoadingDailog dialog=loadBuilder.create();
                                dialog.show();
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        dialog.dismiss();
                                    }
                                },1000);
                                edt_email.setText(gerenInfoBean.getData().getMail());
                                edt_name.setText(gerenInfoBean.getData().getName()+"");
                                edt_phone.setText(gerenInfoBean.getData().getContact_mobile()+"");
                                edt_card.setText(gerenInfoBean.getData().getId_card());
                                String[] shu = gerenInfoBean.getData().getId_card_img().split(",");
                                Glide.with(WodeShenfenyanzhengActivity.this).load(Api.ossUrl+shu[0]).into(img_zhengmian);
                                Glide.with(WodeShenfenyanzhengActivity.this).load(Api.ossUrl+shu[1]).into(img_fanmian);
                            }

                        }else {
                            ToastUtils.shortToast(gerenInfoBean.getMessage());
                        }
                    }
                });
    }
}
