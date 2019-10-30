package com.led.ledshow.adapter;

import com.bumptech.glide.Glide;
import com.led.ledshow.R;

import androidx.recyclerview.widget.RecyclerView;
import cn.bingoogolapple.baseadapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.baseadapter.BGAViewHolderHelper;

/**
 * Created by linjianwen on 2019/10/8.
 */
public class ResAdapter extends BGARecyclerViewAdapter<String> {


    public ResAdapter(RecyclerView recyclerView) {
        super(recyclerView, R.layout.item_res);
    }

    @Override
    protected void fillData(BGAViewHolderHelper helper, int position, String picPath) {

        Glide.with(mContext).load(picPath).into(helper.getImageView(R.id.iv_pic));

    }
}
