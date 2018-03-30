package com.vs.vipsai.publish.viewmodels;

import android.databinding.BindingAdapter;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;
import android.support.annotation.WorkerThread;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.TextHttpResponseHandler;
import com.vs.vipsai.AppOperator;
import com.vs.vipsai.R;
import com.vs.vipsai.bean.SubTab;
import com.vs.vipsai.main.SubFragment;
import com.vs.vipsai.util.TLog;
import com.vs.vipsai.widget.FragmentPagerAdapter;

import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * * Author: chends
 * Created on 3/28/18 5:33 PM
 * Description:
 *
 *  tablayout + viewpager model
 */
public abstract class VMTabFragment {

    private static final String Tag = VMTabFragment.class.getSimpleName();

    public ObservableArrayList<SubTab> tabList = new ObservableArrayList<SubTab>();
    public ObservableField<FragmentPagerAdapter> adapter = new ObservableField<>();
    public ObservableField<ViewPager> viewPager = new ObservableField<>();

    private TextHttpResponseHandler mResponseHandler = new TextHttpResponseHandler() {

        @Override
        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
            onRequestTabFailure(responseString);
        }

        @Override
        public void onSuccess(int statusCode, Header[] headers, String responseString) {
            onRequestTabSuccess();
        }

    };

    public VMTabFragment(FragmentManager fm, View root) {
        AppOperator.runOnThread(new Runnable() {
            @Override
            public void run() {
                List<SubTab> tabs = getTabConfig();
                if(tabs == null || tabs.isEmpty()) {
                    requestTabConfigFromServer(mResponseHandler);
                }else {
                    tabList.addAll(tabs);
                }
            }
        });

        adapter.set(new FragmentPagerAdapter(fm) {
            @Override
            public Fragment getItem(int position) {
                return SubFragment.newInstance(null, tabList.get(position));
            }

            @Override
            public int getCount() {
                return tabList.size();
            }

            @Override
            public void setPrimaryItem(ViewGroup container, int position, Object object) {
                super.setPrimaryItem(container, position, object);
                commitUpdate();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return tabList.get(position).getName();
            }

        });

        viewPager.set((ViewPager)root.findViewById(R.id.view_pager));
        viewPager.get().addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
                if (state == ViewPager.SCROLL_STATE_IDLE) {
                    adapter.get().commitUpdate();
                }
            }
        });
    }

    /**
     * 绑定TabLayout
     * @param tabs
     */
    @BindingAdapter({"tabs", "viewPager"})
    public static void bindTabLayout(TabLayout layout, List<SubTab> tabs, ViewPager viewPager) {
        for (SubTab tab : tabs) {
            layout.addTab(layout.newTab().setText(tab.getName()));
            TLog.i(Tag, "tab:" + tab.getName());
        }
        layout.setTabMode(tabs.size() > 5 ? TabLayout.MODE_SCROLLABLE : TabLayout.MODE_FIXED);
        layout.setTabGravity(tabs.size() > 5 ? TabLayout.GRAVITY_CENTER : TabLayout.GRAVITY_FILL);
        layout.setupWithViewPager(viewPager);
    }

    /**
     * 获取服务器配置失败
     * @param responseString
     */
    protected void onRequestTabFailure(String responseString) {
        TLog.w(Tag, responseString + "");
    }

    /**
     * 获取服务器配置成功
     */
    protected void onRequestTabSuccess(){
        TLog.i(Tag, "request success.");
    }

    /**
     * 本地tab配置
     * @return
     */
    protected abstract List<SubTab> getTabConfig();

    /**
     * 请求服务器tab 配置
     * @param responseHandler
     */
    protected void requestTabConfigFromServer(AsyncHttpResponseHandler responseHandler){};

}
