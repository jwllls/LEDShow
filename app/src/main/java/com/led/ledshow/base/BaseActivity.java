package com.led.ledshow.base;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseActivity extends AppCompatActivity {

    protected Unbinder mBinder;

    protected Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        mBinder = ButterKnife.bind(this);
        mContext = this;
        initData();
    }


    protected abstract int getLayoutId();

    protected abstract void initData();

//    protected abstract void initView();

    @Override
    protected void onDestroy() {
        mBinder.unbind();
        super.onDestroy();
    }


}
