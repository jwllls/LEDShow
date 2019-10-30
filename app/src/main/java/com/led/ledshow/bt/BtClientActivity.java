package com.led.ledshow.bt;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ToastUtils;
import com.led.ledshow.R;
import com.led.ledshow.base.MyAPP;
import com.led.ledshow.util.BtReceiver;

import java.io.File;
import java.util.List;



public class BtClientActivity extends AppCompatActivity implements BtBase.Listener, BtReceiver.Listener, BtDevAdapter.Listener {
    private TextView mTips;
    private EditText mInputMsg;
    private EditText mInputFile;
    private TextView mLogs;
    public BtReceiver mBtReceiver;
    private final BtDevAdapter mBtDevAdapter = new BtDevAdapter(this);
    public final BtClient mClient = new BtClient(this);
    private Toolbar toolbar;



    private BluetoothDevice device;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_btclient);
        initView();
        mBtReceiver = new BtReceiver(this, this);//注册蓝牙广播
        BluetoothAdapter.getDefaultAdapter().startDiscovery();


    }

    private void initView(){
        RecyclerView rv = findViewById(R.id.rv_bt);

        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(mBtDevAdapter);
        mTips = findViewById(R.id.tv_tips);
        mInputMsg = findViewById(R.id.input_msg);
        mInputFile = findViewById(R.id.input_file);
        mLogs = findViewById(R.id.tv_log);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.bt_connect);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        unregisterReceiver(mBtReceiver);
//        mClient.unListener();
//        mClient.close();
    }

    @Override
    public void onItemClick(BluetoothDevice dev) {
        if (mClient.isConnected(dev)) {
            MyAPP.toast("已经连接了", 0);
            return;
        }
        mClient.connect(dev);
        MyAPP.toast("正在连接...", 0);
        mTips.setText("正在连接...");
    }

    @Override
    public void foundDev(BluetoothDevice dev) {
        mBtDevAdapter.add(dev);
    }

    // 重新扫描
    public void reScan(View view) {
        ToastUtils.showShort(R.string.start_scan);
        mBtDevAdapter.reScan();
    }

    public void sendMsg(View view) {
        if (mClient.isConnected(null)) {
            String msg = mInputMsg.getText().toString();
            if (TextUtils.isEmpty(msg))
                MyAPP.toast("消息不能空", 0);
            else
                mClient.sendMsg(msg);
        } else
            MyAPP.toast("没有连接", 0);
    }

    public void sendFile(String filePath) {
        if (mClient.isConnected(null)) {
//            String filePath = mInputFile.getText().toString();
            if (TextUtils.isEmpty(filePath) || !new File(filePath).isFile())
                MyAPP.toast("文件无效", 0);
            else
                mClient.sendFile(filePath);
        } else
            MyAPP.toast("没有连接", 0);
    }

    int REQUESTCODE_FROM_ACTIVITY = 1000;

    public void openFile(View view){
//        new LFilePicker()
//                .withActivity(BtClientActivity.this)
//                .withFileFilter(new String[]{"gif"})
//                .withRequestCode(REQUESTCODE_FROM_ACTIVITY)
//                .withStartPath("/storage/emulated/0/Download")
//                .withIsGreater(false)
//                .withFileSize(500 * 1024)
//                .start();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUESTCODE_FROM_ACTIVITY) {
                //If it is a file selection mode, you need to get the path collection of all the files selected
                //List<String> list = data.getStringArrayListExtra(Constant.RESULT_INFO);//Constant.RESULT_INFO == "paths"
                List<String> list = data.getStringArrayListExtra("paths");
                Toast.makeText(getApplicationContext(), "selected " + list.size() , Toast.LENGTH_SHORT).show();
                //If it is a folder selection mode, you need to get the folder path of your choice
                String path = data.getStringExtra("path");
                mInputFile.setText(list.get(0));
                Toast.makeText(getApplicationContext(), "The selected path is:" + path, Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void socketNotify(int state, final Object obj) {
        if (isDestroyed())
            return;
        String msg = null;
        switch (state) {
            case BtBase.Listener.CONNECTED:
                BluetoothDevice dev = (BluetoothDevice) obj;
                msg = String.format("与%s(%s)连接成功", dev.getName(), dev.getAddress());
                mTips.setText(msg);
                break;
            case BtBase.Listener.DISCONNECTED:
                msg = "连接断开";
                mTips.setText(msg);
                break;
            case BtBase.Listener.MSG:
                msg = String.format("\n%s", obj);
                mLogs.append(msg);
                break;
        }
        MyAPP.toast(msg, 0);
    }
}