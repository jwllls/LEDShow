package com.led.ledshow.base;

import android.annotation.SuppressLint;
import android.app.Application;
import android.bluetooth.BluetoothDevice;
import android.os.Handler;
import android.widget.Toast;

import com.clj.fastble.BleManager;

import java.util.Set;


public class MyAPP extends Application {

    //    https://github.com/lioilwin/Bluetooth
    private static final Handler sHandler = new Handler();
    private static Toast sToast; // 单例Toast,避免重复创建，显示时间过长


    //全局的蓝牙已绑定的设备
    public static Set<BluetoothDevice> bondedDevices;

    @SuppressLint("ShowToast")
    @Override
    public void onCreate() {
        super.onCreate();
        sToast = Toast.makeText(this, "", Toast.LENGTH_SHORT);

        init();
    }

    private void init() {
        BleManager.getInstance().init(this);

        BleManager.getInstance()
                .enableLog(true)
                .setReConnectCount(1, 5000)
                .setSplitWriteNum(20)
                .setConnectOverTime(10000)
                .setOperateTimeout(5000);
    }


    public static void toast(String txt, int duration) {
        sToast.setText(txt);
        sToast.setDuration(duration);
        sToast.show();
    }


    public static void runUi(Runnable runnable) {
        sHandler.post(runnable);
    }
}
