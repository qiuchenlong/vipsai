package com.vs.library.utils;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;
import android.widget.TimePicker;

/**
 * Created by chends on 2017/7/26.
 */

public class CompatibilityUtil {

    public static void setColor(View view, int color) {

    }

    public static int getColor(Context context, int resId) {
        if (Build.VERSION.SDK_INT >= 23) {
            return context.getColor(resId);
        }else {
            return context.getResources().getColor(resId);
        }
    }

    public static Drawable getDrawable(Context context, int resId) {
        if (Build.VERSION.SDK_INT >= 23) {
            return context.getDrawable(resId);
        }else {
            return context.getResources().getDrawable(resId);
        }
    }

    public static ColorStateList getColorStateList(Context context, int resId) {
        if (Build.VERSION.SDK_INT >= 23) {
            return context.getColorStateList(resId);
        }else {
            return context.getResources().getColorStateList(resId);
        }
    }

    public static int getHour(TimePicker picker) {
        if(Build.VERSION.SDK_INT >= 23) {
            return picker.getHour();
        }else {
            return picker.getCurrentHour();
        }
    }

    public static int getMinute(TimePicker picker) {
        if(Build.VERSION.SDK_INT >= 23) {
            return picker.getMinute();
        }else {
            return picker.getCurrentMinute();
        }
    }
}
