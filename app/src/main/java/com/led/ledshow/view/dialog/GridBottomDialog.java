package com.led.ledshow.view.dialog;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.led.ledshow.R;
import com.lxj.xpopup.core.BottomPopupView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;

/**
 * Created by linjianwen on 2019/10/9.
 */
public class GridBottomDialog extends BottomPopupView {


    private GridView gridView;

    private GridAdapter adapter;

    private List<BackGroundModel> list = new ArrayList<>();

    public void setCallback(ColorCallback callback) {
        this.callback = callback;
    }

    private ColorCallback callback;

    public GridBottomDialog(@NonNull Context context) {
        super(context);
    }


    @Override
    protected int getImplLayoutId() {
        return R.layout.dialog_grid;
    }

    @Override
    protected void onCreate() {
        super.onCreate();

        initData();

        adapter = new GridAdapter();


        gridView = findViewById(R.id.gridview);

        gridView.setAdapter(adapter);

    }

    private void initData() {

        BackGroundModel red = new BackGroundModel("红色", "#FF0000");
        BackGroundModel green = new BackGroundModel("绿色", "#008000");
        BackGroundModel blue = new BackGroundModel("蓝色", "#0000FF");
        BackGroundModel white = new BackGroundModel("白色", "#FFFFFF");
        BackGroundModel orange = new BackGroundModel("橙色", "#FFA500");
        BackGroundModel black = new BackGroundModel("黑色", "#000000");

        list.add(red);
        list.add(green);
        list.add(blue);
        list.add(white);
        list.add(orange);
        list.add(black);

    }


    private class GridAdapter extends BaseAdapter {


        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int i) {
            return list.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View convertView, ViewGroup viewGroup) {
            Holder holder = null;
            if (convertView == null) {
                holder = new Holder();
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_grid, null);
                holder.layout = convertView.findViewById(R.id.layout);
                holder.imageView = convertView.findViewById(R.id.iv_item);
                holder.textView = convertView.findViewById(R.id.tv_item);
                convertView.setTag(holder);
            } else {
                holder = (Holder) convertView.getTag();
            }
            holder.imageView.setBackgroundColor(Color.parseColor(list.get(i).color));
            holder.textView.setText(list.get(i).text);
            holder.layout.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    callback.colorCallback(list.get(i).color,list.get(i).text);
                    dismiss();
                }
            });
            return convertView;
        }


        class Holder {
            LinearLayout layout;
            ImageView imageView;
            TextView textView;
        }


    }


    public interface ColorCallback {
        void colorCallback(String color,String text);
    }

    private class BackGroundModel {

        String text;
        String color;

        public BackGroundModel(String text, String color) {
            this.text = text;
            this.color = color;
        }
    }


}
