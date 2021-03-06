package com.vs.vipsai.settings.information;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Html;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.vs.vipsai.AppConfig;
import com.vs.vipsai.AppContext;
import com.vs.vipsai.R;
import com.vs.vipsai.adapter.ArrayWheelAdapter;
import com.vs.vipsai.base.fragments.BaseFragment;
import com.vs.vipsai.bean.Version;
import com.vs.vipsai.ui.SimpleBackActivity;
import com.vs.vipsai.ui.activity.CityListActivity;
import com.vs.vipsai.update.CheckUpdateManager;
import com.vs.vipsai.util.DialogHelper;
import com.vs.vipsai.util.FileUtil;
import com.vs.vipsai.util.MethodsCompat;
import com.vs.vipsai.util.SimplexToast;
import com.vs.vipsai.util.UIHelper;
import com.vs.vipsai.widget.togglebutton.ToggleButton;
import com.vs.vipsai.widget.wheel.WheelView;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Author: cynid
 * Created on 3/17/18 10:37 AM
 * Description:
 *
 * 界面
 */

public class InformationFragment extends BaseFragment implements EasyPermissions.PermissionCallbacks, CheckUpdateManager.RequestPermissions {

    private static final int RC_EXTERNAL_STORAGE = 0x04;//存储权限



    private Version mVersion;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true); //添加菜单不调用该方法是没有用的
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_information, container,
                false);
        ButterKnife.bind(this, view);
        initView(view);
        initData();
        return view;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_information;
    }

    @Override
    public void initView(View view) {

//        mTbDoubleClickExit.setOnToggleChanged(new ToggleButton.OnToggleChanged() {
//            @Override
//            public void onToggle(boolean on) {
//                AppContext.set(AppConfig.KEY_DOUBLE_CLICK_EXIT, on);
//            }
//        });
//
//        view.findViewById(R.id.rl_clean_cache).setOnClickListener(this);
//        view.findViewById(R.id.rl_double_click_exit).setOnClickListener(this);
//        view.findViewById(R.id.rl_about).setOnClickListener(this);
//        view.findViewById(R.id.rl_check_version).setOnClickListener(this);
//        // view.findViewById(R.id.rl_exit).setOnClickListener(this);
//        view.findViewById(R.id.rl_feedback).setOnClickListener(this);
//
//
//        view.findViewById(R.id.fragment_settings_location_option).setOnClickListener(this);
//        view.findViewById(R.id.rl_payment_password).setOnClickListener(this);
//        view.findViewById(R.id.fragment_settings_help_option).setOnClickListener(this);
//        view.findViewById(R.id.fragment_settings_email_notify).setOnClickListener(this);
//        view.findViewById(R.id.fragment_settings_time_zone).setOnClickListener(this);
//        view.findViewById(R.id.fragment_settings_domain).setOnClickListener(this);



//        mCancel.setOnClickListener(this);

//        SystemConfigView.show((ViewGroup) view.findViewById(R.id.lay_linear));
    }

    @Override
    public void initData() {
//        if (AppContext.get(AppConfig.KEY_DOUBLE_CLICK_EXIT, true)) {
//            mTbDoubleClickExit.setToggleOn();
//        } else {
//            mTbDoubleClickExit.setToggleOff();
//        }
//        calculateCacheSize();
    }

    @Override
    public void onResume() {
        super.onResume();
//        boolean login = AccountHelper.isLogin();
//        if (!login) {
//            mCancel.setVisibility(View.INVISIBLE);
//            mSettingLineTop.setVisibility(View.INVISIBLE);
//            mSettingLineBottom.setVisibility(View.INVISIBLE);
//        } else {
//            mCancel.setVisibility(View.VISIBLE);
//            mSettingLineTop.setVisibility(View.VISIBLE);
//            mSettingLineBottom.setVisibility(View.VISIBLE);
//        }
    }

//    /**
//     * 计算缓存的大小
//     */
//    private void calculateCacheSize() {
//        long fileSize = 0;
//        String cacheSize = "0KB";
//        File filesDir = getActivity().getFilesDir();
//        File cacheDir = getActivity().getCacheDir();
//
//        fileSize += FileUtil.getDirSize(filesDir);
//        fileSize += FileUtil.getDirSize(cacheDir);
//        // 2.2版本才有将应用缓存转移到sd卡的功能
//        if (AppContext.isMethodsCompat(android.os.Build.VERSION_CODES.FROYO)) {
//            File externalCacheDir = MethodsCompat
//                    .getExternalCacheDir(getActivity());
//            fileSize += FileUtil.getDirSize(externalCacheDir);
//        }
//        if (fileSize > 0)
//            cacheSize = FileUtil.formatFileSize(fileSize);
//        mTvCacheSize.setText(cacheSize);
//    }

    @Override
    public void onClick(View v) {
        final int id = v.getId();
        switch (id) {
//            case R.id.rl_clean_cache:
//                onClickCleanCache();
//                break;
//            case R.id.rl_double_click_exit:
//                mTbDoubleClickExit.toggle();
//                break;
//            case R.id.rl_feedback:
                //UIHelper.showSimpleBack(getActivity(), SimpleBackPage.FEED_BACK);
//                if (!AccountHelper.isLogin()) {
//                    LoginActivity.show(getContext());
//                    return;
//                }
//                FeedBackActivity.show(getActivity());
//                break;
//            case R.id.rl_about:
//                UIHelper.showAboutOSC(getActivity());
//                break;
//            case R.id.rl_check_version:
//                onClickUpdate();
//                break;
//            case R.id.rl_cancel:
//                // 清理所有缓存
//                UIHelper.clearAppCache(false);
//                // 注销操作
//                AccountHelper.logout(mCancel, new Runnable() {
//                    @SuppressLint("SetTextI18n")
//                    @Override
//                    public void run() {
//                        //getActivity().finish();
//                        mTvCacheSize.setText("0KB");
//                        AppContext.showToastShort(getString(R.string.logout_success_hint));
//                        mCancel.setVisibility(View.INVISIBLE);
//                        mSettingLineTop.setVisibility(View.INVISIBLE);
//                        mSettingLineBottom.setVisibility(View.INVISIBLE);
//                    }
//                });
//                break;


            case R.id.fragment_settings_location_option:
                CityListActivity.show(getActivity());
                break;

            case R.id.fragment_settings_domain:
                UIHelper.showDomain(getActivity());
                break;

            case R.id.fragment_settings_help_option:
                UIHelper.showHelpCenter(getActivity());
                break;




            default:
                break;
        }

    }

    private void onClickUpdate() {
        CheckUpdateManager manager = new CheckUpdateManager(getActivity(), true);
        manager.setCaller(this);
        manager.checkUpdate(true);
    }

    private void onClickCleanCache() {
        DialogHelper.getConfirmDialog(getActivity(), "是否清空缓存?", new DialogInterface.OnClickListener
                () {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
//                UIHelper.clearAppCache(true);
//                mTvCacheSize.setText("0KB");
            }
        }).show();
    }

    @Override
    public void call(Version version) {
        this.mVersion = version;
        requestExternalStorage();
    }

    @SuppressLint("InlinedApi")
    @AfterPermissionGranted(RC_EXTERNAL_STORAGE)
    public void requestExternalStorage() {
        if (EasyPermissions.hasPermissions(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)) {
//            DownloadService.startService(getActivity(), mVersion.getDownloadUrl());
        } else {
            EasyPermissions.requestPermissions(this, "", RC_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE);
        }
    }


    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        DialogHelper.getConfirmDialog(getActivity(), "温馨提示", "需要开启微赛小视频对您手机的存储权限才能下载安装，是否现在开启", "去开启", "取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(Settings.ACTION_APPLICATION_SETTINGS));
            }
        }).show();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }







    /**
     * 创建菜单
     *
     * @param menu
     * @param inflater
     */
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        if (menu != null) {
            if (menu.getClass().getSimpleName().equalsIgnoreCase("MenuBuilder")) {
                try {
                    Method method = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
                    method.setAccessible(true);
                    method.invoke(menu, true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        inflater.inflate(R.menu.save_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem itemSave = menu.findItem(R.id.public_menu_shared);
        itemSave.setTitle(Html.fromHtml("<font color='#48AAFB'>保存</font>"));
//        if (TextUtils.isEmpty(etMobile.getText())) {
//            itemSubmit.setEnabled(false);
//            itemSubmit.setTitle(Html.fromHtml("<font color='#666666'>完成</font>"));
//        } else {
//            itemSubmit.setEnabled(true);
//            itemSubmit.setTitle(Html.fromHtml("<font color='#5CC31F'>完成</font>"));
//        }

        super.onPrepareOptionsMenu(menu);
    }

    /**
     * 菜单按钮点击事件
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.public_menu_shared:
                // Back To Previous Activity
                ((SimpleBackActivity) mContext).onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
