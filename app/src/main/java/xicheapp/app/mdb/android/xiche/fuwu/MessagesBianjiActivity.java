package xicheapp.app.mdb.android.xiche.fuwu;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.tu.loadingdialog.LoadingDailog;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;
import xicheapp.app.mdb.android.xiche.BaseActivity;
import xicheapp.app.mdb.android.xiche.R;
import xicheapp.app.mdb.android.xiche.Utils.Api;
import xicheapp.app.mdb.android.xiche.Utils.SharedPreferencesHelper;
import xicheapp.app.mdb.android.xiche.Utils.ToastUtils;
import xicheapp.app.mdb.android.xiche.adapter.Messages2Adapter;
import xicheapp.app.mdb.android.xiche.bean.MessagesBean;
import xicheapp.app.mdb.android.xiche.bean.TongyongBean;
import xicheapp.app.mdb.android.xiche.view.XListView;

/**
 * 系统消息
 */
public class MessagesBianjiActivity extends BaseActivity implements XListView.IXListViewListener{

    public static boolean isMulChoice = false; //是否多选
    Messages2Adapter adapter;
    ArrayList<MessagesBean> arrayList = new ArrayList<>();
    @BindView(R.id.messages_xlistview)
    XListView mListView;
    SharedPreferencesHelper sharehelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messagesbianji);
        ButterKnife.bind(this);
        adapter = new Messages2Adapter(MessagesBianjiActivity.this,arrayList);
        sharehelper = new SharedPreferencesHelper(MessagesBianjiActivity.this,"xicheqishou");
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
    @BindView(R.id.xiche_quxiao_title22)
    TextView ttitle2;
    @BindView(R.id.xiche_xiaoxi22)
    ImageView img_bianji;
    @BindView(R.id.messagesbianji_footer)
    LinearLayout ll_footer;
    @BindView(R.id.messagesbianji_quanxuan)
    CheckBox checkBox_quanxuan;
    @BindView(R.id.messagesbianji_delete)
    Button btn_delete;
    public static boolean isChecked = false;
    private void setTtitle(){
        img_bianji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ll_footer.setVisibility(View.VISIBLE);
                if (isMulChoice==false){
                    isMulChoice =true;
                }else {
                    isMulChoice =false;
                }
                adapter.notifyDataSetChanged();
                img_bianji.setVisibility(View.GONE);
                ttitle2.setVisibility(View.VISIBLE);
            }
        });
        ttitle.setText("消息");
        ttitle2.setText("完成");
        ttitle2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ll_footer.setVisibility(View.GONE);
                img_bianji.setVisibility(View.VISIBLE);
                ttitle2.setVisibility(View.GONE);
                isMulChoice =false;
                adapter.notifyDataSetChanged();
            }
        });

        // 全选——全不选
        Map<Integer, Boolean> isCheck = adapter.getMap();
        checkBox_quanxuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkBox_quanxuan.isChecked()==true){
                    adapter.initCheck(true);
                    isMulChoice =true;
                    isChecked = true;
                    adapter.notifyDataSetChanged();
                }else {
                    adapter.initCheck(false);
                    isMulChoice =true;
                    isChecked = false;
                    adapter.notifyDataSetChanged();
                }
            }
        });
        //删除
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 拿到全部数据
                Map<Integer, Boolean> isCheck_delete = adapter.getMap();
                Map<Integer, String> isCheck_delete2 = adapter.getMap2();
                // 获取到条目数量。map.size = list.size,所以
                int count = adapter.getCount();
                // 遍历
                for (int i = 0; i < count; i++) {
                    // 删除有两个map和list都要删除 ,计算方式
                    int position = i - (count - adapter.getCount());
                    // 推断状态 true为删除
                    if (isCheck_delete.get(i) != null && isCheck_delete.get(i)) {
                        // listview删除数据
                        deletes(isCheck_delete2.get(i)+"");
                        isCheck_delete.remove(i);
                        isCheck_delete2.remove(i);
                    }else {
//                        ToastUtils.shortToast("请选择要删除的信息");
                    }
                }
            }
        });
    }

    /**
     * 删除
     */
    private void deletes(final String id){
        OkGo.post(Api.deletemessages)
                .tag(this)
                .params("id",id)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {

                        Gson gson = new Gson();
                        TongyongBean tongyongBean = gson.fromJson(s,TongyongBean.class);
                        if (tongyongBean.getState()==1){
                            LoadingDailog.Builder loadBuilder=new LoadingDailog.Builder(MessagesBianjiActivity.this)
                                    .setMessage("删除中...")
                                    .setCancelable(false)
                                    .setCancelOutside(false);
                            final LoadingDailog dialog=loadBuilder.create();
                            dialog.show();
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    dialog.dismiss();
                                    setinfo();
                                }
                            },1000);

                        }else {
                            ToastUtils.shortToast(tongyongBean.getMessage());
                        }
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
                isMulChoice = false;
                MessagesBianjiActivity.this.finish();
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
