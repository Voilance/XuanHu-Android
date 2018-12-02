package cn.biketomotor.xh.xuanhu.Activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cn.biketomotor.xh.xuanhu.R;

public class MenuAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<String> mData;
    private List<Integer> mDataIcon;

    public MenuAdapter(Context context, List<String> data, List<Integer> dataicon) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.mDataIcon = dataicon;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = mInflater.inflate(R.layout.list_item,null);
        TextView textView = (TextView) view.findViewById(R.id.choice);
        ImageView imageView = (ImageView) view.findViewById(R.id.actionicon);
        //为每一个item设置一个图标和相应的文字
        imageView.setImageResource(mDataIcon.get(position));
        textView.setText(mData.get(position));
        return view;
    }
}

