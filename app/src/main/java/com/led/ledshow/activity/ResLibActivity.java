package com.led.ledshow.activity;


import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;

import com.led.ledshow.R;
import com.led.ledshow.adapter.ResAdapter;
import com.led.ledshow.base.BaseActivity;
import com.luck.picture.lib.decoration.GridSpacingItemDecoration;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import cn.bingoogolapple.baseadapter.BGAOnRVItemClickListener;

public class ResLibActivity extends BaseActivity implements BGAOnRVItemClickListener {


    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;


    private ResAdapter adapter;

    private List<String> pathList = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_res_lib;
    }

    @Override
    public void initData() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        toolbar.setTitle(R.string.res_lib);

        initTextData();

        adapter = new ResAdapter(recyclerView);

        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));

        recyclerView.setAdapter(adapter);

//        recyclerView.addItemDecoration(new DividerGridItemDecoration(this));

        recyclerView.addItemDecoration(new GridSpacingItemDecoration(3,50,true));

        adapter.setData(pathList);

        adapter.setOnRVItemClickListener(this);
    }


    /**
     * 初始化假数据
     */
    private void initTextData() {
        for (int i = 0; i < 30; i++) {
            if (i % 2 == 0 ){
//                pathList.add("http://hamitaocontent.oss-cn-hangzhou.aliyuncs.com/gif1.gif");
                pathList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1570630215318&di=8847194e728300b0d6d73adaa9cc1d6b&imgtype=0&src=http%3A%2F%2Fimg.ewebweb.com%2Fuploads%2F20190403%2F14%2F1554272079-rzHwVAkPNU.jpg");
            }else if (i % 3 == 0 ){
                pathList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1570630242506&di=002da4cf550fe6974813698bb691ed71&imgtype=0&src=http%3A%2F%2Fpic1.win4000.com%2Fwallpaper%2Ff%2F52665cb5a0b5d.jpg");
            }else {
                pathList.add("https://p6-dcd.byteimg.com/img/mosaic-legacy/bef30000c74bf0b36cf3~tplv-resize:1024:0.jpg");
            }

//            pathList.add("https://p6-dcd.byteimg.com/img/mosaic-legacy/bef30000c74bf0b36cf3~tplv-resize:1024:0.jpg");
        }
    }

    @Override
    public void onRVItemClick(ViewGroup parent, View itemView, int position) {
        String path = adapter.getData().get(position);
        Intent it = new Intent(this, PictureActivity.class);
        it.putExtra("picPath", path);
        startActivity(it);

    }
}
