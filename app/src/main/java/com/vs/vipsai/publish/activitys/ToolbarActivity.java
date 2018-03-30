package com.vs.vipsai.publish.activitys;

import android.support.annotation.Nullable;
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

/**
 * * Author: chends
 * Created on 3/28/18 5:33 PM
 * Description:
 *
 *  toolbar 基类
 */
public class ToolbarActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    protected Toolbar mToolbar;

    @BindView(R.id.container)
    protected FrameLayout container;

    @BindView(R.id.tv_center_title)
    protected TextView mCenterTitle;

    @BindView(R.id.tv_menu_btn)
    protected TextView mMenuBtn;

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(R.layout.activity_toolbar);
        if(layoutResID != 0) {
            getLayoutInflater().inflate(layoutResID, (ViewGroup) findViewById(R.id.container), true);
        }else {
            bindContent((ViewGroup)findViewById(R.id.container));
        }

    }

    @Override
    protected void initWidget() {
        super.initWidget();
        setSupportActionBar(mToolbar);
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
        mToolbar.setTitle("");
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
        mToolbar.setTitle("");
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
