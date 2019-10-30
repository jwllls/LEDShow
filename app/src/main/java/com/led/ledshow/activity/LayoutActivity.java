package com.led.ledshow.activity;

import android.content.Intent;
import android.view.View;

import androidx.appcompat.widget.Toolbar;

import com.led.ledshow.Constant;
import com.led.ledshow.R;
import com.led.ledshow.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class LayoutActivity extends BaseActivity {


    @BindView(R.id.toolbar)
    Toolbar toolbar;


    @Override
    public int getLayoutId() {
        return R.layout.activity_layout;
    }

    @Override
    public void initData() {

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        toolbar.setTitle("组合");
    }


    @OnClick({R.id.btn_top_text, R.id.btn_bottom_text, R.id.btn_three_text, R.id.btn_four_text})
    public void onViewClicked(View view) {
        Intent intent = new Intent(this, LayoutEditActivity.class);
        switch (view.getId()) {
            case R.id.btn_top_text:
                //上文下图
                intent.putExtra(Constant.LAYOUT, Constant.LAYOUT_TOP_TEXT_BOTTOM_PIC);
                break;
            case R.id.btn_bottom_text:
                //上图下文
                intent.putExtra(Constant.LAYOUT, Constant.LAYOUT_TOP_PIC_BOTTOM_TEXT);
                break;
            case R.id.btn_three_text:
                //三行字
                intent.putExtra(Constant.LAYOUT, Constant.LAYOUT_THREE_LINE_TEXT);
                break;
            case R.id.btn_four_text:
                //四行字
                intent.putExtra(Constant.LAYOUT, Constant.LAYOUT_FOUR_LINE_TEXT);
                break;
            default:
                break;
        }

        startActivity(intent);
    }
}
