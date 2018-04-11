package com.vs.vipsai.detail.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.graphics.PointF;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vs.vipsai.R;
import com.vs.vipsai.base.adapter.BaseRecyclerAdapter;
import com.vs.vipsai.bean.PlayerComment;
import com.vs.vipsai.widget.DivergeView;
import com.vs.vipsai.widget.comment.CommentsBean;
import com.vs.vipsai.widget.comment.CommentsView;
import com.vs.vipsai.widget.comment.UserBean;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author: cynid
 * Created on 3/21/18 5:48 PM
 * Description:
 */

public class PlayerCommentAdapter extends BaseRecyclerAdapter<PlayerComment> {

    private OnGoodClickListener mListener;

    public PlayerCommentAdapter(Context context) {
        super(context, ONLY_FOOTER);
    }

    @Override
    protected RecyclerView.ViewHolder onCreateDefaultViewHolder(ViewGroup parent, int type) {
        return new PlayerCommentHolderView(LayoutInflater.from(mContext).inflate(R.layout.list_item_player_comment, parent, false));
    }

    @Override
    protected void onBindDefaultViewHolder(RecyclerView.ViewHolder holder, PlayerComment item, final int position) {
        final PlayerCommentHolderView h = (PlayerCommentHolderView) holder;

        if (position == 0) {
            h.title.setVisibility(View.VISIBLE);
            h.comment_layout.setVisibility(View.GONE);
        } else {
            h.title.setVisibility(View.GONE);
            h.comment_layout.setVisibility(View.VISIBLE);
            h.tvName.setText("wechat");


            if (position % 2 == 0) {
                h.textContent.setText(item.getContent());

                h.viewComments.setList(getComments());
                h.viewComments.notifyDataSetChanged();

                if (getComments().size() > 0) {
                    h.btnSeeAllComments.setVisibility(View.VISIBLE);
                }


            }
        }



        h.btnGood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.OnGoodClickListener(v, position);
                }
            }
        });




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



        @BindView(R.id.list_item_player_comment_btn_good)
        LinearLayout btnGood;


        @BindView(R.id.list_item_player_comment_text_content)
        TextView textContent;
        @BindView(R.id.list_item_player_comment_view_comments)
        CommentsView viewComments;
        @BindView(R.id.list_item_player_comment_btn_see_all_comments)
        TextView btnSeeAllComments;


        public PlayerCommentHolderView(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }


    /**
     * 生成评论列表
     *
     * @return
     */
    public static List<CommentsBean> getComments() {
        List<CommentsBean> list = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            CommentsBean commentsBean = new CommentsBean();
            commentsBean.setCommentsId(i);
            commentsBean.setContent("zhe shi wo yong lai ce shi shu ru de wen ben nei rong " + i);
            if (i % 2 == 0) {
                UserBean replyUser = new UserBean();
                replyUser.setUserId(i + 30);
                replyUser.setUserName("用户" + replyUser.getUserId());
                commentsBean.setReplyUser(replyUser);
            }
            UserBean comUser = new UserBean();
            comUser.setUserId(i);
            comUser.setUserName("用户" + comUser.getUserId());

            commentsBean.setCommentsUser(comUser);

            list.add(commentsBean);
        }

        return list;
    }


    public interface OnGoodClickListener {
        void OnGoodClickListener(View view, int position);
    }

    public void setOnGoodClickListener(OnGoodClickListener onGoodClickListener) {
        this.mListener = onGoodClickListener;
    }

}
