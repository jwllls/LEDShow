package com.led.ledshow.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;

import com.blankj.utilcode.util.PermissionUtils;
import com.led.ledshow.R;
import com.led.ledshow.base.BaseActivity;

import java.util.List;

public class SplashActivity extends BaseActivity {


    private String[] permissions = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
//            Manifest.permission.CAMERA,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    finish();
                    break;
                case 2:
                    finish();
                    break;
            }
        }
    };

    @Override
    public int getLayoutId() {
        return R.layout.activity_splash;
    }


    @Override
    public void initData() {
        //请求权限
        PermissionUtils.permission(permissions).callback(new PermissionUtils.FullCallback() {
            @Override
            public void onGranted(List<String> permissionsGranted) {
                if (permissionsGranted.size() == permissions.length)
                    handler.sendEmptyMessageDelayed(1,1000);
            }

            @Override
            public void onDenied(List<String> permissionsDeniedForever, List<String> permissionsDenied) {
                if (permissionsDenied.size()>0){
                    handler.sendEmptyMessageDelayed(2,1000);
                }

            }
        }).request();


    }
}
