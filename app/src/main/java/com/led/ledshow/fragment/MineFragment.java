package com.led.ledshow.fragment;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.led.ledshow.R;
import com.led.ledshow.base.BaseFragment;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by linjianwen on 2019/10/14.
 */
public class MineFragment extends BaseFragment {


    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.iv_other)
    ImageView ivOther;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;


    @Override
    protected void loadData() {
        //获取已绑定的蓝牙

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void initData() {

        initView();
    }

    private void initView() {

        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));


    }


    @OnClick({R.id.iv_back, R.id.iv_other})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                break;
            case R.id.iv_other:
                break;
        }
    }
}
