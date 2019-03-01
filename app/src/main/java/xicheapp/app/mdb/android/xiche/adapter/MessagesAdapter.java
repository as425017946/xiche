package xicheapp.app.mdb.android.xiche.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;


import java.util.ArrayList;

import xicheapp.app.mdb.android.xiche.R;
import xicheapp.app.mdb.android.xiche.bean.MessagesBean;
import xicheapp.app.mdb.android.xiche.fuwu.MessagesMoreActivity;

public class MessagesAdapter extends BaseAdapter {
    Context context;
    ArrayList<MessagesBean> arrayList;
    public MessagesAdapter(Context context,ArrayList<MessagesBean> arrayList){
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
        ViewHolder viewHolder;
        if (view==null){
            view = LayoutInflater.from(context).inflate(R.layout.messages_item,viewGroup,false);
            view.setTag(new ViewHolder(view));
        }
        viewHolder = (ViewHolder) view.getTag();
        final MessagesBean messagesBean = arrayList.get(i);
//        if (messagesBean.getData().getPageInfo().getList().get(i).getTitle().length()>4){
//            viewHolder.tv_title.setText(messagesBean.getData().getPageInfo().getList().get(i).getTitle().substring(0,4)+"...");
//        }else {
//            viewHolder.tv_title.setText(messagesBean.getData().getPageInfo().getList().get(i).getTitle());
//        }

        if (messagesBean.getData().getPageInfo().getList().get(i).getContent().length()>10){
            viewHolder.tv_content.setText(messagesBean.getData().getPageInfo().getList().get(i).getContent().substring(0,10)+"...");
        }else {
            viewHolder.tv_content.setText(messagesBean.getData().getPageInfo().getList().get(i).getContent());
        }
        viewHolder.tv_time.setText(messagesBean.getData().getPageInfo().getList().get(i).getCreate_time());
        viewHolder.tv_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                ToastUtils.shortToast(messagesBean.getData().getPageInfo().getList().get(i).getId()+"");
                Intent intent = new Intent(context,MessagesMoreActivity.class);
                intent.putExtra("title",messagesBean.getData().getPageInfo().getList().get(i).getTitle());
                intent.putExtra("content",messagesBean.getData().getPageInfo().getList().get(i).getContent());
                intent.putExtra("time",messagesBean.getData().getPageInfo().getList().get(i).getCreate_time());
                if (messagesBean.getData().getPageInfo().getList().get(i).getActivity_url()==null){
                    intent.putExtra("activityurl","");
                }else {
                    intent.putExtra("activityurl",messagesBean.getData().getPageInfo().getList().get(i).getActivity_url());
                }
                context.startActivity(intent);
            }
        });

        return view;
    }
    public static class ViewHolder{
        TextView tv_title;
        TextView tv_content;
        TextView tv_time;
        LinearLayout tv_more;
        public ViewHolder(View view){
            tv_title = (TextView) view.findViewById(R.id.messages_title);
            tv_content = (TextView) view.findViewById(R.id.messages_content);
            tv_time = (TextView) view.findViewById(R.id.messages_time);
            tv_more = (LinearLayout) view.findViewById(R.id.messages_more);
        }
    }
}
