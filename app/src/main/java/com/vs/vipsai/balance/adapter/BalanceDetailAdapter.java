package com.vs.vipsai.balance.adapter;

import android.content.Context;
import android.graphics.Color;
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
import com.vs.vipsai.widget.xchart.CircleChart;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author: cynid
 * Created on 3/22/18 2:41 PM
 * Description:
 */

public class BalanceDetailAdapter extends BaseRecyclerAdapter {

    private static final int TYPE_TOP = 0x0000;
    private static final int TYPE_NORMAL= 0x0001;
    private View.OnClickListener onPortraitClickListener;

    List<Integer> datas = null;
    int[] paintColor;
    String[] pieString;
    String[] pieRangeString;

    public BalanceDetailAdapter(Context context) {
        super(context, NEITHER);

    }

    @Override
    protected RecyclerView.ViewHolder onCreateDefaultViewHolder(ViewGroup parent, int type) {
        return new BalanceDetailHolderView(LayoutInflater.from(mContext).inflate(R.layout.list_item_balance_detail_detail, parent, false));
    }

    @Override
    protected void onBindDefaultViewHolder(RecyclerView.ViewHolder holder, Object item, int position) {
        BalanceDetailHolderView h = (BalanceDetailHolderView) holder;

        h.tvName.setText("提出取现");


        if (getItemViewType(position) == TYPE_TOP) {
            // 第一行头的竖线不显示
            h.tvTopLine.setVisibility(View.INVISIBLE);
            // 字体颜色加深
//            h.tvDot.setBackgroundResource(R.drawable.timeline_dot_first);
        } else if (getItemViewType(position) == TYPE_NORMAL) {
            h.tvTopLine.setVisibility(View.VISIBLE);
            h.tvBottomLine.setVisibility(View.VISIBLE);
            h.tvDot.setBackgroundResource(R.drawable.timeline_dot_blue);
        }

        if (position == mItems.size() - 1) {
            h.tvBottomLine.setVisibility(View.INVISIBLE);
        }

    }



    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_TOP;
        }
        return TYPE_NORMAL;
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

    public static final class BalanceDetailHolderView extends RecyclerView.ViewHolder {

        @BindView(R.id.list_item_balance_detail_detail_action_name)
        TextView tvName;
        @BindView(R.id.tvTopLine)
        TextView tvTopLine;
        @BindView(R.id.tvDot)
        TextView tvDot;
        @BindView(R.id.tvBottomLine)
        TextView tvBottomLine;

        public BalanceDetailHolderView(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}
