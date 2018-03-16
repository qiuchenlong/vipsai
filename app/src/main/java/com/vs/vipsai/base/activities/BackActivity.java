package com.vs.vipsai.base.activities;

import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;

import com.vs.vipsai.R;

/**
 * Author: cynid
 * Created on 3/15/18 5:33 PM
 * Description:
 */

public abstract class BackActivity extends BaseActivity {

    protected Toolbar mToolBar;

    @Override
    protected void initWindow() {
        super.initWindow();
        mToolBar = (Toolbar) findViewById(R.id.toolbar);
        if (mToolBar != null) {
            setSupportActionBar(mToolBar);
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.setDisplayHomeAsUpEnabled(true);
                actionBar.setHomeButtonEnabled(false);
            }
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

}
