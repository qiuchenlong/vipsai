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
import com.vs.vipsai.bean.TweetLike;
import com.vs.vipsai.util.UIHelper;
import com.vs.vipsai.widget.IdentityView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author: cynid
 * Created on 3/22/18 2:41 PM
 * Description:
 */

public class PlayerDetailAdapter extends BaseRecyclerAdapter<TweetLike> {

    private View.OnClickListener onPortraitClickListener;

    public PlayerDetailAdapter(Context context) {
        super(context, ONLY_FOOTER);
    }

    @Override
    protected RecyclerView.ViewHolder onCreateDefaultViewHolder(ViewGroup parent, int type) {
        return new LikeUsersHolderView(LayoutInflater.from(mContext).inflate(R.layout.list_item_player_detail, parent, false));
    }

    @Override
    protected void onBindDefaultViewHolder(RecyclerView.ViewHolder holder, TweetLike item, int position) {
        LikeUsersHolderView h = (LikeUsersHolderView) holder;
//        h.identityView.setup(item.getAuthor());
//        h.ivPortrait.setup(item.getAuthor());
//        h.ivPortrait.setTag(R.id.iv_tag, item);
//        h.ivPortrait.setOnClickListener(getOnPortraitClickListener());
         //item.getAuthor().getName()


        if (position == 0) {
            h.headerLayout.setVisibility(View.VISIBLE);
            h.timeLineLayout.setVisibility(View.GONE);
        } else {
            h.headerLayout.setVisibility(View.GONE);
            h.timeLineLayout.setVisibility(View.VISIBLE);

            h.tvName.setText("陈赤赤");
        }


    }

    private View.OnClickListener getOnPortraitClickListener() {
        if (onPortraitClickListener == null) {
            onPortraitClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Object object = v.getTag(R.id.iv_tag);
                    if (object != null && object instanceof TweetLike) {
                        TweetLike liker = (TweetLike) object;
                        UIHelper.showUserCenter(mContext, liker.getAuthor().getId(), liker.getAuthor().getName());
                    }
                }
            };
        }
        return onPortraitClickListener;
    }

    public static final class LikeUsersHolderView extends RecyclerView.ViewHolder {
//        @BindView(R.id.identityView)
//        IdentityView identityView;
//        @BindView(R.id.iv_avatar)
//        PortraitView ivPortrait;
        @BindView(R.id.item_list_sub_me_time)
        TextView tvName;

        @BindView(R.id.list_item_player_detail_header_layout)
        LinearLayout headerLayout;
        @BindView(R.id.list_item_player_detail_time_line_layout)
        LinearLayout timeLineLayout;



        public LikeUsersHolderView(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}
