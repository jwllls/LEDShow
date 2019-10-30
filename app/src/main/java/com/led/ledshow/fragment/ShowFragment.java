package com.led.ledshow.fragment;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ToastUtils;
import com.led.ledshow.R;
import com.led.ledshow.activity.EditActivity;
import com.led.ledshow.adapter.ShowListAdapter;
import com.led.ledshow.base.BaseFragment;
import com.led.ledshow.model.ShowModel;
import com.led.ledshow.util.DialogHelper;
import com.lxj.xpopup.interfaces.OnConfirmListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bingoogolapple.baseadapter.BGAOnItemChildClickListener;

/**
 * Created by linjianwen on 2019/10/14.
 */
public class ShowFragment extends BaseFragment implements BGAOnItemChildClickListener {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.iv_back)
    ImageView ivBack;

    @BindView(R.id.iv_other)
    ImageView ivOther;


    private ShowListAdapter adapter;

    @Override
    protected void loadData() {

    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_show_list;
    }

    @Override
    protected void initData() {

        adapter = new ShowListAdapter(recyclerView);

        adapter.setOnItemChildClickListener(this);

        recyclerView.setAdapter(adapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));

        testData();

        ivBack.setVisibility(View.GONE);
        ivOther.setImageResource(R.drawable.redo);

    }

    //todo
    private void testData() {
        List<ShowModel> models = new ArrayList<>();
        ShowModel m1 = new ShowModel();
        m1.setTitle("节目1");
        m1.setNum("1");
        ShowModel m2 = new ShowModel();
        m2.setTitle("节目2");
        m2.setNum("2");
        ShowModel m3 = new ShowModel();
        m3.setTitle("节目3");
        m3.setNum("3");

        ShowModel m4 = new ShowModel();
        m4.setTitle("节目4");
        m4.setNum("4");

        models.add(m1);
        models.add(m2);
        models.add(m3);
        adapter.setData(models);
    }


    @OnClick({R.id.iv_back,R.id.iv_other})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                break;
            case R.id.iv_other:
                ToastUtils.showShort(R.string.send);
                break;

        }
    }


    @Override
    public void onItemChildClick(ViewGroup parent, View childView, int position) {
        if (childView.getId() == R.id.tv_edit) {
            //根据类型跳转到编辑页面
            startActivity(new Intent(mContext, EditActivity.class));

        } else if (childView.getId() == R.id.tv_delete) {
            DialogHelper.showConfirmDialog(mContext, "确定要删除此节目吗?", new OnConfirmListener() {
                @Override
                public void onConfirm() {
                    //弹出提示删除弹窗
                    adapter.removeItem(position);
                }
            });

        }
    }
}

