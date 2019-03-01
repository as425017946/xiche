package xicheapp.app.mdb.android.xiche.Utils;

import android.app.ActionBar;
import android.app.Activity;
import android.support.v7.app.AlertDialog;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.TextView;

import xicheapp.app.mdb.android.xiche.R;

public class MyDialog4 {
    //点击确认按钮回调接口
    public interface OnConfirmListener {
        public void onConfirmClick();
    }
    /**
     * @Title: show
     * @Description: 显示Dialog
     * @param activity
     * @param content
     * 提示内容
     * @param confirmListener
     * void
     *  @throws */
    public static void show(Activity activity, String content, final OnConfirmListener confirmListener){
        // 加载布局文件
        View view = View.inflate(activity, R.layout.dialog_view3, null);
        TextView text = (TextView) view.findViewById(R.id.text);
        TextView confirm = (TextView) view.findViewById(R.id.confirm);
        TextView cancel = (TextView) view.findViewById(R.id.cancel);

        text.setText(content);

        // 创建Dialog
        final AlertDialog dialog = new AlertDialog.Builder(activity).create();
        // 设置点击dialog以外区域不取消Dialog
        dialog.setCancelable(false);
        dialog.show();
        dialog.setContentView(view);
        //设置弹出框宽度为屏幕宽度的5分之4
        dialog.getWindow().setLayout(getWidth(activity) / 5 * 4, ActionBar.LayoutParams.WRAP_CONTENT);

        // 确定
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                confirmListener.onConfirmClick();
            }
        });
        // 取消
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    /** * 获取屏幕宽度 * * @param activity * @return */
    public static int getWidth(Activity activity) {
        DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int width = metrics.widthPixels; return width;
    }
}
