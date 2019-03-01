package xicheapp.app.mdb.android.xiche;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import xicheapp.app.mdb.android.xiche.Utils.ScreenUtils;


public class BaseActivity extends FragmentActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//取消标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        final int sdk = Build.VERSION.SDK_INT;
        Window window = this.getWindow();
        WindowManager.LayoutParams params = window.getAttributes();

//        if (sdk >= Build.VERSION_CODES.KITKAT) {
//            int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
//            // 设置透明状态栏
//            if ((params.flags & bits) == 0) {
//                params.flags |= bits;
//                window.setAttributes(params);
//            }
//        }

//        ScreenUtils.adaptScreen(this, 392,true);//在绘图之前执行该方法  392是设计图的dp值 可根据实际
        ScreenUtils.adaptScreen(this, 375,true);//在绘图之前执行该方法  392是设计图的dp值 可根据实际
//        //取消标题
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        //取消状态栏
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }
    @Override
    protected void onDestroy() {
        ScreenUtils.cancelAdaptScreen(this);
        super.onDestroy();
    }
}
