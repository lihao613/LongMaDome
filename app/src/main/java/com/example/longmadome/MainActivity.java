package com.example.longmadome;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

@ContentView(R.layout.activity_main)
public class MainActivity extends Activity {
    @ViewInject(R.id.ll)
    private LinearLayout ll;

    @ViewInject(R.id.lv)
    private ListView lv;


    private AdapterLV adapterLV;
    private List<String> list = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        initData();
        IntentFilter filter = new IntentFilter();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        Broadcast broadcast = new Broadcast();
        registerReceiver(broadcast, filter);
        adapterLV = new AdapterLV(this, list);
        lv.setAdapter(adapterLV);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, MyShareActivity.class);
                intent.putExtra("url", list.get(i));
                startActivity(intent);
            }
        });
    }

    private void initData() {
        list.add("http://web.super8.com/Super8/public/uploads/content/2016-12-29/imgs_sureWin.jpg");
        list.add("http://web.super8.com/Super8/public/uploads/content/2016-12-29/imgs_silverFang.jpg");
        list.add("http://web.super8.com/Super8/public/uploads/content/2016-12-29/imgs_asianBeauty.jpg");
        list.add("http://web.super8.com/Super8/public/uploads/content/2016-12-29/imgs_asianBeauty.jpg");
    }

    class Broadcast extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            int count = ll.getChildCount();

            if (mNetworkInfo != null && mNetworkInfo.isAvailable()) {
                if (count >= 2) {
                    ll.removeViewAt(count - 2);
                }
            } else {
                if (count <= 1) {
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                            80);
                    TextView textView = new TextView(MainActivity.this);
//                    Drawable drawable = getResources().getDrawable(0);
//                    drawable.setBounds(0, 0, 40, 40);//必要,不然会不显示 40为宽高
//                    layoutParams.setMargins(0, 0, 0, 0);
                    textView.setHeight(40);
                    textView.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
                    textView.setGravity(Gravity.CENTER);
                    textView.setTextSize(14);
                    //textView.setBackgroundResource(R.drawable.label_style); //设置背景
//                    textView.setCompoundDrawables(drawable, null, null, null);
//                    textView.setCompoundDrawablePadding(5);
                    textView.setText("请检查网络");
                    textView.setTextColor(getResources().getColor(R.color.red));
                    textView.setLayoutParams(layoutParams);

                    ll.addView(textView, 0);
                }

            }
        }
    }
}
