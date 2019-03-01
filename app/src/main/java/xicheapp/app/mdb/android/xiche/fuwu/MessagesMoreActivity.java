package xicheapp.app.mdb.android.xiche.fuwu;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import xicheapp.app.mdb.android.xiche.BaseActivity;
import xicheapp.app.mdb.android.xiche.R;

public class MessagesMoreActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages_more);
        ButterKnife.bind(this);
        setTtitle();
        fanhui();
        setinfo();
    }

    /**
     * 展示信息
     */
    @BindView(R.id.messages_more_title)
    TextView tv_title;
    @BindView(R.id.messages_more_content)
    TextView tv_content;
    @BindView(R.id.messages_more_time)
    TextView tv_time;
    @BindView(R.id.messages_more_url)
    TextView tv_url;

    private void setinfo() {
        tv_title.setText(getIntent().getStringExtra("title"));
        tv_content.setText(getIntent().getStringExtra("content"));
        tv_time.setText(getIntent().getStringExtra("time"));
        if (getIntent().getStringExtra("activityurl")==null || getIntent().getStringExtra("activityurl").equals("")){
            tv_url.setText("");
        }else {
            tv_url.setText(getIntent().getStringExtra("activityurl"));
        }
    }

    /**
     * 写入title名字
     */
    @BindView(R.id.xiche_title)
    TextView ttitle;
    @BindView(R.id.xiche_quxiao_title)
    TextView ttitle2;
    private void setTtitle(){
        ttitle.setText("消息详情");
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
                MessagesMoreActivity.this.finish();
            }
        });
    }
}
