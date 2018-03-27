package com.vs.vipsai.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.vs.vipsai.R;
import com.vs.vipsai.base.BaseFragment;
import com.vs.vipsai.base.activities.BackActivity;
import com.vs.vipsai.bean.SimpleBackPage;
import com.vs.vipsai.emoji.OnSendClickListener;
import com.vs.vipsai.base.activities.BaseActivity;
import com.vs.vipsai.settings.SettingsFragment;
import com.vs.vipsai.util.SimplexToast;

import java.lang.ref.WeakReference;

/**
 * Author: cynid
 * Created on 3/17/18 11:02 AM
 * Description:
 */

public class SimpleBackActivity extends BackActivity implements OnSendClickListener {

    public final static String BUNDLE_KEY_PAGE = "BUNDLE_KEY_PAGE";
    public final static String BUNDLE_KEY_ARGS = "BUNDLE_KEY_ARGS";
    private static final String TAG = "FLAG_TAG";
    protected WeakReference<Fragment> mFragment;
    protected int mPageValue = -1;

    @Override
    public void startActivity(Intent intent) {
        View view = getCurrentFocus();
        if (view != null) {
            view.clearFocus();
            InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            manager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
        super.startActivity(intent);
    }


    protected void initFromIntent(int pageValue, Intent data) {
        if (data == null) {
            throw new RuntimeException("you must provide a page info to display");
        }
        SimpleBackPage page = SimpleBackPage.getPageByValue(pageValue);
        if (page == null) {
            throw new IllegalArgumentException("can not find page by value:" + pageValue);
        }

        setTitle(page.getTitle());

        try {
            Fragment fragment = (Fragment) page.getClz().newInstance();

            Bundle args = data.getBundleExtra(BUNDLE_KEY_ARGS);
            if (args != null) {
                fragment.setArguments(args);
            }

            addFragment(R.id.container, fragment);

        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalArgumentException(
                    "generate fragment error. by value:" + pageValue);
        }
    }

    @Override
    public void onBackPressed() {
        if (mFragment != null && mFragment.get() != null
                && mFragment.get() instanceof BaseFragment) {
            BaseFragment bf = (BaseFragment) mFragment.get();
            if (!bf.onBackPressed()) {
                super.onBackPressed();
            }
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.ACTION_DOWN
                && mFragment.get() instanceof BaseFragment) {
            ((BaseFragment) mFragment.get()).onBackPressed();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onActivityResult(int arg0, int arg1, Intent arg2) {
        super.onActivityResult(arg0, arg1, arg2);
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        setSwipeBackEnable(true);

        Intent intent = getIntent();
        if (mPageValue == -1) {
            mPageValue = intent.getIntExtra(BUNDLE_KEY_PAGE, 0);
        }
        initFromIntent(mPageValue, getIntent());
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_simple_fragment;
    }

    @Override
    public void initData() {
//        if (menu == null) return;
//        // 向Activity菜单添加5个菜单项，菜单项的id从10开始
//        for (int i = 10; i <15; i++)
//        {
//            int id = menuItemId++;
//            menu.add(1, id, id, "菜单" + i);
//        }
    }

    @Override
    public void onClickSendButton(Editable str) {

    }

    @Override
    public void onClickFlagButton() {
    }




//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
////        Log.d("SimpleBackActivity", "" + this.mFragment);
////        if (this.mFragment.getClass().equals(SettingsFragment.class)) {
////            SimplexToast.show(SimpleBackActivity.this, "hello...");
////        }
//        getMenuInflater().inflate(R.menu.browser_menu, menu);
//        return true;
////        return super.onCreateOptionsMenu(menu);
//    }
//
//    @Override
//    public boolean onPrepareOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.browser_menu, menu);
//        return true;
////        return super.onPrepareOptionsMenu(menu);
//    }
}
