package com.led.ledshow.activity;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.led.ledshow.Constant;
import com.led.ledshow.R;
import com.led.ledshow.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class LayoutEditActivity extends BaseActivity {


    @BindView(R.id.iv_src)
    ImageView ivSrc;
    @BindView(R.id.tv1)
    TextView tv1;
    @BindView(R.id.tv2)
    TextView tv2;
    @BindView(R.id.tv3)
    TextView tv3;
    @BindView(R.id.tv4)
    TextView tv4;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.iv_other)
    ImageView ivOther;
    @BindView(R.id.tv_title)
    TextView tvTitle;

    //布局类型:上图下文\上文下图\三行\四行
    private int layoutType;


    private int REQUEST_CODE_EDIT_PICTURE = 0;
    private int REQUEST_CODE_EDIT_TEXT = 1;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_layout_edit;
    }

    @Override
    protected void initData() {
        layoutType = getIntent().getIntExtra(Constant.LAYOUT, 0);
        initView();
    }

    private void initView() {
        tvTitle.setText("编辑组合");
        initLayout(layoutType);
    }


    private void initLayout(int layoutType) {
        if (layoutType == Constant.LAYOUT_TOP_TEXT_BOTTOM_PIC) {
            //上文下图
            ivSrc.setVisibility(View.VISIBLE);
            tv1.setVisibility(View.VISIBLE);
            tv2.setVisibility(View.INVISIBLE);
            tv3.setVisibility(View.INVISIBLE);
            tv4.setVisibility(View.INVISIBLE);
        } else if (layoutType == Constant.LAYOUT_TOP_PIC_BOTTOM_TEXT) {
            //上图下文
            ivSrc.setVisibility(View.VISIBLE);
            tv1.setVisibility(View.GONE);
            tv2.setVisibility(View.INVISIBLE);
            tv3.setVisibility(View.VISIBLE);
            tv4.setVisibility(View.GONE);

        } else if (layoutType == Constant.LAYOUT_THREE_LINE_TEXT) {
            ivSrc.setVisibility(View.GONE);
            tv1.setVisibility(View.VISIBLE);
            tv2.setVisibility(View.VISIBLE);
            tv3.setVisibility(View.VISIBLE);
            tv4.setVisibility(View.INVISIBLE);

        } else if (layoutType == Constant.LAYOUT_FOUR_LINE_TEXT) {
            ivSrc.setVisibility(View.GONE);
            tv1.setVisibility(View.VISIBLE);
            tv2.setVisibility(View.VISIBLE);
            tv3.setVisibility(View.VISIBLE);
            tv4.setVisibility(View.VISIBLE);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == 200) {
            if (requestCode == REQUEST_CODE_EDIT_PICTURE) {
                //图片
            } else if (requestCode == REQUEST_CODE_EDIT_TEXT) {
                //文字
            }

        }

    }

    @OnClick({R.id.iv_src, R.id.tv1, R.id.tv2, R.id.tv3, R.id.tv4, R.id.iv_back, R.id.iv_other})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_src:
                startActivityForResult(new Intent(this, PictureActivity.class), REQUEST_CODE_EDIT_PICTURE);
                break;
            case R.id.tv1:
            case R.id.tv2:
            case R.id.tv3:
            case R.id.tv4:
                startActivityForResult(new Intent(this, EditActivity.class), REQUEST_CODE_EDIT_TEXT);
                break;
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_other:
                break;
        }
    }
}
