package com.led.ledshow.activity;

import android.bluetooth.BluetoothGatt;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ToastUtils;
import com.clj.fastble.BleManager;
import com.clj.fastble.callback.BleGattCallback;
import com.clj.fastble.callback.BleScanCallback;
import com.clj.fastble.data.BleDevice;
import com.clj.fastble.exception.BleException;
import com.led.ledshow.R;
import com.led.ledshow.base.BaseActivity;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BasePopupView;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bingoogolapple.baseadapter.BGAOnRVItemClickListener;
import cn.bingoogolapple.baseadapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.baseadapter.BGAViewHolderHelper;

public class BluetoothActivity extends BaseActivity implements BGAOnRVItemClickListener {


    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.recyclerView2)
    RecyclerView rv_connect;

    private BasePopupView dialog;

    private BtAdapter adapter ,adapter_connect;

    @Override
    public int getLayoutId() {
        return R.layout.activity_bluetooth;
    }

    @Override
    public void initData() {
        tvTitle.setText("BLE蓝牙");
        adapter = new BtAdapter(recyclerView);
        adapter.setOnRVItemClickListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        adapter_connect = new BtAdapter(recyclerView);
        rv_connect.setLayoutManager(new LinearLayoutManager(this));
        rv_connect.setAdapter(adapter_connect);



        scan();
    }



    @OnClick({R.id.iv_back, R.id.iv_other})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_other:
                break;
        }
    }

    @Override
    public void onRVItemClick(ViewGroup parent, View itemView, int position) {
        BleDevice device = adapter.getData().get(position);
        BleManager.getInstance().connect(device, new BleGattCallback() {
            @Override
            public void onStartConnect() {
                dialog = new XPopup.Builder(mContext)
                        .asLoading("正在扫描设备...")
                        .show();
            }

            @Override
            public void onConnectFail(BleDevice bleDevice, BleException exception) {
                dialog.dismiss();
                ToastUtils.showShort("连接失败:" + exception.toString());
            }

            @Override
            public void onConnectSuccess(BleDevice bleDevice, BluetoothGatt gatt, int status) {
                dialog.dismiss();
                ToastUtils.showShort("已连接:" + bleDevice.getName());
                adapter_connect.setData(BleManager.getInstance().getAllConnectedDevice());
            }

            @Override
            public void onDisConnected(boolean isActiveDisConnected, BleDevice device, BluetoothGatt gatt, int status) {
                ToastUtils.showShort("断开连接");
            }
        });
    }

    public class BtAdapter extends BGARecyclerViewAdapter<BleDevice> {

        public BtAdapter(RecyclerView recyclerView) {
            super(recyclerView, R.layout.item_device);
        }

        @Override
        protected void fillData(BGAViewHolderHelper helper, int position, BleDevice model) {
            helper.getTextView(R.id.tv_name).setText(model.getName());
            helper.getTextView(R.id.tv_mac).setText(model.getMac());
        }
    }


    private void scan() {
        BleManager.getInstance().scan(new BleScanCallback() {
            @Override
            public void onScanFinished(List<BleDevice> scanResultList) {
                //扫描结束
                dialog.dismiss();
                adapter.setData(scanResultList);
            }

            @Override
            public void onScanStarted(boolean success) {

                dialog = new XPopup.Builder(mContext)
                        .asLoading("正在扫描设备...")
                        .show();
            }

            @Override
            public void onScanning(BleDevice bleDevice) {

            }
        });



    }



    public void reScan(View view) {
        scan();
    }
}
