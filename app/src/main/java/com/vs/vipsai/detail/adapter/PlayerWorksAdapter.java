package com.vs.vipsai.detail.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vs.vipsai.AppContext;
import com.vs.vipsai.R;
import com.vs.vipsai.base.adapter.BaseRecyclerAdapter;
import com.vs.vipsai.bean.PlayerComment;
import com.vs.vipsai.detail.activity.PlayerDetailCountOfFourActivity;
import com.vs.vipsai.detail.activity.WorkDetailActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author: cynid
 * Created on 3/21/18 5:48 PM
 * Description:
 */

public class PlayerWorksAdapter extends BaseRecyclerAdapter<PlayerComment> {

    public PlayerWorksAdapter(Context context) {
        super(context, ONLY_FOOTER);
    }

    @Override
    protected RecyclerView.ViewHolder onCreateDefaultViewHolder(ViewGroup parent, int type) {
        return new PlayerWorksHolderView(LayoutInflater.from(mContext).inflate(R.layout.list_item_player_works, parent, false));
    }

    @Override
    protected void onBindDefaultViewHolder(RecyclerView.ViewHolder holder, PlayerComment item, int position) {
        PlayerWorksHolderView h = (PlayerWorksHolderView) holder;

//        h.tvName.setText(item.getContent());


        if (position == 0) {
            h.titleWorks.setVisibility(View.VISIBLE);
            h.layoutWorks.setVisibility(View.GONE);
        } else if (position == 1) {
            h.titleWorks.setVisibility(View.INVISIBLE);
            h.layoutWorks.setVisibility(View.GONE);
        } else {
            h.titleWorks.setVisibility(View.GONE);
            h.layoutWorks.setVisibility(View.VISIBLE);
        }


        h.layoutWorks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AppContext.getContext(), WorkDetailActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                AppContext.getInstance().startActivity(intent);
            }
        });

    }

    public static final class PlayerWorksHolderView extends RecyclerView.ViewHolder {
//        @BindView(R.id.identityView)
//        public IdentityView identityView;
//        @BindView(R.id.iv_avatar)
//        public PortraitView ivPortrait;
//        @BindView(R.id.tv_name)
//        public TextView tvName;
//        @BindView(R.id.tv_pub_date)
//        public TextView tvTime;
//        @BindView(R.id.btn_comment)
//        public ImageView btnReply;
//        @BindView(R.id.tv_content)
//        public TweetTextView tvContent;

        @BindView(R.id.list_item_play_works_title)
        TextView titleWorks;
        @BindView(R.id.list_item_play_works_layout)
        LinearLayout layoutWorks;



        public PlayerWorksHolderView(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
