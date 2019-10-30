package com.led.ledshow.view.dialog;

import android.content.Context;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;

import com.led.ledshow.R;
import com.lxj.xpopup.core.BottomPopupView;

import butterknife.BindView;
import butterknife.OnClick;

public class SelectNumBottomDialog extends BottomPopupView {

    private NumberCallback numberCallback;
    @BindView(R.id.et_num)
    EditText etNum;

    public SelectNumBottomDialog(@NonNull Context context,NumberCallback numberCallback) {
        super(context);
        this.numberCallback = numberCallback;
    }


    @Override
    protected int getImplLayoutId() {
        return R.layout.dialog_select_num;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        initData();
    }

    private void initData() {

    }


    @OnClick({R.id.btn_cancel, R.id.btn_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_cancel:
                dismiss();
                break;
            case R.id.btn_confirm:
                numberCallback.getNum(etNum.getText().toString());
                break;
        }
    }


    public interface NumberCallback {
        void getNum(String num);
    }
}
