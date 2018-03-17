package com.vs.vipsai.update;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;

import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.TextHttpResponseHandler;
import com.vs.vipsai.AppOperator;
import com.vs.vipsai.bean.ResultBean;
import com.vs.vipsai.bean.Version;
import com.vs.vipsai.util.DialogHelper;

import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Author: cynid
 * Created on 3/17/18 10:39 AM
 * Description:
 */

public class CheckUpdateManager {

    private ProgressDialog mWaitDialog;
    private Context mContext;
    private boolean mIsShowDialog;
    private RequestPermissions mCaller;

    public CheckUpdateManager(Context context, boolean showWaitingDialog) {
        this.mContext = context;
        mIsShowDialog = showWaitingDialog;
        if (mIsShowDialog) {
            mWaitDialog = DialogHelper.getProgressDialog(mContext);
            mWaitDialog.setMessage("正在检查中...");
            mWaitDialog.setCancelable(true);
            mWaitDialog.setCanceledOnTouchOutside(true);
        }
    }


    public void checkUpdate(final boolean isHasShow) {
        if (mIsShowDialog) {
            mWaitDialog.show();
        }
//        OSChinaApi.checkUpdate(new TextHttpResponseHandler() {
//            @Override
//            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
//                if (mIsShowDialog) {
//                    DialogHelper.getMessageDialog(mContext, "网络异常，无法获取新版本信息").show();
//                }
//                if (mWaitDialog != null) {
//                    mWaitDialog.dismiss();
//                }
//            }
//
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, String responseString) {
//                try {
//                    ResultBean<List<Version>> bean = AppOperator.createGson()
//                            .fromJson(responseString, new TypeToken<ResultBean<List<Version>>>() {
//                            }.getType());
//                    if (bean != null && bean.isSuccess()) {
//                        List<Version> versions = bean.getResult();
//                        if (versions.size() > 0) {
//                            final Version version = versions.get(0);
//                            int curVersionCode = 100; //BuildConfig.VERSION_CODE;
//                            int code = Integer.parseInt(version.getCode());
//                            if (curVersionCode < code) {
//                                //是否弹出更新
//                                if (OSCSharedPreference.getInstance().isShowUpdate() || isHasShow) {
//                                    UpdateActivity.show((Activity) mContext, version);
//                                }
//                            } else {
//                                if (mIsShowDialog) {
//                                    DialogHelper.getMessageDialog(mContext, "已经是新版本了").show();
//                                }
//                            }
//                        }
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onFinish() {
//                super.onFinish();
//                if (mWaitDialog != null) {
//                    mWaitDialog.dismiss();
//                }
//            }
//        });
    }

    public void setCaller(RequestPermissions caller) {
        this.mCaller = caller;
    }

    public interface RequestPermissions {
        void call(Version version);
    }

}
