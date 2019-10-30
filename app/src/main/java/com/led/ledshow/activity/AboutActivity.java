package com.led.ledshow.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.led.ledshow.R;
import com.led.ledshow.base.BaseActivity;
import com.led.ledshow.view.ItemCommomView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AboutActivity extends BaseActivity {

    @BindView(R.id.icv_check_version)
    ItemCommomView icvCheckVersion;

    @BindView(R.id.tv_title)
    TextView tvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_about;
    }

    @Override
    protected void initData() {

    }

    private void initView() {

        tvTitle.setText(R.string.about);
    }


    /**
     * 检查新版本
     */
    public void checkVersion() {
        ToastUtils.showShort("当前已是最新版本");
    }

    @OnClick({R.id.icv_check_version, R.id.iv_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.icv_check_version:
                checkVersion();
                break;
            case R.id.iv_back:
                finish();
                break;
        }
    }
}
