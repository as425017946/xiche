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

/**
 * 设置
 */
public class SettingActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
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
        ttitle.setText("设置");
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
                SettingActivity.this.finish();
            }
        });
    }
    /**
     * 上传信息
     * 账户安全
     * 账号关联
     * 关于我们
     */
    @BindView(R.id.setting_anquan)
    LinearLayout ll_anquan;
    @BindView(R.id.setting_guanlian)
    LinearLayout ll_guanlian;
    @BindView(R.id.setting_guanyu)
    LinearLayout ll_guanyu;
    private void setinfo(){
        ll_anquan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingActivity.this,AccountsecurityActivity.class);
                startActivity(intent);
            }
        });
        ll_guanlian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingActivity.this,ZhanghaoguanlianActivity.class);
                startActivity(intent);
            }
        });
        ll_guanyu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingActivity.this,GuanyuUsActivity.class);
                startActivity(intent);
            }
        });
    }

}
