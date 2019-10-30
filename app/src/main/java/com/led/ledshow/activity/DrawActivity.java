package com.led.ledshow.activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.PermissionUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.led.ledshow.R;
import com.led.ledshow.base.BaseActivity;
import com.led.ledshow.util.DialogHelper;
import com.led.ledshow.view.ItemCommomView;
import com.led.ledshow.view.PaletteView;
import com.led.ledshow.view.dialog.GridBottomDialog;
import com.lxj.xpopup.interfaces.OnConfirmListener;
import com.lxj.xpopup.interfaces.OnSelectListener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.BindView;
import butterknife.OnClick;


public class DrawActivity extends BaseActivity implements PaletteView.Callback, Handler.Callback {

    @BindView(R.id.palette)
    PaletteView mPaletteView;
    @BindView(R.id.undo)
    ImageView mUndoView;
    @BindView(R.id.redo)
    ImageView mRedoView;
    @BindView(R.id.pen)
    ImageView mPenView;
    @BindView(R.id.eraser)
    ImageView mEraserView;
    @BindView(R.id.clear)
    ImageView mClearView;
    @BindView(R.id.tv_bg_colro)
    ItemCommomView tvBgColro;
    @BindView(R.id.tv_pen_color)
    ItemCommomView tvPenColor;
    @BindView(R.id.tv_pen_size)
    ItemCommomView tvPenSize;
    @BindView(R.id.iv_other)
    ImageView ivOther;
    @BindView(R.id.tv_title)
    TextView tvTitle;



    private ProgressDialog mSaveProgressDlg;
    private static final int MSG_SAVE_SUCCESS = 1;
    private static final int MSG_SAVE_FAILED = 2;
    private Handler mHandler;


    private String savedFile;

    @Override
    public int getLayoutId() {
        return R.layout.activity_draw;
    }

    @Override
    public void initData() {
        mHandler = new Handler(this);
        mPaletteView.setCallback(this);
        mPenView.setSelected(true);
        mUndoView.setEnabled(false);
        mRedoView.setEnabled(false);

        tvTitle.setText(getString(R.string.graffiti));
        ivOther.setImageResource(R.drawable.redo);
        tvBgColro.setOhter(Color.BLACK);
        tvPenColor.setOhter(Color.RED);
        tvPenSize.setRightText("5");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeMessages(MSG_SAVE_FAILED);
        mHandler.removeMessages(MSG_SAVE_SUCCESS);
    }

    private void initSaveProgressDlg() {
        mSaveProgressDlg = new ProgressDialog(this);
        mSaveProgressDlg.setMessage("正在保存,请稍候...");
        mSaveProgressDlg.setCancelable(false);
    }


    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case MSG_SAVE_FAILED:
                mSaveProgressDlg.dismiss();
                Toast.makeText(this, "保存失败", Toast.LENGTH_SHORT).show();
                break;
            case MSG_SAVE_SUCCESS:
                mSaveProgressDlg.dismiss();
                if (savedFile != null) {
                    Intent it = new Intent(this, PictureActivity.class);
                    it.putExtra("picPath", savedFile);
                    startActivity(it);
                }
                break;
            default:
                break;
        }
        return true;
    }

    private static void scanFile(Context context, String filePath) {
        Intent scanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        scanIntent.setData(Uri.fromFile(new File(filePath)));
        context.sendBroadcast(scanIntent);
    }

    private static String saveImage(Bitmap bmp, int quality) {
        if (bmp == null) {
            return null;
        }
        File appDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        if (appDir == null) {
            return null;
        }
        String fileName = System.currentTimeMillis() + ".jpg";
        File file = new File(appDir, fileName);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, quality, fos);
            fos.flush();
            return file.getAbsolutePath();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }


    @Override
    public void onUndoRedoStatusChanged() {
        mUndoView.setEnabled(mPaletteView.canUndo());
        mRedoView.setEnabled(mPaletteView.canRedo());
    }


    @OnClick({R.id.iv_back,R.id.undo, R.id.redo, R.id.pen, R.id.eraser, R.id.clear,R.id.tv_bg_colro, R.id.tv_pen_color, R.id.tv_pen_size, R.id.iv_other})
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                DialogHelper.showConfirmDialog(this, "尚未保存,是否离开", new OnConfirmListener() {
                    @Override
                    public void onConfirm() {
                        finish();
                    }
                });
                break;
            case R.id.undo:
                mPaletteView.undo();
                break;
            case R.id.redo:
                break;
            case R.id.pen:
                v.setSelected(true);
                mEraserView.setSelected(false);
                mPaletteView.setMode(PaletteView.Mode.DRAW);
                break;
            case R.id.eraser:
                v.setSelected(true);
                mPenView.setSelected(false);
                mPaletteView.setMode(PaletteView.Mode.ERASER);
                break;
            case R.id.clear:
                mPaletteView.clear();
                break;
            case R.id.iv_other:
                //请求权限
                PermissionUtils.permission(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}).callback(new PermissionUtils.SimpleCallback() {
                    @Override
                    public void onGranted() {
                        if (mSaveProgressDlg == null) {
                            initSaveProgressDlg();
                        }
                        mSaveProgressDlg.show();
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                Bitmap bm = mPaletteView.buildBitmap();
                                savedFile = saveImage(bm, 100);
                                if (savedFile != null) {
                                    scanFile(DrawActivity.this, savedFile);
                                    mHandler.obtainMessage(MSG_SAVE_SUCCESS).sendToTarget();
                                } else {
                                    mHandler.obtainMessage(MSG_SAVE_FAILED).sendToTarget();
                                }
                            }
                        }).start();
                    }

                    @Override
                    public void onDenied() {
                        ToastUtils.showShort("权限不足");
                    }
                }).request();
                break;
            case R.id.tv_bg_colro:
                DialogHelper.showBackgroundColorDialog(this, new GridBottomDialog.ColorCallback() {
                    @Override
                    public void colorCallback(String color, String text) {
                        int bgColor = Color.parseColor(color);
                        tvBgColro.setOhter(bgColor);
                        mPaletteView.setBgColor(color);
                    }
                });
                break;
            case R.id.tv_pen_color:
                DialogHelper.showBackgroundColorDialog(this, new GridBottomDialog.ColorCallback() {
                    @Override
                    public void colorCallback(String color, String text) {
                        int penColor = Color.parseColor(color);
                        tvPenColor.setOhter(penColor);
                        mPaletteView.setPenColor(penColor);
                    }
                });
                break;
            case R.id.tv_pen_size:
                String[] size = {"10", "20",  "30", "40"};
                DialogHelper.showTextSizeDialog(this, size,new OnSelectListener() {
                    @Override
                    public void onSelect(int position, String text) {
                        tvPenSize.setRightText(text);
                        mPaletteView.setPenRawSize(Integer.valueOf(text));
                    }
                });
                break;
            default:
                break;
        }
    }



}
