package xicheapp.app.mdb.android.xiche.fuwu;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;
import xicheapp.app.mdb.android.xiche.R;
import xicheapp.app.mdb.android.xiche.Utils.Api;
import xicheapp.app.mdb.android.xiche.Utils.SharedPreferencesHelper;
import xicheapp.app.mdb.android.xiche.Utils.ToastUtils;
import xicheapp.app.mdb.android.xiche.adapter.MessagesAdapter;
import xicheapp.app.mdb.android.xiche.bean.MessagesBean;
import xicheapp.app.mdb.android.xiche.view.XListView;

/**
 * 系统消息
 */
public class MessagesActivity extends AppCompatActivity implements XListView.IXListViewListener{
    MessagesAdapter adapter;
    ArrayList<MessagesBean> arrayList = new ArrayList<>();
    @BindView(R.id.messages_xlistview)
    XListView mListView;
    SharedPreferencesHelper sharehelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);
        ButterKnife.bind(this);
        adapter = new MessagesAdapter(MessagesActivity.this,arrayList);
        sharehelper = new SharedPreferencesHelper(MessagesActivity.this,"xicheqishou");
        setTtitle();
        fanhui();
        setinfo();
    }

    /**
     * 写入信息
     */
    //设置每页页数和当前页面
    int pageSize=10,page=1;
    private Handler mHandler;

    private void setinfo() {
        mHandler = new Handler();
        //展示信息
        OkGo.post(Api.selectmessages)
                .tag(this)
                .params("page",page)
                .params("pageSize",pageSize)
                .params("personServiceId",sharehelper.getSharedPreference("id","").toString())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Log.e("消息界面",s);
                        arrayList.clear();
                        Gson gson = new Gson();
                        MessagesBean messagesBean = gson.fromJson(s,MessagesBean.class);
                        if (messagesBean.getState()==1){
                            if (messagesBean.getData().getPageInfo().getTotal()==0){
                                ToastUtils.shortToast("暂无系统消息");
                            }else{
                                for (int i = 0; i <messagesBean.getData().getPageInfo().getList().size() ; i++) {
                                    arrayList.add(messagesBean);
                                }
                                mListView.setAdapter(adapter);
                                if (pageSize>messagesBean.getData().getPageInfo().getTotal()){
                                    mListView.zhanshi(false);
                                }else{
                                    mListView.zhanshi(true);
                                }
                            }
                        }else{
                            ToastUtils.shortToast(messagesBean.getMessage());
                        }
                    }
                });
        mListView.setPullLoadEnable(true);
        mListView.setXListViewListener(this);
    }
    private void setinfo2(int page2,int pageSize2) {
        mHandler = new Handler();
        //展示信息
        OkGo.post(Api.selectmessages)
                .tag(this)
                .params("page",page2)
                .params("pageSize",pageSize2)
                .params("personServiceId",sharehelper.getSharedPreference("id","").toString())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
//                        Log.e("消息界面",s);
                        arrayList.clear();
                        Gson gson = new Gson();
                        MessagesBean messagesBean = gson.fromJson(s,MessagesBean.class);
                        if (messagesBean.getState()==1){
                            if (messagesBean.getData().getPageInfo().getTotal()==0){
                                ToastUtils.shortToast("暂无系统消息");
                            }else{
                                for (int i = 0; i <messagesBean.getData().getPageInfo().getList().size() ; i++) {
                                    arrayList.add(messagesBean);
                                }
                                adapter.notifyDataSetChanged();
                                if (pageSize>messagesBean.getData().getPageInfo().getTotal()){
                                    mListView.zhanshi(false);
                                }else{
                                    mListView.zhanshi(true);
                                }
                            }
                        }else{
                            ToastUtils.shortToast(messagesBean.getMessage());
                        }
                    }
                });

    }
    /**
     * 写入title名字
     */
    @BindView(R.id.xiche_title)
    TextView ttitle;
    @BindView(R.id.xiche_xiaoxi)
    ImageView img_bianji;
    private void setTtitle(){
        ttitle.setText("消息");
        img_bianji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MessagesActivity.this,MessagesBianjiActivity.class);
                startActivity(intent);
            }
        });
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
                MessagesActivity.this.finish();
            }
        });
    }
    //下拉刷新
    @Override
    public void onRefresh() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                page = 1;
                pageSize = 10;
                setinfo();
                onLoad();
            }
        }, 2000);
    }

    //上拉加载
    @Override
    public void onLoadMore() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                setinfo2(page ++,(pageSize+10));
                adapter.notifyDataSetChanged();
                onLoad();
            }
        }, 2000);
    }

    private void onLoad() {
        mListView.stopRefresh();
        mListView.stopLoadMore();
        //获取当前时间
        Date curDate = new Date(System.currentTimeMillis());
        //格式化
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日HH:mm:ss");
        String time = formatter.format(curDate);
        mListView.setRefreshTime(time);
    }

}
