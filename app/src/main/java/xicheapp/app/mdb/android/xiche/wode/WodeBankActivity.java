package xicheapp.app.mdb.android.xiche.wode;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
import xicheapp.app.mdb.android.xiche.bean.BankBean;

/**
 * 展示银行卡信息
 */
public class WodeBankActivity extends BaseActivity {
    SharedPreferencesHelper sharedPreferencesHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wode_bank);
        ButterKnife.bind(this);
        sharedPreferencesHelper = new SharedPreferencesHelper(WodeBankActivity.this,"xicheqishou");
        setTtitle();
        fanhui();
        setinfo();
        selectbank();
    }
    /**
     * 写入title名字
     */
    @BindView(R.id.xiche_title)
    TextView ttitle;
    @BindView(R.id.xiche_quxiao_title)
    TextView ttitle2;
    private void setTtitle(){
        ttitle.setText("更换银行卡");
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
                WodeBankActivity.this.finish();
            }
        });
    }
    /**
     * 提交信息
     */
    @BindView(R.id.jiebang_tijiaoshenhe)
    Button btn_tijiao;
    private void setinfo(){
        btn_tijiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WodeBankActivity.this,JieBangPhoneActivity.class);
                intent.putExtra("bank2zhi","1");
                startActivity(intent);
                WodeBankActivity.this.finish();
            }
        });
    }
    /**
     * 查询银行卡
     */
    @BindView(R.id.bank_card)
    TextView tv_card;
    @BindView(R.id.bank_kaihuhang)
    TextView tv_kaihuhang;
    @BindView(R.id.bank_name)
    TextView tv_name;
    @BindView(R.id.bank_phone)
    TextView tv_phone;
    private void selectbank(){
        OkGo.post(Api.selectbankmore)
                .tag(this)
                .params("serviceManId",sharedPreferencesHelper.getSharedPreference("id","").toString())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
//                        Log.e("解绑银行卡测试",s);
                        Gson gson = new Gson();
                        BankBean bankBean = gson.fromJson(s,BankBean.class);
                        if (bankBean.getState()==1){
                            if (bankBean.getData()==null){

                            }else{
                                tv_card.setText(bankBean.getData().getCard_no().substring(0,4)+"****"+bankBean.getData().getCard_no().substring(bankBean.getData().getCard_no().length()-4,bankBean.getData().getCard_no().length()));
                                tv_kaihuhang.setText(bankBean.getData().getCard_produce());
                                tv_name.setText(bankBean.getData().getCard_owner());
                                tv_phone.setText(bankBean.getData().getCard_mobile().substring(0,3)+"****"+bankBean.getData().getCard_mobile().substring(bankBean.getData().getCard_mobile().length()-4,bankBean.getData().getCard_mobile().length()));

                            }

                        }else {
                            ToastUtils.shortToast(bankBean.getMessage());
                        }
                    }
                });
    }
}
