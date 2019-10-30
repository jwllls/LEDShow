package com.led.ledshow.adapter;

import android.widget.CheckBox;

import com.led.ledshow.R;
import com.led.ledshow.model.ShowModel;

import androidx.recyclerview.widget.RecyclerView;
import cn.bingoogolapple.baseadapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.baseadapter.BGAViewHolderHelper;

/**
 * Created by linjianwen on 2019/10/8.
 */
public class ShowListAdapter extends BGARecyclerViewAdapter<ShowModel> {

    public ShowListAdapter(RecyclerView recyclerView) {
        super(recyclerView, R.layout.item_show_list);
    }


    @Override
    protected void setItemChildListener(BGAViewHolderHelper helper, int viewType) {
        super.setItemChildListener(helper, viewType);
        helper.setItemChildClickListener(R.id.tv_edit);
        helper.setItemChildClickListener(R.id.tv_delete);

    }

    @Override
    protected void fillData(BGAViewHolderHelper helper, int position, ShowModel model) {

        helper.getTextView(R.id.tv_show_title).setText("名称:" + model.getTitle());
        helper.getTextView(R.id.tv_show_no).setText("编号:" + model.getNum());
        ((CheckBox) helper.getView(R.id.cb_show)).setChecked(model.isSelected());

    }
}
