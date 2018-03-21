package com.vs.vipsai.notify.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.ActionMenuView;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.vs.vipsai.R;
import com.vs.vipsai.base.activities.BackActivity;
import com.vs.vipsai.bean.News;
import com.vs.vipsai.bean.SubTab;
import com.vs.vipsai.notify.NotifytSubFragment;
import com.vs.vipsai.util.SimplexToast;
import com.vs.vipsai.util.TDevice;
import com.vs.vipsai.widget.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author: cynid
 * Created on 3/20/18 10:10 AM
 * Description:
 */

public class NotifyActicity extends BackActivity implements ViewPager.OnPageChangeListener {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tabs)
    TabLayout mLayoutTab;
    @BindView(R.id.actionMV)
    ActionMenuView mAcitionMenuView;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;

    private Fragment mCurFragment;
    private FragmentPagerAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ButterKnife.bind(this);

        initView();
    }

    private void initView() {

        getSupportActionBar().setDisplayShowTitleEnabled(false);

        // index 0 : tablayout                 center
        // index 1 : actionmenuview            right
        // index 2 : appcompatimagebutton      left

        AppCompatImageButton aib = (AppCompatImageButton) mToolbar.getChildAt(2);
        int mWith = aib.getDrawable().getMinimumWidth();


        Log.d("NotifyActivity", " " + TDevice.getScreenWidth());
        Log.d("NotifyActivity", " " + mToolbar.getWidth());
        Log.d("NotifyActivity", " " + aib.getDrawable().getMinimumWidth());
        Log.d("NotifyActivity", " " + mToolbar.getMeasuredWidth());
        Log.d("NotifyActivity", " " + mToolbar.getContentInsetEndWithActions());
//        ViewGroup.LayoutParams lp = mLayoutTab.getLayoutParams();
//        lp.ma
//        mLayoutTab.setLayoutParams();
//        mLayoutTab.setLayoutParams();

        Toolbar.LayoutParams lp = new Toolbar.LayoutParams(Toolbar.LayoutParams.WRAP_CONTENT, Toolbar.LayoutParams.WRAP_CONTENT);
        lp.setMargins((int)TDevice.px2dp(TDevice.getScreenWidth()/2.0f + mWith*2 - lp.width/2), 0, 0, 0);
        mLayoutTab.setLayoutParams(lp);




        mLayoutTab.setTabGravity(TabLayout.GRAVITY_CENTER);

//        mLayoutTab.setPadding((int)TDevice.dp2px(86), 0, 0, 0 );

        final List<SubTab> tabs = new ArrayList<>();

        SubTab st = new SubTab();
        st.setName("消息");
        st.setHref("https://www.oschina.net/action/apiv2/sub_list?token=d6112fa662bc4bf21084670a857fbd20");
        st.setSubtype(10);
        st.setToken("d6112fa662bc4bf21084670a857fbd20"); // cache key
        st.setType(News.TYPE_MESSAGE); //3

        tabs.add(st);

        SubTab st1 = new SubTab();
        st1.setName("私信");
        st1.setHref("https://www.oschina.net/action/apiv2/sub_list?token=df985be3c5d5449f8dfb47e06e098ef9");
        st1.setSubtype(4);
        st1.setToken("df985be3c5d5449f8dfb47e06e098ef9"); // cache key
        st1.setType(News.TYPE_PRIVATE_MESSAGE);

        tabs.add(st1);
        for (SubTab tab : tabs) {
            mLayoutTab.addTab(mLayoutTab.newTab().setText(tab.getName()));
        }


        // set vertical divider
        LinearLayout mLinearLayout = (LinearLayout) mLayoutTab.getChildAt(0);
        // 在所有子控件的中间显示分割线（还可能只显示顶部、尾部和不显示分割线）
        mLinearLayout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        // 设置分割线的距离本身（LinearLayout）的内间距
        mLinearLayout.setDividerPadding(20);
        // 设置分割线的样式
        mLinearLayout.setDividerDrawable(ContextCompat.getDrawable(this, R.drawable.divider_vertical));


        mViewPager.setAdapter(mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return NotifytSubFragment.newInstance(NotifyActicity.this, tabs.get(position));
//                return DefaultFragment.instantiate(getContext(), "");
            }

            @Override
            public int getCount() {
                return tabs.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return tabs.get(position).getName();
            }

            @Override
            public void setPrimaryItem(ViewGroup container, int position, Object object) {
                super.setPrimaryItem(container, position, object);
                if (mCurFragment == null) {
                    commitUpdate();
                }
                mCurFragment = (Fragment) object;
            }

            //this is called when notifyDataSetChanged() is called
            @Override
            public int getItemPosition(Object object) {
                return PagerAdapter.POSITION_NONE;
            }

        });
        mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
                if (state == ViewPager.SCROLL_STATE_IDLE) {
                    mAdapter.commitUpdate();
                }
            }
        });
        mLayoutTab.setupWithViewPager(mViewPager);
        mLayoutTab.setSmoothScrollingEnabled(true);


        mAcitionMenuView.setOnMenuItemClickListener(new ActionMenuView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                return onOptionsItemSelected(item);
            }
        });

    }

    public static void show(Context context) {
        Intent intent = new Intent(context, NotifyActicity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.browser_menu , mAcitionMenuView.getMenu()); //将menu关联
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.public_menu_shared:
                SimplexToast.show(NotifyActicity.this, "add...");
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    protected int getContentView() {
        return R.layout.activity_notify_detail;
    }
}
