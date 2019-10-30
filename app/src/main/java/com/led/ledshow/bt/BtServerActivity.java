package com.led.ledshow.bt;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.led.ledshow.R;
import com.led.ledshow.base.MyAPP;

import java.io.File;
import java.util.List;



public class BtServerActivity extends Activity implements BtBase.Listener {
    private TextView mTips;
    private EditText mInputMsg;
    private EditText mInputFile;
    private TextView mLogs;
    private BtServer mServer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_btserver);
        mTips = findViewById(R.id.tv_tips);
        mInputMsg = findViewById(R.id.input_msg);
        mInputFile = findViewById(R.id.input_file);
        mLogs = findViewById(R.id.tv_log);
        mServer = new BtServer(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mServer.unListener();
        mServer.close();
    }

    public void sendMsg(View view) {
        if (mServer.isConnected(null)) {
            String msg = mInputMsg.getText().toString();
            if (TextUtils.isEmpty(msg))
                MyAPP.toast("消息不能空", 0);
            else
                mServer.sendMsg(msg);
        } else
            MyAPP.toast("没有连接", 0);
    }

    public void sendFile(View view) {
        if (mServer.isConnected(null)) {
            String filePath = mInputFile.getText().toString();
            if (TextUtils.isEmpty(filePath) || !new File(filePath).isFile())
                MyAPP.toast("文件无效", 0);
            else
                mServer.sendFile(filePath);
        } else
            MyAPP.toast("没有连接", 0);
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
                mServer.listen();
                msg = "连接断开,正在重新监听...";
                mTips.setText(msg);
                break;
            case BtBase.Listener.MSG:
                msg = String.format("\n%s", obj);
                mLogs.append(msg);
                break;
        }
        MyAPP.toast(msg, 0);
    }

    int REQUESTCODE_FROM_ACTIVITY = 1000;

    public void openFile(View view){
//        new LFilePicker()
//                .withActivity(BtServerActivity.this)
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
}