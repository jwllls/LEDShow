package com.led.ledshow.activity;

import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.CacheMemoryUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.led.ledshow.R;
import com.led.ledshow.base.BaseActivity;
import com.led.ledshow.view.ItemCommomView;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnConfirmListener;

import butterknife.BindView;
import butterknife.OnClick;

public class SettingActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.icv_clean_cache)
    ItemCommomView icvCleanCache;
    @BindView(R.id.icv_fast_send)
    ItemCommomView icvFastSend;


    int cacheSize;


    @Override
    public int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    public void initData() {
        //获取数据缓存
        cacheSize = CacheMemoryUtils.getInstance().getCacheCount();
        initView();
    }


    private void initView() {

        icvCleanCache.setleftIcon(R.drawable.icon_audio);

        icvFastSend.setRIghtIcon(R.drawable.icon_audio);

        icvCleanCache.setRightText(cacheSize + "KB");
    }

    @OnClick({R.id.iv_back, R.id.iv_other, R.id.icv_clean_cache, R.id.icv_fast_send})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_other:
                break;
            case R.id.icv_clean_cache:
                new XPopup.Builder(this)
                        .asConfirm(getString(R.string.tips), "是否清除所有缓存?", new OnConfirmListener() {
                            @Override
                            public void onConfirm() {
                                ToastUtils.showShort("清除成功!");
                                icvCleanCache.setRightText("0 KB");
                            }
                        }).show();
                break;
            case R.id.icv_fast_send:
                break;
        }
    }
}
