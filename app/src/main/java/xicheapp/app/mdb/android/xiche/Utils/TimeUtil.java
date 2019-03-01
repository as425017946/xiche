package xicheapp.app.mdb.android.xiche.Utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2016/11/17.
 */
public class TimeUtil {
    public static String getTime() {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日    HH:mm:ss     ");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String str = formatter.format(curDate);
        return str;
    }

    //获取毫秒
    public static Long getTimeMis(){
        Date dt= new Date();
        Long time= dt.getTime();//这就是距离1970年1月1日0点0分0秒的毫秒数
        return time;
    }

}
