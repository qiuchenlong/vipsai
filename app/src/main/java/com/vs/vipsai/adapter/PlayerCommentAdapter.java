package com.vs.vipsai.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vs.vipsai.R;
import com.vs.vipsai.base.adapter.BaseRecyclerAdapter;
import com.vs.vipsai.bean.PlayerComment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author: cynid
 * Created on 3/21/18 5:48 PM
 * Description:
 */

public class PlayerCommentAdapter extends BaseRecyclerAdapter<PlayerComment> {

    public PlayerCommentAdapter(Context context) {
        super(context, ONLY_FOOTER);
    }

    @Override
    protected RecyclerView.ViewHolder onCreateDefaultViewHolder(ViewGroup parent, int type) {
        return new PlayerCommentHolderView(LayoutInflater.from(mContext).inflate(R.layout.list_item_player_comment, parent, false));
    }

    @Override
    protected void onBindDefaultViewHolder(RecyclerView.ViewHolder holder, PlayerComment item, int position) {
        PlayerCommentHolderView h = (PlayerCommentHolderView) holder;

        if (position == 0) {
            h.title.setVisibility(View.VISIBLE);
            h.comment_layout.setVisibility(View.GONE);
        } else {
            h.title.setVisibility(View.GONE);
            h.comment_layout.setVisibility(View.VISIBLE);
            h.tvName.setText(item.getContent());
        }
    }

    public static final class PlayerCommentHolderView extends RecyclerView.ViewHolder {
//        @BindView(R.id.identityView)
//        public IdentityView identityView;
//        @BindView(R.id.iv_avatar)
//        public PortraitView ivPortrait;
        @BindView(R.id.tv_name)
        public TextView tvName;
//        @BindView(R.id.tv_pub_date)
//        public TextView tvTime;
//        @BindView(R.id.btn_comment)
//        public ImageView btnReply;
//        @BindView(R.id.tv_content)
//        public TweetTextView tvContent;
        @BindView(R.id.list_item_play_comment_title)
        TextView title;
        @BindView(R.id.list_item_play_comment_layout)
        LinearLayout comment_layout;

        public PlayerCommentHolderView(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
