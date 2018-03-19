package com.vs.vipsai.search;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.androidkun.xtablayout.XTabLayout;
import com.vs.vipsai.R;
import com.vs.vipsai.base.activities.BaseActivity;
import com.vs.vipsai.base.adapter.BaseRecyclerAdapter;
import com.vs.vipsai.bean.News;
import com.vs.vipsai.bean.SubTab;
import com.vs.vipsai.search.adapters.SearchHistoryAdapter;
import com.vs.vipsai.util.CacheManager;
import com.vs.vipsai.util.DialogHelper;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Author: cynid
 * Created on 3/19/18 5:18 PM
 * Description:
 */

public class SearchActivity extends BaseActivity implements ViewPager.OnPageChangeListener {

    @BindView(R.id.view_root)
    LinearLayout mViewRoot;
    @BindView(R.id.layout_tab)
    TabLayout mLayoutTab;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;
    @BindView(R.id.view_searcher)
    SearchView mViewSearch;
    @BindView(R.id.search_mag_icon)
    ImageView mSearchIcon;
    @BindView(R.id.search_edit_frame)
    LinearLayout mLayoutEditFrame;
    @BindView(R.id.search_src_text)
    EditText mViewSearchEditor;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;


    @BindView(R.id.activity_search_tab)
    TabLayout search_tab;
    List<SubTab> tabs;


    private SearchHistoryAdapter mAdapter;
    private static boolean isMiUi = false;
    private static final String CACHE_NAME = "search_history";
    private List<Pair<String, Fragment>> mPagerItems;
    private String mSearchText;
    private Runnable mSearchRunnable = new Runnable() {
        @Override
        public void run() {
            if (TextUtils.isEmpty(mSearchText))
                return;
//            SearchAction f = (SearchAction) mPagerItems.get(mViewPager.getCurrentItem()).second;
//            f.search(mSearchText);
            SearchHistoryAdapter.SearchItem item = new SearchHistoryAdapter.SearchItem(mSearchText);
            if (mAdapter.getItems().contains(item)) {
                mAdapter.removeItem(item);
            }
            mAdapter.addItem(0, item);
            mRecyclerView.scrollToPosition(0);
            SearchHistoryAdapter.SearchItem last = mAdapter.getItem(mAdapter.getItems().size() - 1);
            if (last != null && last.getType() == 0) {
                mAdapter.addItem(new SearchHistoryAdapter.SearchItem("清空搜索历史", 1));
            }
        }
    };

    public static void show(Context context) {
        Intent intent = new Intent(context, SearchActivity.class);
        context.startActivity(intent);
    }



    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        // pass
    }

    @Override
    public void onPageSelected(int position) {
        String content = mSearchText;
        if (TextUtils.isEmpty(content)) return;
        doSearch(content);
        mViewSearch.clearFocus();
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        // pass
    }

    public interface SearchAction {
        void search(String content);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_v2_search;
    }

    @Override
    protected void initWindow() {
        mPagerItems = new ArrayList<>();

//        mPagerItems.add(new Pair<>("软件", SearchArticleFragment.instantiate(this, News.TYPE_SOFTWARE)));
//        mPagerItems.add(new Pair<>("博客", SearchArticleFragment.instantiate(this, News.TYPE_BLOG)));
//        mPagerItems.add(new Pair<>("资讯", SearchArticleFragment.instantiate(this, News.TYPE_NEWS)));
//        mPagerItems.add(new Pair<>("问答", SearchArticleFragment.instantiate(this, News.TYPE_QUESTION)));
//        mPagerItems.add(new Pair<>("找人", SearchUserFragment.instantiate(this)));

    }

    @Override
    protected void onPause() {
        mViewSearch.clearFocus();
        super.onPause();
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        setStatusBarDarkMode();
        mAdapter = new SearchHistoryAdapter(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
        List<SearchHistoryAdapter.SearchItem> items = CacheManager.readListJson(this, CACHE_NAME, SearchHistoryAdapter.SearchItem.class);
        mAdapter.addAll(items);
        if (mAdapter.getItems().size() != 0) {
            mAdapter.addItem(new SearchHistoryAdapter.SearchItem("清空搜索历史", 1));
        }
        mRecyclerView.setAnimation(null);
        mAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, long itemId) {
                SearchHistoryAdapter.SearchItem item = mAdapter.getItem(position);
                if (item != null && item.getType() == 0) {
                    mViewSearch.clearFocus();
                    String query = item.getSearchText();
                    mViewSearchEditor.setText(query);
                    mViewSearchEditor.setSelection(query.length());
                    doSearch(query);
                } else {
                    DialogHelper.getConfirmDialog(SearchActivity.this, "提示", "确认清空搜索历史记录吗？", "确认", "取消", true, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mAdapter.clear();
                        }
                    }).show();
                }
            }
        });
        mViewSearchEditor.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        mViewSearch.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                // 阻止点击关闭按钮 collapse icon
                return true;
            }
        });
        mViewSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mViewSearch.clearFocus();
                return doSearch(query);
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (TextUtils.isEmpty(newText)) {
                    doSearch("");
                }
                return false;
            }
        });

        mViewPager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mPagerItems.get(position).second;
            }

            @Override
            public int getCount() {
                return mPagerItems.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return mPagerItems.get(position).first;
            }
        });
        mViewPager.addOnPageChangeListener(this);
        mViewPager.setOffscreenPageLimit(0);
        mLayoutTab.setupWithViewPager(mViewPager);

        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mSearchIcon.getLayoutParams();
        params.width = ViewGroup.LayoutParams.WRAP_CONTENT;
        mSearchIcon.setLayoutParams(params);

        LinearLayout.LayoutParams params1 = (LinearLayout.LayoutParams) mLayoutEditFrame.getLayoutParams();
        params1.setMargins(0, 0, 0, 0);
        mLayoutEditFrame.setLayoutParams(params1);

        mViewSearch.post(new Runnable() {
            @Override
            public void run() {
//                TDevice.showSoftKeyboard(mViewSearch);
                mViewSearch.setIconified(false);
            }
        });



        List<SubTab> sts = new ArrayList<>();

        SubTab st1 = new SubTab();
        st1.setName("用户");
        sts.add(st1);

        SubTab st2 = new SubTab();
        st2.setName("比赛");
        sts.add(st2);

        SubTab st3 = new SubTab();
        st3.setName("作品");
        sts.add(st3);

        tabs = new ArrayList<>();
        tabs.addAll(sts);
        for (SubTab tab : tabs) {
            search_tab.addTab(search_tab.newTab().setText(tab.getName()));
        }
    }


    private boolean doSearch(String query) {
        mSearchText = query.trim();
        // Always cancel all request
        mViewPager.removeCallbacks(mSearchRunnable);
        // Search is'nt empty
        if (TextUtils.isEmpty(mSearchText)) {
            mLayoutTab.setVisibility(View.GONE);
            mViewPager.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);
            return false;
        }

        mLayoutTab.setVisibility(View.VISIBLE);
        mViewPager.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.GONE);

        // In this we delay 1 seconds
        mViewPager.postDelayed(mSearchRunnable, 0);
        return true;
    }

    @OnClick(R.id.tv_search)
    void onClickCancel() {
        //onBackPressed();
        doSearch(mViewSearch.getQuery().toString());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onDestroy() {
        SearchHistoryAdapter.SearchItem last = mAdapter.getItem(mAdapter.getItems().size() - 1);
        if (last != null && last.getType() != 0) {
            mAdapter.removeItem(last);
        }
        CacheManager.saveToJson(this, CACHE_NAME, mAdapter.getItems());
        super.onDestroy();
    }

    /**
     * 设置小米黑色状态栏字体
     *
     */
    @SuppressLint("PrivateApi")
    private void setMIUIStatusBarDarkMode() {
        if (isMiUi) {
            Class<? extends Window> clazz = getWindow().getClass();
            try {
                int darkModeFlag;
                Class<?> layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
                Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
                darkModeFlag = field.getInt(layoutParams);
                Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
                extraFlagField.invoke(getWindow(), darkModeFlag, darkModeFlag);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /*
     * 静态域，获取系统版本是否基于MIUI
     */

    static {
        try {
            @SuppressLint("PrivateApi") Class<?> sysClass = Class.forName("android.os.SystemProperties");
            Method getStringMethod = sysClass.getDeclaredMethod("get", String.class);
            String version = (String) getStringMethod.invoke(sysClass, "ro.miui.ui.version.name");
            isMiUi = version.compareTo("V6") >= 0 && !"V9".equalsIgnoreCase(version);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 设置魅族手机状态栏图标颜色风格
     * 可以用来判断是否为Flyme用户
     *
     * @param window 需要设置的窗口
     * @return boolean 成功执行返回true
     */
    public static boolean setMeizuDarkMode(Window window) {
        boolean result = false;
        if (Build.VERSION.SDK_INT >= 24) {
            return false;
        }
        if (window != null) {
            try {
                WindowManager.LayoutParams lp = window.getAttributes();
                Field darkFlag = WindowManager.LayoutParams.class
                        .getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
                Field meizuFlags = WindowManager.LayoutParams.class
                        .getDeclaredField("meizuFlags");
                darkFlag.setAccessible(true);
                meizuFlags.setAccessible(true);
                int bit = darkFlag.getInt(null);
                int value = meizuFlags.getInt(lp);
                value |= bit;
                meizuFlags.setInt(lp, value);
                window.setAttributes(lp);
                result = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    @SuppressLint("InlinedApi")
    private int getStatusBarLightMode() {
        int result = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (isMiUi) {
                result = 1;
            } else if (setMeizuDarkMode(getWindow())) {
                result = 2;
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                result = 3;
            }
        }
        return result;
    }


    @SuppressLint("InlinedApi")
    protected void setStatusBarDarkMode() {
        int type = getStatusBarLightMode();
        if (type == 1) {
            setMIUIStatusBarDarkMode();
        } else if (type == 2) {
            setMeizuDarkMode(getWindow());
        } else if (type == 3) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }

}
