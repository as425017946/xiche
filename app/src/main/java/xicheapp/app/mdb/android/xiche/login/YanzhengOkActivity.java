package xicheapp.app.mdb.android.xiche.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import xicheapp.app.mdb.android.xiche.BaseActivity;
import xicheapp.app.mdb.android.xiche.MainActivity;
import xicheapp.app.mdb.android.xiche.R;
import xicheapp.app.mdb.android.xiche.wode.WodeBank2Activity;
import xicheapp.app.mdb.android.xiche.wode.WodeBankActivity;

/**
 * 提交身份验证完成页面
 */
public class YanzhengOkActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yanzheng_ok);
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
        ttitle.setText("提交审核");
    }
    /**
     * 返回
     */
    @BindView(R.id.xiche_fanhui)
    LinearLayout lfanhui;
    @BindView(R.id.xiche_imgfanhui)
    ImageView img;
    private void fanhui(){
        img.setVisibility(View.INVISIBLE);
        lfanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(YanzhengOkActivity.this,WodeBank2Activity.class);
                startActivity(intent);
                YanzhengOkActivity.this.finish();
            }
        });
    }

    /**
     *提交信息
     */
    @BindView(R.id.sfyz_ok)
    Button btn_next;
    private void setinfo(){
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(YanzhengOkActivity.this,YanzhengShenheActivity.class);
//                intent.putExtra("status","8");
//                startActivity(intent);
                Intent intent = new Intent(YanzhengOkActivity.this,MainActivity.class);
                startActivity(intent);
                YanzhengOkActivity.this.finish();
            }
        });
    }
}
