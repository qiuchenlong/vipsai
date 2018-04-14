package com.vs.vipsai.detail.activity;

import android.graphics.Color;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.TextHttpResponseHandler;
import com.vs.vipsai.AppContext;
import com.vs.vipsai.AppOperator;
import com.vs.vipsai.R;
import com.vs.vipsai.api.remote.VSApi;
import com.vs.vipsai.base.activities.BackActivity;
import com.vs.vipsai.bean.PlayerComment;
import com.vs.vipsai.bean.ResultBean;
import com.vs.vipsai.bean.Tweet;
import com.vs.vipsai.behavior.CommentBar;
import com.vs.vipsai.tweet.contract.TweetDetailContract;
import com.vs.vipsai.detail.viewpager.PlayerDetailViewPagerFragment;
import com.vs.vipsai.ui.videoplayer.SampleVideo;
import com.vs.vipsai.ui.videoplayer.SwitchVideoModel;
//import com.vs.vipsai.ui.fragment.PlayerDetailViewPagerFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cz.msebera.android.httpclient.Header;

/**
 * Author: cynid
 * Created on 3/21/18 4:41 PM
 * Description:
 *
 * 比赛详情页面
 */

public class PlayerDetailActivity extends BasePlayerDetailActivity {

    PlayerDetailViewPagerFragment mPagerFrag;


    @Override
    protected void initWidget() {
        super.initWidget();

        mTabLayout.addTab(mTabLayout.newTab().setText("评论"));
        mTabLayout.addTab(mTabLayout.newTab().setText("详情"));
        mTabLayout.addTab(mTabLayout.newTab().setText("奖金"));


        // viewpager fragment
        mPagerFrag = PlayerDetailViewPagerFragment.instantiate();
        mCmnViewImp = mPagerFrag.getCommentViewHandler();
//        mThumbupViewImp = mPagerFrag.getThumbupViewHandler();
        mAgencyViewImp = mPagerFrag.getAgencyViewHandler();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, mPagerFrag)
                .commit();

    }



    /**
     * 设置 评论栏 是否可见
     * @param position
     */
    @Override
    public void setCommentBarVisible(int position) {
        if (position == 0) {
            mDelegation.setVisible(View.VISIBLE);
        } else {
            mDelegation.setVisible(View.GONE);
        }
    }



}
