package com.led.ledshow.activity;

import android.content.Intent;
import android.view.View;

import com.led.ledshow.R;
import com.led.ledshow.base.BaseActivity;
import com.led.ledshow.bt.BtClientActivity;

import butterknife.OnClick;

public class StartActivity extends BaseActivity {


    @Override
    public int getLayoutId() {
        return R.layout.activity_start;
    }

    @Override
    public void initData() {



    }


    @OnClick({R.id.btn_scan, R.id.btn_skip})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_scan:
                //进入扫描界面扫描蓝牙设备
                startActivity(new Intent(this, BtClientActivity.class));
                break;
            case R.id.btn_skip:
                startActivity(new Intent(this, MainActivity.class));
                break;
        }
        finish();
    }
}
