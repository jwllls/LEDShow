package com.led.ledshow.activity;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.widget.Toolbar;

import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.led.ledshow.R;
import com.led.ledshow.base.BaseActivity;
import com.led.ledshow.util.DialogHelper;
import com.led.ledshow.view.ItemCommomView;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnSelectListener;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class PictureActivity extends BaseActivity {


    @BindView(R.id.iv_pic)
    ImageView ivPic;

    @BindView(R.id.toolbar)
    Toolbar toolbar;


    boolean isGif = false;
    @BindView(R.id.tv_anim)
    ItemCommomView tvAnim;
    @BindView(R.id.tv_outline)
    ItemCommomView tvOutline;
    @BindView(R.id.tv_stay_time)
    ItemCommomView tvStatTime;

    //涂鸦图片路径
    private String picPath;

    @Override
    public int getLayoutId() {
        return R.layout.activity_picture;
    }

    @Override
    public void initData() {

        isGif = getIntent().getBooleanExtra("type", false);

        picPath = getIntent().getStringExtra("picPath");

        if (picPath != null) {
            Glide.with(this).load(picPath).into(ivPic);
        }

        initView();

    }

    private void initView() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        toolbar.setTitle(isGif ? "动画" : "图片");
        tvAnim.setleftIcon(R.drawable.icon_text_anim);
        tvOutline.setleftIcon(R.drawable.icon_text_outline);
        tvStatTime.setleftIcon(R.drawable.icon_text_stay_time);
    }


    @OnClick({R.id.btn_select, R.id.iv_pic, R.id.btn_send, R.id.tv_anim, R.id.tv_outline, R.id.tv_stay_time})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_select:
                //选择图片
                selectPicture();
                break;
            case R.id.iv_pic:
                break;
            case R.id.btn_send:
                //发送图片
               /* boolean hasContent = ivPic.getBackground() == null && ivPic.getResources() == null;
                if (!hasContent) {
                    //如果没有内容则弹出提示
                    ToastUtils.showShort("请选择要发送的图片");
                } else {
                    ToastUtils.showShort("正在发送...");
//                    BluetoothChatUtil.getInstance(this).write();
                }*/
//                mClient.sendFile(picPath);
                break;
            case R.id.tv_anim:
                String[] anim = {"淡入淡出", "旋转", "透明"};
                new XPopup.Builder(this)
                        .asBottomList("动画方式", anim, new OnSelectListener() {
                            @Override
                            public void onSelect(int position, String text) {
                                tvAnim.setRightText(text);
                            }
                        }).show();
                break;
            case R.id.tv_outline:
                String[] outline = {"淡入淡出", "旋转", "透明"};
                new XPopup.Builder(this)
                        .asBottomList("环绕边框", outline, new OnSelectListener() {
                            @Override
                            public void onSelect(int position, String text) {
                                tvOutline.setRightText(text);
                            }
                        }).show();
                break;
            case R.id.tv_stay_time:
                DialogHelper.showStayTimeDialog(this, num -> tvStatTime.setRightText(num));
                break;
            default:
                break;
        }
    }


    private void selectPicture() {
        // 进入相册 以下是例子：用不到的api可以不写
        PictureSelector.create(this)
                .openGallery(PictureMimeType.ofImage())
                .isCamera(false)
                .maxSelectNum(1)
                .isGif(true)
                .minimumCompressSize(100)// 小于100kb的图片不压缩
                .forResult(PictureConfig.CHOOSE_REQUEST);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片、视频、音频选择结果回调
                    List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                    picPath = selectList.get(0).getPath();

                    if (isGif) {
                        if (picPath.contains(".gif")) {
                            Glide.with(this).asGif().load(picPath).into(ivPic);
                        } else {
                            ToastUtils.showShort("请选择gif图");
                        }

                    } else {
                        Glide.with(this).load(picPath).into(ivPic);
                    }
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true  注意：音视频除外
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true  注意：音视频除外
                    // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的
//                    adapter.setList(selectList);
//                    adapter.notifyDataSetChanged();
                    break;
            }
        }
    }

}
