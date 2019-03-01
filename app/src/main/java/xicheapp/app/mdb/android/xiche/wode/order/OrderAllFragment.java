package xicheapp.app.mdb.android.xiche.wode.order;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

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
import xicheapp.app.mdb.android.xiche.adapter.MyOrderAdapter;
import xicheapp.app.mdb.android.xiche.bean.MyOrderBean;
import xicheapp.app.mdb.android.xiche.view.XListView;

/**
 * A simple {@link Fragment} subclass.
 * 我的订单 全部
 */
public class OrderAllFragment extends Fragment implements XListView.IXListViewListener{
    SharedPreferencesHelper sharehelper;
    Context context;
    String status="";
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_order_all, container, false);
        ButterKnife.bind(this,view);
        return view;
    }

    MyOrderAdapter adapter;
    ArrayList<MyOrderBean> arrayList = new ArrayList<>();
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sharehelper = new SharedPreferencesHelper(context,"xicheqishou");
        adapter = new MyOrderAdapter(context,arrayList);
        setinfo();
    }

    /**
     * 提交信息
     */
    //设置每页页数和当前页面
    int pageSize=10,page=1;
    private Handler mHandler;
    @BindView(R.id.myorder_show)
    XListView mListView;
    private void setinfo(){
        mHandler = new Handler();
        OkGo.post(Api.selectorder)
                .tag(this)
                .params("serviceManId",sharehelper.getSharedPreference("id","").toString())
                .params("page",page)
                .params("pageSize",pageSize)
                .params("status",status)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Log.e("全部订单",s);
                        arrayList.clear();
                        Gson gson = new Gson();
                        MyOrderBean myOrderBean = gson.fromJson(s,MyOrderBean.class);
                        if (myOrderBean.getState()==1){
                            for (int i = 0; i <myOrderBean.getData().getPageInfo().getList().size() ; i++) {
                                arrayList.add(myOrderBean);
                            }
                            mListView.setAdapter(adapter);
                            if (pageSize>myOrderBean.getData().getPageInfo().getTotal()){
                                mListView.zhanshi(false);
                            }else{
                                mListView.zhanshi(true);
                            }

                        }else {
                            ToastUtils.shortToast(myOrderBean.getMessage());
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
    private void setinfo2(int page2,int pageSize2){
        mHandler = new Handler();
        OkGo.post(Api.selectorder)
                .tag(this)
                .params("serviceManId",sharehelper.getSharedPreference("id","").toString())
                .params("page",page2)
                .params("pageSize",pageSize2)
                .params("status",status)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
//                        Log.e("全部订单",s);
                        arrayList.clear();
                        Gson gson = new Gson();
                        MyOrderBean myOrderBean = gson.fromJson(s,MyOrderBean.class);
                        if (myOrderBean.getState()==1){
                            for (int i = 0; i <myOrderBean.getData().getPageInfo().getList().size() ; i++) {
                                arrayList.add(myOrderBean);
                            }
                            adapter.notifyDataSetChanged();
                            if (pageSize>myOrderBean.getData().getPageInfo().getTotal()){
                                mListView.zhanshi(false);
                            }else{
                                mListView.zhanshi(true);
                            }

                        }else {
                            ToastUtils.shortToast(myOrderBean.getMessage());
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
