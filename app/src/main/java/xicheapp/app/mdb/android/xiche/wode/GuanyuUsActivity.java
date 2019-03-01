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
 * 关于我们
 */
public class GuanyuUsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guanyu_us);
        ButterKnife.bind(this);
        setTtitle();
        fanhui();
        setinfo();
    }

    /**
     * 展示信息
     *
     * 介绍
     * 版本
     * 使用说明
     * 协议
     */
    @BindView(R.id.us_jieshao)
    LinearLayout ll_jieshao;
    @BindView(R.id.us_banben)
    LinearLayout ll_banben;
    @BindView(R.id.us_shiyongxieyi)
    LinearLayout ll_shiyongxieyi;
    @BindView(R.id.us_xieyi)
    LinearLayout ll_xieyi;
    private void setinfo() {
        ll_jieshao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GuanyuUsActivity.this,USinfoActivity.class);
                intent.putExtra("uszhi","0");
                startActivity(intent);
            }
        });
        ll_banben.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GuanyuUsActivity.this,BanBenActivity.class);
                startActivity(intent);
            }
        });
        ll_shiyongxieyi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GuanyuUsActivity.this,USinfoActivity.class);
                intent.putExtra("uszhi","2");
                startActivity(intent);
            }
        });
        ll_xieyi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GuanyuUsActivity.this,USinfoActivity.class);
                intent.putExtra("uszhi","3");
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
        ttitle.setText("关于我们");
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
                GuanyuUsActivity.this.finish();
            }
        });
    }
}
