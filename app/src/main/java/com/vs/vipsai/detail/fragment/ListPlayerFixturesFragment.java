package com.vs.vipsai.detail.fragment;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.TextHttpResponseHandler;
import com.vs.vipsai.AppConfig;
import com.vs.vipsai.AppOperator;
import com.vs.vipsai.api.remote.VSApi;
import com.vs.vipsai.base.adapter.BaseRecyclerAdapter;
import com.vs.vipsai.base.fragments.BaseRecyclerViewFragment;
import com.vs.vipsai.bean.PageBean;
import com.vs.vipsai.bean.PlayerComment;
import com.vs.vipsai.bean.ResultBean;
import com.vs.vipsai.detail.DetailFragment;
import com.vs.vipsai.detail.adapter.PlayerWorksAdapter;
import com.vs.vipsai.tweet.contract.TweetDetailContract;
import com.vs.vipsai.ui.empty.EmptyLayout;
import com.vs.vipsai.ui.fragment.ReactFragment;
import com.vs.vipsai.util.SimplexToast;

import java.lang.reflect.Type;

import cz.msebera.android.httpclient.Header;

/**
 * Author: cynid
 * Created on 3/21/18 5:45 PM
 * Description:
 *
 * 赛程
 */

public class ListPlayerFixturesFragment extends DetailFragment { //PlayerComment

    private TweetDetailContract.Operator mOperator;
    private TweetDetailContract.IAgencyView mAgencyView;
    private int mDeleteIndex = 0;

    public static ListPlayerFixturesFragment instantiate(TweetDetailContract.Operator operator, TweetDetailContract.IAgencyView mAgencyView) { //
        ListPlayerFixturesFragment fragment = new ListPlayerFixturesFragment();
        fragment.mOperator = operator;
        fragment.mAgencyView = mAgencyView;
        return fragment;
    }

//    @Override
//    public String getMainComponentName() {
//        return "App";
//    }

//    @Override
//    public String getMainComponentName() {
//        return "ReactNativeProject";
//    }


//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        mOperator = (TweetDetailContract.Operator) context;
//    }


    @Override
    protected void initWidget(View root) {
        super.initWidget(root);
    }

    @Override
    protected int getCommentOrder() {
        return 0;
    }


}
