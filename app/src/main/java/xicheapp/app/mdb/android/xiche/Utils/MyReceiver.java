package xicheapp.app.mdb.android.xiche.Utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import cn.jpush.android.api.JPushInterface;

import static android.content.Context.MODE_PRIVATE;

public class MyReceiver  extends BroadcastReceiver {
    public static String ids = "";
    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        String title = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
        SharedPreferences id = context.getSharedPreferences("id", MODE_PRIVATE);
        ids = id.getString("id","");
        id.edit().putString("id",title).commit();

    }
}
