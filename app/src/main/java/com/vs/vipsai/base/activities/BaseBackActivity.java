package com.vs.vipsai.base.activities;

import android.app.ProgressDialog;
import android.support.v7.app.ActionBar;

import com.vs.vipsai.util.DialogHelper;

/**
 * Author: cynid
 * Created on 3/15/18 5:40 PM
 * Description:
 */

public abstract class BaseBackActivity extends BaseActivity {

    private ProgressDialog mWaitDialog;

    @Override
    protected void initWindow() {
        super.initWindow();
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(false);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    protected void showLoadingDialog(String message) {
        if (mWaitDialog == null) {
            mWaitDialog = DialogHelper.getProgressDialog(this, true);
        }
        mWaitDialog.setMessage(message);
        mWaitDialog.show();
    }

    protected void dismissLoadingDialog() {
        if (mWaitDialog == null) return;
        mWaitDialog.dismiss();
    }
    
}
