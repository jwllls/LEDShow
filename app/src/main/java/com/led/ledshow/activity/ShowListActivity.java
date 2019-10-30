package com.led.ledshow.activity;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.led.ledshow.R;
import com.led.ledshow.adapter.ShowListAdapter;
import com.led.ledshow.base.BaseActivity;
import com.led.ledshow.model.ShowModel;
import com.led.ledshow.util.DialogHelper;
import com.lxj.xpopup.interfaces.OnConfirmListener;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;
import cn.bingoogolapple.baseadapter.BGAOnItemChildClickListener;

public class ShowListActivity extends BaseActivity implements BGAOnItemChildClickListener {


    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.btn_send)
    Button btnSend;


    private ShowListAdapter adapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_show_list;
    }

    @Override
    public void initData() {

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        adapter = new ShowListAdapter(recyclerView);

        adapter.setOnItemChildClickListener(this);

        recyclerView.setAdapter(adapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        testData();

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

    @OnClick({R.id.btn_send})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_send:
                //发送节目
                break;
        }
    }


    @Override
    public void onItemChildClick(ViewGroup parent, View childView, int position) {
        if (childView.getId() == R.id.tv_edit) {
            //根据类型跳转到编辑页面
            startActivity(new Intent(this, EditActivity.class));

        } else if (childView.getId() == R.id.tv_delete) {
            DialogHelper.showConfirmDialog(this, "确定要删除此节目吗?", new OnConfirmListener() {
                @Override
                public void onConfirm() {
                    //弹出提示删除弹窗
                    adapter.removeItem(position);
                }
            });

        }
    }
}
