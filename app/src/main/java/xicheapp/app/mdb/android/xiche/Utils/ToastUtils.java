package xicheapp.app.mdb.android.xiche.Utils;


import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

import xicheapp.app.mdb.android.xiche.entity.UrlCollect;

import static xicheapp.app.mdb.android.xiche.MyApplication.getApplication;

public class ToastUtils {

    //短时间的toast
    public static void shortToast(String text){
        if (UrlCollect.isLog){
            Toast.makeText(getApplication(),text,Toast.LENGTH_SHORT).show();
        }
    }

    //长时间的toast
    public static void longToast(String text){
        if (UrlCollect.isLog) {
            Toast.makeText(getApplication(), text, Toast.LENGTH_LONG).show();
        }
    }

    //自定义时间
    public static void timeToast(String text,Long time){
        if (UrlCollect.isLog) {
            final Toast toast = Toast.makeText(getApplication(), text, Toast.LENGTH_LONG);
            final Timer timer = new Timer();
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    toast.cancel();
                    timer.cancel();
                }
            }, time);
        }
    }

    //居中显示
    public static void centerToast(String text){
        if (UrlCollect.isLog) {
            Toast toast = Toast.makeText(getApplication(), text, Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
    }

    //图片Toast
    public static void imageToast(String text,int img){
        if (UrlCollect.isLog) {
            Toast toast = Toast.makeText(getApplication(), text, Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            LinearLayout toastView = (LinearLayout) toast.getView();
            ImageView imageCodeProject = new ImageView(getApplication());
            imageCodeProject.setImageResource(img);
            toastView.addView(imageCodeProject, 0);
            toast.show();
        }
    }

}
