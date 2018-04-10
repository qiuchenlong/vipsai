package com.vs.vipsai.detail.activity;

import android.view.View;

import com.vs.vipsai.R;
import com.vs.vipsai.detail.viewpager.KnockoutDetailViewPagerCountOfFiveFragment;
import com.vs.vipsai.detail.viewpager.PlayerDetailViewPagerFragment;
import com.vs.vipsai.tweet.contract.TweetDetailContract;

//import com.vs.vipsai.ui.fragment.PlayerDetailViewPagerFragment;


/**
 * Author: cynid
 * Created on 3/21/18 4:41 PM
 * Description:
 *
 * 淘汰赛详情页面
 */

public class KnockoutDetailActivity extends BasePlayerDetailActivity implements TweetDetailContract.Operator {

    KnockoutDetailViewPagerCountOfFiveFragment mPagerFrag;


    @Override
    protected int getContentView() {
        return R.layout.activity_player_knockout_detail;
    }

    @Override
    protected void initWidget() {
        super.initWidget();


        mDelegation.setVisible(View.GONE);


        mTabLayout.addTab(mTabLayout.newTab().setText("1对1"));
        mTabLayout.addTab(mTabLayout.newTab().setText("评论"));
        mTabLayout.addTab(mTabLayout.newTab().setText("详情"));
        mTabLayout.addTab(mTabLayout.newTab().setText("奖金"));
        mTabLayout.addTab(mTabLayout.newTab().setText("赛程"));



        // viewpager fragment
        mPagerFrag = KnockoutDetailViewPagerCountOfFiveFragment.instantiate();
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
        if (position == 1) {
            mDelegation.setVisible(View.VISIBLE);
        } else {
            mDelegation.setVisible(View.GONE);
        }
    }



}
