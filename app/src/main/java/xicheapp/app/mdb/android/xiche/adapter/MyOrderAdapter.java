package xicheapp.app.mdb.android.xiche.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import java.util.ArrayList;
import xicheapp.app.mdb.android.xiche.R;
import xicheapp.app.mdb.android.xiche.Utils.Api;
import xicheapp.app.mdb.android.xiche.bean.MyOrderBean;
import xicheapp.app.mdb.android.xiche.wode.order.OrderMoreActivity;

public class MyOrderAdapter extends BaseAdapter {
    Context context;
    ArrayList<MyOrderBean> arrayList;
    public MyOrderAdapter(Context context,ArrayList<MyOrderBean> arrayList){
        this.context = context;
        this.arrayList = arrayList;
    }
    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder v;
        if (view==null){
            view = LayoutInflater.from(context).inflate(R.layout.order_show_item,viewGroup,false);
            view.setTag(new ViewHolder(view));
        }
        v = (ViewHolder)view.getTag();
        final MyOrderBean myOrderBean = arrayList.get(i);
        if (myOrderBean.getData().getPageInfo().getList().size()>0){
//            Log.e("测试1",myOrderBean.getData().getPageInfo().getList().size()+"");
            v.tv_name.setText(myOrderBean.getData().getPageInfo().getList().get(i).getService_name()+"");
            v.tv_creatime.setText(myOrderBean.getData().getPageInfo().getList().get(i).getCreateTime().substring(0,16)+"");
            v.tv_info.setText(myOrderBean.getData().getPageInfo().getList().get(i).getCarNo()+"  "+myOrderBean.getData().getPageInfo().getList().get(i).getBrand()+"  "+myOrderBean.getData().getPageInfo().getList().get(i).getCar_color());
            v.tv_oktime.setText(myOrderBean.getData().getPageInfo().getList().get(i).getOrder_time().substring(0,16)+"");
            v.tv_youhui.setText("优惠："+myOrderBean.getData().getPageInfo().getList().get(i).getCoupon_money()+"元");
            v.tv_zonge.setText(myOrderBean.getData().getPageInfo().getList().get(i).getMoney()+"元");
            if (myOrderBean.getData().getPageInfo().getList().get(i).getStatus()==0){
                v.tv_status.setText("已取消");
            }else if (myOrderBean.getData().getPageInfo().getList().get(i).getStatus()==1){
                v.tv_status.setText("待支付");
            }else if (myOrderBean.getData().getPageInfo().getList().get(i).getStatus()==2){
                v.tv_status.setText("待服务");
            }else if (myOrderBean.getData().getPageInfo().getList().get(i).getStatus()==3){
                v.tv_status.setText("待评价");
            }else if (myOrderBean.getData().getPageInfo().getList().get(i).getStatus()==4){
                v.tv_status.setText("已评价");
            }else if (myOrderBean.getData().getPageInfo().getList().get(i).getStatus()==5){
                v.tv_status.setText("售后");
            }else if (myOrderBean.getData().getPageInfo().getList().get(i).getStatus()==6){
                v.tv_status.setText("退款中");
            }else if (myOrderBean.getData().getPageInfo().getList().get(i).getStatus()==7){
                v.tv_status.setText("退款完成");
            }
            v.tv_type.setText(myOrderBean.getData().getPageInfo().getList().get(i).getType_name()+"x1  "+myOrderBean.getData().getPageInfo().getList().get(i).getMoney()+"元");
            if (myOrderBean.getData().getPageInfo().getList().get(i).getCar_image()!=null && !myOrderBean.getData().getPageInfo().getList().get(i).getCar_image().contains(",")){
                Glide.with(context).load(Api.ossUrl+myOrderBean.getData().getPageInfo().getList().get(i).getCar_image()).into(v.img_show);
            }
            if (myOrderBean.getData().getPageInfo().getList().get(i).getCar_image()!=null && myOrderBean.getData().getPageInfo().getList().get(i).getCar_image().contains(",")){
                String[] shu = myOrderBean.getData().getPageInfo().getList().get(i).getCar_image().split(",");
                Glide.with(context).load(Api.ossUrl+shu[0]).into(v.img_show);
            }
            v.ll_more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context,OrderMoreActivity.class);
                    intent.putExtra("uuid",myOrderBean.getData().getPageInfo().getList().get(i).getUuid());
                    context.startActivity(intent);
                }
            });
        }

        return view;
    }
    public static class ViewHolder{
        TextView tv_name;
        TextView tv_creatime;
        TextView tv_status;
        TextView tv_info;
        TextView tv_type;
        TextView tv_oktime;
        TextView tv_youhui;
        TextView tv_zonge;
        ImageView img_show;
        LinearLayout ll_more;
        public ViewHolder(View view){
            tv_name = (TextView) view.findViewById(R.id.order_show_item_names);
            tv_creatime = (TextView) view.findViewById(R.id.order_show_item_creattimes);
            tv_status = (TextView) view.findViewById(R.id.order_show_item_statuss);
            tv_info = (TextView) view.findViewById(R.id.order_show_item_carinfos);
            tv_oktime = (TextView) view.findViewById(R.id.order_show_item_wanchengtimes);
            tv_youhui = (TextView) view.findViewById(R.id.order_show_item_youhuis);
            tv_zonge = (TextView) view.findViewById(R.id.order_show_item_zonges);
            tv_type = (TextView) view.findViewById(R.id.order_show_item_cartypes);
            img_show = (ImageView)view.findViewById(R.id.order_show_item_imgs);
            ll_more = (LinearLayout)view.findViewById(R.id.ceshishiyong);
        }
    }
}
