package com.vs.vipsai.account;

import android.annotation.SuppressLint;
import android.app.Application;
import android.text.TextUtils;

import com.vs.vipsai.bean.User;
import com.vs.vipsai.common.helper.SharedPreferencesHelper;
import com.vs.vipsai.util.TLog;

import cz.msebera.android.httpclient.Header;

/**
 * Author: cynid
 * Created on 3/28/18 5:25 PM
 * Description:
 *
 * 账户辅助类，
 * 用于更新用户信息和保存当前账户等操作
 */

public final class AccountHelper {

    private static final String TAG = AccountHelper.class.getName();
    private User user;
    private Application application;

    @SuppressLint("StaticFieldLeak")
    private static AccountHelper instances;

    private AccountHelper(Application application) {
        this.application = application;
    }

    public static void init(Application application) {
        if (instances == null) {
            instances = new AccountHelper(application);
        } else {
            // reload from source
            instances.user = SharedPreferencesHelper.loadFormSource(instances.application, User.class);
            TLog.d(TAG, "init reload:" + instances.user);
        }
    }

    public static boolean isLogin() {
        return getUserId() > 0 && !TextUtils.isEmpty(getCookie());
    }

    public static String getCookie() {
        String cookie = getUser().getCookie();
        return cookie == null ? "" : cookie;
    }

    public static long getUserId() {
        return getUser().getId();
    }

    public synchronized static User getUser() {
        if (instances == null) {
            TLog.error("AccountHelper instances is null, you need call init() method.");
            return new User();
        }
        if (instances.user == null) {
            instances.user = SharedPreferencesHelper.loadFormSource(instances.application, User.class);
        }
        if (instances.user == null) {
            instances.user = new User();
        }
        return instances.user;
    }



    public static boolean login(final User user, Header[] headers) {
        boolean saveOk = false;

        return saveOk;
    }

}
