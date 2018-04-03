package com.vs.vipsai.balance.activity;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vs.vipsai.R;
import com.vs.vipsai.balance.fragment.AccountBalanceFragment;
import com.vs.vipsai.balance.fragment.VASHCoinFragment;
import com.vs.vipsai.base.activities.BackActivity;
import com.vs.vipsai.bean.News;
import com.vs.vipsai.bean.SubTab;
import com.vs.vipsai.emoji.OnSendClickListener;
import com.vs.vipsai.util.SimplexToast;
import com.vs.vipsai.widget.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Author: cynid
 * Created on 3/17/18 11:02 AM
 * Description:
 *
 * 帐户金额
 */

public class AccountBalanceActivity extends BackActivity implements OnSendClickListener {


    private TextView title;


    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tabs)
    TabLayout mLayoutTab;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;
    @BindView(R.id.activity_account_balacne_btn_menu)
    FrameLayout frameLayout;
    @BindView(R.id.activity_account_balacne_label_menu)
    TextView labelMenu;

    private Fragment mCurFragment;
    private FragmentPagerAdapter mAdapter;


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





    /**
     * 设置标题居中显示
     *
     * @param fragment
     */
    public void setTitleCenter(Fragment fragment) {
        Toolbar.LayoutParams layoutParams = new Toolbar.LayoutParams(Toolbar.LayoutParams.MATCH_PARENT, Toolbar.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0, 0, 0, 0);
        title.setLayoutParams(layoutParams);
    }


    /**
     * 设置标题颜色
     *
     * @param color
     */
    public void setTitleColorByColorId(int color) {
        title.setTextColor(color);
    }





    @Override
    protected void onActivityResult(int arg0, int arg1, Intent arg2) {
        super.onActivityResult(arg0, arg1, arg2);
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        setSwipeBackEnable(true);



        mLayoutTab.setTabGravity(TabLayout.GRAVITY_CENTER);

//        mLayoutTab.setPadding((int)TDevice.dp2px(86), 0, 0, 0 );

        final List<SubTab> tabs = new ArrayList<>();

        SubTab st = new SubTab();
        st.setName("余额");
//        st.setHref("https://www.oschina.net/action/apiv2/sub_list?token=d6112fa662bc4bf21084670a857fbd20");
        st.setSubtype(10);
        st.setToken("d6112fa662bc4bf21084670a857fbd20"); // cache key
        st.setType(News.TYPE_ACCOUNT_BALANCE); //3

        tabs.add(st);

        SubTab st1 = new SubTab();
        st1.setName("微币");
//        st1.setHref("https://www.oschina.net/action/apiv2/sub_list?token=df985be3c5d5449f8dfb47e06e098ef9");
        st1.setSubtype(4);
        st1.setToken("df985be3c5d5449f8dfb47e06e098ef9"); // cache key
        st1.setType(News.TYPE_VASH_COIN);

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

                if (position == 0) {
                    return AccountBalanceFragment.newInstance(AccountBalanceActivity.this, tabs.get(position));
                } else {
                    return VASHCoinFragment.newInstance(AccountBalanceActivity.this, tabs.get(position));
                }

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

                if (position == 0) {
                    labelMenu.setText("取现账号");
                    labelMenu.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            SimplexToast.show(AccountBalanceActivity.this, "111");
                        }
                    });
                } else {
                    labelMenu.setText("兑换码");
                    labelMenu.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            SimplexToast.show(AccountBalanceActivity.this, "222");
                        }
                    });
                }

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




    }

    @Override
    protected int getContentView() {
        return R.layout.activity_account_balance;
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
