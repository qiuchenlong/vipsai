package com.vs.vipsai.main.my;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vs.vipsai.R;
import com.vs.vipsai.base.adapter.BaseRecyclerAdapter;
import com.vs.vipsai.bean.SubBean;
import com.vs.vipsai.util.ImageUtils;
import com.vs.vipsai.util.TDevice;

/**
 * Author: cynid
 * Created on 3/15/18 11:43 AM
 * Description:
 *
 * 获奖卡片
 */

public class WinnerSubAdapter extends BaseRecyclerAdapter<SubBean> implements BaseRecyclerAdapter.OnLoadingHeaderCallBack {

    int[] bgResIds = {R.drawable.bg_winner_round_first, R.drawable.bg_winner_round_second,
            R.drawable.bg_winner_round_third, R.drawable.bg_winner_round_four };
    int[] icResIds = {R.mipmap.ic_winner_first, R.mipmap.ic_winner_second,
            R.mipmap.ic_winner_third, R.mipmap.ic_winner_four };


    public WinnerSubAdapter(Context context, int mode) {
        super(context, mode);
        setOnLoadingHeaderCallBack(this);
    }

    @Override
    protected RecyclerView.ViewHolder onCreateDefaultViewHolder(ViewGroup parent, int type) {
        return new MeViewHolder(mInflater.inflate(R.layout.item_list_sub_me_of_winner, parent, false));
    }

    @Override
    protected void onBindDefaultViewHolder(RecyclerView.ViewHolder holder, SubBean item, int position) {

        ViewGroup.LayoutParams params =  holder.itemView.getLayoutParams();//得到item的LayoutParams布局参数
        params.height = (int)TDevice.dp2px(250);//把高度赋予item布局
        holder.itemView.setLayoutParams(params);//把params设置给item布局


        MeViewHolder vh = (MeViewHolder) holder;

//        vh.tv_time.setText("2018.02.01");
//        vh.tv_title.setText("go go go ...");

        if (position == 0) {
            vh.iv_winner.setImageResource(R.mipmap.image_no_6);
//            vh.iv_winner.setImageBitmap(ImageUtils.decodeSampledBitmapFromResource(mContext.getResources(), R.mipmap.image_no_15, 100, 100));
            vh.tv_money.setText("奖金:$1,000");
        }else if (position == 1) {
            vh.iv_winner.setImageResource(R.mipmap.image_no_4);
//            vh.iv_winner.setImageBitmap(ImageUtils.decodeSampledBitmapFromResource(mContext.getResources(), R.mipmap.image_no_5, 100, 100));
            vh.tv_money.setText("奖金:$500");
        } else {
            vh.iv_winner.setImageResource(R.mipmap.image_no_7);
//            vh.iv_winner.setImageBitmap(ImageUtils.decodeSampledBitmapFromResource(mContext.getResources(), R.mipmap.image_no_9, 100, 100));
            vh.tv_money.setText("奖金:$100");
        }


//        BlogViewHolder vh = (BlogViewHolder) holder;
//        vh.tv_time.setText("2012-12-01 22:10");



        // reset stats
        FrameLayout.LayoutParams lpReset = new FrameLayout.LayoutParams((int)TDevice.dp2px(38), (int)TDevice.dp2px(22));
        lpReset.setMargins(0, (int)TDevice.dp2px(4), 0, 0);
        vh.ll_ranking.setLayoutParams(lpReset);

        vh.ll_ranking.getBackground().setAlpha(255);

        LinearLayout.LayoutParams lp2Reset = new LinearLayout.LayoutParams((int)TDevice.dp2px(16), (int)TDevice.dp2px(12));
        lp2Reset.setMargins((int)TDevice.dp2px(4), 0, 0, 0);
        vh.tv_icon.setLayoutParams(lp2Reset);

        vh.tv_icon.setBackground(null);
        vh.tv_icon.setText("");


        if (position >= 0 && position < 4) {
            vh.ll_ranking.setBackgroundResource(bgResIds[position]);

            vh.tv_icon.setBackgroundResource(icResIds[position]);
        } else if (position >= 4) {
            vh.ll_ranking.setBackgroundResource(R.drawable.bg_winner_round_four);

            vh.ll_ranking.getBackground().setAlpha(128);

            FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams((int)TDevice.dp2px(32), (int)TDevice.dp2px(22));
            lp.setMargins(0, (int)TDevice.dp2px(4), 0, 0);
            vh.ll_ranking.setLayoutParams(lp);

            LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams((int)TDevice.dp2px(12), (int)TDevice.dp2px(16));
            lp2.setMargins(0, 0, 0, 0);
            vh.tv_icon.setLayoutParams(lp2);

//            vh.tv_icon.setBackground(null);
            vh.tv_icon.setText("#");
        }


        vh.tv_ranking.setText(++position + "");




    }


    /**
     * header view
     * */
    @Override
    public RecyclerView.ViewHolder onCreateHeaderHolder(ViewGroup parent) {
        return new HeaderViewHolder(mHeaderView);
    }

    @Override
    public void onBindHeaderHolder(RecyclerView.ViewHolder holder, int position) {

    }





    private static class MeViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_winner;
        TextView tv_money;

        LinearLayout ll_ranking;
        TextView tv_icon;
        TextView tv_ranking;

        MeViewHolder(View itemView) {
            super(itemView);
            iv_winner = (ImageView) itemView.findViewById(R.id.item_list_sub_me_of_winner_imageview);
            tv_money = (TextView) itemView.findViewById(R.id.item_list_sub_me_of_winner_money);

            ll_ranking = itemView.findViewById(R.id.item_list_sub_me_of_winner_bg_ranking);
            tv_icon = itemView.findViewById(R.id.item_list_sub_me_of_winner_ic_ranking);
            tv_ranking = itemView.findViewById(R.id.item_list_sub_me_of_winner_text_ranking);
        }
    }


}
