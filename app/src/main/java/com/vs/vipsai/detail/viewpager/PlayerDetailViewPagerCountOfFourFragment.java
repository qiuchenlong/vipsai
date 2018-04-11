package com.vs.vipsai.detail.viewpager;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vs.vipsai.R;
import com.vs.vipsai.bean.PlayerComment;
import com.vs.vipsai.detail.activity.BasePlayerDetailActivity;
import com.vs.vipsai.detail.activity.PlayerDetailActivity;
import com.vs.vipsai.detail.activity.PlayerDetailCountOfFourActivity;
import com.vs.vipsai.detail.fragment.ListPlayerBonusFragment;
import com.vs.vipsai.detail.fragment.ListPlayerCommentFragment;
import com.vs.vipsai.detail.fragment.ListPlayerDetailFragment;
import com.vs.vipsai.detail.fragment.ListPlayerWorksFragment;
import com.vs.vipsai.tweet.contract.TweetDetailContract;

/**
 * Author: cynid
 * Created on 3/21/18 5:43 PM
 * Description:
 */

public class PlayerDetailViewPagerCountOfFourFragment extends Fragment implements TweetDetailContract.ICmnView, TweetDetailContract.IAgencyView, BasePlayerDetailActivity.onViewPagerSelectListener {

    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    protected FragmentStatePagerAdapter mAdapter;

    private TweetDetailContract.ICmnView mCmnViewImp;
    private TweetDetailContract.IThumbupView mThumbupViewImp;
    private TweetDetailContract.Operator mOperator;


    private BasePlayerDetailActivity activity;


    public static PlayerDetailViewPagerCountOfFourFragment instantiate() {
        return new PlayerDetailViewPagerCountOfFourFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mOperator = (TweetDetailContract.Operator) context;
    }


    public void setTabLayout(int visibility) {
        if (mTabLayout != null) {
            mTabLayout.setVisibility(visibility);
        }
    }

    public void setTabLayoutBackgroundColor(int color) {
        if (mTabLayout != null) {
            mTabLayout.setBackgroundColor(color);
        }
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_player_view_pager, container, false);
        mViewPager = (ViewPager) view.findViewById(R.id.view_pager);
        mTabLayout = (TabLayout) view.findViewById(R.id.tab_nav);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (mAdapter == null) {
            final ListPlayerDetailFragment mCmnFrag;
            mCmnFrag = ListPlayerDetailFragment.instantiate(); //mOperator, this   mThumbupViewImp =

            final ListPlayerCommentFragment mThumbupFrag;
            mCmnViewImp = mThumbupFrag = ListPlayerCommentFragment.instantiate(mOperator, this); //mOperator, this   mCmnViewImp =

            final ListPlayerBonusFragment bonusFragment;
            mCmnViewImp = bonusFragment = ListPlayerBonusFragment.instantiate(mOperator, this); //mOperator, this   mCmnViewImp =

            final ListPlayerWorksFragment worksFragment;
            mCmnViewImp = worksFragment = ListPlayerWorksFragment.instantiate(mOperator, this);


            mViewPager.setAdapter(mAdapter = new FragmentStatePagerAdapter(getChildFragmentManager()) {
                /**
                 * @param position
                 * @return
                 */
                @Override
                public Fragment getItem(int position) {
                    switch (position) {
                        case 0:
                            // 作品
                            return worksFragment;
                        case 1:
                            // 评论
                            return mThumbupFrag;
                        case 2:
                            // 详情
                            return mCmnFrag;
                        case 3:
                            // 奖金
                            return bonusFragment;
                    }
                    return null;
                }

                @Override
                public int getCount() {
                    return 4;
                }

                @Override
                public CharSequence getPageTitle(int position) {
                    switch (position) {
                        case 0:
                            return String.format("作品(%s)", 50);
                        case 1:
                            return String.format("评论(%s)", 10); //, mOperator.getTweetDetail().getLikeCount()
                        case 2:
                            return String.format("详情(%s)", 20); //, mOperator.getTweetDetail().getCommentCount()
                        case 3:
                            return String.format("奖金(%s)", 50);
                    }
                    return null;
                }
            });
            mTabLayout.setupWithViewPager(mViewPager);
            mViewPager.setCurrentItem(0);
        } else {
            mViewPager.setAdapter(mAdapter);
        }


//        viewpager获取当前item index
//        mViewPager.getCurrentItem();
        activity = (BasePlayerDetailActivity) getActivity();

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                ((TabLayout) activity.findViewById(R.id.activity_player_detail_tab_nav)).getTabAt(position).select();
                activity.setCommentBarVisible(position);
//                mTabLayout.getTabAt(position).select();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        activity.setOnViewPagerSelectListener(this);

    }


    @Override
    public void onCommentSuccess(PlayerComment comment) {
        if (mOperator == null)
            return;
        mOperator.getTweetDetail().setCommentCount(mOperator.getTweetDetail().getCommentCount() + 1); // Bean的事,真不是我想这样干
        if (mCmnViewImp != null) mCmnViewImp.onCommentSuccess(comment);
        TabLayout.Tab tab = mTabLayout.getTabAt(1);
        if (tab != null)
            tab.setText(String.format("评论(%s)", mOperator.getTweetDetail().getCommentCount()));
    }
//
//    @Override
//    public void onLikeSuccess(boolean isUp, User user) {
//        mOperator.getTweetDetail().setLikeCount(mOperator.getTweetDetail().getLikeCount() + (isUp ? 1 : -1));
//        if (mThumbupViewImp != null) mThumbupViewImp.onLikeSuccess(isUp, user);
//        TabLayout.Tab tab = mTabLayout.getTabAt(0);
//        if (tab != null)
//            tab.setText(String.format("赞(%s)", mOperator.getTweetDetail().getLikeCount()));
//    }

    @Override
    public void resetLikeCount(int count) {
        mOperator.getTweetDetail().setLikeCount(count);
        TabLayout.Tab tab = mTabLayout.getTabAt(0);
        if (tab != null) tab.setText(String.format("评论(%s)", count));
    }

    @Override
    public void resetCmnCount(int count) {
        mOperator.getTweetDetail().setCommentCount(count);
        TabLayout.Tab tab = mTabLayout.getTabAt(1);
        if (tab != null) tab.setText(String.format("详情(%s)", count));
    }

    public TweetDetailContract.ICmnView getCommentViewHandler() {
        return this;
    }

//    public TweetDetailContract.IThumbupView getThumbupViewHandler() {
//        return this;
//    }

    public TweetDetailContract.IAgencyView getAgencyViewHandler() {
        return this;
    }



    public void setTabLaoutShow() {
        mTabLayout.setVisibility(View.VISIBLE);
    }

    public void setTabLaoutDisplay() {
        mTabLayout.setVisibility(View.INVISIBLE);
    }





    @Override
    public void SetViewPagerSelectListener(int index) {
        mViewPager.setCurrentItem(index);
    }


//    public interface onTabLayoutItemSelectListener {
//        void SetTabLayoutItemSelectListener(int index);
//    }
//
//    public void setOnTabLayoutItemSelectListener(onTabLayoutItemSelectListener onTabLayoutItemSelectListener) {
//        this.mListener = onTabLayoutItemSelectListener;
//    }


}
