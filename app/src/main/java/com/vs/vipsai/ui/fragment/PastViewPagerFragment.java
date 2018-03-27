package com.vs.vipsai.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.vs.vipsai.OnTabReselectListener;
import com.vs.vipsai.R;
import com.vs.vipsai.base.fragments.BaseGeneralListFragment;
import com.vs.vipsai.base.fragments.BaseGeneralRecyclerFragment;
import com.vs.vipsai.base.fragments.BaseViewPagerFragment;
import com.vs.vipsai.bean.SubTab;
import com.vs.vipsai.main.SubFragment;

/**
 * Author: cynid
 * Created on 3/14/18 9:30 AM
 * Description:
 */

public class PastViewPagerFragment extends BaseViewPagerFragment implements OnTabReselectListener {

    /**
     * @param catalog {@link PastFragment}
     * @return Bundle
     */
    private Bundle getBundle(int catalog) {
        Bundle bundle = new Bundle();
        bundle.putInt(PastFragment.BUNDLE_KEY_REQUEST_CATALOG, catalog);
        return bundle;
    }

    @Override
    public void onTabReselect() {

        if (mBaseViewPager != null) {
            BaseViewPagerAdapter pagerAdapter = (BaseViewPagerAdapter) mBaseViewPager.getAdapter();
            Fragment fragment = pagerAdapter.getCurFragment();
            if (fragment != null) {
                if (fragment instanceof BaseGeneralListFragment)
                    ((BaseGeneralListFragment) fragment).onTabReselect();
                else if (fragment instanceof BaseGeneralRecyclerFragment)
                    ((BaseGeneralRecyclerFragment) fragment).onTabReselect();
            }
        }
    }

    @Override
    protected PagerInfo[] getPagers() {
        SubTab tab = new SubTab();
        tab.setType(3);
        tab.setFixed(false);
        tab.setName("每日乱弹");
        tab.setNeedLogin(false);
        tab.setHref("https://www.oschina.net/action/apiv2/sub_list?token=263ee86f538884e70ee1ee50aed759b6");
        tab.setSubtype(5);
        tab.setToken("263ee86f538884e70ee1ee50aed759b6");

        Bundle bundle = new Bundle();
        bundle.putSerializable("sub_tab", tab);

        /*return new PagerInfo[]{
                new PagerInfo("最新动弹", TweetFragment.class,
                        getBundle(TweetFragment.CATALOG_NEW)),
                new PagerInfo("好友动弹", TweetFragment.class,
                        getBundle(TweetFragment.CATALOG_FRIENDS)),
                new PagerInfo("我的动弹", TweetFragment.class,
                        getBundle(TweetFragment.CATALOG_MYSELF)),
                new PagerInfo("热门动弹", TweetFragment.class,
                        getBundle(TweetFragment.CATALOG_HOT)),

        };*/
        return new PagerInfo[]{
//                new PagerInfo("推荐话题", TopicTweetFragment.class, null),
                new PagerInfo("最新动弹", PastFragment.class,
                        getBundle(PastFragment.CATALOG_NEW)),
                new PagerInfo("热门动弹", PastFragment.class,
                        getBundle(PastFragment.CATALOG_HOT)),
                new PagerInfo("每日乱弹", SubFragment.class,
                        bundle),
                new PagerInfo("我的动弹", PastFragment.class,
                        getBundle(PastFragment.CATALOG_MYSELF))

        };
    }

    @Override
    protected int getTitleRes() {
        return R.string.main_tab_name_past;
    }

    @Override
    protected int getIconRes() {
        return R.mipmap.btn_search_normal;
    }

    @Override
    protected View.OnClickListener getIconClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                SearchActivity.show(getContext());
            }
        };
    }

}
