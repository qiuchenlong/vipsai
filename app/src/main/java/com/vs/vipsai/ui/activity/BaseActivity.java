package com.vs.vipsai.ui.activity;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;

import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.vs.mvp.mvp.IPresent;
import com.vs.mvp.mvp.XActivity;
import com.vs.vipsai.R;
import com.vs.vipsai.widget.Titlebar;
import com.vs.vipsai.widget.loading.LoadingView;

/**
 * Author: cynid
 * Created on 3/12/18 10:26 AM
 * Description:
 */

public abstract class BaseActivity<P extends IPresent> extends XActivity<P> {

    public Titlebar titlebar;
    private LoadingView loadingView;

    @Override
    public void bindEvent() {
//        titlebar = (Titlebar) findViewById(R.id.titlebar);
        initTitle();
    }

    public void showLoading(String text) {
        if (loadingView == null) {
            loadingView = new LoadingView();
        }
        if (text != null)
            loadingView.setText(text);
        loadingView.show(getSupportFragmentManager(), "");
    }

    public void dismissLoading() {
        if (loadingView != null) {
            loadingView.dismiss();
        }
    }

    private void initTitle() {
        if (titlebar != null) {
            titlebar.setOnButtonClickListener(new Titlebar.OnButtonClickListener() {
                @Override
                public void onLeftClick() {
                    finish();
                }
            });
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            // enable status bar tint
            tintManager.setStatusBarTintEnabled(true);
            // enable navigation bar tint
            tintManager.setNavigationBarTintEnabled(true);

            // set a custom tint color for all system bars
            tintManager.setTintColor(ContextCompat.getColor(context, R.color.transparent));
        }
    }

    public void setTitlebar(String title) {
        if (titlebar != null)
            titlebar.setTitleText(title);
    }

}
