package com.example.longmadome;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

/**
 * Created by lihao on 2017/6/1.
 */

public class AdapterLV extends BaseAdapter {
    private Context context;
    private List<String> list;
    private LayoutInflater mInflater;

    public AdapterLV(Context context, List<String> list) {
        this.context = context;
        this.list = list;
        this.mInflater = LayoutInflater.from(context);

    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View v, ViewGroup viewGroup) {
        H h;
        if(v == null){
            v = mInflater.inflate(R.layout.item_iv, null);
            h = new H();
            x.view().inject(h, v);
            v.setTag(h);
        }else {
            h = (H) v.getTag();
        }
        ImageLoader.getInstance().displayImage(list.get(i), h.iv);
        return v;
    }
    class H{
        @ViewInject(R.id.iv)
        ImageView iv;
    }
}
