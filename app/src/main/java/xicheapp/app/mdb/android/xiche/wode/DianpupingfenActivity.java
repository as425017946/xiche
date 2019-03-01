package xicheapp.app.mdb.android.xiche.wode;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
import xicheapp.app.mdb.android.xiche.bean.ServiceBean;

public class DianpupingfenActivity extends BaseActivity {
    SharedPreferencesHelper sharehelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dianpupingfen);
        ButterKnife.bind(this);
        sharehelper = new SharedPreferencesHelper(DianpupingfenActivity.this,"xicheqishou");
        setTtitle();
        fanhui();
        setinfo();
    }

    /**
     * 展示信息
     */
    @BindView(R.id.pingfen_pingfen)
    TextView tv_pingfen;
    @BindView(R.id.pingfen_img1)
    ImageView img1;
    @BindView(R.id.pingfen_img2)
    ImageView img2;
    @BindView(R.id.pingfen_img3)
    ImageView img3;
    @BindView(R.id.pingfen_img4)
    ImageView img4;
    @BindView(R.id.pingfen_img5)
    ImageView img5;
    @BindView(R.id.pingfen_img6)
    ImageView img6;
    @BindView(R.id.pingfen_img7)
    ImageView img7;
    @BindView(R.id.pingfen_img8)
    ImageView img8;
    @BindView(R.id.pingfen_img9)
    ImageView img9;
    @BindView(R.id.pingfen_img10)
    ImageView img10;
    private void setinfo() {
        OkGo.post(Api.selectpingfen)
                .params("serviceManId",sharehelper.getSharedPreference("id","").toString())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Log.e("我的评分",s);
                        Gson gson = new Gson();
                        ServiceBean serviceBean = gson.fromJson(s,ServiceBean.class);
                        if (serviceBean.getState()==1){
                            tv_pingfen.setText(serviceBean.getData().getGrade()+"分");
                                if (serviceBean.getData().getComment_t()>=1)   {
                                    img1.setImageDrawable(DianpupingfenActivity.this.getResources().getDrawable(R.mipmap.starshiti));
                                }
                                if (serviceBean.getData().getComment_t()>=2)   {
                                    img2.setImageDrawable(DianpupingfenActivity.this.getResources().getDrawable(R.mipmap.starshiti));
                                }
                                if (serviceBean.getData().getComment_t()>=3)   {
                                    img3.setImageDrawable(DianpupingfenActivity.this.getResources().getDrawable(R.mipmap.starshiti));
                                }
                                if (serviceBean.getData().getComment_t()>=4)   {
                                    img4.setImageDrawable(DianpupingfenActivity.this.getResources().getDrawable(R.mipmap.starshiti));
                                }
                                if (serviceBean.getData().getComment_t()>=5)   {
                                    img5.setImageDrawable(DianpupingfenActivity.this.getResources().getDrawable(R.mipmap.starshiti));
                                }
                                if (serviceBean.getData().getComment_r()>=1){
                                    img6.setImageDrawable(DianpupingfenActivity.this.getResources().getDrawable(R.mipmap.starshiti));
                                }
                                if (serviceBean.getData().getComment_r()>2){
                                    img7.setImageDrawable(DianpupingfenActivity.this.getResources().getDrawable(R.mipmap.starshiti));
                                }
                                if (serviceBean.getData().getComment_r()>3){
                                    img8.setImageDrawable(DianpupingfenActivity.this.getResources().getDrawable(R.mipmap.starshiti));
                                }
                                if (serviceBean.getData().getComment_r()>4){
                                    img9.setImageDrawable(DianpupingfenActivity.this.getResources().getDrawable(R.mipmap.starshiti));
                                }
                                if (serviceBean.getData().getComment_r()>4){
                                    img10.setImageDrawable(DianpupingfenActivity.this.getResources().getDrawable(R.mipmap.starshiti));
                                }

                        }else {
                            ToastUtils.shortToast(serviceBean.getMessage());
                        }
                    }
                });
    }

    /**
     * 写入title名字
     */
    @BindView(R.id.xiche_title)
    TextView ttitle;
    @BindView(R.id.xiche_quxiao_title)
    TextView ttitle2;
    private void setTtitle(){
        ttitle.setText("我的评价");
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
                DianpupingfenActivity.this.finish();
            }
        });
    }
}
