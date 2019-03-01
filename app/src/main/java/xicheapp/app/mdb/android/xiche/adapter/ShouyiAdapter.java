package xicheapp.app.mdb.android.xiche.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import xicheapp.app.mdb.android.xiche.R;
import xicheapp.app.mdb.android.xiche.bean.NewShouyiBean;

public class ShouyiAdapter extends BaseAdapter {
    Context context;
    ArrayList<NewShouyiBean> arrayList;
    int red = 0xf05151;
    public ShouyiAdapter(Context context, ArrayList<NewShouyiBean> arrayList){
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
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view==null){
            view = LayoutInflater.from(context).inflate(R.layout.shouyi_item,viewGroup,false);
            view.setTag(new ViewHolder(view));
        }
        viewHolder = (ViewHolder) view.getTag();
        NewShouyiBean shouyiBean = arrayList.get(i);
        viewHolder.tv_time.setText(shouyiBean.getData().getPageInfo().getList().get(i).getTime());
        viewHolder.tv_daonum.setText(shouyiBean.getData().getPageInfo().getList().get(i).getNum()+"");
        viewHolder.tv_zong.setText(shouyiBean.getData().getPageInfo().getList().get(i).getMoney()+"");

        return view;
    }
    public static class ViewHolder{
        TextView tv_time;
        TextView tv_daonum;
        TextView tv_zong;
        public ViewHolder(View view){
            tv_time = (TextView) view.findViewById(R.id.shouyi_item_time);
            tv_daonum = (TextView) view.findViewById(R.id.shouyi_item_daodiannum);
            tv_zong = (TextView) view.findViewById(R.id.shouyi_item_zongjine);

        }
    }
}
