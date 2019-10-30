package com.led.ledshow.activity;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.blankj.utilcode.util.ToastUtils;
import com.clj.fastble.BleManager;
import com.led.ledshow.R;
import com.led.ledshow.base.BaseActivity;
import com.led.ledshow.fragment.MenuFragment;
import com.led.ledshow.fragment.SettingFragment;
import com.led.ledshow.fragment.ShowFragment;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnConfirmListener;

import java.util.List;

import butterknife.BindViews;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {


    @BindViews({R.id.iv_menu, R.id.iv_show_list, R.id.iv_me})
    List<ImageView> tab_ivs;
    @BindViews({R.id.tab_menu, R.id.tab_show_list, R.id.tab_me})
    List<View> tabs;
    @BindViews({R.id.tv_menu, R.id.tv_show_list, R.id.tv_me})
    List<TextView> tab_tvs;


    private FragmentManager fragmentManager;
    private MenuFragment menuFragment;

    private SettingFragment settingFragment;

    private ShowFragment showListFragment;
//    private MineFragment mineFragment;


    @Override
    public int getLayoutId() {
        return R.layout.activity_main_tab;
    }


    @Override
    public void initData() {
        fragmentManager = getSupportFragmentManager();
        showFragment(1);
        initBlueTooth();
    }

    private void initBlueTooth() {
        //判断是否支持蓝牙,不支持则退出应用

        if (!BleManager.getInstance().isSupportBle()) {
            ToastUtils.showShort("设备不支持低功耗蓝牙");
            return;
        }

        if (BluetoothAdapter.getDefaultAdapter() == null) {
            new XPopup.Builder(this)
                    .asConfirm(getString(R.string.tips), getString(R.string.not_support_bluetooth), new OnConfirmListener() {
                        @Override
                        public void onConfirm() {
                            finish();
                        }
                    }).show();
            return;
        }

        //判断蓝牙是否打开
        if (!BluetoothAdapter.getDefaultAdapter().isEnabled()) {
            ToastUtils.showShort("蓝牙尚未打开");
            new XPopup.Builder(this)
                    .asConfirm(getString(R.string.tips), "是否打开蓝牙?", new OnConfirmListener() {
                        @Override
                        public void onConfirm() {
                            //强制打开蓝牙
                            BluetoothAdapter.getDefaultAdapter().enable();
                            //打开蓝牙后开始扫描
                            //扫描已绑定的设备
                            BluetoothAdapter.getDefaultAdapter().getBondedDevices();
                        }
                    }).show();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (resultCode == PictureConfig.CHOOSE_REQUEST) {
                // 图片、视频、音频选择结果回调
                List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
            }
        }
    }

    //记录用户首次点击返回键的时间
    private long firstTime = 0;

    @Override
    public void onBackPressed() {
        long secondTime = System.currentTimeMillis();
        if (secondTime - firstTime > 2000) {
            ToastUtils.showShort("再按一次退出程序");
            firstTime = secondTime;
        } else {
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    //    ---------------------------------------------Fragment显藏-----------------------------------------------
    private void showFragment(int page) {
        FragmentTransaction ft = fragmentManager.beginTransaction();
        // 想要显示一个fragment,先隐藏所有fragment，防止重叠
        hideFragments(ft);
        switch (page) {
            case 1:
                // 如果fragment1已经存在则将其显示出来
                if (menuFragment != null) {
                    ft.show(menuFragment);
                } else {
                    // 否则添加fragment1，注意添加后是会显示出来的，replace方法也是先remove后add
                    menuFragment = new MenuFragment();
                    ft.add(R.id.fragment_container, menuFragment);
                }
                //选中第一个Tab时的状态
                tab_ivs.get(0).setImageResource(tabResId_p[0]);
                tab_tvs.get(0).setTextColor(ContextCompat.getColor(this, R.color.blue));
                break;
            case 2:
                if (showListFragment != null) {
                    ft.show(showListFragment);
                } else {
                    showListFragment = new ShowFragment();
                    ft.add(R.id.fragment_container, showListFragment);
                }
                break;
            case 3:
                if (settingFragment != null) {
                    ft.show(settingFragment);
                } else {
                    settingFragment = new SettingFragment();
                    ft.add(R.id.fragment_container, settingFragment);
                }
                break;
            default:
                break;

        }
        ft.commit();
    }

    //当fragment已被实例化，相当于发生过切换，就隐藏起来
    public void hideFragments(FragmentTransaction ft) {
        if (menuFragment != null) {
            ft.hide(menuFragment);
        }
        if (showListFragment != null) {
            ft.hide(showListFragment);
        }
        if (settingFragment != null) {
            ft.hide(settingFragment);
        }
    }

    //    ---------------------------------------------点击事件----------------------------------------------------
    private int index;
    private int currentTabIndex;// 当前fragment的index
    private int tabResId[] = {R.drawable.icon_menu_n, R.drawable.icon_show_n, R.drawable.icon_mine_n};
    private int tabResId_p[] = {R.drawable.icon_menu_p, R.drawable.icon_show_p, R.drawable.icon_mine_p};


    public void onTabClick(View v) {
        //每次点击遍历下标
        for (int i = 0; i < tabs.size(); i++) {
            if (tabs.get(i) == v) {
                index = i;
                break;
            }
        }
        if (currentTabIndex != index) {
            //未选中
            showFragment(index + 1);
            tab_ivs.get(currentTabIndex).setImageResource(tabResId[currentTabIndex]);
            tab_tvs.get(currentTabIndex).setTextColor(ContextCompat.getColor(this, R.color.gray));
        }
        //选中
        tab_ivs.get(index).setImageResource(tabResId_p[index]);
        tab_tvs.get(index).setTextColor(ContextCompat.getColor(this, R.color.white));
        currentTabIndex = index;
    }

    @OnClick({R.id.tab_menu, R.id.tab_show_list, R.id.tab_me})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tab_menu:
                onTabClick(tabs.get(0));
                break;
            case R.id.tab_show_list:
                onTabClick(tabs.get(1));
                break;
            case R.id.tab_me:
                onTabClick(tabs.get(2));
                break;
        }
    }
}
