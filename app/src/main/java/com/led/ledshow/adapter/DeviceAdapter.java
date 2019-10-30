package com.led.ledshow.adapter;

import android.bluetooth.BluetoothDevice;

import androidx.recyclerview.widget.RecyclerView;

import com.led.ledshow.R;

import cn.bingoogolapple.baseadapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.baseadapter.BGAViewHolderHelper;

public class DeviceAdapter extends BGARecyclerViewAdapter<BluetoothDevice> {


    public DeviceAdapter(RecyclerView recyclerView) {
        super(recyclerView, R.layout.item_device);
    }

    @Override
    protected void fillData(BGAViewHolderHelper helper, int position, BluetoothDevice model) {

        helper.getTextView(R.id.tv_name).setText(model.getName());
    }
}
