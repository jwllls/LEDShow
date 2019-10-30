package com.led.ledshow.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.led.ledshow.R;
import com.led.ledshow.activity.AboutActivity;
import com.led.ledshow.activity.SettingActivity;
import com.led.ledshow.base.BaseFragment;
import com.led.ledshow.view.ItemCommomView;

import butterknife.BindView;
import butterknife.OnClick;

public class SettingFragment extends BaseFragment {


    @BindView(R.id.icv_setting)
    ItemCommomView icvSetting;

    @BindView(R.id.icv_about)
    ItemCommomView icvAbout;


    @BindView(R.id.iv_back)
    ImageView ivBack;

    @Override
    protected void loadData() {


    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_setting;
    }

    @Override
    protected void initData() {
        ivBack.setVisibility(View.GONE);
        icvSetting.setleftIcon(R.drawable.icon_audio);
        icvAbout.setleftIcon(R.drawable.icon_audio);
    }

    @OnClick({R.id.icv_setting, R.id.icv_about})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.icv_setting:
                startActivity(new Intent(mContext, SettingActivity.class));
                break;
            case R.id.icv_about:
                startActivity(new Intent(mContext, AboutActivity.class));
                break;
        }
    }
}
