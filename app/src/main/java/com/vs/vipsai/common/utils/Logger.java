package com.vs.vipsai.common.utils;

import android.util.Log;

/**
 * Author: cynid
 * Created on 3/12/18 4:36 PM
 * Description:
 */

public class Logger {

    public static boolean DEBUG = false;

    public static void d(String tag, String msg) {
        if (DEBUG)
            Log.d(tag, msg);
    }

    public static void e(String tag, String msg) {
        if (DEBUG)
            Log.e(tag, msg);
    }

}
