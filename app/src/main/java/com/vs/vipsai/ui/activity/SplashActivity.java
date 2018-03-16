package com.vs.vipsai.ui.activity;

import android.os.Bundle;
import android.util.Log;

import com.vs.mvp.cache.SharedPref;
import com.vs.mvp.mvp.XActivity;
import com.vs.vipsai.R;
import com.vs.vipsai.constants.Constants;

/**
 * Author: cynid
 * Created on 3/12/18 11:00 AM
 * Description:
 */

public class SplashActivity extends XActivity {
    
    public static final String TAG = SplashActivity.class.getName();
    
    @Override
    public void initData(Bundle savedInstanceState) {
        if (SharedPref.getInstance(context).getBoolean(Constants.IS_FIRST, false)) {
            toMain();
        } else {
            toGuidePage();
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    public Object newP() {
        return null;
    }

    public void toMain() {
        Log.d(TAG, "toMain: ");
        MainActivity.launch(this);
        finish();
    }

    public void toGuidePage() {
        Log.d(TAG, "toGuidePage: ");
        GuidePageActivity.launch(this);
        finish();
    }

}
