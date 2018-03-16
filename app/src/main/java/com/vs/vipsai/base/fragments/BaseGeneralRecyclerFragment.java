package com.vs.vipsai.base.fragments;

import com.vs.vipsai.OnTabReselectListener;

/**
 * Author: cynid
 * Created on 3/13/18 2:47 PM
 * Description:
 */

public abstract class BaseGeneralRecyclerFragment<T> extends BaseRecyclerViewFragment<T> implements OnTabReselectListener {

    @Override
    public void onTabReselect() {
        if (mRecyclerView != null && !isRefreshing) {
            mRecyclerView.scrollToPosition(0);
            mRefreshLayout.setRefreshing(true);
            onRefreshing();
        }
    }

}
