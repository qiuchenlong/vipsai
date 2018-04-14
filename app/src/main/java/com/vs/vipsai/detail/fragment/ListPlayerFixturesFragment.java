package com.vs.vipsai.detail.fragment;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.TextHttpResponseHandler;
import com.vs.vipsai.AppConfig;
import com.vs.vipsai.AppOperator;
import com.vs.vipsai.R;
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
import com.vs.vipsai.util.SimplexToast;
import com.vs.vipsai.widget.MatchView;


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


//    @BindView(R.id.fragment_player_fixtures_webview)

    private ScrollView mScrollView;
    private LinearLayout mLinearLayout;
    private MatchView mMatchView;


    public static ListPlayerFixturesFragment instantiate(TweetDetailContract.Operator operator, TweetDetailContract.IAgencyView mAgencyView) { //
        ListPlayerFixturesFragment fragment = new ListPlayerFixturesFragment();
        fragment.mOperator = operator;
        fragment.mAgencyView = mAgencyView;
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_player_fixtures;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mOperator = (TweetDetailContract.Operator) context;
    }


    @Override
    protected void initWidget(View root) {
        super.initWidget(root);


//
//        FrameLayout mFrameLayout = (FrameLayout) root.getParent();
////        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 5000);
////        mFrameLayout.setLayoutParams(params);
//
//        Log.d("TAG", "" + mFrameLayout);
//

        int height = (120 + 80) * 16 / 2;

//        mScrollView = root.findViewById(R.id.fragment_player_fixtures_root_layout);
//        ViewGroup.LayoutParams mParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);
//        mScrollView.setLayoutParams(mParams);
//
////        mLinearLayout = root.findViewById(R.id.fragment_player_fixtures_linear_layout);
////        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, height);
////        mLinearLayout.setLayoutParams(params);
//
//
        mMatchView = root.findViewById(R.id.fragment_player_fixtures_match_view);
        LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, height);
        mMatchView.setLayoutParams(params2);


//        NestedScrollView nestedScrollView = root.findViewById(R.id.fragment_player_fixtures_mested_scroll_view);
//        nestedScrollView.setFillViewport(true);

    }

    @Override
    protected int getCommentOrder() {
        return 0;
    }


}
