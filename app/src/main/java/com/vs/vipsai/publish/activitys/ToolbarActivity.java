package com.vs.vipsai.publish.activitys;

import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.vs.vipsai.R;
import com.vs.vipsai.base.activities.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

import android.support.v4.app.Fragment;

/**
 * * Author: chends
 * Created on 3/28/18 5:33 PM
 * Description:
 *
 *  toolbar 基类
 */
public class ToolbarActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    protected Toolbar toolbar;

    @BindView(R.id.toolbar_container)
    protected FrameLayout container;

    @BindView(R.id.tv_center_title)
    protected TextView mCenterTitle;

    @BindView(R.id.tv_menu_btn)
    protected TextView mMenuBtn;

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(R.layout.activity_toolbar);
        ButterKnife.bind(this);
        if(layoutResID != 0) {
            getLayoutInflater().inflate(layoutResID, (ViewGroup) findViewById(R.id.toolbar_container), true);
        }else {
            bindContent((ViewGroup)findViewById(R.id.toolbar_container));
        }

    }

    @Override
    protected void initWidget() {
        super.initWidget();
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(false);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        setTitle(getTitle());
    }

    protected void bindContent(ViewGroup parent){}

    @Override
    public void setTitle(int titleId) {
        mCenterTitle.setText(titleId);
        toolbar.setTitle("");
    }

    protected void setMenuButton(int textRes) {
        mMenuBtn.setVisibility(View.VISIBLE);
        mMenuBtn.setText(textRes);
        mMenuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onMenuBtnClick(v);
            }
        });
    }

    protected void onMenuBtnClick(View view) {}

    @Override
    public void setTitle(CharSequence title) {
        mCenterTitle.setText(title);
        toolbar.setTitle("");
    }

    /**
     * 显示toolbar 左边标题， 传0时不显示
     * @param str
     */
    protected void showToolbarTitle(int str) {
        if(str == 0) {
            toolbar.setTitle("");
        }else {
            toolbar.setTitle(str);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    @Override
    protected int getContentView() {
        return 0;
    }
}
