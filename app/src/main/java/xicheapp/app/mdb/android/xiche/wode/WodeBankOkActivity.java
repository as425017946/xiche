
package xicheapp.app.mdb.android.xiche.wode;

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
import xicheapp.app.mdb.android.xiche.R;

public class WodeBankOkActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wode_bank_ok);
        ButterKnife.bind(this);
        setTtitle();
        fanhui();
        setinfo();
    }

    @BindView(R.id.sfyz_ok)
    Button btn_tijiao;
    private void setinfo() {
        btn_tijiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WodeBankOkActivity.this.finish();
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
        ttitle.setText("修改成功");
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
                Intent intent = new Intent(WodeBankOkActivity.this,WodeBank2Activity.class);
                intent.putExtra("bank2zhi","2");
                startActivity(intent);
                WodeBankOkActivity.this.finish();
            }
        });
    }
}
