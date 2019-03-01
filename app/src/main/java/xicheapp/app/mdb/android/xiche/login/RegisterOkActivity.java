package xicheapp.app.mdb.android.xiche.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import xicheapp.app.mdb.android.xiche.BaseActivity;
import xicheapp.app.mdb.android.xiche.R;

/**
 * 注册成功
 */
public class RegisterOkActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_ok);
        ButterKnife.bind(this);
        setnext();
        setTtitle();
        fanhui();
    }
    /**
     * 写入title名字
     */
    @BindView(R.id.xiche_title)
    TextView ttitle;
    @BindView(R.id.xiche_quxiao_title)
    TextView ttitle2;
    private void setTtitle(){
        ttitle.setText("注册");
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
                Intent intent = new Intent(RegisterOkActivity.this,PasswordActivity.class);
                startActivity(intent);
                RegisterOkActivity.this.finish();
            }
        });
    }
    /**
     * 下一步
     */
    @BindView(R.id.registerok_next)
    Button btn_next;
    private void setnext(){
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterOkActivity.this,LoginActivity.class);
                startActivity(intent);
                RegisterOkActivity.this.finish();
            }
        });
    }
}
