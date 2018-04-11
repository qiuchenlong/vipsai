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
        DialogHelper.getConfirmDialog(getActivity(), "温馨提示", "需要开启开源中国对您手机的存储权限才能下载安装，是否现在开启", "去开启", "取消", new DialogInterface.OnClickListener() {
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
        // 单项选择
        mList.add("从不提醒");
        mList.add("每日一次提醒");
        mList.add("全部提醒");


        AlertWheelDialog.alertBottomWheelOption(getContext(), mList, new OnWheelViewClick() {
            @Override
            public void onClick(View view, int postion) {
//                SimplexToast.show(getContext(), "slect on: " + mList.get(postion));
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
        final ArrayList<String> mList2 = new ArrayList<String>();

        String[] array = new String[]{
                "(UTC-12:00)国际日期变更线西",
                "(UTC-11:00)萨摩亚群岛",
                "(UTC-11:00)协调世界时-11",
                "(UTC-10:00)夏威夷",
                "(UTC-09:00)阿拉斯加 ",
                "(UTC-08:00)太平洋时间 (美国和加拿大)",
                "(UTC-08:00)下加利福尼亚州 ",
                "(UTC-07:00)奇瓦瓦、拉巴斯、马萨特兰",
                "(UTC-07:00)山地时间 (美国和加拿大)",
                "(UTC-07:00)亚利桑那 ",
                "(UTC-06:00)瓜达拉哈拉、墨西哥城、蒙特雷",
                "(UTC-06:00)萨斯喀彻温 ",
                "(UTC-06:00)中部时间 (美国和加拿大)",
                "(UTC-06:00)中美洲 ",
                "(UTC-05:00)波哥大、利马、基多",
                "(UTC-05:00)东部时间 (美国和加拿大)",
                "(UTC-05:00)印第安那州(东部)",
                "(UTC-04:30)加拉加斯 ",
                "(UTC-04:00)大西洋时间(加拿大)",
                "(UTC-04:00)库亚巴 ",
                "(UTC-04:00)乔治敦、拉巴斯、马瑙斯、圣胡安",
                "(UTC-04:00)圣地亚哥",
                "(UTC-04:00)亚松森",
                "(UTC-03:30)纽芬兰",
                "(UTC-03:00)巴西利亚",
                "(UTC-03:00)布宜诺斯艾利斯",
                "(UTC-03:00)格陵兰",
                "(UTC-03:00)卡宴、福塔雷萨",
                "(UTC-03:00)蒙得维的亚",
                "(UTC-02:00)协调世界时-02",
                "(UTC-02:00)中大西洋",
                "(UTC-01:00)佛得角群岛",
                "(UTC-01:00)亚速尔群岛 ",
                "(UTC)都柏林、爱丁堡、里斯本、伦敦",
                "(UTC)卡萨布兰卡 ",
                "(UTC)蒙罗维亚、雷克雅未克",
                "(UTC)协调世界时 ",
                "(UTC+01:00)阿姆斯特丹、柏林、伯尔尼、罗马、斯德哥尔摩、维也纳",
                "(UTC+01:00)贝尔格莱德、布拉迪斯拉发、布达佩斯、卢布尔雅那、布拉格",
                "(UTC+01:00)布鲁塞尔、哥本哈根、马德里、巴黎",
                "(UTC+01:00)萨拉热窝、斯科普里、华沙、萨格勒布",
                "(UTC+01:00)温得和克",
                "(UTC+01:00)中非西部",
                "(UTC+02:00)安曼",
                "(UTC+02:00)贝鲁特",
                "(UTC+02:00)大马士革 ",
                "(UTC+02:00)哈拉雷、比勒陀利亚 ",
                "(UTC+02:00)赫尔辛基、基辅、里加、索非亚、塔林、维尔纽斯",
                "(UTC+02:00)开罗",
                "(UTC+02:00)明斯克 ",
                "(UTC+02:00)雅典、布加勒斯特、伊斯坦布尔",
                "(UTC+02:00)耶路撒冷",
                "(UTC+03:00)巴格达",
                "(UTC+03:00)科威特、利雅得 ",
                "(UTC+03:00)莫斯科、圣彼得堡、伏尔加格勒",
                "(UTC+03:00)内罗毕",
                "(UTC+03:30)德黑兰 ",
                "(UTC+04:00)阿布扎比、马斯喀特",
                "(UTC+04:00)埃里温",
                "(UTC+04:00)巴库",
                "(UTC+04:00)第比利斯",
                "(UTC+04:00)路易港",
                "(UTC+04:30)喀布尔",
                "(UTC+05:00)塔什干",
                "(UTC+05:00)叶卡捷琳堡",
                "(UTC+05:00)伊斯兰堡、卡拉奇 ",
                "(UTC+05:30)钦奈、加尔各答、孟买、新德里",
                "(UTC+05:30)斯里加亚渥登普拉",
                "(UTC+05:45)加德满都",
                "(UTC+06:00)阿斯塔纳",
                "(UTC+06:00)达卡",
                "(UTC+06:00)新西伯利亚",
                "(UTC+06:30)仰光 ",
                "(UTC+07:00)克拉斯诺亚尔斯克",
                "(UTC+07:00)曼谷、河内、雅加达 ",
                "(UTC+08:00)北京、重庆、香港特别行政区、乌鲁木齐",
                "(UTC+08:00)吉隆坡、新加坡",
                "(UTC+08:00)珀斯",
                "(UTC+08:00)台北",
                "(UTC+08:00)乌兰巴托",
                "(UTC+08:00)伊尔库茨克",
                "(UTC+09:00)大阪、札幌、东京",
                "(UTC+09:00)首尔",
                "(UTC+09:00)雅库茨克",
                "(UTC+09:30)阿德莱德",
                "(UTC+09:30)达尔文",
                "(UTC+10:00)布里斯班",
                "(UTC+10:00)符拉迪沃斯托克",
                "(UTC+10:00)关岛、莫尔兹比港",
                "(UTC+10:00)霍巴特 ",
                "(UTC+10:00)堪培拉、墨尔本、悉尼",
                "(UTC+11:00)马加丹 ",
                "(UTC+11:00)所罗门群岛、新喀里多尼亚",
                "(UTC+12:00)奥克兰、惠灵顿",
                "(UTC+12:00)斐济 ",
                "(UTC+12:00)协调世界时+12",
                "(UTC+13:00)努库阿洛法"
        };

        // 单项选择
        for (String s : array) {
            mList2.add(s);
        }

        AlertWheelDialog.alertBottomWheelOption(getContext(), mList2, new OnWheelViewClick() {
            @Override
            public void onClick(View view, int postion) {
//                SimplexToast.show(getContext(), "slect on: " + mList2.get(postion));
                String timeZoneStr = mList2.get(postion);
                BaseApplication.set(Constants.SETTING_TIME_ZONE, timeZoneStr);
                timeZoneLabel.setText(timeZoneStr);
            }
        });
    }

}
