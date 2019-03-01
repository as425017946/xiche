package xicheapp.app.mdb.android.xiche.wode.order;

import android.content.res.Resources;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import xicheapp.app.mdb.android.xiche.BaseActivity;
import xicheapp.app.mdb.android.xiche.R;
import xicheapp.app.mdb.android.xiche.adapter.OrderFragmentAdapter;

public class OrdersActivity extends BaseActivity {
    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.viewpager)
    ViewPager mViewpager;
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private ArrayList<String> list = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);
        ButterKnife.bind(this);
        initData();
        setTtitle();
        fanhui();
//        if (getIntent().getStringExtra("orderzhi").equals("1")){
//            mTabLayout.getTabAt(0).select();
//        }else  if (getIntent().getStringExtra("orderzhi").equals("2")){
//            mTabLayout.getTabAt(1).select();
//        }else  if (getIntent().getStringExtra("orderzhi").equals("3")){
//            mTabLayout.getTabAt(2).select();
//        }else  if (getIntent().getStringExtra("orderzhi").equals("4")){
//            mTabLayout.getTabAt(3).select();
//        }
    }

    /**
     * 写入title名字
     */
    @BindView(R.id.xiche_title)
    TextView ttitle;
    @BindView(R.id.xiche_quxiao_title)
    TextView ttitle2;
    private void setTtitle(){
        ttitle.setText("我的订单");
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
                OrdersActivity.this.finish();
            }
        });
    }

    private void initData(){
        //设置投资页面的里面的6个fragment页面
        OrderAllFragment allFragment = new OrderAllFragment();
        OrderDaifuwuFragment daifuwuFragment = new OrderDaifuwuFragment();
        OrderYiwanchengFragment yiwanchengFragment = new OrderYiwanchengFragment();
        OrderYiquxiaoFragment yiquxiaoFragment = new OrderYiquxiaoFragment();
        mFragments.add(allFragment);
        mFragments.add(daifuwuFragment);
        mFragments.add(yiwanchengFragment);
        mFragments.add(yiquxiaoFragment);
        list.add("全部");
        list.add("待服务");
        list.add("已服务");
        list.add("已取消");
        mTabLayout.setupWithViewPager(mViewpager);
        mViewpager.setAdapter(new OrderFragmentAdapter(getSupportFragmentManager(),mFragments,list));
//        mTabLayout.post(new Runnable() {
//            @Override
//            public void run() {
//                setIndicator(mTabLayout, 10, 10);
//            }
//        });


    }
//    public static void setIndicator(TabLayout tabs, int leftDip, int rightDip) {
//        Class<?> tabLayout = tabs.getClass();
//        Field tabStrip = null;
//        try {
//            tabStrip = tabLayout.getDeclaredField("mTabStrip");
//        } catch (NoSuchFieldException e) {
//            e.printStackTrace();
//        }
//
//        tabStrip.setAccessible(true);
//        LinearLayout llTab = null;
//        try {
//            llTab = (LinearLayout) tabStrip.get(tabs);
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        }
//
//        int left = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, leftDip, Resources.getSystem().getDisplayMetrics());
//        int right = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, rightDip, Resources.getSystem().getDisplayMetrics());
//
//        for (int i = 0; i < llTab.getChildCount(); i++) {
//            View child = llTab.getChildAt(i);
//            child.setPadding(0, 0, 0, 0);
//            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
//            params.leftMargin = left;
//            params.rightMargin = right;
//            child.setLayoutParams(params);
//            child.invalidate();
//        }
//    }
}
