package com.vs.vipsai.detail.activity;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.vs.vipsai.R;
import com.vs.vipsai.detail.viewpager.PlayerDetailViewPagerFragment;
import com.vs.vipsai.detail.viewpager.WorkDetailViewPagerFragment;
import com.vs.vipsai.tweet.contract.TweetDetailContract;
import com.vs.vipsai.util.SimplexToast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

//import com.vs.vipsai.ui.fragment.PlayerDetailViewPagerFragment;


/**
 * Author: cynid
 * Created on 3/21/18 4:41 PM
 * Description:
 *
 * 比赛详情页面
 */

public class WorkDetailActivity extends BasePlayerDetailActivity implements TweetDetailContract.Operator {

    WorkDetailViewPagerFragment mPagerFrag;

    private Context mContext;

    @BindView(R.id.activity_player_works_detail_recyclerview)
    RecyclerView mRecyclerView;

    private WorksDetailAdapter mAdapter;

    @BindView(R.id.activity_player_works_detail_btn_vote)
    Button mBtnVote;
    @BindView(R.id.activity_player_works_detail_btn_vote_add_1)
    Button mBtnVoteAdd1;
    @BindView(R.id.activity_player_works_detail_btn_vote_add_2)
    Button mBtnVoteAdd2;



    @Override
    protected int getContentView() {
        return R.layout.activity_player_works_detail;
    }

    @Override
    protected void initWidget() {
        super.initWidget();

        mTabLayout.addTab(mTabLayout.newTab().setText("评论"));
        mTabLayout.setVisibility(View.GONE);
//        mTabLayout.addTab(mTabLayout.newTab().setText("详情"));
//        mTabLayout.addTab(mTabLayout.newTab().setText("奖金"));


        // viewpager fragment
        mPagerFrag = WorkDetailViewPagerFragment.instantiate();
        mCmnViewImp = mPagerFrag.getCommentViewHandler();
//        mThumbupViewImp = mPagerFrag.getThumbupViewHandler();
        mAgencyViewImp = mPagerFrag.getAgencyViewHandler();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, mPagerFrag)
                .commit();



        initEvent();

    }


    @Override
    protected void initData() {
        super.initData();

        mContext = WorkDetailActivity.this;


        List<Map<String, String>> list = new ArrayList<>();
        for (int i=0 ; i<7; i++) {
            Map<String, String> map = new HashMap<>();
            map.put("title", "aaaa" + i);
            list.add(map);
        }

        mAdapter = new WorksDetailAdapter(mContext, list);
        mRecyclerView.setAdapter(mAdapter);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(mContext);
        mLinearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL); // 设置 recyclerview 布局方式为横向布局
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

    }

    private void initEvent() {
        mBtnVote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBtnVote.setVisibility(View.GONE);
                mBtnVoteAdd1.setVisibility(View.VISIBLE);
                mBtnVoteAdd2.setVisibility(View.VISIBLE);
            }
        });

        mBtnVoteAdd1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimplexToast.show(WorkDetailActivity.this, "vote 1");
                mBtnVote.setVisibility(View.VISIBLE);
                mBtnVoteAdd1.setVisibility(View.GONE);
                mBtnVoteAdd2.setVisibility(View.GONE);
            }
        });

        mBtnVoteAdd2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimplexToast.show(WorkDetailActivity.this, "vote 2");
                mBtnVote.setVisibility(View.VISIBLE);
                mBtnVoteAdd1.setVisibility(View.GONE);
                mBtnVoteAdd2.setVisibility(View.GONE);
            }
        });

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




    class WorksDetailAdapter extends RecyclerView.Adapter {

        private Context mContext;
        private List<Map<String, String>> mItem;

        public WorksDetailAdapter(Context context, List<Map<String, String>> list) {
            this.mContext = context;
            this.mItem = list;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.list_item_works_detail, parent, false));
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return mItem.size();
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


}
