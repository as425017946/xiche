package xicheapp.app.mdb.android.xiche.wode;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import xicheapp.app.mdb.android.xiche.BaseActivity;
import xicheapp.app.mdb.android.xiche.R;
import xicheapp.app.mdb.android.xiche.login.BankActivity;

/**
 * 账户安全
 */
public class AccountsecurityActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accountsecurity);
        ButterKnife.bind(this);
        setTtitle();
        fanhui();
        setinfo();
    }

    /**
     * 写入信息
     */
    @BindView(R.id.account_phone)
    LinearLayout ll_phone;
    @BindView(R.id.account_password)
    LinearLayout ll_password;
    @BindView(R.id.account_card)
    LinearLayout ll_card;
    private void setinfo() {
        ll_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AccountsecurityActivity.this,JieBangPhoneActivity.class);
                intent.putExtra("bank2zhi","2");
                startActivity(intent);
            }
        });
        ll_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AccountsecurityActivity.this,WodePasswordActivity.class);
                startActivity(intent);
            }
        });
        ll_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AccountsecurityActivity.this,WodeBankActivity.class);
                intent.putExtra("bankzhi","1");
                startActivity(intent);
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
        ttitle.setText("账户与安全");
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
                AccountsecurityActivity.this.finish();
            }
        });
    }
}
