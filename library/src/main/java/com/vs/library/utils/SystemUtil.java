package com.vs.library.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.LoaderManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import java.io.File;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by chends on 2017/7/28.
 */

public class SystemUtil {

    public static void openApplicationSettings(Activity activity) {
        try {
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:" + activity.getPackageName()));
            intent.addCategory(Intent.CATEGORY_DEFAULT);

            // Android L 之后Activity的启动模式发生了一些变化
            // 如果用了下面的 Intent.FLAG_ACTIVITY_NEW_TASK ，并且是 startActivityForResult
            // 那么会在打开新的activity的时候就会立即回调 onActivityResult
            // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            activity.startActivity(intent);

        } catch (Throwable e) {}
    }

    /**
     * 屏幕高度
     * @param context
     * @return
     */
    public static int getScreenHeight(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(dm);
        return dm.heightPixels;
    }

    /**
     * 屏幕宽度
     * @param context
     * @return
     */
    public static int getScreenWidth(Context  context){
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }

    /**
     * 屏幕密度
     * @param context
     * @return
     */
    public static float getScreenDensity(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(dm);
        return dm.density;
    }

    public static int getScreenMin(Context context) {
        DisplayMetrics dm = context.getApplicationContext().getResources().getDisplayMetrics();
        return (dm.widthPixels > dm.heightPixels) ? dm.heightPixels : dm.widthPixels;
    }

    /**
     * dp 2 px
     * @param dp
     * @param context
     * @return
     */
    public static int convertDpToPixel(float dp, Context context) {
        final float scale = getScreenDensity(context);
        return (int) (dp * scale + 0.5f);
    }

    public static String name() {
        return Build.MODEL;
    }

    public static String deviceUUID(Context context) {
        String androidId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        if(TextUtils.isEmpty(androidId)) {
            androidId = Build.SERIAL;
        }
        return androidId;
    }

    /**
     * 打开系统相册
     * @param activity
     * @param requestCode
     */
    public static void openSystemGalleryForResult(Activity activity, int requestCode) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");

        activity.startActivityForResult(intent, requestCode);
    }

    public static void openImageVideoPickerForResult(Activity activity, int requestCode) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("video/*;image/*");
        activity.startActivityForResult(intent, requestCode);
    }

    public static void openFilePickerForResult(Activity activity, int requestCode) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        try {
            activity.startActivityForResult(intent, requestCode);
        } catch (android.content.ActivityNotFoundException ex) {}

    }

    /**
     * 获取当前进程名
     * @param context
     * @return 进程名
     */
    public static final String getProcessName(Context context) {
        String processName = null;

        // ActivityManager
        ActivityManager am = ((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE));

        while (true) {
            for (ActivityManager.RunningAppProcessInfo info : am.getRunningAppProcesses()) {
                if (info.pid == android.os.Process.myPid()) {
                    processName = info.processName;
                    break;
                }
            }

            // go home
            if (!TextUtils.isEmpty(processName)) {
                return processName;
            }

            // take a rest and again
            try {
                Thread.sleep(100L);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static boolean isLowLocationAvailable(Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    public static boolean isHighLocationAvailable(Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    /**
     * 处理拍照结果并裁剪图片
     * @param data
     * @param saveFile
     * @param returnData
     * @return
     */
    public static Intent handleAnGetCameraCaptureAndCropIntent(Intent data, String saveFile, boolean returnData) {

        Intent intent = null;

        if(data == null || TextUtils.isEmpty(saveFile)) {
            return null;
        }

        Uri uri = data.getData();

        if(uri != null) {
            intent = getCropImageIntent(uri, Uri.fromFile(new File(saveFile)), returnData);
        }else {

            Bitmap bitmap = data.getParcelableExtra("data");

            if(bitmap != null) {
                if(BitmapUtil.saveBitmap(bitmap, saveFile, true)) {
                    intent = getCropImageIntent(Uri.fromFile(new File(saveFile)), Uri.fromFile(new File(saveFile)), returnData);
                }else {
                    try {
                        bitmap.recycle();
                    }catch(Exception e){}
                }
            }
        }

        return intent;
    }

    /**
     * 启动系统裁剪图片intent
     * @param returnData
     * @return
     */
    public static Intent getCropImageIntent(Uri source, Uri save, boolean returnData){

        Intent intent = new Intent("com.android.camera.action.CROP");

        intent.setDataAndType(source, "image/*");

        intent.putExtra("crop", "true");

        intent.putExtra("aspectX", 1);

        intent.putExtra("aspectY", 1);

//        intent.putExtra("outputX", outputX);
//
//        intent.putExtra("outputY", outputY);

        intent.putExtra("scale", true);

        intent.putExtra(MediaStore.EXTRA_OUTPUT, save);

        intent.putExtra("return-data", returnData);

        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());

        intent.putExtra("noFaceDetection", true); // no face detection

        return intent;
    }

    /**
     * 设置软键盘状态
     * @param context
     */
    public static void setSoftInputVisibility(Activity context, boolean show) {

        View view = context.getCurrentFocus();

        if(view != null) {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            if(!show) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }else {
                imm.showSoftInput(view, 0);
            }
        }
    }

    public static void openFileAs(Context context, File file, String extend) {
        if(file != null && file.exists()) {
            Intent intent = new Intent();
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setAction(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.fromFile(file), FileUtil.getMIMEType(extend));
            //跳转
            context.startActivity(intent);
        }
    }

    /**
     * 打开拨号键盘
     * @param context
     * @param phoneNum
     */
    public static void openDialPan(Context context, String phoneNum) {
        if(!TextUtils.isEmpty(phoneNum)) {
            Intent intent = new Intent(Intent.ACTION_DIAL,Uri.parse("tel:"+phoneNum));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }
    }

    /**
     * 读取手机通讯录电话号码
     * @param context
     * @return null or map, 单项以phone:name 形式映射
     */
    public static Map<String, String> readPhoneNumFromContacts(Context context) {
        ContentResolver resolver = context.getContentResolver();
        Cursor phoneCursor = resolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME}, null, null, null);

        if (phoneCursor != null) {
            Map<String,String> phones = new HashMap<>();
            while (phoneCursor.moveToNext()) {

                //得到手机号码
                String phoneNumber = phoneCursor.getString(0);
                //当手机号码为空的或者为空字段 跳过当前循环
                if (TextUtils.isEmpty(phoneNumber))
                    continue;

                phones.put(phoneNumber, phoneCursor.getString(1));
            }

            phoneCursor.close();
            return phones;
        }
        return null;
    }

    /**
     * 读取Sim卡通讯录手机号码
     * @param context
     * @return null or list
     */
    public static Map<String,String> readPhoneNumFromSim(Context context) {
        ContentResolver resolver = context.getContentResolver();
        Uri uri = Uri.parse("content://icc/adn");
        Cursor phoneCursor = resolver.query(uri, new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME}, null, null,null);

        if (phoneCursor != null) {
            Map<String,String> phones = new HashMap<>();
            while (phoneCursor.moveToNext()) {
                String phoneNumber = phoneCursor.getString(0);
                if (TextUtils.isEmpty(phoneNumber))
                    continue;
                phones.put(phoneNumber, phoneCursor.getString(1));
            }

            phoneCursor.close();
            return phones;
        }

        return null;
    }

    /**
     * 通过类对象，运行指定方法
     * @param obj 类对象
     * @param methodName 方法名
     * @param params 参数值
     * @return 失败返回null
     */
    public static Object invokeMethod(Object obj, String methodName, Object[] params) {
        if (obj == null || TextUtils.isEmpty(methodName)) {
            return null;
        }

        Class<?> clazz = obj.getClass();
        try {
            Class<?>[] paramTypes = null;
            if (params != null) {
                paramTypes = new Class[params.length];
                for (int i = 0; i < params.length; ++i) {
                    paramTypes[i] = params[i].getClass();
                }
            }
            Method method = clazz.getMethod(methodName, paramTypes);
            method.setAccessible(true);
            return method.invoke(obj, params);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
