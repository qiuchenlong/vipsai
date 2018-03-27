package com.vs.vipsai.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class PlayerBonusAdapter extends BaseRecyclerAdapter<PlayerComment> {

    public PlayerBonusAdapter(Context context) {
        super(context, ONLY_FOOTER);
    }

    @Override
    protected RecyclerView.ViewHolder onCreateDefaultViewHolder(ViewGroup parent, int type) {
        return new PlayerCommentHolderView(LayoutInflater.from(mContext).inflate(R.layout.list_item_player_bonus, parent, false));
    }

    @Override
    protected void onBindDefaultViewHolder(RecyclerView.ViewHolder holder, PlayerComment item, int position) {
        PlayerCommentHolderView h = (PlayerCommentHolderView) holder;

        h.tvName.setText("money : " + item.getContent());
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


        public PlayerCommentHolderView(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
