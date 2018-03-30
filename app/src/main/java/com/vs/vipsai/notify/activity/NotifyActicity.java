package com.vs.vipsai.notify.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.vs.vipsai.R;
import com.vs.vipsai.base.activities.BackActivity;
import com.vs.vipsai.bean.News;
import com.vs.vipsai.bean.SubTab;
import com.vs.vipsai.notify.NotifytSubFragment;
import com.vs.vipsai.util.SimplexToast;
import com.vs.vipsai.util.TDevice;
import com.vs.vipsai.widget.FragmentPagerAdapter;
import com.vs.vipsai.widget.popupwindow.BlurPopupWindow;
import com.vs.vipsai.widget.popupwindow.PopupListAdapter;
import com.vs.vipsai.widget.popupwindow.PopupListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.vs.vipsai.widget.popupwindow.BlurPopupWindow.KEYWORD_LOCATION_CLICK;
import static com.vs.vipsai.widget.popupwindow.BlurPopupWindow.KEYWORD_LOCATION_TOP;

/**
 * Author: cynid
 * Created on 3/20/18 10:10 AM
 * Description:
 *
 * 消息 私信 界面
 */

public class NotifyActicity extends BackActivity implements ViewPager.OnPageChangeListener {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tabs)
    TabLayout mLayoutTab;
//    @BindView(R.id.actionMV)
//    ActionMenuView mAcitionMenuView;
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

//        AppCompatImageButton aib = (AppCompatImageButton) mToolbar.getChildAt(2);
//        int mWith = aib.getDrawable().getMinimumWidth();
//
//        if (mWith > 48) { // simple slove problem
//            mWith = -mWith;
//        }
//
//
//        Log.d("NotifyActivity", " " + TDevice.getScreenWidth());
//        Log.d("NotifyActivity", " " + mToolbar.getWidth());
//        Log.d("NotifyActivity", " " + aib.getDrawable().getMinimumWidth());
//        Log.d("NotifyActivity", " " + mToolbar.getMeasuredWidth());
//        Log.d("NotifyActivity", " " + mToolbar.getContentInsetEndWithActions());


//        Toolbar.LayoutParams lp = new Toolbar.LayoutParams(Toolbar.LayoutParams.WRAP_CONTENT, Toolbar.LayoutParams.WRAP_CONTENT);
//        lp.setMargins((int)TDevice.px2dp(TDevice.getScreenWidth()/2.0f - mWith - lp.width/2), 0, 0, 0);
//        mLayoutTab.setLayoutParams(lp);




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


        // set current index
        int index = getIntent().getIntExtra("current_item", 0);
        mViewPager.setCurrentItem(index);


//        mAcitionMenuView.setOnMenuItemClickListener(new ActionMenuView.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem item) {
//                return onOptionsItemSelected(item);
//            }
//        });


        initPopupWindow(NotifyActicity.this);
    }

    public static void show(Context context, int index) {
        Intent intent = new Intent(context, NotifyActicity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("current_item", index);
        context.startActivity(intent);
    }

    public static void show(Context context) {
        Intent intent = new Intent(context, NotifyActicity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.notify_add_menu , menu); //将menu关联 mAcitionMenuView.getMenu()
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.public_menu_add:

                View menuView = findViewById(R.id.public_menu_add);
                blurPopupWindow.displayPopupWindow(menuView);

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


    private BlurPopupWindow blurPopupWindow;

    private void initPopupWindow(Activity context) {
        final List<Map<String, Object>> list_popup = new ArrayList<>();
        final PopupListView lv_popup = new PopupListView(context);


        String[] titles = new String[]{"发私信", "广告信息", "黑名单", "已删除"};
        int[] images = new int[]{R.mipmap.ic_trash_gray, R.mipmap.ic_ad_gray, R.mipmap.ic_blacklist_gray, R.mipmap.ic_trash_gray};


        for (int i=0; i<titles.length; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("title", titles[i]);
            map.put("image", images[i]);
            list_popup.add(map);
        }


        lv_popup.setDivider(new ColorDrawable(Color.GRAY));
        lv_popup.setDividerHeight(1);
        lv_popup.setAdapter(new PopupListAdapter(context, list_popup));
        lv_popup.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(blurPopupWindow != null) {
                    blurPopupWindow.dismissPopupWindow();
                }
            }
        });



//        ImageView iv_popup_top = new ImageView(context);
//        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams
//                .MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        iv_popup_top.setLayoutParams(params);
        blurPopupWindow = new BlurPopupWindow(context, lv_popup);
        blurPopupWindow.setOnPopupStateListener(new BlurPopupWindow.OnPopupStateListener() {
            @Override
            public void onDisplay(boolean isDisplay) {
//                TitleBar.this.isDisplay = isDisplay;
            }

            @Override
            public void onDismiss(boolean isDisplay) {
//                TitleBar.this.isDisplay = isDisplay;
//                dismissAnim();
            }
        });
    }

}
