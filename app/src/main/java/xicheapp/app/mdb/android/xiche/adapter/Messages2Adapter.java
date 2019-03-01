package xicheapp.app.mdb.android.xiche.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import xicheapp.app.mdb.android.xiche.R;
import xicheapp.app.mdb.android.xiche.bean.MessagesBean;
import xicheapp.app.mdb.android.xiche.fuwu.MessagesMoreActivity;

import static xicheapp.app.mdb.android.xiche.fuwu.MessagesBianjiActivity.isChecked;
import static xicheapp.app.mdb.android.xiche.fuwu.MessagesBianjiActivity.isMulChoice;

public class Messages2Adapter extends BaseAdapter {
    Context context;
    ArrayList<MessagesBean> arrayList;
    // 存储勾选框状态的map集合
    private Map<Integer, Boolean> isCheck = new HashMap<Integer, Boolean>();
    //用于保存消息id
    private Map<Integer, String> isCheck2 = new HashMap<Integer, String>();
    public String xiaoxizhi="";
    public Messages2Adapter(Context context, ArrayList<MessagesBean> arrayList){
        this.context = context;
        this.arrayList = arrayList;
        // 默觉得不选中
        initCheck(false);
    }
    // 初始化map集合
    public void initCheck(boolean flag) {
        // map集合的数量和list的数量是一致的
        for (int i = 0; i < arrayList.size(); i++) {
            // 设置默认的显示
            isCheck.put(i, flag);
        }
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
        final ViewHolder viewHolder;
        if (view==null){
            view = LayoutInflater.from(context).inflate(R.layout.messages_item2,viewGroup,false);
            view.setTag(new ViewHolder(view));
        }
        viewHolder = (ViewHolder) view.getTag();
        if (isMulChoice==false){
            viewHolder.checkBox.setVisibility(View.GONE);
        }else {
            viewHolder.checkBox.setVisibility(View.VISIBLE);
            if (isChecked==true){
                viewHolder.checkBox.setChecked(true);
            }else {
                viewHolder.checkBox.setChecked(false);
            }

        }
        final MessagesBean messagesBean = arrayList.get(i);
//        if (messagesBean.getData().getPageInfo().getList().get(i).getTitle().length()>4){
//            viewHolder.tv_title.setText(messagesBean.getData().getPageInfo().getList().get(i).getTitle().substring(0,4)+"...");
//        }else {
//            viewHolder.tv_title.setText(messagesBean.getData().getPageInfo().getList().get(i).getTitle());
//        }
        for (int j = 0; j <arrayList.size() ; j++) {
            isCheck2.put(i, messagesBean.getData().getPageInfo().getList().get(i).getId()+"");
        }
        if (messagesBean.getData().getPageInfo().getList().get(i).getContent().length()>8){
            viewHolder.tv_content.setText(messagesBean.getData().getPageInfo().getList().get(i).getContent().substring(0,8)+"...");
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

        viewHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // 用map集合保存
                isCheck.put(i, isChecked);
            }
        });
        // 设置状态
        if (isCheck.get(i) == null) {
            isCheck.put(i, false);
        }
        viewHolder.checkBox.setChecked(isCheck.get(i));
//        viewHolder.checkBox.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                ToastUtils.shortToast(messagesBean.getData().getPageInfo().getList().get(i).getId()+",");
//                if (viewHolder.checkBox.isChecked()==true){
//
//                    xiaoxizhi += messagesBean.getData().getPageInfo().getList().get(i).getId()+",";
//
//                }else{
//                    String[] a = xiaoxizhi.split(",");
//                    String zhi = messagesBean.getData().getPageInfo().getList().get(i).getId()+"";
//                    String zhi2 = i+"";
//                    for (int j = 0; j <a.length ; j++) {
//                        if (zhi.equals(a[j])){
//                            xiaoxizhi = xiaoxizhi.substring(0,xiaoxizhi.indexOf(a[j]+","))+xiaoxizhi.substring(xiaoxizhi.indexOf(a[j]+",")+1);
//                        }
//                    }
////
//                }
//            }
//        });

        return view;
    }
    // 全选button获取状态
    public Map<Integer, Boolean> getMap() {
        // 返回状态
        return isCheck;
    }
    public Map<Integer, String> getMap2() {
        // 返回状态
        return isCheck2;
    }
    public static class ViewHolder{
        TextView tv_title;
        TextView tv_content;
        TextView tv_time;
        LinearLayout tv_more;
        CheckBox checkBox;
        public ViewHolder(View view){
            tv_title = (TextView) view.findViewById(R.id.messages_title);
            tv_content = (TextView) view.findViewById(R.id.messages_content);
            tv_time = (TextView) view.findViewById(R.id.messages_time);
            tv_more = (LinearLayout) view.findViewById(R.id.messages_more);
            checkBox = (CheckBox) view.findViewById(R.id.xitong_checkbox);
        }
    }
}
