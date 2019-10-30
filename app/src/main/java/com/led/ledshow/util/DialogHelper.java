package com.led.ledshow.util;

import android.content.Context;

import com.led.ledshow.view.dialog.GridBottomDialog;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BasePopupView;
import com.lxj.xpopup.interfaces.OnConfirmListener;
import com.lxj.xpopup.interfaces.OnInputConfirmListener;
import com.lxj.xpopup.interfaces.OnSelectListener;
import com.lxj.xpopup.util.XPopupUtils;

/**
 * Created by linjianwen on 2019/10/8.
 * <p>
 * 弹窗工具类
 */
public class DialogHelper {


    private BasePopupView basePopupView;

    public void dismissLoadingDialog() {
        basePopupView.dismiss();
    }


    //字体
    public static void showTextStyleDialog(Context context, OnSelectListener listener) {
        String[] title = {"黑体", "宋体", "楷体", "隶书", "雅黑", "幼圆", "华园"};
//        int[] iconId = {R.drawable.ic_apk_box, R.drawable.ic_apk_box, R.drawable.ic_apk_box, R.drawable.ic_apk_box,
//                R.drawable.ic_apk_box, R.drawable.ic_apk_box, R.drawable.ic_apk_box};
        new XPopup.Builder(context)
                .maxHeight((int) (XPopupUtils.getWindowHeight(context) * .40f))
                .asBottomList("字体", title, listener)
                .show();
    }


    //字体大小
    public static void showTextSizeDialog(Context context, String[] sizeList, OnSelectListener listener) {
        new XPopup.Builder(context)
                .maxHeight((int) (XPopupUtils.getWindowHeight(context) * .40f))
                .asBottomList("字体大小", sizeList, listener)
                .show();
    }


    //背景颜色
    public static void showBackgroundColorDialog(Context context, GridBottomDialog.ColorCallback colorCallback) {
        GridBottomDialog dialog = new GridBottomDialog(context);
        dialog.setCallback(colorCallback);
        new XPopup.Builder(context)
                .maxHeight((int) (XPopupUtils.getWindowHeight(context) * .40f))
                .hasStatusBarShadow(true) //启用状态栏阴影
                .asCustom(dialog)
                .show();
    }


    public static void showListDialog(Context context, String title, String[] sizeList, OnSelectListener listener) {
        new XPopup.Builder(context)
                .maxHeight((int) (XPopupUtils.getWindowHeight(context) * .40f))
                .asBottomList(title, sizeList, listener)
                .show();
    }


    public static void showConfirmDialog(Context context, String content, OnConfirmListener listener) {
        new XPopup.Builder(context)
                .asConfirm("温馨提示", content, listener)
                .show();
    }

    public static void showStayTimeDialog(Context context, OnInputConfirmListener listener) {
        new XPopup.Builder(context)
                .maxHeight((int) (XPopupUtils.getWindowHeight(context) * .40f))
                .hasStatusBarShadow(true) //启用状态栏阴影
                .asInputConfirm("停留时间", "请输入数字", listener)
                .show();
    }



    public static void showBondDevices(){

    }






}
