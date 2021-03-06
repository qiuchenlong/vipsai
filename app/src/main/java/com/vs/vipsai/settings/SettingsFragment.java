package com.vs.vipsai.settings;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.vs.vipsai.AppConfig;
import com.vs.vipsai.AppContext;
import com.vs.vipsai.BaseApplication;
import com.vs.vipsai.R;
import com.vs.vipsai.adapter.ArrayWheelAdapter;
import com.vs.vipsai.base.fragments.BaseFragment;
import com.vs.vipsai.bean.Version;
import com.vs.vipsai.constants.Constants;
import com.vs.vipsai.ui.activity.CityListActivity;
import com.vs.vipsai.update.CheckUpdateManager;
import com.vs.vipsai.util.DialogHelper;
import com.vs.vipsai.util.FileUtil;
import com.vs.vipsai.util.MethodsCompat;
import com.vs.vipsai.util.SimplexToast;
import com.vs.vipsai.util.UIHelper;
import com.vs.vipsai.widget.dialog.AlertWheelDialog;
import com.vs.vipsai.widget.togglebutton.ToggleButton;
import com.vs.vipsai.widget.wheel.WheelView;

import java.io.File;
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
 * 系统设置界面
 */

public class SettingsFragment extends BaseFragment implements EasyPermissions.PermissionCallbacks, CheckUpdateManager.RequestPermissions {

    private static final int RC_EXTERNAL_STORAGE = 0x04;//存储权限

    @BindView(R.id.tv_cache_size)
    TextView mTvCacheSize;
    @BindView(R.id.rl_check_version)
    FrameLayout mRlCheck_version;
    @BindView(R.id.tb_double_click_exit)
    ToggleButton mTbDoubleClickExit;
//    @BindView(R.id.setting_line_top)
//    View mSettingLineTop;
//    @BindView(R.id.setting_line_bottom)
//    View mSettingLineBottom;
    @BindView(R.id.rl_cancel)
    FrameLayout mCancel;

    private Version mVersion;




    @BindView(R.id.fragment_settings_time_zone_label)
    TextView timeZoneLabel;
    @BindView(R.id.fragment_settings_email_notify_label)
    TextView emailNotifyLabel;



    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container,
                false);
        ButterKnife.bind(this, view);
        initView(view);
        initData();
        return view;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_settings;
    }

    @Override
    public void initView(View view) {

        mTbDoubleClickExit.setOnToggleChanged(new ToggleButton.OnToggleChanged() {
            @Override
            public void onToggle(boolean on) {
                AppContext.set(AppConfig.KEY_DOUBLE_CLICK_EXIT, on);
            }
        });

        view.findViewById(R.id.rl_clean_cache).setOnClickListener(this);
        view.findViewById(R.id.rl_double_click_exit).setOnClickListener(this);

        view.findViewById(R.id.rl_check_version).setOnClickListener(this);
        // view.findViewById(R.id.rl_exit).setOnClickListener(this);
        view.findViewById(R.id.rl_feedback).setOnClickListener(this);


        // onclick event
        view.findViewById(R.id.fragment_settings_information_option).setOnClickListener(this);
        view.findViewById(R.id.fragment_settings_social_platforms_option).setOnClickListener(this);
        view.findViewById(R.id.fragment_settings_location_option).setOnClickListener(this);
        view.findViewById(R.id.fragment_settings_time_zone_option).setOnClickListener(this);
        view.findViewById(R.id.fragment_settings_domain_option).setOnClickListener(this);
        view.findViewById(R.id.fragment_settings_email_notify_option).setOnClickListener(this);

        view.findViewById(R.id.fragment_settings_payment_password_option).setOnClickListener(this);

        view.findViewById(R.id.fragment_settings_help_option).setOnClickListener(this);
        view.findViewById(R.id.fragment_settings_about_option).setOnClickListener(this);


        mCancel.setOnClickListener(this);

    }

    @Override
    public void initData() {
        if (AppContext.get(AppConfig.KEY_DOUBLE_CLICK_EXIT, true)) {
            mTbDoubleClickExit.setToggleOn();
        } else {
            mTbDoubleClickExit.setToggleOff();
        }
        calculateCacheSize();



        getLocalTimeZone();
        getLocalEmailNotify();
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

    /**
     * 计算缓存的大小
     */
    private void calculateCacheSize() {
        long fileSize = 0;
        String cacheSize = "0KB";
        File filesDir = getActivity().getFilesDir();
        File cacheDir = getActivity().getCacheDir();

        fileSize += FileUtil.getDirSize(filesDir);
        fileSize += FileUtil.getDirSize(cacheDir);
        // 2.2版本才有将应用缓存转移到sd卡的功能
        if (AppContext.isMethodsCompat(android.os.Build.VERSION_CODES.FROYO)) {
            File externalCacheDir = MethodsCompat
                    .getExternalCacheDir(getActivity());
            fileSize += FileUtil.getDirSize(externalCacheDir);
        }
        if (fileSize > 0)
            cacheSize = FileUtil.formatFileSize(fileSize);
        mTvCacheSize.setText(cacheSize);
    }

    @Override
    public void onClick(View v) {
        final int id = v.getId();
        switch (id) {


            case R.id.rl_clean_cache:
                onClickCleanCache();
                break;
            case R.id.rl_double_click_exit:
                mTbDoubleClickExit.toggle();
                break;
            case R.id.rl_feedback:
                //UIHelper.showSimpleBack(getActivity(), SimpleBackPage.FEED_BACK);
//                if (!AccountHelper.isLogin()) {
//                    LoginActivity.show(getContext());
//                    return;
//                }
//                FeedBackActivity.show(getActivity());
                break;
            case R.id.fragment_settings_about_option:
                UIHelper.showAboutOSC(getActivity());
                break;
            case R.id.rl_check_version:
                onClickUpdate();
                break;
            case R.id.rl_cancel:
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
                break;



            case R.id.fragment_settings_information_option:
                UIHelper.showInformation(getActivity());
                break;
            case R.id.fragment_settings_social_platforms_option:
                UIHelper.showSocialPlatform(getActivity());
                break;
            case R.id.fragment_settings_location_option:
                CityListActivity.show(getActivity());
                break;
            case R.id.fragment_settings_time_zone_option:
                showTimeZone();
                break;
            case R.id.fragment_settings_domain_option:
                UIHelper.showDomain(getActivity());
                break;
            case R.id.fragment_settings_payment_password_option:
                UIHelper.showPaymentPassword(getActivity());
                break;

            case R.id.fragment_settings_email_notify_option:
                showEmainNotify();
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
                mTvCacheSize.setText("0KB");
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


    public void getLocalTimeZone() {
        String timeZoneStr = BaseApplication.get(Constants.SETTING_TIME_ZONE, "北京时间(GMT - 8:00)");
        timeZoneLabel.setText(timeZoneStr);
    }

    public void getLocalEmailNotify() {
        String emailNotifyStr = BaseApplication.get(Constants.SETTING_EMAINL_NOTIFY, "从不提醒");
        emailNotifyLabel.setText(emailNotifyStr);
    }


    // 单项选择
    /**
     * 底部滚轮点击事件回调
     */
    public interface OnWheelViewClick {
        void onClick(View view, int postion);
    }



    /**
     * 邮件提醒
     */
    private void showEmainNotify() {
        final ArrayList<String> mList = new ArrayList<String>();

        String[] email_notify_options = mContext.getResources().getStringArray(R.array.email_notify_options);
        for (String option : email_notify_options) {
            mList.add(option);
        }


        AlertWheelDialog.alertBottomWheelOption(getContext(), mList, new OnWheelViewClick() {
            @Override
            public void onClick(View view, int postion) {
                String emailNotifyStr = mList.get(postion);
                BaseApplication.set(Constants.SETTING_EMAINL_NOTIFY, emailNotifyStr);
                emailNotifyLabel.setText(emailNotifyStr);
            }
        });
    }


    /**
     * 显示时区
     */
    private void showTimeZone() {
        final ArrayList<String> mList = new ArrayList<String>();


        String[] time_zone_options = mContext.getResources().getStringArray(R.array.time_zone_options);
        for (String option : time_zone_options) {
            mList.add(option);
        }

        AlertWheelDialog.alertBottomWheelOption(getContext(), mList, new OnWheelViewClick() {
            @Override
            public void onClick(View view, int postion) {
                String timeZoneStr = mList.get(postion);
                BaseApplication.set(Constants.SETTING_TIME_ZONE, timeZoneStr);
                timeZoneLabel.setText(timeZoneStr);
            }
        });
    }

}
