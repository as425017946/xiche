package xicheapp.app.mdb.android.xiche.wode.order;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;
import xicheapp.app.mdb.android.xiche.R;
import xicheapp.app.mdb.android.xiche.Utils.Api;
import xicheapp.app.mdb.android.xiche.Utils.ToastUtils;
import xicheapp.app.mdb.android.xiche.bean.OrderMoresBean;

/**
 * 订单详情
 */
public class OrderMoreActivity extends AppCompatActivity {
    int bodadianhua=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_more);
        ButterKnife.bind(this);
        setTtitle();
        fanhui();
        showinfo();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions((Activity) this,
                        new String[]{Manifest.permission.CALL_PHONE},
                        1);
            }else {
                bodadianhua=2;
//                showToast("权限已申请");
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode==1){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                ToastUtils.shortToast("定位权限已申请");
                bodadianhua = 2;
            } else {
                bodadianhua = 0;
                ToastUtils.shortToast("拨打电话权限已拒绝");
            }
        }
    }

    /**
     * 展示信息
     */
    @BindView(R.id.ordermores_status)
    TextView tv_status;
    @BindView(R.id.ordermores_cartype)
    TextView tv_cartype;
    @BindView(R.id.ordermores_carinfo)
    TextView tv_carinfo;
    @BindView(R.id.ordermores_yudingwanchengtime)
    TextView tv_yudingtime;
    @BindView(R.id.ordermores_youhui)
    TextView tv_youhui;
    @BindView(R.id.ordermores__zhifu)
    TextView tv_zhifu;
    @BindView(R.id.ordermores_uuid)
    TextView tv_uuid;
    @BindView(R.id.wanchengtime)
    LinearLayout ll_wancheng;
    @BindView(R.id.ordermores_wanchengtime)
    TextView tv_wanchengtime;
    @BindView(R.id.ordermores_phone)
    ImageView img_phone;
    @BindView(R.id.ordermores_carimg)
    ImageView img_carimg;

    private void showinfo() {
        Log.e("订单号",getIntent().getStringExtra("uuid"));
        OkGo.post(Api.selectordermore)
                .tag(this)
                .params("orderUUID",getIntent().getStringExtra("uuid"))
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Log.e("订单详情",s);
                        Gson gson = new Gson();
                        final OrderMoresBean orderMoresBean = gson.fromJson(s,OrderMoresBean.class);
                        if (orderMoresBean.getState()==1){
                            if (orderMoresBean.getData()!=null){
                                //                            Log.e("车辆信息",orderMoresBean.getData().getCarNo()+"  "+orderMoresBean.getData().getBrand()+"  "+orderMoresBean.getData().getCar_color());
                                tv_carinfo.setText(orderMoresBean.getData().getCarNo()+"  "+orderMoresBean.getData().getBrand()+"  "+orderMoresBean.getData().getCar_color());
                                if (orderMoresBean.getData().getStatus()==0){
                                    tv_status.setText("已取消");
                                }else if (orderMoresBean.getData().getStatus()==1){
                                    tv_status.setText("待支付");
                                }else if (orderMoresBean.getData().getStatus()==2){
                                    tv_status.setText("待服务");
                                }else if (orderMoresBean.getData().getStatus()==3){
                                    tv_status.setText("待评价");
                                }else if (orderMoresBean.getData().getStatus()==4){
                                    tv_status.setText("已评价");
                                }else if (orderMoresBean.getData().getStatus()==5){
                                    tv_status.setText("售后");
                                }else if (orderMoresBean.getData().getStatus()==6){
                                    tv_status.setText("退款中");
                                }else if (orderMoresBean.getData().getStatus()==7){
                                    tv_status.setText("退款完成");
                                }
                                tv_cartype.setText(orderMoresBean.getData().getService_name()+"  "+"("+orderMoresBean.getData().getType_name()+")"+"  "+orderMoresBean.getData().getMoney()+"元");
                                Glide.with(OrderMoreActivity.this).load(Api.ossUrl+orderMoresBean.getData().getCar_image()).into(img_carimg);
                                img_phone.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        if (bodadianhua!=0){
                                            final android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(OrderMoreActivity.this);
                                            LayoutInflater inflater = LayoutInflater.from(OrderMoreActivity.this);
                                            final View DialogView = inflater .inflate ( R.layout.callphone4, null);//1、自定义布局
                                            TextView ok = (TextView) DialogView.findViewById(R.id.confirm);//自定义控件
                                            TextView paizhao = (TextView) DialogView.findViewById(R.id.cancel);//自定义控件
                                            TextView dianhua = (TextView) DialogView.findViewById(R.id.text4444);//自定义控件
                                            dianhua.setText("您确认拨打："+orderMoresBean.getData().getTel());
                                            final EditText edt_xinxi = (EditText) DialogView.findViewById(R.id.qitayuanyin);
                                            builder.setView(DialogView);
                                            final android.support.v7.app.AlertDialog dialog = builder.create();
                                            dialog.show();
                                            //拨打
                                            ok.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    dialog.dismiss();
                                                    if (bodadianhua!=0){
                                                        Intent intent = new Intent();

                                                        intent.setAction(Intent.ACTION_CALL);

                                                        intent.setData(Uri.parse("tel:"+ orderMoresBean.getData().getTel()));

                                                        startActivity(intent);
                                                    }else {
                                                        ToastUtils.shortToast("请在设置里面打开拨打电话的权限");
                                                    }

                                                }
                                            });
                                            //取消
                                            paizhao.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    dialog.dismiss();
                                                }
                                            });


                                        }else {
                                            ToastUtils.shortToast("请在设置里面打开拨打电话的权限");
                                        }

                                    }
                                });
                                //如果完成时间没有，择隐藏
                                if (orderMoresBean.getData().getReturnTime()==null){
                                    ll_wancheng.setVisibility(View.GONE);
                                }else {
                                    ll_wancheng.setVisibility(View.VISIBLE);
                                    tv_wanchengtime.setText(orderMoresBean.getData().getReturnTime());
                                }
                                tv_yudingtime.setText(orderMoresBean.getData().getOrderTime());
                                tv_youhui.setText(orderMoresBean.getData().getCoupon_money()+"元");
                                tv_zhifu.setText((orderMoresBean.getData().getMoney()-orderMoresBean.getData().getCoupon_money())+"元");
                                tv_uuid.setText(orderMoresBean.getData().getUuid());


                            }else {
                                ToastUtils.shortToast("服务器请求失败");
                            }

                        }else {
                            ToastUtils.shortToast(orderMoresBean.getMessage());
                        }

                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        ToastUtils.shortToast(e+"");
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
        ttitle.setText("订单详情");
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
                OrderMoreActivity.this.finish();
            }
        });
    }
}
