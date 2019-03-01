package xicheapp.app.mdb.android.xiche.wode;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import xicheapp.app.mdb.android.xiche.BaseActivity;
import xicheapp.app.mdb.android.xiche.R;

/**
 * 关于我们详情页面
 */
public class USinfoActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usinfo);
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
        if (getIntent().getStringExtra("uszhi").equals("0")){
            ttitle.setText("平台介绍");
        }else if (getIntent().getStringExtra("uszhi").equals("1")){
            ttitle.setText("版本信息");
        }else if (getIntent().getStringExtra("uszhi").equals("2")){
            ttitle.setText("使用协议");
        }else if (getIntent().getStringExtra("uszhi").equals("3")){
            ttitle.setText("服务协议");
        }

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
                USinfoActivity.this.finish();
            }
        });
    }

    /**
     * 展示信息
     */
    @BindView(R.id.aboutus_1)
    TextView tv_1;
    @BindView(R.id.aboutus_2)
    TextView tv_2;
    @BindView(R.id.aboutus_3)
    TextView tv_3;
    @BindView(R.id.aboutus_4)
    TextView tv_4;
    private void setinfo() {
        if (getIntent().getStringExtra("uszhi").equals("0")){
            tv_1.setText("");
            tv_1.setVisibility(View.VISIBLE);
            tv_2.setVisibility(View.GONE);
            tv_3.setVisibility(View.GONE);
            tv_4.setVisibility(View.GONE);
        }else if (getIntent().getStringExtra("uszhi").equals("1")){
            tv_2.setText("");
            tv_2.setVisibility(View.VISIBLE);
            tv_1.setVisibility(View.GONE);
            tv_3.setVisibility(View.GONE);
            tv_4.setVisibility(View.GONE);
        }else if (getIntent().getStringExtra("uszhi").equals("2")){
            tv_3.setText("");
            tv_3.setVisibility(View.VISIBLE);
            tv_2.setVisibility(View.GONE);
            tv_1.setVisibility(View.GONE);
            tv_4.setVisibility(View.GONE);
        }else if (getIntent().getStringExtra("uszhi").equals("3")){
            tv_4.setVisibility(View.VISIBLE);
            tv_2.setVisibility(View.GONE);
            tv_3.setVisibility(View.GONE);
            tv_1.setVisibility(View.GONE);
        }
    }
}
