package com.vs.vipsai.base.fragments;

import com.vs.vipsai.OnTabReselectListener;

/**
 * Author: cynid
 * Created on 3/14/18 9:55 AM
 * Description:
 */

public abstract class BaseGeneralListFragment<T> extends BaseListFragment<T> implements OnTabReselectListener {

    @Override
    public void onTabReselect() {
        if (mListView != null) {
            mListView.setSelection(0);
            mRefreshLayout.setRefreshing(true);
            onRefreshing();
        }
    }

}
