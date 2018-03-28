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
 * 系统设置界面
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

            case R.id.rl_payment_password:
                UIHelper.showPaymentPassword(getActivity());
//                PayForPasswordActivity.show(getActivity());
                break;

            case R.id.fragment_settings_email_notify:
                final ArrayList<String> mList = new ArrayList<String>();
                // 单项选择
                mList.add("从不提醒");
                mList.add("每日一次提醒");
                mList.add("全部提醒");


                alertBottomWheelOption(getContext(), mList, new OnWheelViewClick() {
                    @Override
                    public void onClick(View view, int postion) {
                        SimplexToast.show(getContext(), "slect on: " + mList.get(postion));
                    }
                });
                break;

            case R.id.fragment_settings_time_zone:
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

                alertBottomWheelOption(getContext(), mList2, new OnWheelViewClick() {
                    @Override
                    public void onClick(View view, int postion) {
                        SimplexToast.show(getContext(), "slect on: " + mList2.get(postion));
                    }
                });
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





    // 单项选择
    /**
     * 底部滚轮点击事件回调
     */
    public interface OnWheelViewClick {
        void onClick(View view, int postion);
    }

    /**
     * 弹出底部滚轮选择
     *
     * @param context
     * @param list
     * @param click
     */
    public static void alertBottomWheelOption(Context context, ArrayList<?> list, final OnWheelViewClick click) {

        final PopupWindow popupWindow = new PopupWindow();

        View view = LayoutInflater.from(context).inflate(R.layout.layout_bottom_wheel_option, null);
        TextView tv_confirm = (TextView) view.findViewById(R.id.btnSubmit);
        final WheelView wv_option = (WheelView) view.findViewById(R.id.wv_option);
        wv_option.setAdapter(new ArrayWheelAdapter(list));
        wv_option.setCyclic(false);
        wv_option.setTextSize(16);
        wv_option.setCurrentItem(0);
        tv_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
                click.onClick(view, wv_option.getCurrentItem());
            }
        });

        view.findViewById(R.id.btnCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: 2016/8/11 0011 取消
                popupWindow.dismiss();
            }
        });
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int top = view.findViewById(R.id.ll_container).getTop();
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    int y = (int) motionEvent.getY();
                    if (y < top) {
                        popupWindow.dismiss();
                    }
                }
                return true;
            }
        });
        popupWindow.setContentView(view);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.showAtLocation(((ViewGroup) ((Activity) context).findViewById(android.R.id.content)).getChildAt(0), Gravity.CENTER, 0, 0);
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
                SimplexToast.show(getContext(), "public_menu_shared");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
