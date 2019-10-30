package com.led.ledshow.fragment;


import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.fragment.app.Fragment;

import com.led.ledshow.R;
import com.led.ledshow.activity.BluetoothActivity;
import com.led.ledshow.activity.DrawActivity;
import com.led.ledshow.activity.EditActivity;
import com.led.ledshow.activity.LayoutActivity;
import com.led.ledshow.activity.PictureActivity;
import com.led.ledshow.activity.ResLibActivity;
import com.led.ledshow.base.BaseFragment;
import com.led.ledshow.bt.BtServerActivity;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * A simple {@link Fragment} subclass.
 */
public class MenuFragment extends BaseFragment {


    @BindView(R.id.titlebar)
    RelativeLayout titlebar;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.iv_other)
    ImageView ivOther;

    @Override
    protected void loadData() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_menu;
    }

    @Override
    protected void initData() {

//        titlebar.setBackground(ContextCompat.getDrawable(mContext, R.color.white));
        ivBack.setImageResource(R.drawable.icon_add_connect);
        ivOther.setImageResource(R.drawable.icon_more);

    }

    @OnClick({ R.id.btn_gif, R.id.btn_text, R.id.btn_pic, R.id.btn_draw, R.id.btn_file, R.id.btn_lib,R.id.iv_back, R.id.iv_other})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_gif:
                Intent it = new Intent(mContext, PictureActivity.class);
                it.putExtra("type", true);
                startActivity(it);
                break;
            case R.id.btn_text:
                startActivity(new Intent(mContext, EditActivity.class));
                break;
            case R.id.btn_pic:
                startActivity(new Intent(mContext, PictureActivity.class));
                break;
            case R.id.btn_draw:
                startActivity(new Intent(mContext, DrawActivity.class));
                break;
            case R.id.btn_file:
                startActivity(new Intent(mContext, LayoutActivity.class));
                break;
            case R.id.btn_lib:
                startActivity(new Intent(mContext, ResLibActivity.class));
                break;
            case R.id.iv_back:
//                startActivity(new Intent(mContext, BtClientActivity.class));
                startActivity(new Intent(mContext, BluetoothActivity.class));
                break;
            case R.id.iv_other:
                startActivity(new Intent(mContext, BtServerActivity.class));
                break;
            default:
                break;
        }
    }
}
