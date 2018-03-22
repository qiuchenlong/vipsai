package com.vs.vipsai.ui.fragment;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.gson.reflect.TypeToken;
import com.vs.vipsai.adapter.TweetLikeUsersAdapter;
import com.vs.vipsai.api.remote.VSApi;
import com.vs.vipsai.base.adapter.BaseRecyclerAdapter;
import com.vs.vipsai.base.fragments.BaseRecyclerViewFragment;
import com.vs.vipsai.bean.PageBean;
import com.vs.vipsai.bean.ResultBean;
import com.vs.vipsai.bean.TweetLike;
import com.vs.vipsai.bean.User;
import com.vs.vipsai.tweet.contract.TweetDetailContract;

import java.lang.reflect.Type;

/**
 * Author: cynid
 * Created on 3/22/18 2:38 PM
 * Description:
 */

public class ListTweetLikeUsersFragment extends BaseRecyclerViewFragment<TweetLike> implements TweetDetailContract.IThumbupView {

    private TweetDetailContract.Operator mOperator;
    private TweetDetailContract.IAgencyView mAgencyView;

    public static ListTweetLikeUsersFragment instantiate(TweetDetailContract.Operator operator, TweetDetailContract.IAgencyView mAgencyView) {
        ListTweetLikeUsersFragment fragment = new ListTweetLikeUsersFragment();
        fragment.mOperator = operator;
        fragment.mAgencyView = mAgencyView;
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mOperator = (TweetDetailContract.Operator) context;
    }

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                    mOperator.onScroll();
                }
            }
        });
    }

    @Override
    protected BaseRecyclerAdapter<TweetLike> getRecyclerAdapter() {
        return new TweetLikeUsersAdapter(getContext());
    }

    @Override
    protected Type getType() {
        return new TypeToken<ResultBean<PageBean<TweetLike>>>() {
        }.getType();
    }

    @Override
    protected boolean isNeedCache() {
        return false;
    }

    @Override
    protected boolean isNeedEmptyView() {
        return false;
    }

    @Override
    protected void requestData() {
        String token = isRefreshing ? null : mBean.getNextPageToken();
        VSApi.getTweetLikeList(16768658, token, mHandler); //mOperator.getTweetDetail().getId()
    }

    @Override
    protected void onRequestSuccess(int code) {
        super.onRequestSuccess(code);
        if (mAdapter.getCount() < 20 && mAgencyView != null)
            mAgencyView.resetLikeCount(mAdapter.getCount());
    }

    @Override
    public void onItemClick(int position, long itemId) {
//        super.onItemClick(position, itemId);
//        TweetLike liker = mAdapter.getItem(position);
//        if (liker == null) return;
//        UIHelper.showUserCenter(getContext(), liker.getAuthor().getId(), liker.getAuthor().getName());
    }

    @Override
    public void onLikeSuccess(boolean isUp, User user) {
        onRefreshing();
    }

}
