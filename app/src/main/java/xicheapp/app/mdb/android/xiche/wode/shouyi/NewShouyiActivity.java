package xicheapp.app.mdb.android.xiche.wode.shouyi;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.airsaid.pickerviewlibrary.TimePickerView;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;
import xicheapp.app.mdb.android.xiche.BaseActivity;
import xicheapp.app.mdb.android.xiche.R;
import xicheapp.app.mdb.android.xiche.Utils.Api;
import xicheapp.app.mdb.android.xiche.Utils.SharedPreferencesHelper;
import xicheapp.app.mdb.android.xiche.Utils.ToastUtils;
import xicheapp.app.mdb.android.xiche.adapter.ShouyiAdapter;
import xicheapp.app.mdb.android.xiche.bean.NewShouyiBean;
import xicheapp.app.mdb.android.xiche.bean.ZongshouyiBean;
import xicheapp.app.mdb.android.xiche.view.XListView;
import xicheapp.app.mdb.android.xiche.wode.DianpupingfenActivity;

public class NewShouyiActivity extends BaseActivity implements XListView.IXListViewListener{
    SharedPreferencesHelper sharehelper;
    ShouyiAdapter adapter;
    ArrayList<NewShouyiBean> arrayList = new ArrayList<>();
    @BindView(R.id.shouyi_xlistview)
    XListView mListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_shouyi);
        ButterKnife.bind(this);
        sharehelper = new SharedPreferencesHelper(NewShouyiActivity.this,"xicheqishou");
        adapter = new ShouyiAdapter(NewShouyiActivity.this,arrayList);
        setTtitle();
        fanhui();
        showtimemonthday();
        selectinfo();
    }

    /**
     * 展示时间信息
     */
    @BindView(R.id.shouyi_star)
    TextView tv_star;
    @BindView(R.id.shouyi_end)
    TextView tv_end;
    private void showtimemonthday() {
        //获取当前时间
        Date curDate = new Date(System.currentTimeMillis());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String time = formatter.format(curDate);
        tv_star.setText(time);
        tv_end.setText(time);

        setinfo(tv_star.getText().toString(),tv_end.getText().toString());
    }

    /**
     * 写入信息
     */
    //设置每页页数和当前页面
    int pageSize=10,page=1;
    private Handler mHandler;
    private void setinfo(String startime,String endtime) {
        mHandler = new Handler();
        OkGo.post(Api.selectshouyi)
                .tag(this)
                .params("page",page)
                .params("pageSize",pageSize)
                .params("serviceManId",sharehelper.getSharedPreference("id","").toString())
                .params("startTime",startime+" 00:00:00")
                .params("endTime",endtime+" 23:59:59")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Gson gson = new Gson();
                        NewShouyiBean shouyiBean = gson.fromJson(s,NewShouyiBean.class);
                        arrayList.clear();
                        if (shouyiBean.getState()==1){
                            for (int i = 0; i <shouyiBean.getData().getPageInfo().getList().size() ; i++) {
                                arrayList.add(shouyiBean);
                            }
                            if (shouyiBean.getData().getPageInfo().getList().size()==1){
                                ToastUtils.shortToast("当前暂无查询信息！");
                            }
                            mListView.setAdapter(adapter);
                            if (pageSize>shouyiBean.getData().getPageInfo().getTotal()){
                                mListView.zhanshi(false);
                            }else {
                                mListView.zhanshi(true);
//                                ToastUtils.shortToast("暂无查询信息！");
                            }
                        }else {
                            ToastUtils.shortToast(shouyiBean.getMessage());
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        ToastUtils.shortToast(e+"");
                    }
                });
        mListView.setPullLoadEnable(true);
        mListView.setXListViewListener(this);
    }
    private void setinfo2(int page2, final int pageSize2) {
        mHandler = new Handler();
        OkGo.post(Api.selectshouyi)
                .tag(this)
                .params("page",page2)
                .params("pageSize",pageSize2)
                .params("serviceManId",sharehelper.getSharedPreference("id","").toString())
                .params("startTime",tv_star.getText().toString()+" 00:00:00")
                .params("endTime",tv_end.getText().toString()+" 23:59:59")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Gson gson = new Gson();
                        NewShouyiBean shouyiBean = gson.fromJson(s,NewShouyiBean.class);
                        arrayList.clear();
                        if (shouyiBean.getState()==1){
                            for (int i = 0; i <shouyiBean.getData().getPageInfo().getList().size() ; i++) {
                                arrayList.add(shouyiBean);
                            }
                            adapter.notifyDataSetChanged();
                            if (pageSize>shouyiBean.getData().getPageInfo().getTotal()){
                                mListView.zhanshi(false);
                            }else {
                                mListView.zhanshi(true);
//                                ToastUtils.shortToast("暂无查询信息！");
                            }
                        }else {
                            ToastUtils.shortToast(shouyiBean.getMessage());
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        ToastUtils.shortToast(e+"");
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
                setinfo("","");
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

    /**
     * 查询信息
     */
    @BindView(R.id.shouyi_zongshouyi)
    TextView tv_zongshouyi;
    @BindView(R.id.shouyi_chaxun)
    Button btn_chaxun;
    private void selectinfo() {
        OkGo.post(Api.selectzongshouyi)
                .tag(this)
                .params("serviceManId",sharehelper.getSharedPreference("id","").toString())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        //查询店家总收益
                        Gson gson = new Gson();
                        ZongshouyiBean zongshouyiBean = gson.fromJson(s,ZongshouyiBean.class);
                        if (zongshouyiBean.getState()==1){
                            tv_zongshouyi.setText(zongshouyiBean.getData().getMoney()+"元");
                        }else {
                            ToastUtils.shortToast(zongshouyiBean.getMessage());
                        }

                    }
                });
        tv_star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimer(tv_star);
            }
        });
        tv_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimer(tv_end);
            }
        });

        btn_chaxun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tv_star.getText().equals("开始时间") || tv_end.getText().equals("结束时间")){
                    ToastUtils.shortToast("请选择要查询的日期后再来查询");
                }else {
                    setinfo(tv_star.getText().toString()+" 00:00:00",tv_end.getText().toString()+" 23:59:59");
                }
            }
        });
    }
    //时间选择器
    private void showTimer(final TextView editText){
        //     TimePickerView 同样有上面设置样式的方法
        TimePickerView mTimePickerView = new TimePickerView(NewShouyiActivity.this, TimePickerView.Type.YEAR_MONTH_DAY);// 四种选择模式，年月日时分，年月日，时分，月日时分
        // 设置是否循环
        mTimePickerView.setCyclic(true);

        // 设置滚轮文字大小
        mTimePickerView.setTextSize(TimePickerView.TextSize.BIG);
        // 设置时间可选范围(结合 setTime 方法使用,必须在)
//        Calendar calendar = Calendar.getInstance();
//        mTimePickerView.setRange(calendar.get(Calendar.YEAR) - 100, calendar.get(Calendar.YEAR));
        // 设置选中时间
        mTimePickerView.setTime(new Date());//设置选中的时间  new date（）是今天的时间
        mTimePickerView.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date) {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
//                Toast.makeText(context, format.format(date), Toast.LENGTH_SHORT).show();
                editText.setText(format.format(date));
            }
        });
        mTimePickerView.show();
    }



    /**
     * 写入title名字
     */
    @BindView(R.id.xiche_title)
    TextView ttitle;
    @BindView(R.id.xiche_quxiao_title)
    TextView ttitle2;
    private void setTtitle(){
        ttitle.setText("我的收益");
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
                NewShouyiActivity.this.finish();
            }
        });
    }
}
