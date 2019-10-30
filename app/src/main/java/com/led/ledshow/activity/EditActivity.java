package com.led.ledshow.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.led.ledshow.R;
import com.led.ledshow.base.BaseActivity;
import com.led.ledshow.util.DialogHelper;
import com.led.ledshow.view.ItemCommomView;
import com.led.ledshow.view.dialog.GridBottomDialog;
import com.lxj.xpopup.interfaces.OnInputConfirmListener;
import com.lxj.xpopup.interfaces.OnSelectListener;
import com.yalantis.ucrop.util.BitmapUtils;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;

public class EditActivity extends BaseActivity {


    @BindView(R.id.btn_save)
    Button btnSave;
    @BindView(R.id.editText)
    EditText editText;
    @BindView(R.id.tv_plate)
    TextView tvPlate;
    @BindView(R.id.tv_text_style)
    ItemCommomView tvTextStyle;
    @BindView(R.id.tv_text_size)
    ItemCommomView tvTextSize;
    @BindView(R.id.tv_text_colorful)
    ItemCommomView tvTextColorful;
    @BindView(R.id.tv_text_anim)
    ItemCommomView tvTextAnim;
    @BindView(R.id.tv_text_speed)
    ItemCommomView tvTextSpeed;
    @BindView(R.id.tv_text_color)
    ItemCommomView tvTextColor;
    @BindView(R.id.tv_stay_time)
    ItemCommomView tvStayTime;
    @BindView(R.id.tv_alia)
    ItemCommomView tvAlia;
    @BindView(R.id.tv_outline)
    ItemCommomView tvOutline;
    @BindView(R.id.tv_couplet)
    ItemCommomView tvCouplet;
    @BindView(R.id.tv_bg_color)
    ItemCommomView tvBgColor;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.iv_other)
    ImageView ivOther;
    @BindView(R.id.tv_title)
    TextView tvTitle;



    @Override
    public int getLayoutId() {
        return R.layout.activity_edit;
    }

    @Override
    public void initData() {

        initView();
    }

    private void initView() {

        ivBack.setImageResource(R.drawable.picture_back);
        ivOther.setImageResource(R.drawable.redo);
        tvTitle.setText(getString(R.string.picture));

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                tvPlate.setText(editable.toString());
            }
        });

        initDefaultData();


    }

    private void initDefaultData() {
        //字体
        tvTextStyle.setleftIcon(R.drawable.icon_text_text_style);
        //大小
        tvTextSize.setleftIcon(R.drawable.icon_text_text_size);
        //炫彩字体
        tvTextColorful.setleftIcon(R.drawable.icon_text_colorful);
        //动画方式
        tvTextAnim.setleftIcon(R.drawable.icon_text_anim);
        //移动速度
        tvTextSpeed.setleftIcon(R.drawable.icon_text_speed);
        //颜色
        tvTextColor.setleftIcon(R.drawable.icon_text_color);
        //停留时间
        tvStayTime.setleftIcon(R.drawable.icon_text_stay_time);
        //对齐方式
        tvAlia.setleftIcon(R.drawable.icon_text_alia);
        //环绕边框
        tvOutline.setleftIcon(R.drawable.icon_text_outline);
        //背景
        tvBgColor.setleftIcon(R.drawable.icon_text_background);
        //对联显示
        tvCouplet.setleftIcon(R.drawable.icon_text_couplet);
        //默认字体
        tvTextStyle.setRightText("宋体");
        tvTextSize.setRightText("20");
        tvBgColor.setRightText(getString(R.string.color_black));

    }

    private void saveBitmap() {
        String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath() + "/" + System.currentTimeMillis() + ".jpg";
        BitmapUtils.saveBitmap(getViewBitmap(tvPlate), path);
        ToastUtils.showShort("图片已经保存到：" + path);
        scanFile(this, path);
    }


    private static void scanFile(Context context, String filePath) {
        Intent scanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        scanIntent.setData(Uri.fromFile(new File(filePath)));
        context.sendBroadcast(scanIntent);
    }

    public Bitmap getViewBitmap(View view) {
        if (view == null)
            return null;
        Rect r = new Rect();
        view.getGlobalVisibleRect(r);
        WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        int w = display.getWidth();
        int h = display.getHeight();
        Bitmap Bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        View decorview = getWindow().getDecorView();
        decorview.setDrawingCacheEnabled(true);
        Bmp = decorview.getDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(Bmp, r.left, r.top, r.right - r.left, r.bottom - r.top);
        decorview.setDrawingCacheEnabled(false);
        return bitmap;
    }


    @OnClick({R.id.btn_cancel, R.id.btn_save, R.id.tv_text_style, R.id.tv_text_size, R.id.tv_text_colorful,
            R.id.tv_text_anim, R.id.tv_text_speed, R.id.tv_text_color, R.id.tv_stay_time,
            R.id.tv_alia, R.id.tv_outline, R.id.tv_couplet, R.id.tv_bg_color,
            R.id.iv_back, R.id.iv_other
    })
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_text_style:
                DialogHelper.showTextStyleDialog(this, (position, text) -> {
                    tvTextStyle.setRightText(text);
                });
                break;
            case R.id.tv_text_size:
                String[] size = {"14", "16", "18", "20", "22", "24", "26", "28", "30", "32"};
                DialogHelper.showTextSizeDialog(this, size, (position, text) -> {
                    tvTextSize.setRightText(text);
                    tvPlate.setTextSize(ConvertUtils.sp2px(Integer.valueOf(text)));
                });
                break;
            case R.id.tv_text_colorful:
                DialogHelper.showListDialog(this, "炫彩字体", new String[]{"一字一色", "两字一色", "一行一色"}, new OnSelectListener() {
                    @Override
                    public void onSelect(int position, String text) {
                        tvTextColorful.setRightText(text);
                    }
                });
                break;
            case R.id.tv_text_anim:
                ToastUtils.showShort("动画");
                break;
            case R.id.tv_text_speed:
                break;
            case R.id.tv_text_color:
                //文字颜色
                DialogHelper.showBackgroundColorDialog(this, new GridBottomDialog.ColorCallback() {
                    @Override
                    public void colorCallback(String color, String text) {
                        tvPlate.setTextColor(Color.parseColor(color));
                        tvTextColor.setRightText(text);
                    }
                });
                break;
            case R.id.tv_stay_time:
                //持续时间
                DialogHelper.showStayTimeDialog(this, new OnInputConfirmListener() {
                    @Override
                    public void onConfirm(String time) {
                        tvStayTime.setRightText(time);
                    }
                });
                break;
            case R.id.tv_alia:
                //对齐方式
                DialogHelper.showListDialog(this, "对齐方式", new String[]{"方式1", "方式2", "方式3", "方式4"}, new OnSelectListener() {
                    @Override
                    public void onSelect(int position, String text) {
                        tvAlia.setRightText(text);
                    }
                });

                break;
            case R.id.tv_outline:
                //背景外框
                DialogHelper.showListDialog(this, "背景外框", new String[]{"外框1", "外框2", "外框3", "外框4"}, new OnSelectListener() {
                    @Override
                    public void onSelect(int position, String text) {
                        tvOutline.setRightText(text);
                    }
                });
                break;
            case R.id.tv_couplet:
                //对联
                DialogHelper.showListDialog(this, "对联显示", new String[]{"对联1", "对联2", "对联3", "对联4"}, new OnSelectListener() {
                    @Override
                    public void onSelect(int position, String text) {
                        tvCouplet.setRightText(text);
                    }
                });
                break;
            case R.id.tv_bg_color:
                DialogHelper.showBackgroundColorDialog(this, new GridBottomDialog.ColorCallback() {
                    @Override
                    public void colorCallback(String color, String text) {
                        tvPlate.setBackgroundColor(Color.parseColor(color));
                        tvBgColor.setRightText(text);
                    }
                });
                break;
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_other:
                if (!tvPlate.getText().toString().isEmpty()) {
                    saveBitmap();
                } else {
                    ToastUtils.showShort("请输入内容!");
                }
                break;
            default:
                break;
        }
    }
}
